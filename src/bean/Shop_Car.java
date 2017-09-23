package bean;
/**
 * 
 * @author Administrator
 *Shop_Car                          购物车表

UseNo	varchar(50)	Checked                 用户编号
Date_time	datetime	Checked                 时间
UserName	varchar(50)	Checked             用户名
cStoreNo	varchar(20)	Checked                 店铺编号
cGoodsNo	varchar(50)	Checked             商品编号
Last_Price	money	Checked                 价格（最终）
Num	   money	Checked                     数量
Last_Money	money	Checked                 钱数
 */
public class Shop_Car {
	private String UseNo;
	private String Date_time;
	private String UserName;
	private String cStoreNo;
	private String cGoodsNo;
	private String Last_Price;
	private String Num;
	private String Last_Money;
	private String cGoodsName;
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
	public Shop_Car(String useNo, String date_time, String userName,
			String cStoreNo, String cGoodsNo, String last_Price, String num,
			String last_Money) {
		super();
		this.UseNo = useNo;
		this.Date_time = date_time;
		this.UserName = userName;
		this.cStoreNo = cStoreNo;
		this.cGoodsNo = cGoodsNo;
		this.Last_Price = last_Price;
		this.Num = num;
		this.Last_Money = last_Money;
	}
	public Shop_Car() {
		super();
	}
	public String getUseNo() {
		return UseNo;
	}
	public void setUseNo(String useNo) {
		UseNo = useNo;
	}
	public String getDate_time() {
		return Date_time;
	}
	public void setDate_time(String date_time) {
		Date_time = date_time;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getcStoreNo() {
		return cStoreNo;
	}
	public void setcStoreNo(String cStoreNo) {
		this.cStoreNo = cStoreNo;
	}
	public String getcGoodsNo() {
		return cGoodsNo;
	}
	public void setcGoodsNo(String cGoodsNo) {
		this.cGoodsNo = cGoodsNo;
	}
	public String getLast_Price() {
		return Last_Price;
	}
	public void setLast_Price(String last_Price) {
		Last_Price = last_Price;
	}
	public String getNum() {
		return Num;
	}
	public void setNum(String num) {
		Num = num;
	}
	public String getLast_Money() {
		return Last_Money;
	}
	public void setLast_Money(String last_Money) {
		Last_Money = last_Money;
	}
	@Override
	public String toString() {
		return "Shop_Car [UseNo=" + UseNo + ", Date_time=" + Date_time
				+ ", UserName=" + UserName + ", cStoreNo=" + cStoreNo
				+ ", cGoodsNo=" + cGoodsNo + ", Last_Price=" + Last_Price
				+ ", Num=" + Num + ", Last_Money=" + Last_Money
				+ ", cGoodsName=" + cGoodsName + ", cGoodsImagePath="
				+ cGoodsImagePath + "]";
	}
	
	
	

	
}
