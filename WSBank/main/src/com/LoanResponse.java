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
		String loanDetails = String.format("{\"SSN\": \"%1s\"," +
				" \"interestRate\": %2$f}", SSN,interestRate);
		
		return loanDetails;
	}
}
