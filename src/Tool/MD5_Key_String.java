package Tool;

import ModelRas.MD5key;

public class MD5_Key_String {
	
	public static String MD5_KEY="warelucent"; //把这和价签拼接的字符串一起MD5
	
	//public static String MD5_KEY=new ReadConfig().getprop().getProperty("key");
	//钱包用户编号,钱数,和LUFAWANJIA;
	//可以自己修改
	
	public static void main(String args[]){
		// MD5_KEY=new ReadConfig().getprop().getProperty("key");
		System.out.println(MD5_KEY);
		
		String str = MD5key.getMD5Pass("15510578956"+"69.7000"+ MD5_Key_String.MD5_KEY);
		System.out.println(str);
	}
	
}

   