package cn.jpush.api;

/*
 * 发�?�消息回调的数据格式
 * 与消息发送接口的 callback_url 参数相对应，极光Push Server消息发�?�结束时，调用此回调接口，以向调用�?�反馈发送结果�??
 * 请开发�?�提供此接口支持 HTTP POST 请求�?
 * 请求参数名称是："push_result"�?
 */
public class CallbackMessage {
	//发�?�序�?
	private int sendno;
	//错误码，详见ErrorCodeEnum
	private int errcode;
	//错误说明
	private String errmsg;
	//本次推�?�满足条件的用户�?
	private int total_user;
	//本次推�?�成功发送的用户�?
	private int send_cnt;
	
	public int getSendno() {
		return sendno;
	}
	public void setSendno(int sendno) {
		this.sendno = sendno;
	}
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public int getTotal_user() {
		return total_user;
	}
	public void setTotal_user(int total_user) {
		this.total_user = total_user;
	}
	public int getSend_cnt() {
		return send_cnt;
	}
	public void setSend_cnt(int send_cnt) {
		this.send_cnt = send_cnt;
	}
	
}
