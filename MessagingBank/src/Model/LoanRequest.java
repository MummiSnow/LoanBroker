package Model;

import java.util.Random;

public class LoanRequest {

    private String ssn;
    private int loanAmount;
    private int loanDuration;
    private int creditScore;
    private double rate;

    private Random r  = new Random();

    public LoanRequest(String ssn, int loanAmount, int loanDuration, int creditScore) {
        this.ssn = ssn;
        this.loanAmount = loanAmount;
        this.loanDuration = loanDuration;
        this.creditScore = creditScore;
        this.rate = getInterestRate();
}

    public String getSsn() {
        return ssn;
    }

    private double getInterestRate()
    {
        double d = r.nextDouble()*(r.nextInt(6)+5);
        return d;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(int loanDuration) {
        this.loanDuration = loanDuration;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        String requestDetails = String.format("{\"SSN\": \"%1s\"," +
                " \"interestRate\": %2$f}", getSsn(), rate);
        return requestDetails;
    }
}
