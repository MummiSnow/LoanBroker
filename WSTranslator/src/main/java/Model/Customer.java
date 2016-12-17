package Model;

import org.json.JSONObject;


public class Customer {
    
    private String id;
    private String SSN;
    private int loanDuration;
    private int loanAmount;
    private int creditScore;
    private long epoch;


    public Customer(String jsonObject) {
        parseJSONToObject(jsonObject);
    }

    //region private setters
    
    
    public void setId(String id) {
        this.id = id;
    }
    
    private String setSSN(String SSN) {
        if (SSN.matches("[0-9]{6}-([0-9]{4})")){
            this.SSN = SSN;
        } else {
            throw new IllegalArgumentException("SSN is not valid");
        }
        return null;
    }
    
    private void setLoanAmount(int loanAmount) {
        if (loanAmount > 0) {
            this.loanAmount = loanAmount;
        } else{
            throw new IllegalArgumentException("Cannot Issue a Loan of 0 or less");
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
    
    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }
    
    public void setEpoch(long epoch) {this.epoch = epoch; }
    
    //endregion

    public String getId() {return id;}
    
    public String getSSN() {
        return SSN;
    }
    
    public double getLoanAmount() {
        return loanAmount;
    }
    
    public int getLoanDuration() {
        return loanDuration;
    }
    
    public int getCreditScore() {
        return creditScore;
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
        String loanDetails = String.format("{\"Id\":\"%1s\","+
                "\"SSN\": \"%2s\"," +
                " \"LoanAmount\": %3$d," +
                " \"LoanDuration\": %4$d," +
                " \"CreditScore\": %5$d,"+
                " \"Epoch\": %6$d}", id, SSN, loanAmount, loanDuration, creditScore, epoch);

        return loanDetails;
    }



}


