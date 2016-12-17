package com;

import org.json.JSONObject;

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
	private LoanResponse loanResponse;
	
	
	public Customer (String JSON) {
		parseJSONToObject(JSON);
	}
	
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
	public void setLoanResponse(LoanResponse loanResponse) {
		this.loanResponse = loanResponse;
	}
	
	public String getSSN() {
		return SSN;
	}
	
	public String getId() {
		return Id;
	}
	
	public LoanResponse getLoanResponse() {
		return loanResponse;
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
	
	
	private void parseJSONToObject(String json) {
		if (json != null || json == "") {
			JSONObject obj = new JSONObject(json);
			String id = obj.getString("Id");
			String ssn = obj.getString("SSN");
			int lA = obj.getInt("LoanAmount");
			int lD = obj.getInt("LoanDuration");
			int lC = obj.getInt("CreditScore");
			long epoch = obj.getLong("Epoch");
			setId(id);
			setSSN(ssn);
			setLoanAmount(lA);
			setLoanDuration(lD);
			setCreditScore(lC);
			setEpoch(epoch);
		} else {
			throw new NullPointerException("JSON object is null or invalid");
		}
	}
	
	@Override
	public String toString() {
		String loanDetails = String.format("{\"Id\": %1s," +
				" \"SSN\": \"%2s\"," +
				" \"LoanAmount\": %3$d," +
				" \"LoanDuration\": %4$d," +
				" \"CreditScore\": %5$d, " +
				" \"Epoch\": %6$d} ", Id,SSN,loanAmount, loanDuration, creditScore, epoch);
		
		return loanDetails;
	}
}
