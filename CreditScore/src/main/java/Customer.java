import org.json.JSONObject;

import java.util.UUID;

public class Customer {
	
	private String Id;
	private String SSN;
	private int loanDuration;
	private int loanAmount;
	private int creditScore;

	public Customer(String jsonObject) {
		parseJSONToObject(jsonObject);
	}
	
	public void setId() {
		this.Id = UUID.randomUUID().toString();
	}
	
	private String setSSN(String SSN) {
		if (SSN.matches("[0-9]{6}-([0-9]{4})")){
			this.SSN = SSN;
			return this.SSN;
		} else {
			throw new IllegalArgumentException("SSN is not valid");
		}
	}
	
	private void setLoanDuration(int loanDuration) {
		if (loanDuration > 0) {
			this.loanDuration = loanDuration;
		} else {
			throw new IllegalArgumentException("Cannot Issue a Loan with a loan period of " + loanDuration);
		}
	}
	
	private void setLoanAmount(int loanAmount) {
		if (loanAmount > 0) {
			this.loanAmount = loanAmount;
		} else{
			throw new IllegalArgumentException("Cannot Issue a Loan of 0 or less");
		}
	}

	public String getId() {
		return Id;
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
			setId();
			setSSN(ssn);
			setLoanAmount(lA);
			setLoanDuration(lD);
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
				" \"CreditScore\": %5$d}", Id,SSN,loanAmount, loanDuration,creditScore);
		
		return loanDetails;
	}
}

