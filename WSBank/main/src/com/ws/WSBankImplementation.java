package com.ws;

import com.Customer;
import com.LoanDetails;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

//Web Service Endpoint Implementation
@WebService(endpointInterface = "com.ws.WSBankInterface")
public class WSBankImplementation implements WSBankInterface {
	
	Random random = new Random();
	Customer newCust = null;
	LoanDetails lD;
	
	private void Setup(Customer customer) {
		//For random generation of Interest Rate
		random = new Random();
		
		newCust = customer;
		lD = new LoanDetails();
	}
	
	
	public Customer RequestLoanDetails(Customer customer) {
		
		Setup(customer);
		
		try{
			if (newCust != null) {
				//calculations
				if (newCust.getLoanAmount() < 3000 || newCust.getCreditScore() <= 600) {
					double iR = randomInRange(3.99, 6.99);
					lD.setSSN(customer.getSSN());
					lD.setInterestRate(iR);
				} else if (newCust.getLoanAmount() >= 3000 || newCust.getCreditScore() > 600 ) {
					double iR = randomInRange(0.00, 3000);
					lD.setSSN(customer.getSSN());
					lD.setInterestRate(iR);
				}
				//Add LoanDetails To Customer
				newCust.setLoanDetails(lD);
				
				//Parse to XML
				File file = new File("logs/Customer.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
				Marshaller marshaller = jaxbContext.createMarshaller();
				
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				marshaller.marshal(customer, file);
				marshaller.marshal(customer, System.out);
			}
			
			return customer;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public double randomInRange(double min, double max) {
		double range = max - min;
		double scaled = random.nextDouble() * range;
		double shifted = scaled + min;
		return shifted;
	}
}
