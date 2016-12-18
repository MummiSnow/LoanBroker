import org.json.JSONObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;

@XmlRootElement(name = "LoanResponse")
public class LoanResponse {
	
	private double interestRate;
	private int SSN;
	
	@XmlElement(name = "interestRate")
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	@XmlElement(name = "ssn")
	public void setSSN(int ssn) {
		this.SSN = ssn;
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	
	public int getSSN() {
		return SSN;
	}
	
	
	public LoanResponse parseJSONToObject(String json) {
		if (json != null || json == "") {
			JSONObject obj = new JSONObject(json);
			int ssn = obj.getInt("SSN");
			double iR = obj.getDouble("interestRate");
			setSSN(ssn);
			setInterestRate(iR);
			return this;
		} else {
			throw new NullPointerException("JSON object is null or invalid");
		}
	}
	
	public LoanResponse parseXMLToObject(String XML) {
		
		try {
			
			if (XML != null || XML == "") {
				JAXBContext jaxbContext = JAXBContext.newInstance(LoanResponse.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				
				StringReader reader = new StringReader(XML);
				LoanResponse loanResponse = (LoanResponse) unmarshaller.unmarshal(reader);
				return loanResponse;
			} else {
				throw new JAXBException("XML Object is null or invalid");
			}
			
		} catch (JAXBException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public String toString() {
		String loanResponse = String.format("{\"SSN\": \"%1$d\"," +
				" \"interestRate\": %2$f}", SSN, interestRate );
		return loanResponse;
	}
}
