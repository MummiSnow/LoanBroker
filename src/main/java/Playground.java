

import RabbitSuperClass.PublishConsume;
import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class Playground extends PublishConsume {
	
		private static final String TASK_QUEUE_NAME = "CreditScore";
	
		static String msg = "123";
	
		// Not relevant, Only for test use!
		public static void main(String[] argv) throws Exception {
			/*ConnectionFactory factory = new ConnectionFactory();
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
			channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);*/

			LocalDate today = LocalDate.now();
			LocalDate loanDurationLocalDate = today.plus(23, ChronoUnit.MONTHS);
			Date toDate = java.sql.Date.valueOf(loanDurationLocalDate);
			Date fromDate = java.sql.Date.valueOf(today);
			//get date as epoch
			String strDate = toDate.toString();
			String strFromDate = fromDate.toString();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(strDate);
			Date d = dateFormat.parse(strFromDate);
			long epoch = date.getTime();
			long todayLong = d.getTime();

			long loanDuration = epoch-todayLong;


			Date da = new Date(loanDuration);

			SimpleDateFormat dF = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			//dF.applyPattern("yyyy-MM-dd hh:mm:ss.s z");
			SimpleDateFormat ddf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.s zzz");
			System.out.println(ddf.format(da));
			System.out.println(da);
			//dF.parse(da.toString());


		}




    private static String printMessage(String queMsg) {
			
			System.out.println("KOM NU!!! "+msg);
			msg = queMsg;
			System.out.println("KOM NU!!! "+msg);
			return msg;
		}
	
}

