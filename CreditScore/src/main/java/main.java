import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * Created by MJPS on 28/11/2016.
 */
public class main {
	
	private static String QUEUE_NAME;
	private static String EXCHANGE_NAME = "test";
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("188.166.29.160");
		factory.setUsername("admin");
		factory.setPassword("admin");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		QUEUE_NAME = channel.queueDeclare().getQueue();
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);
		
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			
			System.out.println(" [x] Received '" + msg + "'");
		}
		
		
		
	}
}
