package com.ws;

import RabbitSuperClass.PublishConsume;

import javax.jws.WebService;

//Web Service Endpoint Implementation
@WebService(endpointInterface = "com.ws.LoanRequestInterface")
public class LoanRequestImplementation extends PublishConsume implements LoanRequestInterface {
	
	public String LoanRequest(String SSN, int loanAmount, int loanDurationInMonths) {
		
		String loanDetails = String.format("SSN: %1s,LoanAmount: %2$d,LoanDuration: %3$d ", SSN,loanAmount,loanDurationInMonths);
		
		publisher("aaInternal", "CreditScore", loanDetails);
		
		return "User requested a loan: " + loanDetails;
	}
}
