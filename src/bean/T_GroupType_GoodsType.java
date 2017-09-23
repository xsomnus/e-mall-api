package bean;
/*
 * 类别编号商品表
 */
public class T_GroupType_GoodsType {
	/*
	 * cGroupTypeNo	varchar(50)	Unchecked      类别编号
cPath	varchar(256)	Checked                
cGroupTypeName	varchar(100)	Checked        类别名称
cGoodsTypeNo	varchar(50)	Unchecked      商品类别编号
cGoodsTypeName	varchar(100)	Checked        商品类别名称
cMarket	varchar(100)	Checked               
cOper	varchar(50)	Checked
dDateOper	datetime	Checked
	 */
	private String cGroupTypeNo;
	private String cPath;
	private String cGroupTypeName;
	private String cGoodsTypeNo;
	private String cGoodsTypeName;
	private String cMarket;
	private String cOper;
	private String dDateOper;
	public String getcGroupTypeNo() {
		return cGroupTypeNo;
	}
	public void setcGroupTypeNo(String cGroupTypeNo) {
		this.cGroupTypeNo = cGroupTypeNo;
	}
	public String getcPath() {
		return cPath;
	}
	public void setcPath(String cPath) {
		this.cPath = cPath;
	}
	public String getcGroupTypeName() {
		return cGroupTypeName;
	}
	public void setcGroupTypeName(String cGroupTypeName) {
		this.cGroupTypeName = cGroupTypeName;
	}
	public String getcGoodsTypeNo() {
		return cGoodsTypeNo;
	}
	public void setcGoodsTypeNo(String cGoodsTypeNo) {
		this.cGoodsTypeNo = cGoodsTypeNo;
	}
	public String getcGoodsTypeName() {
		return cGoodsTypeName;
	}
	public void setcGoodsTypeName(String cGoodsTypeName) {
		this.cGoodsTypeName = cGoodsTypeName;
	}
	public String getcMarket() {
		return cMarket;
	}
	public void setcMarket(String cMarket) {
		this.cMarket = cMarket;
	}
	public String getcOper() {
		return cOper;
	}
	public void setcOper(String cOper) {
		this.cOper = cOper;
	}
	public String getdDateOper() {
		return dDateOper;
	}
	public void setdDateOper(String dDateOper) {
		this.dDateOper = dDateOper;
	}
	public T_GroupType_GoodsType(String cGroupTypeNo, String cPath,
			String cGroupTypeName, String cGoodsTypeNo, String cGoodsTypeName,
			String cMarket, String cOper, String dDateOper) {
		super();
		this.cGroupTypeNo = cGroupTypeNo;
		this.cPath = cPath;
		this.cGroupTypeName = cGroupTypeName;
		this.cGoodsTypeNo = cGoodsTypeNo;
		this.cGoodsTypeName = cGoodsTypeName;
		this.cMarket = cMarket;
		this.cOper = cOper;
		this.dDateOper = dDateOper;
	}
	public T_GroupType_GoodsType() {
		super();
	}
	@Override
	public String toString() {
		return "T_GroupType_GoodsType [cGroupTypeNo=" + cGroupTypeNo
				+ ", cPath=" + cPath + ", cGroupTypeName=" + cGroupTypeName
				+ ", cGoodsTypeNo=" + cGoodsTypeNo + ", cGoodsTypeName="
				+ cGoodsTypeName + ", cMarket=" + cMarket + ", cOper=" + cOper
				+ ", dDateOper=" + dDateOper + "]";
	}
	
	
}
