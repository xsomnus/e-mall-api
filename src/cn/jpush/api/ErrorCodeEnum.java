package cn.jpush.api;

public enum ErrorCodeEnum {
	//没有错误，发送成�?
	NOERROR(0),
	
	//系统内部错误
	SystemError(10),
	
	//不支持GET请求
	NotSupportGetMethod(1001),
	
	//缺少必须参数
	MissingRequiredParameters(1002),
	
	//参数值不合法
	InvalidParameter(1003),
	
	//验证失败
	ValidateFailed(1004),
	
	//消息体太�?
	DataTooBig(1005),
	
	//IMEI不合�?
	InvalidIMEI(1007),
	
	//appkey不合�?
	InvalidAppKey(1008),
	
	//msg_content不合�?
	InvalidMsgContent(1010),
	
	//没有满足条件的推送目�?
	InvalidPush(1011),
	
	//IOS不支持自定义消息
	CustomMessgaeNotSupportIOS(1012);
	
	private final int value;
	private ErrorCodeEnum(final int value) {
		this.value = value;
	}
	public int value() {
		return this.value;
	}
}
