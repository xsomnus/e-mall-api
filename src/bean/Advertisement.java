package bean;
/**
 * 
 * @author Administrator
 * dbo.Advertisement      广告表

adcGoodsNo	varchar(32)	Unchecked                商品编号
adImagePath	varchar(128)	Checked                  图片路径
adcGoodsName	varchar(32)	Checked              商品名称，
describe         varchar(50)   Unchecked            商品描述
 *
 */
public class Advertisement {
	private String adcGoodsNo;
	private String adImagePath;
	private String adcGoodsName;
	private String describe;
	public String getAdcGoodsNo() {
		return adcGoodsNo;
	}
	public void setAdcGoodsNo(String adcGoodsNo) {
		this.adcGoodsNo = adcGoodsNo;
	}
	public String getAdImagePath() {
		return adImagePath;
	}
	public void setAdImagePath(String adImagePath) {
		this.adImagePath = adImagePath;
	}
	public String getAdcGoodsName() {
		return adcGoodsName;
	}
	public void setAdcGoodsName(String adcGoodsName) {
		this.adcGoodsName = adcGoodsName;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Advertisement(String adcGoodsNo, String adImagePath,
			String adcGoodsName, String describe) {
		super();
		this.adcGoodsNo = adcGoodsNo;
		this.adImagePath = adImagePath;
		this.adcGoodsName = adcGoodsName;
		this.describe = describe;
	}
	public Advertisement() {
		super();
	}
	@Override
	public String toString() {
		return "Advertisement [adcGoodsNo=" + adcGoodsNo + ", adImagePath="
				+ adImagePath + ", adcGoodsName=" + adcGoodsName
				+ ", describe=" + describe + "]";
	}
	
}
