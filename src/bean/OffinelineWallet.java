package bean;

public class OffinelineWallet {
	
//	String paycode=obj.getString("PayCode");
//	String money=obj.getString("Money");
//	String StoreNo=obj.getString("cStoreNo");
//	String cOSS_No=obj.getString("cOSS_No");
//	String sign=obj.getString("Sign");
	
	
	
	private String PayCode;
	private String SheetNo;
	private String BgnWMoney;
	private String SalMoney;
	private String EndWMoney;
	private String SalDate;
	private String Money;
	private String cStoreNo;
	private String cOSS_No;
	private String Sign;
	private String out_trade_no;
	private String oto_pid;
	private String err_code;
	private String errMsg;
	
	private String buyer_logon_id;  //用这个软件的id
	
	private String buyer_logon_Msg; //记录使用者的手机号，账号，位置信息
	
	
	
	
	public String getBuyer_logon_Msg() {
		return buyer_logon_Msg;
	}
	public void setBuyer_logon_Msg(String buyer_logon_Msg) {
		this.buyer_logon_Msg = buyer_logon_Msg;
	}
	public String getBuyer_logon_id() {
		return buyer_logon_id;
	}
	public void setBuyer_logon_id(String buyer_logon_id) {
		this.buyer_logon_id = buyer_logon_id;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getOto_pid() {
		return oto_pid;
	}
	public void setOto_pid(String oto_pid) {
		this.oto_pid = oto_pid;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getMoney() {
		return Money;
	}
	public void setMoney(String money) {
		Money = money;
	}
	public String getcStoreNo() {
		return cStoreNo;
	}
	public void setcStoreNo(String cStoreNo) {
		this.cStoreNo = cStoreNo;
	}
	public String getcOSS_No() {
		return cOSS_No;
	}
	public void setcOSS_No(String cOSS_No) {
		this.cOSS_No = cOSS_No;
	}
	public String getSign() {
		return Sign;
	}
	public void setSign(String sign) {
		Sign = sign;
	}
	public OffinelineWallet() {
		super();
	}
	public OffinelineWallet(String payCode, String sheetNo, String bgnWMoney,
			String salMoney, String endWMoney, String salDate) {
		super();
		PayCode = payCode;
		SheetNo = sheetNo;
		BgnWMoney = bgnWMoney;
		SalMoney = salMoney;
		EndWMoney = endWMoney;
		SalDate = salDate;
	}
	public String getPayCode() {
		return PayCode;
	}
	public void setPayCode(String payCode) {
		PayCode = payCode;
	}
	public String getSheetNo() {
		return SheetNo;
	}
	public void setSheetNo(String sheetNo) {
		SheetNo = sheetNo;
	}
	public String getBgnWMoney() {
		return BgnWMoney;
	}
	public void setBgnWMoney(String bgnWMoney) {
		BgnWMoney = bgnWMoney;
	}
	public String getSalMoney() {
		return SalMoney;
	}
	public void setSalMoney(String salMoney) {
		SalMoney = salMoney;
	}
	public String getEndWMoney() {
		return EndWMoney;
	}
	public void setEndWMoney(String endWMoney) {
		EndWMoney = endWMoney;
	}
	public String getSalDate() {
		return SalDate;
	}
	public void setSalDate(String salDate) {
		SalDate = salDate;
	}
}
