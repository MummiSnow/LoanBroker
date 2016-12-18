package com;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;

@XmlRootElement(name="LoanRequest")
public class Customer {

    private String Id;
    private String SSN;
    private int loanDuration;
    private int loanAmount;
    private long epoch;
    private int creditScore;
    private LoanResponse loanResponse;


    public Customer() {
    }


    @XmlElement
    public void setId(String id) {
        Id = id;
    }

    @XmlElement(name = "SSN")
    public void setSSN(String ssn) {
        SSN = ssn;
    }

    @XmlElement
    public void setLoanDuration(int loanDuration) {
        this.loanDuration = loanDuration;
    }

    @XmlElement
    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    @XmlElement
    public void setEpoch(long epoch) {
        this.epoch = epoch;
    }

    @XmlElement
    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    @XmlElement
    public void setLoanResponse(LoanResponse loanResponse) {
        this.loanResponse = loanResponse;
    }

    public String getSSN() {
        return SSN;
    }

    public String getId() {
        return Id;
    }

    public LoanResponse getLoanResponse() {
        return loanResponse;
    }

    public int getLoanDuration() {
        return loanDuration;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public int getCreditScore() {
        return creditScore;
    }


    public static Customer parseJSONToObject(String json) {
        if (json != null || json == "") {
            try {
                JAXBContext jaxb = JAXBContext.newInstance(Customer.class);
                Unmarshaller um = jaxb.createUnmarshaller();
                StringReader sR = new StringReader(json);
                Customer cust = (Customer) um.unmarshal(sR);
                return cust;

            } catch (JAXBException e) {
                e.printStackTrace();
            }

        }

		/*if (json != null || json == "") {
            JSONObject obj = new JSONObject(json);
			String ssn = obj.getString("SSN");
			int lA = obj.getInt("LoanAmount");
			int lD = obj.getInt("LoanDuration");
			int lC = obj.getInt("CreditScore");
			long epoch = obj.getLong("Epoch");
			setSSN(ssn);
			setLoanAmount(lA);
			setLoanDuration(lD);
			setCreditScore(lC);
			setEpoch(epoch);
		} else {
			throw new NullPointerException("JSON object is null or invalid");
		}*/
		return null;
    }



}
