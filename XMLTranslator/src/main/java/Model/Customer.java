package Model;


import org.json.JSONObject;

import javax.lang.model.element.Name;
import javax.xml.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@XmlRootElement(name="LoanRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer
{
    @XmlElement(name= "SSN", required = true, nillable = false)
    private String SSN;
    @XmlElement(required = true, type = Integer.class, nillable = false)
    private int creditScore;
    @XmlElement(required = true, type = Integer.class, nillable = false)
    private int loanAmount;
    @XmlElement(required = true, type = Integer.class, nillable = false)
    private Date loanDuration;

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
            long lD = obj.getLong("Epoch");
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
        if (SSN.matches("[0-9]{6}-([0-9]{4})")){
            char[] c = SSN.toCharArray();
            char[] ssnc = new char[]{c[0],c[1],c[2],c[3],c[4],c[5],c[7],c[8]};
            this.SSN = new String(ssnc);
        } else {
            throw new IllegalArgumentException("SSN is not valid");
        }
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

    public Date getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(long loanDuration) {

        LocalDate today = LocalDate.now();
        Date fromDate = java.sql.Date.valueOf(today);
        String strFromDate = fromDate.toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        long duration;
        long todayLong;
        try {
            d = dateFormat.parse(strFromDate);

            todayLong = d.getTime();
            duration = loanDuration - todayLong;

            this.loanDuration = new Date(duration);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
