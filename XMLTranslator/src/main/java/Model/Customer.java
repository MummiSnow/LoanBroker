package Model;


import org.json.JSONObject;

import javax.lang.model.element.Name;
import javax.xml.bind.annotation.*;

@XmlRootElement(name="LoanRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer
{
    @XmlElement(name= "ssn", required = true, nillable = false)
    private String SSN;
    @XmlElement(required = true, type = Integer.class, nillable = false)
    private int creditScore;
    @XmlElement(required = true, type = Integer.class, nillable = false)
    private int loanAmount;
    @XmlElement(required = true, type = Integer.class, nillable = false)
    private int loanDuration;

    public Customer() {
    }

    public Customer(String jsonObject) {
        parseJSONToObject(jsonObject);
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
    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
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
}
