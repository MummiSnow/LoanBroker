import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoanResponse {
	
	private double interestRate;
	private int ssn;
	
	@XmlElement
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	@XmlElement
	public void setSSN(int ssn) {
		this.ssn = ssn;
	}
	
	public double getInterestRate() {
		return interestRate;
	}
	
	public int getSsn() {
		return ssn;
	}
}
