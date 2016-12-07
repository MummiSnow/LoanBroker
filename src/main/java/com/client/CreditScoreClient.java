package com.client;


import com.com.ws.CreditScoreRequestInterface;
import com.com.ws.CreditScoreRequestImplementationService;

public class CreditScoreClient {
	
	public static void main(String[] args) {
		
		CreditScoreRequestImplementationService creditService = new CreditScoreRequestImplementationService();
		CreditScoreRequestInterface service = creditService.getCreditScoreRequestImplementationPort();
		
		System.out.println(service.creditScoreRequest("2341234, 20000, 12"));
		
	}
	
	
}
