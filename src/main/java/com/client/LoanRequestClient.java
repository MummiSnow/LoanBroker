package com.client;


import com.ws.LoanRequestImplementation;

public class LoanRequestClient {
	
	public static void main(String[] args) {
		
		//LoanRequestImplementationService loanService = new LoanRequestImplementationService();
		//LoanRequestInterface service = loanService.getLoanRequestImplementationPort();
		com.ws.LoanRequestImplementation lS = new LoanRequestImplementation();
		for (int i = 0; i < 1; i++) {
			String ser = lS.LoanRequest("311299-9999", 12, 30);
		}
		
		/*ArrayList<String> list = lS.ListTest();
		
		System.out.println(list.size());
		
		list.forEach(item -> System.out.println(item));
		*/
			
		
	}
	
}
