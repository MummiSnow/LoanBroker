package com.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface RuleBaseInterface {
        @WebMethod
        boolean[] GetBanks(String SSN, int loanAmount, int loanDurationInMonths, int creditScore );
}
