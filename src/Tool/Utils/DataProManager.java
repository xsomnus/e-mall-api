package Tool.Utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.google.gson.Gson;
import com.sun.xml.internal.xsom.impl.scd.Iterators;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import Tool.String_Tool;

/**
 * Created by xwy_brh on 2017/7/15.
 */
public class DataProManager {

    static Logger logger = Logger.getLogger(DataProManager.class);

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
                    if(String_Tool.isEmpty(value)){
                        value="";
                    }
                    try {
                        jsonObj.put(columnName, value);
                    } catch (JSONException e) {
                        logger.error("把结果集放到json中出现异常");
                        e.printStackTrace();
                    }
                }
                array.put(jsonObj);
            }
        } catch (SQLException e) {
            logger.error("把结果集异常"+e.getLocalizedMessage());
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
                    if(String_Tool.isEmpty(value)){
                        value="0";
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
    public String Map_To_JSON(List<Map<String,String>> list){
        Gson gson=new Gson();
        String str=gson.toJson(list);
        return str;
    }


    public static  JSONArray AppendWithJSON(JSONArray originArr, String key, JSONArray desArr) throws JSONException {
        try {
            int length = desArr.length();
            JSONObject o = desArr.getJSONObject(length-1);
            o.put(key, originArr);
            return desArr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static  JSONArray AppendWithJSONMap(HashMap<String, JSONArray> map, JSONArray desArr, String Is_Collect) throws JSONException{
        try {
            int length = desArr.length();
            if (length == 0) {
                return null;
            }
            JSONObject o = desArr.getJSONObject(length-1);
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                JSONArray value = (JSONArray) entry.getValue();
                o.put(key, value);
                o.put("Is_Collect", Is_Collect);
            }
            return desArr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

