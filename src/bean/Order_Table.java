package bean;
/**
 * 
 * @author Administrator
 *Order_Table                      订单表
         
cSheetno	varchar(50)	Unchecked              单号   用（UUID生成）
Date_time	datetime	Unchecked              单号时间
AddressID  varchar(20)                        地址ID
Total_money	money	Unchecked              最终钱数
Pay_state	nchar(10)	Unchecked   付款状态，0是待付款，1是待发货，2是待收货，3是完成
Notes	varchar(50)	Checked                备注
 */
public class Order_Table {
	private String cSheetno;
	private String Date_time;
	private String Total_money;
	private String AddressID;
	private String Pay_state;
	private String Notes;
	public Order_Table(String cSheetno, String date_time, String total_money,
			String addressID, String pay_state, String notes) {
		super();
		this.cSheetno = cSheetno;
		this.Date_time = date_time;
		this.Total_money = total_money;
		this.AddressID = addressID;
		this.Pay_state = pay_state;
		this.Notes = notes;
	}
	public Order_Table() {
		super();
	}
	public String getcSheetno() {
		return cSheetno;
	}
	public void setcSheetno(String cSheetno) {
		this.cSheetno = cSheetno;
	}
	public String getDate_time() {
		return Date_time;
	}
	public void setDate_time(String date_time) {
		Date_time = date_time;
	}
	public String getTotal_money() {
		return Total_money;
	}
	public void setTotal_money(String total_money) {
		Total_money = total_money;
	}
	public String getAddressID() {
		return AddressID;
	}
	public void setAddressID(String addressID) {
		AddressID = addressID;
	}
	public String getPay_state() {
		return Pay_state;
	}
	public void setPay_state(String pay_state) {
		Pay_state = pay_state;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	@Override
	public String toString() {
		return "Order_Table [cSheetno=" + cSheetno + ", Date_time=" + Date_time
				+ ", Total_money=" + Total_money + ", AddressID=" + AddressID
				+ ", Pay_state=" + Pay_state + ", Notes=" + Notes + "]";
	}
	
	
	
}
