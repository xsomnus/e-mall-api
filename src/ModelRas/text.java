package ModelRas;

import java.net.ServerSocket;
import java.net.Socket;

public class text {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//String info=paycode+money+StoreNo+cOSS_No;
		
        String info="195522723403203414"+"90"+"10001"+"PSOSS";
       // ServerSocket s=new ServerSocket();
        
        
        System.out.println(info);
       // 1929498870035224129001PSOSS
       //   192949887003522412901PSOSS
		System.out.println(MD5key.getMD5Pass(info));
//1bcc30d9f6c0071fbaba0d4007806738
	}

}
