package com.ws;

import javax.jws.WebService;

//Web Service Endpoint Implementation
@WebService(endpointInterface = "com.ws.CreditScoreRequestInterface")
public class CreditScoreRequestImplementation implements CreditScoreRequestInterface {
	
	public String CreditScoreRequest(String name) {
		return "Requested a CreditScore: " + name;
	}
}
