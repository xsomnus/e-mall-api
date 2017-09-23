package bean;

import java.util.List;

public class Request_Order_Json {
	
	private String cSheetno;
	private String Date_time;
    private List<Order_Details> details;
	private User_Address user_address;
	
	
	private String Send_Money;
	private String Total_money;
	private String All_Money;
	private String  Pay_wayId;
	
	private String cTel;
	private String cStoreName;

	
	
	public String getcTel() {
		return cTel;
	}
	public void setcTel(String cTel) {
		this.cTel = cTel;
	}
	public String getcStoreName() {
		return cStoreName;
	}
	public void setcStoreName(String cStoreName) {
		this.cStoreName = cStoreName;
	}
	public String getSend_Money() {
		return Send_Money;
	}
	public void setSend_Money(String send_Money) {
		Send_Money = send_Money;
	}
	public String getTotal_money() {
		return Total_money;
	}
	public void setTotal_money(String total_money) {
		Total_money = total_money;
	}
	public String getAll_Money() {
		return All_Money;
	}
	public void setAll_Money(String all_Money) {
		All_Money = all_Money;
	}
	public String getPay_wayId() {
		return Pay_wayId;
	}
	public void setPay_wayId(String pay_wayId) {
		Pay_wayId = pay_wayId;
	}
	public String getDate_time() {
		return Date_time;
	}
	public void setDate_time(String date_time) {
		Date_time = date_time;
	}
	
	
	public String getcSheetno() {
		return cSheetno;
	}
	public void setcSheetno(String cSheetno) {
		this.cSheetno = cSheetno;
	}
	public List<Order_Details> getDetails() {
		return details;
	}
	public void setDetails(List<Order_Details> details) {
		this.details = details;
	}
	public User_Address getUser_address() {
		return user_address;
	}
	public void setUser_address(User_Address user_address) {
		this.user_address = user_address;
	}

	
}
