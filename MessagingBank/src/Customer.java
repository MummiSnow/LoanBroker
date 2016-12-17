import org.json.JSONObject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


public class Customer {
	private String SSN;
	private int loanDuration;
	private int loanAmount;
	private int creditScore;
	private double interestRate;
	
	public Customer(String jsonObject) {
		parseJSONToObject(jsonObject);
	}
	
	//region private setters
	private String setSSN(String SSN) {
		if (SSN.matches("[0-9]{6}-([0-9]{4})")){
			this.SSN = SSN;
			return this.SSN;
		} else {
			throw new IllegalArgumentException("SSN is not valid");
		}
	}
	
	private int setLoanDuration(int loanDuration) {
		if (loanDuration > 0) {
			this.loanDuration = loanDuration;
			return this.loanDuration;
		} else {
			throw new IllegalArgumentException("Cannot Issue a Loan with a loan period of " + loanDuration);
		}
	}
	
	private int setLoanAmount(int loanAmount) {
		if (loanAmount > 0) {
			this.loanAmount = loanAmount;
			return this.loanAmount;
		} else{
			throw new IllegalArgumentException("Cannot Issue a Loan of 0 or less");
		}
	}


	//endregion

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public String getSSN() {
		return SSN;
	}
	
	public int getLoanDuration() {
		return loanDuration;
	}
	
	public int getLoanAmount() {
		return loanAmount;
	}

	public double getInterestRate() {return interestRate;}
	
	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}
	
	public int getCreditScore() {
		if (creditScore == 0) {
			throw new NullPointerException("Remember that CreditScore gets added after initialization and after connection to CreditBureau has been made");
		} else{
			return creditScore;
		}
	}


	private void parseJSONToObject(String json) {
		if (json != null || json == "") {
			JSONObject obj = new JSONObject(json);
			String ssn = obj.getString("SSN");
			int lA = obj.getInt("LoanAmount");
			int lD = obj.getInt("LoanDuration");
			int lC = obj.getInt("CreditScore");
			setSSN(ssn);
			setLoanAmount(lA);
			setLoanDuration(lD);
			setCreditScore(lC);
		} else {
			throw new NullPointerException("JSON object is null or invalid");
		}
	}



	@Override
	public String toString() {

		String loanDetails = String.format("{\"SSN\": \"%1s\"," +
				" \"LoanAmount\": %2$d," +
				" \"LoanDuration\": %3$d, " +
				" \"InterestRate\" %4$e}",SSN, loanAmount, loanDuration, interestRate);
		
		return loanDetails;
	}



}

