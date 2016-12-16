package com;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoanDetails {
	
	private double interestRate;
	private String ssn;
	
	@XmlElement
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	@XmlElement
	public void setSSN(String ssn) {
		this.ssn = ssn;
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	
	public String getSSN() {
		return ssn;
	}
}
