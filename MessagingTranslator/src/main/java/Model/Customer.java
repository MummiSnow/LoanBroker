package Model;
import org.json.JSONObject;


public class Customer {
    private String SSN;
    private int loanDuration;
    private int loanAmount;
    private int creditScore;


    public Customer(String jsonObject) {
        parseJSONToObject(jsonObject);
    }

    //region private setters
    private String setSSN(String SSN) {
        if (SSN.matches("[0-9]{6}-([0-9]{4})")){
            char[] c = SSN.toCharArray();
            char[] ssnc = new char[]{c[0],c[1],c[2],c[3],c[4],c[5],c[7],c[8],c[9],c[10]};
            this.SSN = new String(ssnc);
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

    private void setLoanAmount(int loanAmount) {
        if (loanAmount > 0) {
            this.loanAmount = loanAmount;
            System.out.println(this.loanAmount);
        } else{
            throw new IllegalArgumentException("Cannot Issue a Loan of 0 or less");
        }
    }

    //endregion

    public String getSSN() {
        return SSN;
    }

    public int getLoanDuration() {
        return loanDuration;
    }

    public double getLoanAmount() {


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


        String loanDetails = String.format("{\"ssn\": \"%1s\"," +
                " \"creditScore\": %4$d," +
                " \"loanAmount\": %2$d," +
                " \"loanDuration\": %3$d}", SSN, loanAmount, loanDuration, creditScore);

        return loanDetails;
    }



}


