import org.json.JSONObject;

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
	
	public void setEpoch(long epoch) {
		this.epoch = epoch;
	}
	
	public long getEpoch() {
		return epoch;
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
		
		String loanDetails = String.format("{\"Id\": \"%1s\"," +
				" \"SSN\": \"%2s\"," +
				" \"LoanAmount\": %3$d," +
				" \"LoanDuration\": %4$d," +
				" \"CreditScore\": %6$d, " +
				" \"Epoch\": %5$d} ", Id,SSN,loanAmount, loanDuration, epoch, creditScore);
		
		return loanDetails;
	}
}

