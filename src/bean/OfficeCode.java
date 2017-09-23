package bean;

public class OfficeCode {
	private String PayCode;
	private String retCode;
	
	public String getPayCode() {
		return PayCode;
	}
	public void setPayCode(String payCode) {
		PayCode = payCode;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public OfficeCode(String payCode, String retCode) {
		super();
		PayCode = payCode;
		this.retCode = retCode;
	}
	
	

}
