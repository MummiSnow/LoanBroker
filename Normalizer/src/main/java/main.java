import RabbitSuperClass.PublishConsume;
import com.rabbitmq.client.*;

import java.io.IOException;

public class main extends PublishConsume
{
    private static String SEND_QUEUE = "Aggregator";
    
    
    private static String EXCHANGE_NAME_MSG = "aa.RabbitMQBank";
    private static String QUEUE_NAME_MSG = "aaExtFromMsgBank";
    private static String BINDING_KEY_MSG = "aaExtFromMsgBank";
    
    private static String EXCHANGE_NAME_XML = "cphbusiness.bankXML";
    private static String QUEUE_NAME_XML = "aaExtFromXmlBank";
    
    
    
    private static ConnectionFactory factory;
    private static Connection connection;
    private static Channel channel;

    public static void main(String[] args) {
        System.out.println(" [*] Normalizer Waiting for messages from Banks...");
        consumeMessagesFromMessagingBank(EXCHANGE_NAME_MSG, QUEUE_NAME_MSG, BINDING_KEY_MSG);
        consumeMessagesFromXMLBank(QUEUE_NAME_XML);
    }
    
    
    
    public static void consumeMessagesFromXMLBank(String queueName)
    {
        factory = new ConnectionFactory();
        try {
            factory.setHost("datdb.cphbusiness.dk");
            factory.setUsername("aa");
            factory.setPassword("aa");
            connection = factory.newConnection();
            channel = connection.createChannel();
            
            channel.queueDeclare(queueName, true, false, false, null);
            
            Consumer consumer = new DefaultConsumer(channel) {
                
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    
                    try {
                        getDataFromXMLBank(message);
                    }  finally {
                        
                    }
                    
                }
            };
            
            channel.basicConsume(queueName, true, consumer);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void getDataFromXMLBank(String message){
        LoanResponse lR;
        if (message != null || message != "") {
            try {
                System.out.println("\t--> Received message from XML Bank..");
                System.out.println("\t---> Validating and Normalizing Data");
                lR = new LoanResponse().parseXMLToObject(message);
                System.out.println("\t----> "+ lR.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("========================================================================================");
    }
    
    
    public static void consumeMessagesFromMessagingBank(String exchangeName, String queueName, String bindingKey)
    {
        factory = new ConnectionFactory();
        try {
            factory.setHost("datdb.cphbusiness.dk");
            factory.setUsername("aa");
            factory.setPassword("aa");
            connection = factory.newConnection();
            channel = connection.createChannel();
            
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, bindingKey);
            
            Consumer consumer = new DefaultConsumer(channel) {
                
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    
                    try {
                        getDataFromMessagingBank(message);
                    }  finally {
                        
                    }
                    
                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void getDataFromMessagingBank(String message){
        LoanResponse lR;
        try {
			System.out.println("\t--> Received message from Messaging Bank..");
			System.out.println("\t---> Validating and Normalizing Data");
			lR = new LoanResponse().parseJSONToObject(message);
			System.out.println("\t----> "+ lR.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println("========================================================================================");
    }
    
}
