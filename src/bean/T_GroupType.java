package bean;
/*
 * Àà±ð±í
 */
public class T_GroupType {
	private String cGroupTypeNo;
	private String cParentNo;
	private String cMarket;
	private String cIMG;
	private String cPath;
	private String bFresh;
	private String cGroupTypeName;
	
	public String getcGroupTypeName() {
		return cGroupTypeName;
	}
	public void setcGroupTypeName(String cGroupTypeName) {
		this.cGroupTypeName = cGroupTypeName;
	}
	public String getcGroupTypeNo() {
		return cGroupTypeNo;
	}
	public void setcGroupTypeNo(String cGroupTypeNo) {
		this.cGroupTypeNo = cGroupTypeNo;
	}
	public String getcParentNo() {
		return cParentNo;
	}
	public void setcParentNo(String cParentNo) {
		this.cParentNo = cParentNo;
	}
	public String getcMarket() {
		return cMarket;
	}
	public void setcMarket(String cMarket) {
		this.cMarket = cMarket;
	}
	public String getcIMG() {
		return cIMG;
	}
	public void setcIMG(String cIMG) {
		this.cIMG = cIMG;
	}
	public String getcPath() {
		return cPath;
	}
	public void setcPath(String cPath) {
		this.cPath = cPath;
	}
	public String getbFresh() {
		return bFresh;
	}
	public void setbFresh(String bFresh) {
		this.bFresh = bFresh;
	}
	@Override
	public String toString() {
		return "T_GroupType [cGroupTypeNo=" + cGroupTypeNo + ", cParentNo="
				+ cParentNo + ", cMarket=" + cMarket + ", cIMG=" + cIMG
				+ ", cPath=" + cPath + ", bFresh=" + bFresh
				+ ", cGroupTypeName=" + cGroupTypeName + "]";
	}
	
	
}
