package Model;


import org.json.JSONObject;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Customer {

    private String Id;
    private String SSN;
    private int loanDuration;
    private int loanAmount;
    private double interestRate;
    private int resposesReceived = 0;
    private long timeStampOfArrival;

    public Customer(){
        Date arrival = new Date();
        this.timeStampOfArrival = arrival.getTime();
        interestRate = -1.0;}

    public Customer(String jsonObject) {
        Date arrival = new Date();
        this.timeStampOfArrival = arrival.getTime();
        interestRate = -1.0;
        parseJSONToObject(jsonObject);
    }

    public void parseFromNormalizer(String jsonObject)
    {
        parseJSONToObjectFromNormalizer(jsonObject);
    }

    public void setId() {
        this.Id = UUID.randomUUID().toString();
    }

    private void setId(String id) {
        this.Id = id;
    }

    private String setSSN(String SSN) {
        if (SSN.matches("[0-9]{10}") || SSN.matches("[0-9]{8}") || SSN.matches("[0-9]{6}-[0-9]{4}")){
            this.SSN = SSN;
            return this.SSN;
        } else {
            throw new IllegalArgumentException("SSN is not valid");
        }
    }

    public long getTimeStampOfArrival() {
        return timeStampOfArrival;
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

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        if (this.interestRate == -1.0)
        {this.interestRate = interestRate;}
        else if (interestRate < this.interestRate)
        {
            this.interestRate = interestRate;
        }
        resposesReceived++;
    }

    public int getResposesReceived() {
        return resposesReceived;
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

    private void parseJSONToObject(String json) {
        if (json != null || json == "") {
            JSONObject obj = new JSONObject(json);
            String id = obj.getString("Id");
            String ssn = obj.getString("SSN");
            int lA = obj.getInt("LoanAmount");
            int lD = obj.getInt("LoanDuration");
            setId(id);
            setSSN(ssn);
            setLoanAmount(lA);
            setLoanDuration(lD);
        } else {
            throw new NullPointerException("JSON object is null or invalid");
        }
    }

    private void parseJSONToObjectFromNormalizer(String json) {
        if (json != null || json == "") {
            JSONObject obj = new JSONObject(json);
            String ssn = obj.getString("SSN");
            double interestRate = obj.getDouble("interestRate");
            setSSN(ssn);
            setInterestRate(interestRate);

        } else {
            throw new NullPointerException("JSON object is null or invalid");
        }
    }


    @Override
    public String toString() {

        String loanDetails = String.format(Locale.ENGLISH, "{\"Id\": \"%1s\"," +
                " \"SSN\": \"%2s\"," +
                " \"LoanAmount\": %3$d," +
                " \"LoanDuration\": %4$d," +
                " \"InterestRate\": %5$f}", Id,SSN,loanAmount, loanDuration, interestRate);

        return loanDetails;
    }
}
