package com.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.ArrayList;


//Web Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface LoanRequestInterface {
	
	@WebMethod()
	@WebResult(name = "JSON Object")
	String LoanRequest(@WebParam(name = "SSN") String SSN, @WebParam(name = "loanAmount")int loanAmount, @WebParam(name = "loanDurationInMonths") int loanDurationInMonths );
	
	@WebMethod
	ArrayList<String> ListTest();
	
}
