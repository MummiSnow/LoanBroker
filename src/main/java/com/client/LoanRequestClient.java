package com.client;


import com.com.ws.LoanRequestImplementationService;
import com.com.ws.LoanRequestInterface;

public class LoanRequestClient {
	
	public static void main(String[] args) {
		
		LoanRequestImplementationService loanService = new LoanRequestImplementationService();
		LoanRequestInterface service = loanService.getLoanRequestImplementationPort();
		
		//user request for a loan
		System.out.println(service.loanRequest("020219-9600",12, 30));
		
	}
	
	
}
