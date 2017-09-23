package cn.jpush.api;

public enum ReceiverTypeEnum {
	//指定�? IMEI。此时必须指�? appKeys�?
	IMEI(1),
	
	//指定�? tag�?
	TAG(2),
	
	//指定�? alias�?
	ALIAS(3),
	
	//对指定appkeys 的所有用户推送消息�??
	APPKEYS(4);
	
	private final int value;
	private ReceiverTypeEnum(final int value) {
		this.value = value;
	}
	public int value() {
		return this.value;
	}
}
