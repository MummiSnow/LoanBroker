import RabbitSuperClass.PublishConsume;
import com.rabbitmq.client.*;

import java.io.IOException;

public class main extends PublishConsume
{
    private static String BINDING_KEY = "Aggregator";
    private static String EXCHANGE_NAME = "aaInternal";
    
    private static String EXCHANGE_NAME_MSG = "aa.RabbitMQBank";
    private static String QUEUE_NAME_MSG = "aaExtFromMsgBank";
    private static String BINDING_KEY_MSG = "aaExtFromMsgBank";
    
    private static String EXCHANGE_NAME_XML = "cphbusiness.bankXML";
    private static String QUEUE_NAME_XML = "aaExtFromXmlBank";
	
	private static String QUEUE_NAME_WS = "aaExtFromWSBank";
	private static String BINDING_KEY_WS = "aaExtFromWSBank";
	private static String EXCHANGE_NAME_WS = "aaExtFromWSBank";
	
	private static String QUEUE_NAME_JSON = "aaExtFromJSONBank";
    
	
    private static ConnectionFactory factory;
    private static Connection connection;
    private static Channel channel;

    public static void main(String[] args) {
        System.out.println(" [*] Normalizer Waiting for messages from Banks...");
		consumeMessagesFromWSBank(EXCHANGE_NAME_WS, QUEUE_NAME_WS, BINDING_KEY_WS);
        consumeMessagesFromMessagingBank(EXCHANGE_NAME_MSG, QUEUE_NAME_MSG, BINDING_KEY_MSG);
        consumeMessagesFromXMLBank(QUEUE_NAME_XML);
		//consumeMessagesFromJSONBank(QUEUE_NAME_JSON);
    }
	
	public static void consumeMessagesFromJSONBank(String queueName)
	{
		factory = new ConnectionFactory();
		try {
			factory.setHost("datdb.cphbusiness.dk");
			factory.setUsername("aa");
			factory.setPassword("aa");
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.queueDeclare(queueName, true, false, false, null);
			channel.queueBind(queueName, queueName, "");
			
			Consumer consumer = new DefaultConsumer(channel) {
				
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					
					try {
						getDataFromJSONBank(message);
					}  finally {
						
					}
					
				}
			};
			channel.basicConsume(queueName, true, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void getDataFromJSONBank(String message){
		LoanResponse lR;
		try {
			System.out.println("\t--> Received message from JSON Bank..");
			System.out.println("\t---> Validating and Normalizing Data");
			lR = new LoanResponse().parseJSONToObject(message);
			String lRString = lR.toString();
			System.out.println("\t----> "+ lRString);
			publishMessageToAggregator(EXCHANGE_NAME,BINDING_KEY, lRString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	
	public static void consumeMessagesFromWSBank(String exchangeName, String queueName, String bindingKey)
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
						getDataFromWSBank(message);
					}  finally {
						
					}
					
				}
			};
			channel.basicConsume(queueName, true, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void getDataFromWSBank(String message){
		LoanResponse lR;
		try {
			System.out.println("\t--> Received message from WS Bank..");
			System.out.println("\t---> Validating and Normalizing Data");
			lR = new LoanResponse().parseXMLToObject(message);
			String lRString = lR.toString();
			System.out.println("\t----> "+ lRString);
			publishMessageToAggregator(EXCHANGE_NAME,BINDING_KEY, lRString);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				String lRString = lR.toString();
				System.out.println("\t----> "+ lRString);
				publishMessageToAggregator(EXCHANGE_NAME,BINDING_KEY, lRString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
			System.out.println(message);
			lR = new LoanResponse().parseJSONToObject(message);
			String lRString = lR.toString();
			System.out.println("\t----> "+ lRString);
			publishMessageToAggregator(EXCHANGE_NAME,BINDING_KEY, lRString);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void publishMessageToAggregator(String exchangeName, String bindingKey, String loanResponse) {
	
		try {
			Thread.sleep(5000);
			factory = new ConnectionFactory();
			factory.setHost("188.166.29.160");
			factory.setUsername("admin");
			factory.setPassword("admin");
		
			connection = factory.newConnection();
			channel = connection.createChannel();
		
			channel.exchangeDeclare(exchangeName, "direct", true);
		
			channel.basicPublish(exchangeName, bindingKey, null, loanResponse.getBytes());
		
			
			System.out.println("\t------> Sent: '"+loanResponse+"'");
			System.out.println("========================================================================================");
			channel.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
    
    
}
