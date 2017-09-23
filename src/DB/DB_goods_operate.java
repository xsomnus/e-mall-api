package DB;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Tool.Paging_Index_Sql;
import Tool.ResultSet_To_JSON;
import Tool.String_Tool;
import bean.T_JSON;
import bean.T_PloyOfSale;

public class DB_goods_operate {

	public static JSONArray All_cSoreNo(Connection conn) {
		PreparedStatement past = null;
		ResultSet rs = null;
		try {
			String sql = "select cStoreNo,cStoreName,cTel,cStyle,image_path,longitude,latitude from dbo.t_Store  where cParentNo <> '--' and cStoreNo<> '1099' ";
			System.out.println(sql);
			past = conn.prepareStatement(sql);
			rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			System.out.println(array.toString());
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}
	public static JSONArray All_MarketIp(Connection conn) {
		PreparedStatement past = null;
		ResultSet rs = null;
		try {
			String sql = "select * from MarketIp";
			System.out.println(sql);
			past = conn.prepareStatement(sql);
			rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			System.out.println(array.toString());
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static void main(String args[]) {
		// System.out.println(JSON(GetConnection.getSpuerConn(), "102",
		// "1","000","123"));
	}

	public static JSONArray JSON(Connection conn, String cGroupTypeNo, String Number_of_pages, String cStoreNo,
			String UserNo) {
		ResultSet rs = null;
		CallableStatement c = null;
		try {
			c = conn.prepareCall("{call Select_Type_Goods (?,?,?,?)}");
			c.setString(1, cGroupTypeNo);
			c.setString(2, Number_of_pages);
			c.setString(3, "000"); // cStoreNo
			c.setString(4, UserNo); // UserNo
			rs = c.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, c, conn);
		}
		return null;
	}

	public static JSONArray goods_def(Connection conn, String fage, String Number_of_pages, String cStoreNo) {
		PreparedStatement past = null;
		ResultSet rs = null;
		try {
			String sql = "  SELECT TOP 10 * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY a.cGoodsNo) AS RowNumber,a.cGoodsNo,a.cGoodsName,a.cGoodsImagePath,a.bFresh,b.fNormalPrice,b.fVipPrice,b.bOnLine_Price,b.cUnit,b.cSpec FROM posmanagement_main.dbo.t_Goods a  ,posmanagement_main.dbo.t_cStoreGoods b where  cStoreNo=? and a.cGoodsNo=b.cGoodsNo and b.bOnLine='1' )  as A WHERE RowNumber > 10*(?-1)  ";
			past = conn.prepareStatement(sql);
			past.setString(1, cStoreNo);
			past.setString(2, Number_of_pages);
			rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static JSONArray goods_price(Connection conn, String fage, String Number_of_pages, String cStoreNo) {
		PreparedStatement past = null;
		ResultSet rs = null;
		String sql = "";
		try {
			if (fage.equals("0")) {
				sql = "SELECT TOP 10 * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY b.fNormalPrice asc) AS RowNumber,a.cGoodsNo,a.cGoodsName,a.cGoodsImagePath,a.bFresh,b.fNormalPrice,b.fVipPrice,b.bOnLine_Price,b.cUnit,b.cSpec FROM posmanagement_main.dbo.t_Goods a  ,posmanagement_main.dbo.t_cStoreGoods b where  cStoreNo=? and a.cGoodsNo=b.cGoodsNo and b.bOnLine='1')  as A WHERE RowNumber > 10*(?-1) ";

			} else {
				sql = "SELECT TOP 10 * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY b.fNormalPrice desc) AS RowNumber,a.cGoodsNo,a.cGoodsName,a.cGoodsImagePath,a.bFresh,b.fNormalPrice,b.fVipPrice,b.bOnLine_Price,b.cUnit,b.cSpec FROM posmanagement_main.dbo.t_Goods a  ,posmanagement_main.dbo.t_cStoreGoods b where  cStoreNo=? and a.cGoodsNo=b.cGoodsNo and b.bOnLine='1')  as A WHERE RowNumber > 10*(?-1) ";
			}
			past = conn.prepareStatement(sql);
			past.setString(1, cStoreNo);
			past.setString(2, Number_of_pages);
			rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static JSONArray goods_sale_num(Connection conn, String fage, String Number_of_pages, String cStoreNo) {
		PreparedStatement past = null;
		ResultSet rs = null;
		String sql = "";
		try {
			if (fage.equals("0")) {
				sql = "SELECT TOP 10 * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY a.Sale_number asc) AS RowNumber,a.cGoodsNo,a.cGoodsName,a.cGoodsImagePath,a.bFresh,b.fNormalPrice,b.fVipPrice,b.bOnLine_Price,b.cUnit,b.cSpec FROM posmanagement_main.dbo.t_Goods a  ,posmanagement_main.dbo.t_cStoreGoods b where  cStoreNo=? and a.cGoodsNo=b.cGoodsNo and b.bOnLine='1')  as A WHERE RowNumber > 10*(?-1) ";
			} else {
				sql = "SELECT TOP 10 * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY a.Sale_number desc) AS RowNumber,a.cGoodsNo,a.cGoodsName,a.cGoodsImagePath,a.bFresh,b.fNormalPrice,b.fVipPrice,b.bOnLine_Price,b.cUnit,b.cSpec  FROM posmanagement_main.dbo.t_Goods a  ,posmanagement_main.dbo.t_cStoreGoods b where  cStoreNo=? and a.cGoodsNo=b.cGoodsNo and b.bOnLine='1')  as A WHERE RowNumber > 10*(?-1) ";
			}
			past = conn.prepareStatement(sql);
			past.setString(1, cStoreNo);
			past.setString(2, Number_of_pages);
			rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static JSONArray goods_browse_num(Connection conn, String fage, String Number_of_pages, String cStoreNo) {
		PreparedStatement past = null;
		ResultSet rs = null;
		String sql = "";
		try {
			if (fage.equals("0")) {
				sql = "SELECT TOP 10 * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY a.Browse_number asc) AS RowNumber,a.cGoodsNo,a.cGoodsName,a.cGoodsImagePath,a.bFresh,b.fNormalPrice,b.fVipPrice,b.bOnLine_Price,b.cUnit,b.cSpec FROM posmanagement_main.dbo.t_Goods a  ,posmanagement_main.dbo.t_cStoreGoods b where  cStoreNo=? and a.cGoodsNo=b.cGoodsNo and b.bOnLine='1')  as A WHERE RowNumber > 10*(?-1) ";
			} else {
				sql = "SELECT TOP 10 * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY a.Browse_number desc) AS RowNumber,a.cGoodsNo,a.cGoodsName,a.cGoodsImagePath,a.bFresh,b.fNormalPrice,b.fVipPrice,b.bOnLine_Price,b.cUnit,b.cSpec FROM posmanagement_main.dbo.t_Goods a  ,posmanagement_main.dbo.t_cStoreGoods b where  cStoreNo=? and a.cGoodsNo=b.cGoodsNo and b.bOnLine='1')  as A WHERE RowNumber > 10*(?-1) ";
			}
			past = conn.prepareStatement(sql);
			past.setString(1, cStoreNo);
			past.setString(2, Number_of_pages);
			rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static JSONArray goods_like_serach(Connection conn, String fage, int Number_of_pages, String cStoreNo) {
		PreparedStatement past = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = " SELECT TOP 10 * FROM ( SELECT ROW_NUMBER() OVER ( ORDER BY b.fNormalPrice desc) AS RowNumber,a.cGoodsNo,a.cGoodsName,a.cGoodsImagePath,a.bFresh,b.fNormalPrice,b.fVipPrice,b.bOnLine_Price,b.cUnit,b.cSpec FROM posmanagement_main.dbo.t_Goods a  ,posmanagement_main.dbo.t_cStoreGoods b where  b.cStoreNo='"
					+ cStoreNo + "' and a.cGoodsNo=b.cGoodsNo and b.bOnLine='1' and ( b.cGoodsName like '%" + fage
					+ "%'  or b.cGoodsNo  =?  or b.cBarcode =?  ) )as A WHERE RowNumber > 10*(" + (Number_of_pages - 1)
					+ ")";
			System.out.println("搜索" + sql);
			past = conn.prepareStatement(sql);
			past.setString(1, "" + fage);
			past.setString(2, "" + fage);
			System.out.println(fage);
			rs = past.executeQuery();
			System.out.println(past.toString());
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static String upload_order(Connection conn, String UserNo, JSONArray array, String AddressID, String Notes,
			String freight, String cStoreNo, String Send_Way, String Cover_Fresh, String Start_time, String End_time,
			String Send_cStoreNo) {
		String cSheetno = cStoreNo + String_Tool.reformat() + UserNo;
		double money = 0;
		try {
			System.out.println(UserNo);
			System.out.println(array);
			System.out.println(AddressID);
			System.out.println(Notes);
			System.out.println("提货方式" + Send_Way);
			System.out.println(Cover_Fresh);
			System.out.println(Start_time);
			System.out.println(End_time);
			conn.setAutoCommit(false);

			PreparedStatement past0 = conn
					.prepareStatement("delete from  dbo.Shop_Car where UserNo =? and cGoodsNo =?"); // 删除购物车
			PreparedStatement past1 = conn.prepareStatement(
					"INSERT INTO dbo.Order_Details (cSheetno,cGoodsNo,cGoodsName,Num,Last_Price,Last_Money,RealityNum,Reality_Money) values(?,?,?,?,?,?,?,?)");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				past1.setString(1, cSheetno);
				past1.setString(2, obj.getString("cGoodsNo"));
				past1.setString(3, obj.getString("cGoodsName"));
				past1.setString(4, obj.getString("Num"));
				past1.setString(5, obj.getString("Last_Price"));
				past1.setString(6, obj.getString("Last_Money"));
				past1.setString(7, obj.getString("Num"));
				past1.setString(8, obj.getString("Last_Money"));//商品的总钱数
				money = money + Double.parseDouble(obj.getString("Last_Money"));
				past1.addBatch();
				/* 删除购物车 */
				past0.setString(1, UserNo);
				past0.setString(2, obj.getString("cGoodsNo"));
				past0.addBatch();
			}
			past1.executeBatch();
			DB.closePreparedStatement(past1);
			past0.executeBatch();
			DB.closePreparedStatement(past0);

			PreparedStatement past2 = conn.prepareStatement("INSERT INTO dbo.Order_Table (cSheetno,UserNo,Date_time,Total_money,AddressID,Pay_state,Notes,Send_Money,Order_State,cStoreNo,All_Money,Send_Way,Cover_Fresh,Start_time, End_time,Send_cStoreNo,Reality_All_Money,Pay_wayId) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			past2.setString(1, cSheetno);
			past2.setString(2, UserNo);
			past2.setString(3, String_Tool.DataBaseTime());
			past2.setString(4, "" + money);
			past2.setString(5, AddressID);
			past2.setString(6, "0");
			past2.setString(7, Notes);
			
			past2.setString(8, freight);
			past2.setString(9, "1");
			past2.setString(10, cStoreNo);
			past2.setString(11, "" + (money + Double.parseDouble(freight)));
			past2.setString(12, "" + Send_Way);
			past2.setString(13, "" + Cover_Fresh);

			past2.setString(14, "" + Start_time);
			past2.setString(15, "" + End_time);// Send_cStoreNo
			past2.setString(16, "" + Send_cStoreNo);//
			past2.setString(17, "" + (money + Double.parseDouble(freight)));//
			past2.setString(18, "0");//
			System.out.println("" + (money + Double.parseDouble(freight)));
			past2.execute();
			DB.closePreparedStatement(past2);

			if (DB_goods_operate.is_FirstOrder(GetConnection.getSpuerConn(), UserNo)) {
			    PreparedStatement past3 = conn.prepareStatement("UPDATE Simple_online.dbo.User_Table SET cNineOffSheetNo = ?");
			    past3.setString(1, cSheetno);
			    past3.execute();
			    DB.closePreparedStatement(past3);
            }
			conn.commit();
			conn.setAutoCommit(true);
			return cSheetno;
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
		return null;
	}

	/***Ego-Start**/
	public static boolean is_FirstOrder(Connection conn, String UserNo) throws SQLException {
		ResultSet rs = null;
		PreparedStatement past = null;
		try {
			String isFirstOrder = "0";
			past = conn.prepareStatement("SELECT Top 1 cNineOffSheetNo FROM Simple_online.dbo.User_Table WHERE UserNo = ?");
			past.setString(1, UserNo);
			rs = past.executeQuery();
			if (rs.next()) {
				isFirstOrder = rs.getString("cNineOffSheetNo");
				if (String_Tool.isEmpty(isFirstOrder)) {
					return true;
				}
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
	}

	public static String Select_GoodsDetail(Connection conn, String UserNo, String cGoodsNo, String cStoreNo) {
		ResultSet rs = null;
		String sql = "";
		String Is_collect = "0";
		try {
			sql = "select a.cGoodsImagePath,a.cGoodsName,a.Description,a.Sale_number,a.Stock_number,a.Browse_number,a.Collect_number,b.cUnit,b.cSpec,b.bOnLine_Price,b.fNormalPrice from posmanagement_main.dbo.T_goods  a ,posmanagement_main.dbo.t_cStoreGoods b  where  a.cGoodsNo=? and b.cStoreNo=? and a.cGoodsNo=b.cGoodsNo";
			PreparedStatement past = conn.prepareStatement(sql);
			past.setString(1, cGoodsNo);
			past.setString(2, cStoreNo);
			rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs); // 查询商品详情
			DB.closeResultSet(rs);

			sql = " select UserNo,dDate,Appraisecontent from dbo.Goods_Appraise  where  cGoodsNo=?  ";
			PreparedStatement past1 = conn.prepareStatement(sql);
			past1.setString(1, cGoodsNo);
			rs = past1.executeQuery();
			JSONArray array1 = ResultSet_To_JSON.resultSetToJsonArray(rs); // 查询商品评价
			DB.closeResultSet(rs);

			if (!String_Tool.isEmpty(UserNo)) {
				PreparedStatement past2_1 = conn.prepareStatement(
						"select * from  User_browse_collection where UserNo=? and cGoodsNo=? and Operation= '1' "); // 1是浏览
				// 2是收藏
				past2_1.setString(1, UserNo);
				past2_1.setString(2, cGoodsNo);
				ResultSet rs2_1 = past2_1.executeQuery();
				if (rs2_1.next()) {
					PreparedStatement past2_2 = conn.prepareStatement("Update  User_browse_collection set Browse_number =(ISNULL(Browse_number,0)+'1')  where UserNo=? and cGoodsNo=? and Operation= '1' "); // 1是浏览
																																									// //																																		// 2是收藏
					past2_2.setString(1, UserNo);
					past2_2.setString(2, cGoodsNo);
					past2_2.execute();
					DB.closePreparedStatement(past2_2);
				} else {
					PreparedStatement past2 = conn.prepareStatement(
							"INSERT INTO User_browse_collection (UserNo,cGoodsNo,Operation,Browse_number,Date_time,cStoreNo) Values  (?,?,?,?,?,?)");
					past2.setString(1, UserNo);
					past2.setString(2, cGoodsNo);
					past2.setString(3, "1"); // 1是浏览 2是收藏
					past2.setString(4, "1"); // 1是浏览 2是收藏
					past2.setString(5, String_Tool.DataBaseTime()); // 1是浏览 2是收藏
					past2.setString(6, cStoreNo); // 1是浏览 2是收藏
					past2.execute();
					DB.closePreparedStatement(past2);
				} // 如果有已经浏览就数量+1

				PreparedStatement past2_2 = conn.prepareStatement(
						"select * from  User_browse_collection where UserNo=? and cGoodsNo=? and Operation= '2' "); // 查询是不是收藏了此商品
				past2_2.setString(1, UserNo);
				past2_2.setString(2, cGoodsNo);
				ResultSet rs2_2 = past2_2.executeQuery();
				if (rs2_2.next()) {
					Is_collect = "1"; // 收藏了
				}
				DB.closeResultSet(rs2_2);
				DB.closePreparedStatement(past2_2);
			}

			PreparedStatement past3 = conn.prepareStatement(
					"update posmanagement_main.dbo.T_goods set Browse_number =(ISNULL(Browse_number,0)+'1')");
			past3.execute();

			PreparedStatement past4 = conn.prepareStatement("select Pay_wayId, Describe from dbo.Pay_way_Table");
			rs = past4.executeQuery();
			JSONArray array2 = ResultSet_To_JSON.resultSetToJsonArray(rs);
			DB.closePreparedStatement(past);
			DB.closePreparedStatement(past1);

			DB.closePreparedStatement(past3);
			String str = "{\"resultStatus\":\"" + 1 + "\"," + "\"array\":" + array.toString().replace(" ", "") + ","
					+ "\"array1\":" + array1.toString().replace(" ", "") + "," + "\"array2\":"
					+ array2.toString().replace(" ", "") + "," + "\"Is_collect\":\"" + Is_collect + "\"}";
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeConn(conn);
		}
		return null;
	}

	public static boolean Add_Shop_Car(Connection conn, String UserNo, String cGoodsNo, String Price, String cStoreNo) {
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			PreparedStatement past = conn.prepareStatement(
					"select Last_Price,Num from Shop_Car where cGoodsNo=? and UserNo=? and cStoreNo=? ");
			past.setString(1, cGoodsNo);
			past.setString(2, UserNo);
			past.setString(3, cStoreNo);
			rs = past.executeQuery();
			if (rs.next()) {
				double Price_Last = Double.parseDouble(rs.getString("Last_Price"));
				double num = Double.parseDouble(rs.getString("Num"));
				if (Price_Last > Double.parseDouble(Price)) {
					Price_Last = Double.parseDouble(Price);
				}
				double nownum = num + 1;
				double nowmoney = nownum * Price_Last;
				PreparedStatement past0 = conn.prepareStatement(
						"Update Shop_Car set Num=?,Last_Price=?,Last_Money=?  where cGoodsNo=? and UserNo=? and cStoreNo=? ");
				past0.setString(1, "" + nownum);
				past0.setString(2, "" + Price_Last);
				past0.setString(3, "" + nowmoney);
				past0.setString(4, "" + cGoodsNo);
				past0.setString(5, "" + UserNo);
				past0.setString(6, "" + cStoreNo);
				int b = past0.executeUpdate();
				System.out.println("修改数量" + b);
				DB.closeResultSet(rs);
				DB.closePreparedStatement(past0);
			} else {
				PreparedStatement past1 = conn.prepareStatement(
						"INSERT INTO Shop_Car  (UserNo,Date_time,UserName,cStoreNo,cGoodsNo,Last_Price,Num,Last_Money) values(?,?,?,?,?,?,?,?)");
				past1.setString(1, UserNo);
				past1.setString(2, String_Tool.DataBaseTime());
				past1.setString(3, "");
				past1.setString(4, cStoreNo);
				past1.setString(5, cGoodsNo);
				past1.setString(6, Price);
				past1.setString(7, "1");
				past1.setString(8, Price);
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

	public static int add_(Connection conn, String UserNo, String cGoodsNo, String cStoreNo) {
		// 0是减1是加
		PreparedStatement past = null;
		int a = 0;
		ResultSet rs = null;
		double Num;
		try {

			past = conn.prepareStatement("select Num from  Shop_Car  where  UserNo = ? and cGoodsNo=? and cStoreNo =? ");
			past.setString(1, UserNo);
			past.setString(2, cGoodsNo);
			past.setString(3, cStoreNo);
			rs = past.executeQuery();
			if (rs.next()) {

				Num = Double.parseDouble(rs.getString("Num"));
				Num = Num + 1;

				PreparedStatement past1 = conn.prepareStatement(
						" UPDATE Shop_Car set Num= ? where UserNo = ? and cGoodsNo=? and cStoreNo =? ");
				past1.setString(1, "" + Num);
				past1.setString(2, UserNo);
				past1.setString(3, cGoodsNo);
				past1.setString(4, cStoreNo);
				a = past1.executeUpdate();
			}
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(past);
			DB.closeConn(conn);
		}
		return a;
	}

	public static int sub_(Connection conn, String UserNo, String cGoodsNo, String cStoreNo) {
		// 0是减1是加
		PreparedStatement past = null;
		int a = 0;
		ResultSet rs = null;
		double Num;
		try {
			past = conn.prepareStatement("select Num from  Shop_Car  where  UserNo = ? and cGoodsNo=? and cStoreNo=? ");
			past.setString(1, UserNo);
			past.setString(2, cGoodsNo);
			past.setString(3, cStoreNo);
			rs = past.executeQuery();
			if (rs.next()) {
				Num = Double.parseDouble(rs.getString("Num"));
				if (Num == 1) {
					return -1;
				}
				Num = Num - 1;
				PreparedStatement past1 = conn.prepareStatement(
						"UPDATE Shop_Car set Num= ?   where  UserNo = ? and cGoodsNo=? and cStoreNo =?");
				past1.setString(1, "" + Num);
				past1.setString(2, UserNo);
				past1.setString(3, cGoodsNo);
				past1.setString(4, cStoreNo);
				a = past1.executeUpdate();
				DB.closePreparedStatement(past1);
			}
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return a;
	}

	public static int delete_Order(Connection conn, String cSheetno) {
		// 付款状态，0是待付款，1是待发货，2是待收货，3是完成,4是所有
		int a = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement past = conn.prepareStatement("delete from Order_Table where cSheetno= ? ");
			past.setString(1, cSheetno);
			a = past.executeUpdate();
			DB.closePreparedStatement(past);

			past = conn.prepareStatement("delete from Order_Details where cSheetno= ? ");
			past.setString(1, cSheetno);
			a = past.executeUpdate();
			DB.closePreparedStatement(past);
			conn.commit();
			conn.setAutoCommit(true);
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			DB.closeConn(conn);
		}
		return a;
	}





	public static int cancel_Order(Connection conn, String cSheetno) {
		int a = 0;
		try {
			conn.setAutoCommit(false);
			PreparedStatement past = conn.prepareStatement("update  Order_Table set Order_State='0' where cSheetno= ? ");
			past.setString(1, cSheetno);
			a = past.executeUpdate();
			DB.closePreparedStatement(past);
			conn.commit();
			conn.setAutoCommit(true);
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			DB.closeConn(conn);
		}
		return a;
	}

	public static int collection_goods(Connection conn, String UserNo, String cGoodsNo) {
		// 1是浏览 2是是收藏

		ResultSet rs = null;
		try {
			PreparedStatement past = conn.prepareStatement("select * from dbo.User_browse_collection where cGoodsNo=? and Operation='2'  and UserNo=? ");
			past.setString(1, cGoodsNo);
			past.setString(2, UserNo);
			rs = past.executeQuery();
			if (rs.next()) {
				DB.closeResultSet(rs);
				DB.closePreparedStatement(past);
				return 2; // 已经收藏
			} else {
				DB.closeResultSet(rs);
				DB.closePreparedStatement(past);
				PreparedStatement past1 = conn.prepareStatement(
						"insert into dbo.User_browse_collection (UserNo,Date_time,cGoodsNo,Operation,Browse_number)Values(?,?,?,?,?)");
				past1.setString(1, UserNo);
				past1.setString(2, String_Tool.DataBaseTime());
				past1.setString(3, cGoodsNo);
				past1.setString(4, "2");
				past1.setString(5, "1");
				past1.executeUpdate();
				DB.closePreparedStatement(past1);
				
				PreparedStatement pastU = conn.prepareStatement("update Posmanagement_main.dbo.t_goods set Collect_number =isnull(Collect_number,0)+? where cGoodsNo=? ");
				pastU.setBigDecimal(1, new BigDecimal("1"));
				pastU.setString(2, cGoodsNo);
				pastU.executeUpdate();
				DB.closePreparedStatement(pastU);
				
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeConn(conn);
		}
		return -1;
	}

	public static int delcollection_goods(Connection conn, String UserNo, String cGoodsNo) {
		// 1是浏览 2是是收藏
		PreparedStatement past = null;
		try {
			past = conn.prepareStatement(
					" delete from dbo.User_browse_collection where cGoodsNo=? and Operation='2'  and UserNo=? ");
			past.setString(1, cGoodsNo);
			past.setString(2, UserNo);
			return past.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement_Rs(past, conn);
		}
		return -1;
	}

	public static int del_collection_goods(Connection conn, String UserNo, String cGoodsNo) {
		// 1是浏览 2是是收藏

		ResultSet rs = null;
		try {
			PreparedStatement past = conn.prepareStatement(
					"select * from dbo.User_browse_collection where cGoodsNo=? and Operation='2'  and UserNo=? ");
			past.setString(1, cGoodsNo);
			past.setString(2, UserNo);
			rs = past.executeQuery();
			if (rs.next()) {
				DB.closeResultSet(rs);
				past = conn
						.prepareStatement(" delete from  dbo.User_browse_collection   where cGoodsNo=? and UserNo=? ");
				past.setString(1, cGoodsNo);
				past.setString(3, UserNo);
				past.executeUpdate();
				DB.closePreparedStatement(past);
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeConn(conn);
		}
		return -1;
	}

	public static int delete_Shop_car(Connection conn, String UserNo, String cGoodsNo, String cStoreNo) { // 删除购物车商品
		PreparedStatement past = null;
		int a = 0;
		try {
			past = conn.prepareStatement("delete from dbo.Shop_Car where UserNo=? and cGoodsNo=? and cStoreNo=? ");
			past.setString(1, UserNo);
			past.setString(2, cGoodsNo);
			past.setString(3, cStoreNo);
			a = past.executeUpdate();
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement_Rs(past, conn);
		}
		return a;
	}

	public static String select_Order(Connection conn, String Pay_state, String UserNo, int Number_of_pages,String cStoreNo, String Send_Way) {
		// 付款状态，0是待付款，1是待发货，2是待收货，3是完成,4是所有
		PreparedStatement past = null;
		ResultSet rs = null;
		String str = "";
		JSONArray list = new JSONArray();
		try {
			String sql0 = "";
			if (Pay_state.equals("0")) {// 0的话没有问题 自提和配送都显示出来
				sql0 = "select a.*,b.cTel,b.cStorename from Order_Table a,posmanagement_main.dbo.t_Store b where Pay_state='0' and UserNo= ? and a.cStoreNo=b.cStoreNo and a.cStoreNo=?  and a.Order_State<> 0  ";
			} else if (Send_Way.equals("1") && (Pay_state.equals("1") || Pay_state.equals("2"))) { // 已经配送方式
				sql0 = "select a.*,b.cTel,b.cStorename from Order_Table a,posmanagement_main.dbo.t_Store b where (Pay_state='1' or Pay_state='2') and UserNo= ? and a.cStoreNo=b.cStoreNo and a.cStoreNo=?  and a.Order_State<> 0 and Send_Way='"
						+ 1 + "'";
			} else if (Send_Way.equals("2") && Pay_state.equals("1")) { // fage=1,Send_Way=1
																		// 已支付,是自提
																		// fage=1,Send_Way=2//
																		// 已支付,是自提
																		// 送货上门
				sql0 = "select a.*,b.cTel,b.cStorename from Order_Table a,posmanagement_main.dbo.t_Store b where Pay_state='1' and UserNo= ? and a.cStoreNo=b.cStoreNo and a.cStoreNo=?  and a.Order_State<> 0 and Send_Way='"
						+ 2 + "'";
			} else if (Send_Way.equals("2") && Pay_state.equals("2")) { //带收货
				sql0 = "select a.*,b.cTel,b.cStorename from Order_Table a,posmanagement_main.dbo.t_Store b where Pay_state='2' and UserNo= ? and a.cStoreNo=b.cStoreNo and a.cStoreNo=?  and a.Order_State<> 0 and Send_Way='"
						+ 2 + "'";
			} else if (Pay_state.equals("3")) { // fage=3 已经完成
				sql0 = "select a.*,b.cTel,b.cStorename from Order_Table a,posmanagement_main.dbo.t_Store b where Pay_state='3' and UserNo= ? and a.cStoreNo=b.cStoreNo and a.cStoreNo=?  and a.Order_State<> 0";
			}
			String sql = Paging_Index_Sql.sql("Date_time desc", sql0, Number_of_pages);
			past = conn.prepareStatement(sql);
			past.setString(1, UserNo);
			past.setString(2, cStoreNo);
			rs = past.executeQuery();
			while (rs.next()) {
				JSONObject request_Order_Json = new JSONObject();
				String cSheetno = rs.getString("cSheetno").replace(" ", "");
				String AddressID = rs.getString("AddressID");
				String date_time = rs.getString("Date_time");
				String send_Money = rs.getString("Send_Money");
				String total_money = rs.getString("Total_money");
				String all_Money = rs.getString("All_Money");
				String Reality_All_Money = rs.getString("Reality_All_Money");
				String pay_wayId = rs.getString("Pay_wayId");
				String cTel = rs.getString("cTel");
				String cStoreName = rs.getString("cStoreName");
				String Send_Way1 = rs.getString("Send_Way");
				String Send_cStoreNo = rs.getString("Send_cStoreNo");
				String Pay_statE = rs.getString("Pay_state");

				PreparedStatement past1 = conn.prepareStatement("select a.cGoodsImagePath,a.Description,b.cGoodsNo,b.cGoodsName,b.Num,b.Last_Price,b.Last_Money,b.RealityNum,b.Reality_Money from  posmanagement_main.dbo.T_goods a, dbo.Order_Details  b where cSheetno= ? and a.cGoodsNo=b.cGoodsNo ");
				past1.setString(1, cSheetno);
				ResultSet rs1 = past1.executeQuery();
				JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs1);
				DB.closeRs_Statement(rs1, past1);

				request_Order_Json.put("details_list", array);
				request_Order_Json.put("cSheetno", cSheetno);
				request_Order_Json.put("Date_time", date_time);
				request_Order_Json.put("All_Money", all_Money);
				request_Order_Json.put("Pay_wayId", pay_wayId);
				request_Order_Json.put("Send_Money", send_Money);
				request_Order_Json.put("Total_money", total_money);
				request_Order_Json.put("cTel", cTel);
				request_Order_Json.put("cStoreName", cStoreName);
				request_Order_Json.put("Send_Way", Send_Way1);
				request_Order_Json.put("Send_cStoreNo", Send_cStoreNo);
				request_Order_Json.put("Reality_All_Money", Reality_All_Money);
				request_Order_Json.put("Pay_state", Pay_statE);
				list.put(request_Order_Json);
			}
			if (list != null && list.length() > 0) {
				str = "{\"resultStatus\":\"" + 1 + "\"," + "\"array\":" + list.toString().replace(" ", "") + "}";
			} else {
				str = "{\"resultStatus\":\"" + 0 + "\"," + "\"array\":" + list.toString() + "}";
			}
			return str;
		} catch (Exception e) {
			str = "{\"resultStatus\":\"" + 0 + "\"," + "\"array\":\"" + "" + "\"}";
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}
	//石头门
	public static String PF_select_Order(Connection conn, String Pay_state, String UserNo, int Number_of_pages,String cStoreNo, String Send_Way) {
		// 付款状态，0是待付款，1是待发货，2是待收货，3是完成,4是所有

		PreparedStatement past = null;
		ResultSet rs = null;
		String str = "";
		JSONArray list = new JSONArray();
		try {
			String sql0 = "select a.*,b.cTel,b.cStorename from Order_Table a,posmanagement_main.dbo.t_Store b where Pay_state=? and UserNo= ? and a.cStoreNo=b.cStoreNo and a.cStoreNo=?  and a.Order_State<> 0 and Send_Way=? ";
			
			String sql = Paging_Index_Sql.sql("Date_time desc", sql0, Number_of_pages);
			past = conn.prepareStatement(sql);
			past.setString(1, Pay_state);
			past.setString(2, UserNo);
			past.setString(3, cStoreNo);
			past.setString(4, Send_Way);
			rs = past.executeQuery();
			while (rs.next()) {
				JSONObject request_Order_Json = new JSONObject();
				String cSheetno = rs.getString("cSheetno").replace(" ", "");
				String AddressID = rs.getString("AddressID");
				String date_time = rs.getString("Date_time");
				String send_Money = rs.getString("Send_Money");
				String total_money = rs.getString("Total_money");
				String all_Money = rs.getString("All_Money");
				String Reality_All_Money = rs.getString("Reality_All_Money");
				String pay_wayId = rs.getString("Pay_wayId");
				String cTel = rs.getString("cTel");
				String cStoreName = rs.getString("cStoreName");
				String Send_Way1 = rs.getString("Send_Way");
				String Send_cStoreNo = rs.getString("Send_cStoreNo");
				String Pay_statE = rs.getString("Pay_state");

				PreparedStatement past1 = conn.prepareStatement("select a.cGoodsImagePath,a.Description,a.cSpec,a.cUnit,b.cGoodsNo,b.cGoodsName,b.Num,b.Last_Price,b.Last_Money,b.RealityNum,b.Reality_Money from  posmanagement_main.dbo.T_goods a, dbo.Order_Details  b where cSheetno= ? and a.cGoodsNo=b.cGoodsNo ");
				past1.setString(1, cSheetno);
				ResultSet rs1 = past1.executeQuery();
				JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs1);
				DB.closeRs_Statement(rs1, past1);

				request_Order_Json.put("details_list", array);
				request_Order_Json.put("cSheetno", cSheetno);
				request_Order_Json.put("Date_time", date_time);
				request_Order_Json.put("All_Money", all_Money);
				request_Order_Json.put("Pay_wayId", pay_wayId);
				request_Order_Json.put("Send_Money", send_Money);
				request_Order_Json.put("Total_money", total_money);
				request_Order_Json.put("cTel", cTel);
				request_Order_Json.put("cStoreName", cStoreName);
				request_Order_Json.put("Send_Way", Send_Way1);
				request_Order_Json.put("Send_cStoreNo", Send_cStoreNo);
				request_Order_Json.put("Reality_All_Money", Reality_All_Money);
				request_Order_Json.put("Pay_state", Pay_statE);
				list.put(request_Order_Json);
			}
			if (list != null && list.length() > 0) {
				str = "{\"resultStatus\":\"" + 1 + "\"," + "\"array\":" + list.toString().replace(" ", "") + "}";
			} else {
				str = "{\"resultStatus\":\"" + 0 + "\"," + "\"array\":" + list.toString() + "}";
			}
			return str;
		} catch (Exception e) {
			str = "{\"resultStatus\":\"" + 0 + "\"," + "\"array\":\"" + "" + "\"}";
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}
	
	

	public static String select_order_PickInStore(Connection conn, String Pay_state, String UserNo, int Number_of_pages,
			String cStoreNo) {
		// 付款状态，0是待付款，1是待发货，2是待收货，3是完成,4是所有
		PreparedStatement past = null;
		ResultSet rs = null;
		String str = "";
		JSONArray list = new JSONArray();
		try {
			String sql = Paging_Index_Sql.sql("Date_time desc",
					" select a.*,b.cTel,b.cStorename from Order_Table a,posmanagement_main.dbo.t_Store b where Pay_state=? and UserNo= ? and a.cStoreNo=b.cStoreNo and a.cStoreNo=?  and a.Order_State<> '0'  and ISnull(Send_Way ,'1') ='1' ",
					Number_of_pages);
			past = conn.prepareStatement(sql);
			past.setString(1, Pay_state);
			past.setString(2, UserNo);
			past.setString(3, cStoreNo);
			rs = past.executeQuery();
			while (rs.next()) {
				JSONObject obj = new JSONObject();

				String cSheetno = rs.getString("cSheetno").replace(" ", "");
				String AddressID = rs.getString("AddressID");
				String date_time = rs.getString("Date_time");
				String send_Money = rs.getString("Send_Money");
				String total_money = rs.getString("Total_money");
				String all_Money = rs.getString("All_Money");
				String pay_wayId = rs.getString("Pay_wayId");
				String cTel = rs.getString("cTel");
				String cStoreName = rs.getString("cStoreName");
				String Send_cStoreNo = rs.getString("Send_cStoreNo");

				obj.put("cSheetno", cSheetno);
				obj.put("AddressID", AddressID);
				obj.put("Date_time", date_time);
				obj.put("Send_Money", send_Money);
				obj.put("Total_money", total_money);
				obj.put("All_Money", all_Money);
				obj.put("Pay_wayId", pay_wayId);
				obj.put("cTel", cTel);
				obj.put("cStoreName", cStoreName);
				obj.put("Send_cStoreNo", Send_cStoreNo);

				PreparedStatement past1 = conn.prepareStatement(
						"select a.cGoodsImagePath,a.Description,b.cGoodsNo,b.cGoodsName,b.Num,b.Last_Price,b.Last_Money from  posmanagement_main.dbo.T_goods a, dbo.Order_Details  b where cSheetno= ? and a.cGoodsNo=b.cGoodsNo ");

				past1.setString(1, cSheetno);
				System.out.println(cSheetno);
				ResultSet rs1 = past1.executeQuery();
				JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs1);
				DB.closeRs_Statement(rs1, past1);
				obj.put("details_list", array);

				list.put(obj);
			}
			if (list != null && list.length() > 0) {
				str = "{\"resultStatus\":\"" + 1 + "\"," + "\"array\":" + list.toString().replace(" ", "") + "}";
			} else {
				str = "{\"resultStatus\":\"" + 0 + "\"," + "\"array\":\"" + "" + "\"}";
			}
			return str;
		} catch (Exception e) {
			str = "{\"resultStatus\":\"" + 0 + "\"," + "\"array\":\"" + "" + "\"}";
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static JSONObject select_Order_Content(Connection conn, String cSheetno) {
		// 付款状态，0是待付款，1是待发货，2是待收货，3是完成,4是所有
		PreparedStatement past = null;
		ResultSet rs = null;
		String sql = "";
		try {
			past = conn.prepareStatement("select * from dbo.Order_Table  where cSheetno= ? ");
			past.setString(1, cSheetno);
			rs = past.executeQuery();
			JSONObject obj = ResultSet_To_JSON.resultSetToJsonObject(rs);
			System.out.println(obj);

			PreparedStatement past1 = conn.prepareStatement("select * from dbo.Order_Details where cSheetno= ? ");
			past1.setString(1, cSheetno);
			ResultSet rs1 = past1.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs1);
			DB.closeRs_Statement(rs1, past1);
			obj.put("goodslist", array);

			if (obj.getString("Send_Way").equals("1")) {
				sql = "select * from dbo.Store_address_site  where id=? ";
			} else if (obj.getString("Send_Way").equals("2")) {
				sql = "  select * from dbo.User_Address where AddressID=?  ";
			}
			PreparedStatement past2 = conn.prepareStatement(sql);
			past2.setString(1, obj.getString("AddressID"));
			ResultSet rs2 = past2.executeQuery();
			JSONObject addressobj = ResultSet_To_JSON.resultSetToJsonObject(rs2);
			DB.closeRs_Statement(rs2, past2);
			obj.put("addressobj", addressobj);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static JSONArray select_BuyerCar(Connection conn, String UserNo, String Number_of_pages, String cStoreNo) {
		PreparedStatement past = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql =  "SELECT TOP 10 *  FROM (SELECT ROW_NUMBER() OVER (ORDER BY  Date_time desc) "
					+ "AS RowNumber,* FROM (select a.Date_time,a.cGoodsNo,a.Num,a.Last_Price,b.cGoodsImagePath,b.cGoodsName,b.bfresh,b.bWeight "
					+ "from dbo.Shop_Car a,posmanagement_main.dbo.T_goods b where  a.cGoodsNo=b.cGoodsNo and a.UserNo=? and a.cStoreNo=? ) c ) as A WHERE RowNumber > 10*(?-1)   ";
			System.out.println(sql);
			past = conn.prepareStatement(sql);
			past.setString(1, UserNo);
			past.setString(2, cStoreNo);
			past.setString(3, Number_of_pages);
			rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static JSONArray Select_collect_browse_Goods(Connection conn, String UserNo, String fage,
			String Number_of_pages, String cStoreNo) {
		PreparedStatement past = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = " SELECT TOP 10 * FROM (SELECT ROW_NUMBER() OVER (ORDER BY Date_time desc) AS RowNumber,* FROM (select  a.Date_time ,a.cGoodsNo, a.Browse_number,b.cGoodsName,b.[Description],b.cGoodsImagePath,c.fNormalPrice,c.bOnline_Price as fVipPrice from dbo.User_browse_collection a, posmanagement_main.dbo.T_goods b,posmanagement_main.dbo.t_cStoreGoods c    where UserNo=?  and  a.cGoodsNo=b.cGoodsNo and a.cGoodsNo=c.cGoodsNo and c.cStoreNo=? and a.Operation=? ) c ) as A WHERE RowNumber > 10*("
					+ (Integer.parseInt(Number_of_pages) - 1) + ") ";
			System.out.println(sql);
			past = conn.prepareStatement(sql);
			past.setString(1, UserNo);
			past.setString(2, cStoreNo);
			past.setString(3, fage);
			rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static String Select_advertisement_Goods(Connection conn, String cStoreNo) {
		ResultSet rs = null;
		try {
			PreparedStatement past = conn.prepareStatement("select cGoodsName,cGoodsNo,cGoodsImagePath,describe,Url,is_goods from dbo.Advertisement ");
			rs = past.executeQuery();
			JSONArray advertisement = ResultSet_To_JSON.resultSetToJsonArray(rs);
			DB.closeRs_Statement(rs, past);

			past = conn.prepareStatement(
					"select * from posmanagement_main.dbo.T_GroupType where cParentNo='--'  and ISNULL(bOnLine,0)=1 ");
			rs = past.executeQuery();
			JSONArray T_GroupType = ResultSet_To_JSON.resultSetToJsonArray(rs);
			DB.closeRs_Statement(rs, past);

			past = conn.prepareStatement(
					"select DISTINCT  cPloyTypeNo,cPloyTypeName from  posmanagement_main.dbo.t_PloyOfSale where  cStoreNo=? and bEnabled='1' and  ? between dDateStart and dDateEnd ");
			past.setString(1, cStoreNo);
			past.setString(2, String_Tool.DataBaseTime());
			rs = past.executeQuery();
			List<T_JSON> t_json_list = new ArrayList<T_JSON>();
			while (rs.next()) {
				T_JSON t_JSON = new T_JSON();
				String cPloyTypeNo = rs.getString("cPloyTypeNo");
				String cPloyTypeName = rs.getString("cPloyTypeName");
				t_JSON.setcPloyTypeName(cPloyTypeName);
				t_JSON.setcPloyTypeNo(cPloyTypeNo);
				PreparedStatement past1 = conn.prepareStatement(
						"select top 6 a.cGoodsNo,a.cGoodsName,a.cGoodsImagePath,b.cPloyNo,b.cPloyTypeNo,b.cPloyTypeName,b.dDateStart,b.dDateEnd,b.cTimeStart,b.cTimeEnd, b.fPrice_SO ,b.fQuantity_Ploy,b.fVipValue,c.fNormalPrice,c.bfresh from posmanagement_main.dbo.T_goods a,posmanagement_main.dbo.t_PloyOfSale b ,posmanagement_main.dbo.t_cStoreGoods c where  b.cPloyTypeNo=? and b.cStoreNo=?  and c.cStoreNo=b.cStoreNo and a.cGoodsNo=b.cGoodsNo and a.cGoodsNo=c.cGoodsNo and  ? between b.dDateStart and b.dDateEnd");
				past1.setString(1, cPloyTypeNo);
				past1.setString(2, cStoreNo);
				past1.setString(3, String_Tool.DataBaseTime());
				ResultSet rs1 = past1.executeQuery();
				JSONArray T_PloyOfSale_array = ResultSet_To_JSON.resultSetToJsonArray(rs1);
				DB.closeRs_Statement(rs1, past1);
				Gson gson = new Gson();
				List<T_PloyOfSale> list1 = gson.fromJson(T_PloyOfSale_array.toString(),
						new TypeToken<List<T_PloyOfSale>>() {
						}.getType());
				t_JSON.setT_PloyOfSale(list1);
				t_json_list.add(t_JSON);
			}
			String str1 = new Gson().toJson(t_json_list);

			String str = "{\"resultStatus\":\"" + 1 + "\"," + "\"array1\":" + advertisement.toString().replace(" ", "")
					+ "," + "\"array2\":" + T_GroupType.toString().replace(" ", "") + "," + "\"array3\":"
					+ str1.replace(" ", "") + "}";
			System.out.println(str);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeConn(conn);
		}
		return null;
	}

	public static JSONArray Goods_strategy_Discount(Connection conn) {
		ResultSet rs = null;
		PreparedStatement past = null;
		try {
			past = conn.prepareStatement("select cGoodsNo,cGoodsName,fNormalPrice,fVipPrice,cGoodsImagePath,bOnline_Price as bOnLine_Price,bOnline_Price,bFresh,cUnit,cSpec,describle from dbo.Activity_goods order by Show_Level desc ");
			rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeConn(conn);
		}
		return null;
	}
	public static JSONArray Specialty_goods(Connection conn) {
		ResultSet rs = null;
		PreparedStatement past = null;
		try {
			past = conn.prepareStatement("select cGoodsNo,cGoodsName,fNormalPrice,fVipPrice,cGoodsImagePath,bOnline_Price as bOnLine_Price ,bOnline_Price,bFresh,cUnit,cSpec,describle from dbo.Specialty_goods order by  Show_Level desc ");
			rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static String strategy_More_Goods(Connection conn, String cPloyTypeNo, String Number_of_pages,
			String cStoreNo) {
		ResultSet rs = null;
		try {
			String sql = Paging_Index_Sql.sql("cGoodsNo",
					"select  a.cGoodsNo,a.cGoodsName,a.cGoodsImagePath,b.cPloyNo,b.cPloyTypeNo,b.cPloyTypeName,b.dDateStart,b.dDateEnd,b.cTimeStart,b.cTimeEnd, b.fPrice_SO ,b.fQuantity_Ploy,b.fVipValue ,c.fNormalPrice,c.cUnit,c.cSpec  from posmanagement_main.dbo.T_goods a,posmanagement_main.dbo.t_PloyOfSale b ,posmanagement_main.dbo.t_cStoreGoods c where  b.cPloyTypeNo=? and b.cStoreNo=?  and c.cStoreNo=b.cStoreNo and a.cGoodsNo=b.cGoodsNo and a.cGoodsNo=c.cGoodsNo and ?  between b.dDateStart and b.dDateEnd ",
					Integer.parseInt(Number_of_pages));
			PreparedStatement past = conn.prepareStatement(sql);
			past.setString(1, cPloyTypeNo);
			past.setString(2, cStoreNo);
			past.setString(3, String_Tool.DataBaseTime());
			rs = past.executeQuery();
			JSONArray T_Groue1 = ResultSet_To_JSON.resultSetToJsonArray(rs);
			DB.closeRs_Statement(rs, past);
			Gson gson = new Gson();
			List<T_PloyOfSale> list = gson.fromJson(T_Groue1.toString(), new TypeToken<List<T_PloyOfSale>>() {
			}.getType());
			String str = gson.toJson(list);
			if (list != null && list.size() > 0) {
			
				str = "{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + str.replace(" ", "") + "}";
				System.out.println(str);
			} else {
				str = "{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + str.replace(" ", "") + "}";
				System.out.println(str);
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeConn(conn);
		}
		return null;
	}

	// 根据某个大类的类型编号, 按照显示级别的高低, 返回结果 --- 首页数据使用
	public static JSONArray getHighShowLevelGoodsWithGroupTypeNo(Connection conn, String cGroupTypeNo,
			String cStoreNo) {
		PreparedStatement past = null;
		ResultSet rs = null;
		try {
			String sql = " select top 6 c.cGoodsNo,c.cGoodsName,c.bFresh,c.fNormalPrice,c.fVipPrice,c.bOnLine_Price ,c.cUnit,c.cSpec,d.cGoodsImagePath,c.Show_Level  from  dbo.T_GroupType a  ,T_GroupType_GoodsType  b ,posmanagement_main.dbo.t_cStoreGoods c, posmanagement_main.dbo.T_Goods d  where a.cParentNo=? and a.cGroupTypeNo=b.cGroupTypeNo and c.cGoodsTypeno=b.cGoodsTypeno and c.cStoreNo=? and c.cGoodsNo=d.cGoodsNo and c.bOnLine='1'  order by c.Show_Level DESC ";
			System.out.println(sql);
			past = conn.prepareStatement(sql);
			past.setString(1, cGroupTypeNo);
			past.setString(2, cStoreNo);
			rs = past.executeQuery();
			System.out.println(sql);
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

}
