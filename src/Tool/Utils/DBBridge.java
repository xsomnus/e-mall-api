package Tool.Utils;

import DB.DB;
import Tool.String_Tool;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by xwy_brh on 2017/7/15.
 * 主要用来和数据库进行交互的类, 各个类方法的主要返回值
 * <1>JSONArray</1>
 * <2>JSON</2>
 * <3>JSON String</3>
 */
public class DBBridge {



    public static JSONArray Select_Goods_Info(Connection conn, String cGoodsNo, String userNo) {

        ResultSet rs=null;
        PreparedStatement past=null;
        ResultSet rs2=null;
        PreparedStatement past2=null;
        ResultSet rs3=null;
        PreparedStatement past3=null;
        String Is_collect = "0";
        try {
            //查询商品的基本信息
            String sql = "SELECT cGoodsNo,cUnit,cSpec,cGoodsImagePath,cGoodsName,Description,bFresh,bOnLine_Price,cFeatureTags,cMarketingTags, cSupportDistribution,cSupportMingRiSongDa FROM posmanagement_main.dbo.t_cStoreGoods where cGoodsNo=?";
            past = conn.prepareStatement(sql);
            past.setString(1, cGoodsNo);
            rs = past.executeQuery();

            String sql2 = "SELECT cGoodsNo, cSpecDescrible, cSpec, cSpecAmount, cUnit FROM Simple_online.dbo.PF_GoodsPrice WHERE cGoodsNo = ? GROUP BY cGoodsNo, cSpecDescrible, cSpec, cSpecAmount, cUnit";
            past2 = conn.prepareStatement(sql2);
            past2.setString(1, cGoodsNo);
            rs2 = past2.executeQuery();
            JSONArray rs2JsonArr = DataProManager.resultSetToJsonArray(rs2);
            for (int i = 0; i < rs2JsonArr.length(); i++) {
                JSONObject o = rs2JsonArr.getJSONObject(i);
                ResultSet tmpRS = null;
                PreparedStatement tmpPast = null;
                try {
                    String cSpecNo = o.getString("cGoodsNo");
                    String tmpSql = "SELECT cLower, cUpper, cPrice FROM Simple_online.dbo.PF_GoodsPrice WHERE cGoodsNo = ?";
                    tmpPast = conn.prepareStatement(tmpSql);
                    tmpPast.setString(1, cSpecNo);
                    tmpRS = tmpPast.executeQuery();
                    o.put("cSpecPrices", DataProManager.resultSetToJsonArray(tmpRS));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    DB.closeResultSet(tmpRS);
                    DB.closePreparedStatement(tmpPast);
                }
            }

            String sql3 = "SELECT cShowLevel, cGoodsImagePath FROM dbo.PF_GoodsImages WHERE cGoodsNo = ?";
            past3 = conn.prepareStatement(sql3);
            past3.setString(1, cGoodsNo);
            rs3 = past3.executeQuery();

            HashMap<String, JSONArray> map = new HashMap<>();
            map.put("cGoodsSpecs", rs2JsonArr);
            map.put("cGoodsImages", DataProManager.resultSetToJsonArray(rs3));
            PreparedStatement past2_2 = conn.prepareStatement(
                    "select * from  User_browse_collection where UserNo=? and cGoodsNo=? and Operation= '2' "); // 查询是不是收藏了此商品
            past2_2.setString(1, userNo);
            past2_2.setString(2, cGoodsNo);
            ResultSet rs2_2 = past2_2.executeQuery();
            if (rs2_2.next()) {
                Is_collect = "1"; // 收藏了
            }
            DB.closeResultSet(rs2_2);
            DB.closePreparedStatement(past2_2);

            return DataProManager.AppendWithJSONMap(map, DataProManager.resultSetToJsonArray(rs), Is_collect);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closePreparedStatement(past);
            DB.closeResultSet(rs2);
            DB.closePreparedStatement(past2);
            DB.closeResultSet(rs3);
            DB.closePreparedStatement(past3);
            DB.closeConn(conn);
        }
    }

    //猜你喜欢的结果
    public static  JSONArray Select_Recommended_Goods(Connection conn, String userNo) {

        ResultSet rs = null;
        PreparedStatement past = null;

        try {
            String sql = "SELECT TOP 16 cGoodsImagePath,cGoodsName,Description,Sale_number,Stock_number,Browse_number,Collect_number,cRetailPrice,cFeatureTags,cSupportDistribution,cOnlySupportZiti,cOnlySupportMingRiSongDa FROM T_GOODS  ORDER BY NEWID()";
            past = conn.prepareStatement(sql);
            rs = past.executeQuery();
            return  DataProManager.resultSetToJsonArray(rs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DB.closeRs_Con(rs, past, conn);
        }
    }


    //根据订单号, 查询用户的个人信息
    public static String Select_UserTel(Connection conn, String orderSheetNo) {


        return null;
    }


    //添加商品至购物车
    public static boolean  Add_Shop_Cart(Connection conn, String userNo, String cGoodsNo, String cStoreNo, String Num) {
        ResultSet rs = null;
        if (String_Tool.isEmpty(Num)) {
            Num = "1";
        }
        try {
            conn.setAutoCommit(false);
            PreparedStatement past = conn.prepareStatement("SELECT Num FROM Shop_Car WHERE cGoodsNo=? AND UserNo=? AND cStoreNo=? ");
            past.setString(1, cGoodsNo);
            past.setString(2, userNo);
            past.setString(3, cStoreNo);
            rs = past.executeQuery();
            if (rs.next()) {
                double currentNum = Double.parseDouble(rs.getString("Num"));
                double stepNum = Double.parseDouble(Num);
                double newNum = currentNum + stepNum;
                PreparedStatement past0 = conn.prepareStatement(
                        "Update Shop_Car SET Num=? WHERE cGoodsNo=? AND UserNo=? AND cStoreNo=?");
                past0.setString(1, "" + newNum);
                past0.setString(2, "" + cGoodsNo);
                past0.setString(3, "" + userNo);
                past0.setString(4, "" + cStoreNo);
                int b = past0.executeUpdate();
                System.out.println("修改数量" + b);
                DB.closeResultSet(rs);
                DB.closePreparedStatement(past0);
            } else {
                PreparedStatement past1 = conn.prepareStatement("INSERT INTO Shop_Car(UserNo,Date_time,UserName,cStoreNo,cGoodsNo,Num) values(?,?,?,?,?,?)");
                double stepNum = Double.parseDouble(Num);
                past1.setString(1, userNo);
                past1.setString(2, String_Tool.DataBaseTime());
                past1.setString(3, "");
                past1.setString(4, cStoreNo);
                past1.setString(5, cGoodsNo);
                past1.setString(6, ""+stepNum);

                past1.execute();
                DB.closePreparedStatement(past1);
            }
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DB.closeConn(conn);
        }
        return false;
    }


}
