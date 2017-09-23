package bean;
/**
 * User_browse_collection              用户收藏浏览记录

UserNo	varchar(50)	 Unchecked                 用户编号
cGoodsNo	nchar(10)	 Checked                   商品编号
Operation	nchar(10)	 Checked                   1是浏览，2是收藏
 * @author Administrator
 *
 */
public class User_browse_collection {
	private String UserNo;
	private String cGoodsNo;
	private String Operation;
	public String getUserNo() {
		return UserNo;
	}
	public void setUserNo(String userNo) {
		UserNo = userNo;
	}
	public String getcGoodsNo() {
		return cGoodsNo;
	}
	public void setcGoodsNo(String cGoodsNo) {
		this.cGoodsNo = cGoodsNo;
	}
	public String getOperation() {
		return Operation;
	}
	public void setOperation(String operation) {
		Operation = operation;
	}
	public User_browse_collection(String userNo, String cGoodsNo,
			String operation) {
		super();
		this.UserNo = userNo;
		this.cGoodsNo = cGoodsNo;
		this.Operation = operation;
	}
	public User_browse_collection() {
		super();
	}
	@Override
	public String toString() {
		return "User_browse_collection [UserNo=" + UserNo + ", cGoodsNo="
				+ cGoodsNo + ", Operation=" + Operation + "]";
	}
	
	

}
