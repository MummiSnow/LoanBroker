import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerXML {
	
	private String Id;
	private String SSN;
	private LoanResponse loanResponse;
	
	
	@XmlElement
	public void setId(String id) {
		Id = id;
	}
	
	@XmlElement
	public void setSSN(String SSN) {
		this.SSN = SSN;
	}
	
	@XmlElement
	public void setLoanResponse(LoanResponse loanResponse) {
		this.loanResponse = loanResponse;
	}
	
	public LoanResponse getLoanResponse() {
		return loanResponse;
	}
	
	public String getId() {
		return Id;
	}
	
	public String getSSN() {
		return SSN;
	}
	
}
