import RabbitSuperClass.PublishConsume;
import com.rabbitmq.client.*;
import com.ws.RuleBaseImplementation;

import java.io.IOException;
import java.util.Arrays;


public class main {


    private static String QUEUE_NAME = "MessagingTranslator";
    private static String BINDING_KEY = "MessagingTranslator";
    private static String EXCHANGE_NAME = "aaInternal";
    
    private static ConnectionFactory factory;
    private static Connection connection;
    private static Channel channel;
    private static Customer customer;



    public static void main(String[] args) throws IOException, InterruptedException {
        consumeMessage(EXCHANGE_NAME, QUEUE_NAME, BINDING_KEY);
        
        
    }
    
    public static void consumeMessage(String exchangeName, String queueName, String bindingKey)
    {
        factory = new ConnectionFactory();
        try {
            factory.setHost("188.166.29.160");
            factory.setUsername("admin");
            factory.setPassword("admin");
            connection= factory.newConnection();
            channel = connection.createChannel();
            
            channel.queueDeclare(queueName, true, false, false, null);
            System.out.println(" [*] GetBank Waiting for Messages from Credit Score...");
            channel.queueBind(queueName, exchangeName, bindingKey);
            
            Consumer consumer = new DefaultConsumer(channel) {
                
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    
                    try {
                        getDataFromMessage(message);
                    }  finally {
                        if (message == "") {
                            channel.close();
                            connection.close();
                            throw new NullPointerException("\t\t Data invalid could not enrich data");
                        }
                        
                    }
                    
                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getDataFromMessage(String message){
        if (message != null || message != "") {
            customer = new Customer(message);
            try {
                System.out.println("\t--> Received message from CreditScore..");
                System.out.println("\t---> Validating Data...");
                Thread.sleep(1000);
                RuleBaseImplementation ruleBaseService = new RuleBaseImplementation();
                Thread.sleep(2000);
                System.out.println("\t----> Requesting Rule base service...");
                boolean[] ser = ruleBaseService.GetBanks(customer.getSSN(),customer.getLoanAmount(), customer.getLoanDuration(), customer.getCreditScore());
                Thread.sleep(2000);
                if (ser != null) {
                    System.out.printf("\t-----> Response: 200 Ok \t(");
                    customer.setBanks(ser);
                    System.out.printf("(True = accepted) BankXML: %1s, BankJSON: %2s, BankWS: %3s, BankMSG: %4s )\n",ser[0],ser[1],ser[2],ser[3]);
                } else {
                    System.out.println("\t------> Response: 406 Not acceptable");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (customer != null){
            publishMessageToRecipientList(customer);
        } else {
            throw new NullPointerException("Customer cannot be null");
        }


    }

    private static void publishMessageToRecipientList(Customer customer) {
        try {
            byte[] message = customer.toString().getBytes();
            factory = new ConnectionFactory();
            factory.setHost("188.166.29.160");
            factory.setUsername("admin");
            factory.setPassword("admin");

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);

            channel.basicPublish(EXCHANGE_NAME, "RecipientList", null, message);
    
            String msgStr = new String(message, "UTF-8");
            System.out.println("\t------> Sent: '"+ msgStr+"'");
            System.out.println("========================================================================================");
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
