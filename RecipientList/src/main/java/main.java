import com.rabbitmq.client.*;
import org.json.JSONObject;

import java.io.IOException;


public class main {

	private static String AGGREGATOR_QUEUE = "AggregatorFromRecipient";
	private static String[] SEND_QUEUE = {"XMLTranslator", "JSONTranslator", "WSTranslator", "MessagingTranslator" };
	private static String QUEUE_NAME = "RecipientList";
	private static String BINDING_KEY = "RecipientList";
	private static String EXCHANGE_NAME = "aaInternal";
	
	private static ConnectionFactory factory;
	private static Connection connection;
	private static Channel channel;
	private static Customer customer;
	
	public static void main(String[] args) {
		
		
		consumeMessage(EXCHANGE_NAME, QUEUE_NAME, BINDING_KEY, SEND_QUEUE);
		
		
	}
	
	
	public static void consumeMessage(String exchangeName, String queueName, String bindingKey, String[] translators)
	{
		factory = new ConnectionFactory();
		try {
			factory.setHost("188.166.29.160");
			factory.setUsername("admin");
			factory.setPassword("admin");
			connection= factory.newConnection();
			channel = connection.createChannel();
			
			channel.queueDeclare(queueName, true, false, false, null);
			System.out.println(" [*] RecipientList Waiting for messages from GetBanks...");
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
			try {
				System.out.println("\t--> Received message from GetBank..");
				System.out.println("\t---> Validating Data...");
				System.out.println("\t----> "+message);
				checkAcceptedBanks(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void checkAcceptedBanks(String banks) throws InterruptedException {
		JSONObject obj = new JSONObject(banks);
		boolean xml = obj.getBoolean("BankXML");
		boolean json = obj.getBoolean("BankJSON");
		boolean ws = obj.getBoolean("BankWS");
		boolean msg = obj.getBoolean("BankMSG");
		customer = new Customer(banks);
		System.out.println("\t-----> Checking Which Banks to send too... ");
		if (xml == true) {
			System.out.println("\t     -> Accepted by XML Bank: "+xml);
			
			String sendTo = SEND_QUEUE[0].toString();
			
			publishMessage(sendTo,customer);
		}
		if (json == true) {
			System.out.println("\t     -> Accepted by JSON Bank: "+json);
			
			String sendTo = SEND_QUEUE[1].toString();
			
			publishMessage(sendTo,customer);
		}
		if (ws == true) {
			System.out.println("\t     -> Accepted by WebService Bank: "+ws);
			
			String sendTo = SEND_QUEUE[2].toString();
			
			publishMessage(sendTo,customer);
		}
		if (msg == true) {
			System.out.println("\t     -> Accepted by Messaging Bank: "+msg);
			
			String sendTo = SEND_QUEUE[3].toString();
			
			publishMessage(sendTo,customer);
		}
		publishMessageToAggregator(AGGREGATOR_QUEUE, customer);
		System.out.println("========================================================================================");
	}
	
	private static void publishMessage(String queueName, Customer object) {
		try {
			factory = new ConnectionFactory();
			factory.setHost("188.166.29.160");
			factory.setUsername("admin");
			factory.setPassword("admin");
			
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
			
			channel.basicPublish(EXCHANGE_NAME, queueName, null, object.toString().getBytes());

			
			System.out.println("\t      --> Sent: '"+ object+"'");
			
			channel.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void publishMessageToAggregator(String queueName, Customer object) {
		try {
			factory = new ConnectionFactory();
			factory.setHost("188.166.29.160");
			factory.setUsername("admin");
			factory.setPassword("admin");
			
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
			
			channel.basicPublish(EXCHANGE_NAME, queueName, null, object.toString().getBytes());
			
			System.out.println("\t 		---> Sent to Aggregator for later comparison");
			
			channel.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
