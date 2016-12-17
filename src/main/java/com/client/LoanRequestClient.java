package com.client;


import com.ws.LoanRequestImplementation;

public class LoanRequestClient {
	
	public static void main(String[] args) throws InterruptedException {
		
		//LoanRequestImplementationService loanService = new LoanRequestImplementationService();
		//LoanRequestInterface service = loanService.getLoanRequestImplementationPort();
		com.ws.LoanRequestImplementation lS = new LoanRequestImplementation();
		for (int i = 0; i < 10; i++) {
			Thread.sleep(5000);
			String ser = lS.LoanRequest("020219-9699", 12, 30);
		}
		
		/*ArrayList<String> list = lS.ListTest();
		
		System.out.println(list.size());
		
		list.forEach(item -> System.out.println(item));
		*/
			
		
	}
	
}
