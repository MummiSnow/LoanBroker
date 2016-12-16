import RabbitSuperClass.PublishConsume;

import java.io.IOException;

import com.rabbitmq.client.*;
import org.bank.credit.web.service.*;


public class main extends PublishConsume {

    private static String SEND_QUEUE = "GetBanks";
    private static String QUEUE_NAME = "CreditScore";
    private static String BINDING_KEY = "CreditScore";
    private static String EXCHANGE_NAME = "aaInternal";
    
    private static ConnectionFactory factory;
    private static Connection connection;
    private static Channel channel;
    private static Customer customer;
    
    private static CreditScoreService_Service cs;
    private static CreditScoreService score;
    
    public static void main(String[] args) throws IOException, InterruptedException {
        
        cs = new CreditScoreService_Service();
        score = cs.getCreditScoreServicePort();
        consumeMessage(EXCHANGE_NAME,QUEUE_NAME, BINDING_KEY);
        
        
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
            System.out.println(" [*] CreditScore Waiting for messages from LoanRequest...");
            System.out.println("========================================================================================");
    
            channel.queueBind(queueName, exchangeName, bindingKey);
            
            Consumer consumer = new DefaultConsumer(channel) {
                
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    
                    try {
                        getDataFromMessage(message);
                    }  finally {
                        if (message == null) {
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
    
    public static void getDataFromMessage(String message){
        if (message != null || message != "") {
            customer = new Customer(message);
            try {
                System.out.println("\t--> Recieved message from LoanRequest Validating and Enriching Data...");
                System.out.println("\t---> Validating and Enriching Data...");
                Thread.sleep(1000);
                System.out.printf("\t----> Id:%1s, SSN:%2s, LoanAmount:%3d, LoanDuration:%4d Months \n",customer.getId(), customer.getSSN(),
						customer.getLoanAmount(),
						customer.getLoanDuration());
                System.out.println("\t-----> Requesting Credit Score Bureau service... ");
                Thread.sleep(2000);
                customer.setCreditScore(score.creditScore(customer.getSSN()));
                System.out.println("\t------> Customer Credit Score: "+customer.getCreditScore());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if (customer != null){
            publishMessageToGetBanks(customer);
        } else {
            throw new NullPointerException("Customer cannot be null");
        }
    }
    
    public static void publishMessageToGetBanks(Customer customer) {
        try {
            byte[] message = customer.toString().getBytes();
            factory = new ConnectionFactory();
            factory.setHost("188.166.29.160");
            factory.setUsername("admin");
            factory.setPassword("admin");
        
            connection = factory.newConnection();
            channel = connection.createChannel();
        
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
        
            channel.basicPublish(EXCHANGE_NAME, SEND_QUEUE, null, message);
        
            String msgStr = new String(message, "UTF-8");
            System.out.printf("\t-------> Sent: '%1s'\n", msgStr);
            System.out.println("========================================================================================");
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
