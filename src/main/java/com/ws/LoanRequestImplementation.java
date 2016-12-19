package com.ws;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.jws.WebService;
import java.io.IOException;

//Web Service Endpoint Implementation
@WebService(endpointInterface = "com.ws.LoanRequestInterface")
public class LoanRequestImplementation implements LoanRequestInterface {
	String EXCHANGE_NAME = "aaInternal";
	String ROUTING_KEY = "CreditScore";
	ConnectionFactory factory;
	Connection connection;
	Channel channel;
	
	public String LoanRequest(String SSN, int loanAmount, int loanDurationInMonths) {

		if(SSN.matches("[0-9]{6}-([0-9]{4})") && loanAmount > 0 && loanDurationInMonths > 0) {
			String loanDetails = String.format("{\"SSN\": \"%1s\", \"LoanAmount\": %2$d, \"LoanDuration\": %3$d}", SSN, loanAmount, loanDurationInMonths);

			try {
				factory = new ConnectionFactory();
				factory.setHost("188.166.29.160");
				factory.setUsername("admin");
				factory.setPassword("admin");

				connection = factory.newConnection();
				channel = connection.createChannel();

				channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);

				channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, loanDetails.getBytes());

				System.out.printf("Sent: '%1s' ", loanDetails +"\n");

				channel.close();
				connection.close();
				return "User requested a loan: " + loanDetails;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "Something went wrong in 'LoanRequestImplementation' Class";
	}
	
}
