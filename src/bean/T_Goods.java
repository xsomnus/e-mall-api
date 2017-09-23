package bean;

public class T_Goods {
	
	private String cGoodsNo;
	private String fNormalPrice;
	private String cGoodsName;
	private String Recommend_price;
	private String cGoodsImagePath;
	
	
	public String getcGoodsImagePath() {
		return cGoodsImagePath;
	}
	public void setcGoodsImagePath(String cGoodsImagePath) {
		this.cGoodsImagePath = cGoodsImagePath;
	}
	public String getcGoodsName() {
		return cGoodsName;
	}
	public void setcGoodsName(String cGoodsName) {
		this.cGoodsName = cGoodsName;
	}
	public String getcGoodsNo() {
		return cGoodsNo;
	}
	public void setcGoodsNo(String cGoodsNo) {
		this.cGoodsNo = cGoodsNo;
	}

	public String getfNormalPrice() {
		return fNormalPrice;
	}
	public void setfNormalPrice(String fNormalPrice) {
		this.fNormalPrice = fNormalPrice;
	}
	public String getRecommend_price() {
		return Recommend_price;
	}
	public void setRecommend_price(String recommend_price) {
		Recommend_price = recommend_price;
	}
	
}
