package com.ws;

import com.Customer;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


//Web Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface WSBankInterface {
	
	@WebMethod()
	@WebResult(name = "XMLLoanResponse")
	Customer RequestLoanDetails(Customer customer);
	
	
	
	
}
