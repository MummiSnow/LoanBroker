import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;


public class Customer {
	private String Id;
	private String SSN;
	private int loanDuration;
	private int loanAmount;
	private long epoch;
	private int creditScore;
	private boolean[] banks;
	
	public Customer(String jsonObject) {
		parseJSONToObject(jsonObject);
	}
	
	//region private setters
	private void setId(String id) {
		this.Id = id;
	}
	
	private void setSSN(String SSN) {
		if (SSN.matches("[0-9]{6}-([0-9]{4})")){
			this.SSN = SSN;
		} else {
			throw new IllegalArgumentException("SSN is not valid");
		}
	}
	
	private void setLoanDuration(int loanDuration) {
		if (loanDuration > 0) {
			this.loanDuration = loanDuration;
			try {
				convertToEpoch();
			} catch (ParseException e) {
				System.out.println("Error converting loanDuration to epoch (format required yyyy-MM-dd):  " + e.getMessage());
			}
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
	
	private void convertToEpoch() throws ParseException {
		
		//get loan duration as date
		LocalDate today = LocalDate.now();
		LocalDate loanDurationLocalDate = today.plus(loanDuration, ChronoUnit.MONTHS);
		Date toDate = java.sql.Date.valueOf(loanDurationLocalDate);
		
		//get date as epoch
		String strDate = toDate.toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(strDate);
		long epoch = date.getTime();
		this.epoch = epoch;
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
	
	public long getEpoch() {
		return epoch;
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
			int lC = obj.getInt("CreditScore");
 			setId(id);
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
		
		String loanDetails = String.format("{\"Id\": %1s," +
				" \"SSN\": \"%2s\"," +
				" \"LoanAmount\": %3$d," +
				" \"LoanDuration\": %4$d," +
				" \"CreditScore\": %6$d, " +
				" \"Epoch\": %5$d, " +
				" \"BankXML\": %7$s, " +
				" \"BankJSON\": %8$s, " +
				" \"BankWS\": %9$s, " +
				" \"BankMSG\": %10$s}, ", Id,SSN,loanAmount, loanDuration, epoch,creditScore, banks[0], banks[1], banks[2], banks[3]);
		
		return loanDetails;
	}



}

