package Tool;
import java.util.HashMap;
import com.cloopen.rest.sdk.CCPRestSDK;

public class SDKTestSendTemplateSMS {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		GET_Tel_SMS("18310111662");
	}
	public static HashMap<String, Object> GET_Tel_SMS(String Tel){
		HashMap<String, Object> result = null;
		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount("8a216da85660607901566d0e77d4061c",
				"aad5b6cf1a134e74aaaba45f72b550c7");// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId("8a216da85660607901566d0e78300622");// 初始化应用ID
		int str = (int) ((Math.random() + 1) * 1000);
		result = restAPI.sendTemplateSMS(Tel, "109213", new String[] {""+str, "5分钟" });
		result.put("telcode", str);
		System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		return result;
	}

}
