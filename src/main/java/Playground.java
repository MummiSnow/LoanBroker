

import RabbitSuperClass.PublishConsume;
import com.rabbitmq.client.*;

import java.io.IOException;


public class Playground extends PublishConsume {
	
		private static final String TASK_QUEUE_NAME = "CreditScore";
	
		static String msg = "123";
	
		// Not relevant, Only for test use!
		public static void main(String[] argv) throws Exception {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("188.166.29.160");
			factory.setUsername("admin");
			factory.setPassword("admin");
			final Connection connection = factory.newConnection();
			final Channel channel = connection.createChannel();
			
			channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			
			channel.basicQos(1);
			
			
			final Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					
					
					try {
						printMessage(message);
						System.out.println(msg);
					} finally {
						channel.basicAck(envelope.getDeliveryTag(), false);
						System.out.println("SENDING TO CreditScore");
					}
				}
			};
			boolean autoAck = false;
			channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
			
			
			
		}
		
		private static String printMessage(String queMsg) {
			
			System.out.println("KOM NU!!! "+msg);
			msg = queMsg;
			System.out.println("KOM NU!!! "+msg);
			return msg;
		}
	
}

