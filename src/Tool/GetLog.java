package Tool;

import org.apache.log4j.Logger;

public class GetLog {
	
	public static <T> Logger getLogger (Class<T> a){
		Logger logger = Logger.getLogger(a);
		return logger;
	}
	
	public static void main(String args[]){
		System.out.println(123);
		try{
		     System.out.println(3/0);;
		}catch (Exception e) {
			GetLog.getLogger(GetLog.class).error(e.getLocalizedMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.toString());
		}
		
	}
}
