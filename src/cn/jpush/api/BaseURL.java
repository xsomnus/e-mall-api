package cn.jpush.api;

public class BaseURL {
	//添加代理时需修改 HOST_NAME �? ALL_PATH 路径根据自己的需要配�?
	public static String HOST_NAME_SSL = "https://api.jpush.cn:443";
	public static String HOST_NAME = "http://api.jpush.cn:8800";
	protected static final String ALL_PATH = "/sendmsg/v2/sendmsg";       //全功能，发�?�地�?
	protected static final String SIMPLE_CUSTOM_PATH = "/sendmsg/v2/custom_message"; //�?易接口，自定义消�?
	protected static final String SIMPLE_NOTIFICATION_PATH = "/sendmsg/v2/notification"; //�?易接口，自定义�?�知
	private static String getHostname(boolean enableSSL) {
		return enableSSL? HOST_NAME_SSL :HOST_NAME;
	}

	public  static String getUrlForPath(final String path,boolean enableSSL) {
		return getHostname(enableSSL) + path;
	}
	
}
