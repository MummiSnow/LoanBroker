package com.client;


import com.ws.LoanRequestImplementation;

import java.util.ArrayList;

public class LoanRequestClient {
	
	public static void main(String[] args) {
		
		//LoanRequestImplementationService loanService = new LoanRequestImplementationService();
		//LoanRequestInterface service = loanService.getLoanRequestImplementationPort();
		com.ws.LoanRequestImplementation lS = new LoanRequestImplementation();
		String ser = lS.LoanRequest("020219-9600",12, 30);
		
		/*ArrayList<String> list = lS.ListTest();
		
		
		System.out.println(list.size());
		
		list.forEach(item -> System.out.println(item));
		*/
			
		
	}
	
	
}
