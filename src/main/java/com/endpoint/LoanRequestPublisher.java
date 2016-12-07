package com.endpoint;

import com.ws.LoanRequestImplementation;
import javax.xml.ws.Endpoint;

//Endpoint Publisher
public class LoanRequestPublisher {
	
	
	public static void main(String[] args ) {
		
		Endpoint.publish("http://localhost:9999/ws/LoanRequest", new LoanRequestImplementation());
		
	}
}

