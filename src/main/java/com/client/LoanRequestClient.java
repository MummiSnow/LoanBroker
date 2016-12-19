package com.client;


import com.rabbitmq.client.*;
import com.ws.LoanRequestImplementation;
import org.json.JSONObject;

import java.io.IOException;

public class LoanRequestClient {
	
	private static ConnectionFactory factory;
	private static Connection connection;
	private static Channel channel;
	
	private static LoanQuote loanQuote;
	public static void main(String[] args) {
		
		//LoanRequestImplementationService loanService = new LoanRequestImplementationService();
		//LoanRequestInterface service = loanService.getLoanRequestImplementationPort();
		com.ws.LoanRequestImplementation lS = new LoanRequestImplementation();
		for (int i = 0; i < 1; i++) {
			String ser = lS.LoanRequest("111111-9601", 300000000, 100);
			System.out.println(ser);
		}
		
		consumeMessage("aaInternal", "Answer","Answer");
		
		/*ArrayList<String> list = lS.ListTest();
		
		System.out.println(list.size());
		
		list.forEach(item -> System.out.println(item));
		*/
		
		
		
	}
	
	public static class LoanQuote {
		private String SSN;
		private int loanAmount;
		private int loanDuration;
		private double interestRate;
		
		public void setSSN(String SSN) {
			this.SSN = SSN;
		}
		
		public void setLoanAmount(int loanAmount) {
			this.loanAmount = loanAmount;
		}
		
		public void setLoanDuration(int loanDuration) {
			this.loanDuration = loanDuration;
		}
		
		public void setInterestRate(double interestRate) {
			this.interestRate = interestRate;
		}
		
		@Override
		public String toString() {
			return "LoanQuote{" +
					"SSN='" + SSN + '\'' +
					", loanAmount=" + loanAmount +
					", loanDuration=" + loanDuration +
					", interestRate=" + interestRate +
					'}';
		}
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
			System.out.println(" [*] Waiting for loan quote...");
			
			channel.queueBind(queueName, exchangeName, bindingKey);
			
			Consumer consumer = new DefaultConsumer(channel) {
				
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
					
					getLoanQuoteFromMessage(message);
					
				}
			};
			channel.basicConsume(queueName, true, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void getLoanQuoteFromMessage(String message) {
		loanQuote = new LoanQuote();
		JSONObject obj = new JSONObject(message);
		loanQuote.setSSN(obj.getString("SSN"));
		loanQuote.setLoanAmount(obj.getInt("LoanAmount"));
		loanQuote.setLoanDuration(obj.getInt("LoanDuration"));
		loanQuote.setInterestRate(obj.getDouble("InterestRate"));
		System.out.println("Your loan quote: " +loanQuote);
		System.out.println("========================================================================================");
		
	}
	
	
}
