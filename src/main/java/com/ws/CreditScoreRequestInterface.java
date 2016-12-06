package com.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


//Web Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface CreditScoreRequestInterface {
	
	@WebMethod
	String CreditScoreRequest(String name);
}
