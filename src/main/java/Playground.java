import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;


public class Playground {
	
	public static void main(String[] args ) throws IOException {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("datdb.cphbusiness.dk");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare("cphbusiness.bankXML", "fanout");
		
		String message = "";
		for (int i = 0; i < 10; i++) {
			channel.basicPublish("cphbusiness.bankXML", "", null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		channel.close();
		connection.close();
		
		
		
		
		
		
	}
}

