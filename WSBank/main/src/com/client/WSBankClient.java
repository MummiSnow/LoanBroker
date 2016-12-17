package com.client;


import com.Customer;
import com.LoanResponse;
import com.rabbitmq.client.*;
import com.ws.WSBankImplementation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class WSBankClient {
	static String EXCHANGE_NAME = "aaExternal";
	static String QUEUE_NAME = "Normalizer";
	static ConnectionFactory factory;
	static Connection connection;
	static Channel channel;
	
	
	public static void main(String[] args) {
		
		//LoanRequestImplementationService loanService = new LoanRequestImplementationService();
		//LoanRequestInterface service = loanService.getLoanRequestImplementationPort();
		WSBankImplementation lS = new WSBankImplementation();
		
		
		try {
			
			File file = new File("logs/Customer.xml");
			
			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
			
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Customer cust = (Customer) unmarshaller.unmarshal(file);
			
			System.out.println("Customer has an interest Rate of " + cust.getLoanResponse().getInterestRate() );
			//publishMessage(EXCHANGE_NAME, QUEUE_NAME, cust );
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void publishMessage(String exchange, String queueName, Customer object) {
		try {
			factory = new ConnectionFactory();
			factory.setHost("188.166.29.160");
			factory.setUsername("admin");
			factory.setPassword("admin");
			
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.exchangeDeclare(exchange, "direct", true);
			
			channel.basicPublish(exchange, queueName, null, object.toString().getBytes());
			
			System.out.println("\t      --> Sent: '"+ object+"'");
			
			channel.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
