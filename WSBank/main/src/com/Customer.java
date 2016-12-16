package com;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {
	
	private String Id;
	private String SSN;
	private int loanDuration;
	private int loanAmount;
	private long epoch;
	private int creditScore;
	private LoanDetails loanDetails;
	
	@XmlElement
	public void setId(String id) {
		Id = id;
	}
	
	@XmlElement
	public void setSSN(String ssn) {
		SSN = ssn;
	}
	
	@XmlElement
	public void setLoanDuration(int loanDuration) {
		this.loanDuration = loanDuration;
	}
	
	@XmlElement
	public void setLoanAmount(int loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	@XmlElement
	public void setEpoch(long epoch) {
		this.epoch = epoch;
	}
	
	@XmlElement
	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}
	
	@XmlElement
	public void setLoanDetails(LoanDetails loanDetails) {
		this.loanDetails = loanDetails;
	}
	
	public String getSSN() {
		return SSN;
	}
	
	public String getId() {
		return Id;
	}
	
	public long getEpoch() {
		return epoch;
	}
	
	public LoanDetails getLoanDetails() {
		return loanDetails;
	}
	
	public int getLoanDuration() {
		return loanDuration;
	}
	
	public int getLoanAmount() {
		return loanAmount;
	}
	
	public int getCreditScore() {
		return creditScore;
	}
	
	
}
