
package com.com.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "LoanRequestInterface", targetNamespace = "http://ws.com/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface LoanRequestInterface {


    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "LoanRequest")
    @WebResult(partName = "return")
    @Action(input = "http://ws.com/LoanRequestInterface/LoanRequestRequest", output = "http://ws.com/LoanRequestInterface/LoanRequestResponse")
    public String loanRequest(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0,
        @WebParam(name = "arg1", partName = "arg1")
        int arg1,
        @WebParam(name = "arg2", partName = "arg2")
        int arg2);

}
