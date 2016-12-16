package com.endpoint;

import com.ws.WSBankImplementation;

import javax.xml.ws.Endpoint;

//Endpoint Publisher
public class WSBankPublisher {
	
	
	public static void main(String[] args ) {
		
		Endpoint.publish("http://localhost:9998/ws/WSBank", new WSBankImplementation());
		
	}
}

