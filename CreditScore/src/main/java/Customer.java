import org.json.JSONObject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * Created by MJPS on 07/12/2016.
 */
public class Customer {
	private String Id;
	private String SSN;
	private int loanDuration;
	private int loanAmount;
	private LocalDate loanDurationLocalDate;
	private int creditScore;
	
	public Customer(String jsonObject) {
		parseJSONToObject(jsonObject);
	}
	
	//region private setters
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
	
	private int setLoanDuration(int loanDuration) {
		if (loanDuration > 0) {
			this.loanDuration = loanDuration;
			setLoanDurationInDate();
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
	
	private LocalDate setLoanDurationInDate() {
		LocalDate today = LocalDate.now();
		this.loanDurationLocalDate = today.plus(loanDuration, ChronoUnit.MONTHS);
		return loanDurationLocalDate;
	}
	//endregion
	
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
	
	public LocalDate getLoanDurationLocalDate() {
		return loanDurationLocalDate;
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
				" \"LoanDurationAsDate\": %5s, " +
				" \"CreditScore\": %6$d}", Id,SSN,loanAmount, loanDuration, loanDurationLocalDate.toString(),creditScore);
		
		return loanDetails;
	}
}

