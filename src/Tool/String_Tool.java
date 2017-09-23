package Tool;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

public class String_Tool {

	public static String String_IS_Four(double d) { // 精确到四位小说
		DecimalFormat df = new DecimalFormat("0.0000");
		return df.format(d);
	}

	public static String String_IS_Four(int d) { // 精确到四位小说
		DecimalFormat df = new DecimalFormat("0000");
		return df.format(d);
	}

	public static String DataBaseTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String time = simpleDateFormat.format(new Date());
		return time;
	}
	public static String DataBaseTime_MM() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmm");
		String time = simpleDateFormat.format(new Date());
		return time;
	}

	public static String DataBaseYear() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		String time = simpleDateFormat.format(new Date());
		return time;
	}

	public static String DataBaseYear_Month_Day() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = simpleDateFormat.format(new Date());
		return time;
	}

	public static String DataBaseH_M_S() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		String time = simpleDateFormat.format(new Date());
		return time;
	}

	public static String DataBaseH_M_S_String() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
		String time = simpleDateFormat.format(new Date());
		return time;
	}

	public static String DataBaseM() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
		String time = simpleDateFormat.format(new Date());
		return time;
	}

	public static String reformat() {
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		try {
			String dateString = formatter.format(today);
			return dateString;
		} catch (IllegalArgumentException iae) {

		}
		return null;
	}

	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		} else if (str.equals("null")) {
			return true;
		} else if (str.equals("NULL")) {
			return true;
		} else if (str.equals("")) {
			return true;
		} else if (str.equals("''")) {
			return true;
		} else {
			return false;
		}
	}
	public static String get_output_str(JSONArray array){
	    String str="{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + array.toString().replace(" ", "") + "}";
		if(array!=null&&array.length()>0){
			 str="{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.toString().replace(" ", "") + "}";
		}
		else{
			str="{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + array.toString().replace(" ", "") + "}";
		}
		return str;
	}
	public static String get_output_str(JSONObject obj){
	    String str="{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + obj.toString().replace(" ", "") + "}";
		if(obj!=null){
			 str="{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + obj.toString().replace(" ", "") + "}";
		}
		return str;
	}


	public static void main(String args[]) {
		System.out.println(reformat());
		System.out.println(DataBaseH_M_S());

		System.out.println(String_IS_Four(100));
		System.out.println(UUID.randomUUID());
	}

}
