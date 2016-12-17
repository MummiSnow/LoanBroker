package com;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoanResponse {
	
	private double interestRate;
	private String SSN;
	
	@XmlElement
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	@XmlElement
	public void setSSN(String ssn) {
		this.SSN = ssn;
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	
	public String getSSN() {
		return SSN;
	}
	
	public String toString() {

		String loanDet = String.format("<LoanResponse>" +
				"<SSN>%1$s</SSN>" +
				"<interestRate>%2$f</interestRate>" +
				"</LoanResponse>",SSN, interestRate);
		String loanDetails = String.format("{\"SSN\": \"%1s\"," +
				" \"interestRate\": %2$f}", SSN,interestRate);
		
		return loanDet;
	}
}
