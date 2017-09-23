package Tool;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class ResultSet_To_JSON {

	static Logger logger = Logger.getLogger(ResultSet_To_JSON.class);

	public static String resultSetToJson(ResultSet rs) {
		JSONArray array = new JSONArray();
		try {
			// 获取列数
			ResultSetMetaData metaData;
			metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			// 遍历ResultSet中的每条数据
			while (rs.next()) {
				JSONObject jsonObj = new JSONObject();
				// 遍历每一列
				for (int i = 1; i <= columnCount; i++) { // 列数
					String columnName = metaData.getColumnLabel(i);
					String value = rs.getString(columnName);
					if (String_Tool.isEmpty(value)) {
						value = "";
					}
					try {
						jsonObj.put(columnName, value);
					} catch (JSONException e) {
						logger.error("把结果集放到json中异常");
						e.printStackTrace();
					}
				}
				array.put(jsonObj);
			}
		} catch (SQLException e) {
			logger.error("把结果集异常" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return array.toString().replace(" ", "");
	}

	public static JSONArray resultSetToJsonArray(ResultSet rs) {
		JSONArray array = new JSONArray();
		try {
			// 获取列数
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			// 遍历ResultSet中的每条数据
			while (rs.next()) {
				JSONObject jsonObj = new JSONObject();
				// 遍历每一列
				for (int i = 1; i <= columnCount; i++) { // 列数
					String columnName = metaData.getColumnLabel(i);
					String value = rs.getString(columnName);
					if (String_Tool.isEmpty(value)) {
						value = "";
					}
					jsonObj.put(columnName, value);
				}
				array.put(jsonObj);
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return array;
	}

	public static JSONObject resultSetToJsonObject(ResultSet rs) {
		JSONObject jsonObj = new JSONObject();
		try {
			// 获取列数
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			// 遍历ResultSet中的每条数据
			if (rs.next()) {
				jsonObj = new JSONObject();
				// 遍历每一列
				for (int i = 1; i <= columnCount; i++) { // 列数
					String columnName = metaData.getColumnLabel(i);
					String value = rs.getString(columnName);
					if (String_Tool.isEmpty(value)) {
						value = "";
					}
					jsonObj.put(columnName, value.replace(" ", ""));
				}
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return jsonObj;
	}

	public String Map_To_JSON(List<Map<String, String>> list) {
		Gson gson = new Gson();
		String str = gson.toJson(list);
		return str;
	}
	
	public static String Map_To_JSONStringWithTypeKeyAndValue(HashMap<String, JSONArray> resultDic){
		String str = "[";

		Iterator iter = resultDic.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			Object cParentNo = "--";
			str += "{\"type\"" + ":\"" + key + "\"," + " \" cParentNo \" "+ ":\" " + cParentNo + "\"," + " \" goodsData \"" + ":" + val + "},";
		}
		str = str.substring(0, str.length()-1);
		str += "]";
		return str.replace(" ", "");
	}


	public static <T> String getKeyByValue(String value, HashMap<String, T> keyDic) {
		String key = null;
		for (String getKey : keyDic.keySet()) {
			if (keyDic.get(getKey).equals(value)) {
				key = getKey;
			}
		}
		return key;
	}

	public static <T> String Map_To_JSONString(HashMap<String, T> hashmap) {
		String str = "";
		Iterator iter = hashmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			str += " \" " + key + " \" " + ":" + val + ",";
		}
		str = str.substring(0, str.length() - 1);
		str = str + "";
		return str.replace(" ", "");
	}

}
