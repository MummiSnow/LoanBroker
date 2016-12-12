import org.json.JSONObject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


public class Customer {
	private String Id;
	private String SSN;
	private int loanDuration;
	private int loanAmount;
	private LocalDate loanDurationLocalDate;
	private int creditScore;
	private boolean[] banks;
	
	public Customer(String jsonObject) {
		parseJSONToObject(jsonObject);
	}
	
	//region private setters
	public void setId() {
		this.Id = UUID.randomUUID().toString();
	}

	private void setId(String id) {
		this.Id = id;
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

	public void setBanks(boolean[] banks) {
		this.banks = banks;
	}
	
	private void parseJSONToObject(String json) {
		if (json != null || json == "") {
			JSONObject obj = new JSONObject(json);
			String id = obj.getString("Id");
			String ssn = obj.getString("SSN");
			int lA = obj.getInt("LoanAmount");
			int lD = obj.getInt("LoanDuration");
			//LocalDate lDD = obj.get("LoanDurationAsDate");
			int lC = obj.getInt("CreditScore");
 			setId(id);
			setSSN(ssn);
			setLoanAmount(lA);
			setLoanDuration(lD);
			setCreditScore(lC);
			//setLoanDurationInDate();
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
				" \"CreditScore\": %6$d, " +
				" \"BankXML\": %7$s, " +
				" \"BankJSON\": %8$s, " +
				" \"BankWS\": %9$s, " +
				" \"BankMSG\": %10$s}, ", Id,SSN,loanAmount, loanDuration, loanDurationLocalDate.toString(),creditScore, banks[0], banks[1], banks[2], banks[3]);
		
		return loanDetails;
	}



}

