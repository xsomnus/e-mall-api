package ModelRas;

import java.util.Map;

public class RasHelp {

	public static Map<String, Object> keyMap;

	public static Map<String, Object> getmap() throws Exception {
		if (keyMap == null) {
			keyMap = Coder.RasGetPut.initKey();
		}
		return keyMap ;
	}

	public static String getPublicKey() throws Exception {
		String getPublicKey = Coder.RasGetPut.getPublicKey(getmap()); // 取得公钥
	
		return getPublicKey;
	}

	public static String getPrivateKey() throws Exception {
		String getPrivateKey = Coder.RasGetPut.getPrivateKey(getmap()); // 取得私钥
		
		return getPrivateKey;
	}

	public static boolean judge(byte data[], String getPublicKey, String sign) {
		try {
			boolean istrue = Coder.RasGetPut.verify(data, getPublicKey, sign);
			return istrue;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getstr(byte data[], String privateKey) {
		try {
			byte[] a1 = Coder.RasGetPut.decryptByPrivateKey(data, privateKey);
			System.out.println(a1);
			String str = new String(a1,"utf-8");
			return str;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
}
