package com.ws;

import com.Customer;
import com.LoanResponse;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONObject;
import org.json.XML;

import javax.jws.WebService;
import java.io.IOException;
import java.util.Random;

//Web Service Endpoint Implementation
@WebService(endpointInterface = "com.ws.WSBankInterface")
public class WSBankImplementation implements WSBankInterface {
	
	private Random random = new Random();
	private Customer newCust;
	private LoanResponse lD;
	
	
	public String RequestLoanDetails(String customer) {
		if (customer != null || customer == "") {
			//For random generation of Interest Rate
			random = new Random();
			newCust = new Customer(customer);
			lD = new LoanResponse();
			try{
				if (newCust != null) {
					//calculations
					if (newCust.getLoanAmount() < 3000 || newCust.getCreditScore() <= 600) {
						double iR = randomInRange(3.99, 6.99);
						lD.setSSN(newCust.getSSN());
						lD.setInterestRate(iR);
					} else if (newCust.getLoanAmount() >= 3000 || newCust.getCreditScore() > 600 ) {
						double iR = randomInRange(0.00, 3000);
						lD.setSSN(newCust.getSSN());
						lD.setInterestRate(iR);
					}
					//Add LoanDetails To Customer
					//Just incase we need to  process more about the customer
					//newCust.setLoanResponse(lD);
					JSONObject obj = new JSONObject(lD.toString());
					String xml = XML.toString(obj);
					//TODO: if time make it right
					String validXML = "<LoanResponse>"+ xml+ "</LoanResponse>";
					
					System.out.println(xml);
					publishMessageToNormalizer("aaExtFromWSBank", "aaExtFromWSBank",validXML);
					return validXML;
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
