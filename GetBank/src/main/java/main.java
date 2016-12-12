import RabbitSuperClass.PublishConsume;
import com.rabbitmq.client.*;
import com.ws.GetBanksImplementation;
import com.ws.GetBanksInterface;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;


public class main extends PublishConsume {


    private static String QUEUE_NAME = "GetBanks";
    private static String BINDING_KEY = "GetBanks";
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


                System.out.println("\t--> Recieved message from CreditScore..");
                Thread.sleep(1000);
                System.out.println();
                com.ws.GetBanksImplementation lS = new GetBanksImplementation();
                Thread.sleep(2000);
                boolean[] ser = lS.GetBanks(customer.getSSN(),customer.getLoanAmount(), customer.getLoanDuration(), customer.getCreditScore());
                Thread.sleep(2000);
                System.out.println(Arrays.toString(ser));
                customer.setBanks(ser);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (customer != null){
            publishMessage(customer);
        } else {
            throw new NullPointerException("Customer cannot be null");
        }


    }

    private static void publishMessage(Customer customer) {
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

            System.out.printf("Sent: '%1s' ", message);

            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
