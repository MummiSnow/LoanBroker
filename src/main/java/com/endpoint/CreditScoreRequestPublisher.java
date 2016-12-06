package com.endpoint;

import com.ws.CreditScoreRequestImplementation;
import javax.xml.ws.Endpoint;

//Endpoint Publisher
public class CreditScoreRequestPublisher {
	
	
	public static void main(String[] args ) {
		
		Endpoint.publish("http://localhost:9999/ws/CreditScore", new CreditScoreRequestImplementation());
		
	}
}

