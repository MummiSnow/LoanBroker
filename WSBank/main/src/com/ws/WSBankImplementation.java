package com.ws;

import com.Customer;
import com.LoanResponse;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.jws.WebService;
import java.io.IOException;
import java.util.Random;

//Web Service Endpoint Implementation
@WebService(endpointInterface = "com.ws.WSBankInterface")
public class WSBankImplementation implements WSBankInterface {
	
	private Random random = new Random();
	private Customer newCust;
	private LoanResponse lR;
	
	
	public String RequestLoanDetails(String customer) {
		if (customer != null || customer == "") {
			//For random generation of Interest Rate
			random = new Random();
			newCust = Customer.parseJSONToObject(customer);
			lR = new LoanResponse();
			try{
				if (newCust != null) {
					//calculations
					if (newCust.getLoanAmount() < 3000 || newCust.getCreditScore() <= 600) {
						double iR = randomInRange(3.99, 6.99);
						lR.setSSN(Integer.parseInt(newCust.getSSN()));
						lR.setInterestRate(3.862);
					} else if (newCust.getLoanAmount() >= 3000 || newCust.getCreditScore() > 600 ) {
						double iR = randomInRange(1.00, 3.99);
						lR.setSSN(Integer.parseInt(newCust.getSSN()));
						lR.setInterestRate(iR);
					}
					//Add LoanDetails To Customer
					//Just incase we need to  process more about the customer
					//newCust.setLoanResponse(lR);
					/*JSONObject obj = new JSONObject(lR.toString());
					String xml = XML.toString(obj);
					//TODO: if time make it right
					String validXML = "<LoanResponse>"+ xml+ "</LoanResponse>";*/
					String loanRes = lR.toString();
					System.out.println(loanRes);
					publishMessageToNormalizer("aaExtFromWSBank", "aaExtFromWSBank",loanRes);
					return loanRes;
				}
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	
	private double randomInRange(double min, double max) {
		double range = max - min;
		double scaled = random.nextDouble() * range;
		double shifted = scaled + min;
		return shifted;
	}
	
	private void publishMessageToNormalizer(String exchangeName, String queueName, String xml) {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection;
		Channel channel;
		try {
			factory.setHost("datdb.cphbusiness.dk");
			factory.setUsername("aa");
			factory.setPassword("aa");
			
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.exchangeDeclare(exchangeName, "direct", true);
			
			channel.basicPublish(exchangeName, queueName, null, xml.getBytes());
			
			System.out.println("\t      --> Sent: '"+xml+"'");
			
			channel.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
