package com;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Locale;

@XmlRootElement
public class LoanResponse {
	
	private double interestRate;
	private int SSN;
	
	@XmlElement
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	@XmlElement(name = "SSN")
	public void setSSN(int ssn) {
		this.SSN = ssn;
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	
	public int getSSN() {
		return SSN;
	}
	
	public String toString() {

		String loanDet = String.format(Locale.ENGLISH,"<LoanResponse>" +
				"<ssn>%1$d</ssn>" +
				"<interestRate>%2$f</interestRate>" +
				"</LoanResponse>",SSN, interestRate);
		
		return loanDet;
	}
}
