package bean;
/**
 * User_Table                        用户表
UserNo    varchar(20) Checked                   用户编号，服务器生成
Email	varchar(50)	Checked                   邮箱
Tel	varchar(20)	Unchecked                     手机号
UserPass	varchar(50)	Unchecked                 MD5加密
UserName	varchar(50)	Checked               用户名
 * @author Administrator
 *
 */
public class User_Table {
	private String UserNo;
	private String Email;
	private String Tel;
	private String UserPass;
	private String UserName;
	public String getUserNo() {
		return UserNo;
	}
	public void setUserNo(String userNo) {
		UserNo = userNo;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getTel() {
		return Tel;
	}
	public void setTel(String tel) {
		Tel = tel;
	}
	public String getUserPass() {
		return UserPass;
	}
	public void setUserPass(String userPass) {
		UserPass = userPass;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public User_Table(String userNo, String email, String tel, String userPass,
			String userName) {
		super();
		this.UserNo = userNo;
		this.Email = email;
		this.Tel = tel;
		this.UserPass = userPass;
		this.UserName = userName;
	}
	public User_Table() {
		super();
	}
	@Override
	public String toString() {
		return "User_Table [UserNo=" + UserNo + ", Email=" + Email + ", Tel="
				+ Tel + ", UserPass=" + UserPass + ", UserName=" + UserName
				+ "]";
	}
	
}
