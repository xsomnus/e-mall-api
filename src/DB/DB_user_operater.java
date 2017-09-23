package DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ModelRas.MD5key;
import Tool.MD5_Key_String;
import Tool.ResultSet_To_JSON;
import Tool.String_Tool;
import bean.City;
import bean.Distrinct;
import bean.Street;

public class DB_user_operater {

	public static String User_Log_in(Connection conn, String UserName, String pass) {
		PreparedStatement past = null;
		ResultSet rs = null;
		String str = null;
		try {
			past = conn.prepareStatement(
					"select Tel,UserNo,ImagePath,Integral,cStoreNo,rank,discount_rate from dbo.User_Table where (UserNo=? or Tel=? Or UserName=? or Email=?) and UserPass=? ");
			past.setString(1, UserName);
			past.setString(2, UserName);
			past.setString(3, UserName);
			past.setString(4, UserName);
			past.setString(5, pass);
			rs = past.executeQuery();
			if (rs.next()) {
				String UserNo = rs.getString("UserNo");
				String Tel = rs.getString("Tel");
				String ImagePath = rs.getString("ImagePath");
				String Integral = rs.getString("Integral");
				String cStoreNo = rs.getString("cStoreNo");
				String rank = rs.getString("rank");
				// String discount_rate=rs.getString(columnIndex);
				if (String_Tool.isEmpty(ImagePath)) {
					ImagePath = "";
				}
				System.out.println(UserNo);

				// PreparedStatement past2 = conn.prepareStatement("select
				// leftmoney from Supermarket.dbo.moneycard where tel=? ");
				// String leftmoney="";
				// past2.setString(1, Tel);
				// ResultSet rs1 = past2.executeQuery();
				// if(rs1.next()){
				// leftmoney=rs1.getString("leftmoney");
				// }
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("UserNo", UserNo);
				map.put("Tel", Tel);
				map.put("ImagePath", "" + ImagePath);
				map.put("Integral", "" + Integral);
				// map.put("cStoreNo", cStoreNo);
				map.put("cStoreNo", "000");
				map.put("rank", rank);
				// map.put("leftmoney", leftmoney);
				Gson gson = new Gson();
				str = gson.toJson(map);
				System.out.println(str);
				PreparedStatement past1 = conn
						.prepareStatement("update User_Table set IS_log_in= '1' where UserNo = ?");
				past1.setString(1, UserNo);
				past1.execute();
				DB.closePreparedStatement(past1);
				// past = conn.prepareStatement("select UserNo from
				// dbo.User_Table where (Tel=? Or UserName=? or Email=?) and
				// UserPass=?");
				// past.setString(1, UserName);
				// past.setString(2, UserName);
				// past.setString(3, UserName);
				// past.setString(4, pass);
			} else {
				// PreparedStatement past = conn.prepareStatement("select UserNo
				// from dbo.t_Vip where (Tel=? Or UserName=? or Email=?) and
				// UserPass=?");
				// past.setString(1, UserName);
				// past.setString(2, UserName);
				// past.setString(3, UserName);
				// past.setString(4, pass);
				// rs=past.executeQuery();

			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static String Cancel_Log_in(Connection conn, String UserName, String pass) {
		PreparedStatement past = null;
		ResultSet rs = null;
		String str = null;
		try {
			past = conn.prepareStatement(
					"select UserNo,ImagePath from dbo.User_Table where (UserNo=? or Tel=? Or UserName=? or Email=?) and UserPass=?");
			past.setString(1, UserName);
			past.setString(2, UserName);
			past.setString(3, UserName);
			past.setString(4, UserName);
			past.setString(5, pass);
			rs = past.executeQuery();
			if (rs.next()) {
				String UserNo = rs.getString("UserNo");
				PreparedStatement past1 = conn
						.prepareStatement("update User_Table set IS_log_in= '0' where UserNo = ?");
				past1.setString(1, UserNo);
				past1.execute();
				DB.closePreparedStatement(past1);
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}

	public static boolean User_Has_Tel(Connection conn, String Tel) {// 手机号已经注册
		PreparedStatement past = null;
		ResultSet rs = null;
		try {
			past = conn.prepareStatement("select UserNo from dbo.User_Table where Tel= ?  ");
			past.setString(1, Tel);
			rs = past.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(past);
			DB.closeResultSet(rs);
		}
		return false;
	}

	public static int User_register_in(Connection conn, String Tel, String pass, String cStoreNo) {
		// 0 已经注册，2是注册失败
		PreparedStatement past = null;
		CallableStatement c = null;
		try {
			conn.setAutoCommit(false);
			if (User_Has_Tel(conn, Tel)) {
				return 0; // 已經有次用戶
			} else {
				past = conn.prepareStatement("insert into dbo.User_Table  (UserNo,UserPass,Tel,Integral,Date_time,cStoreNo,rank,discount_rate) values (?,?,?,?,?,?,?,?)");
				past.setString(1, Tel);
				past.setString(2, pass);
				past.setString(3, Tel);
				past.setInt(4, 0);
				past.setString(5, String_Tool.DataBaseTime());
				past.setString(6, cStoreNo);
				past.setString(7, "1");
				past.setString(8, "1");
				int a = past.executeUpdate();
				if (a == 1) {
					c = conn.prepareCall("{call p_O_WalletReg (?,?,?,?,?,?,?,?,?,?)}");
					c.setString(1, Tel);
					c.setString(2, Tel);
					c.setString(3, pass);// 用户密码
					c.setString(4, pass); // 支付密码
					c.setString(5, Tel);
					c.setString(6, Tel + "@.com");// 邮箱
					c.setString(7, Tel);
					c.setString(8, Tel);
					c.setString(9, MD5key.getMD5Pass(Tel + "0.0000" + MD5_Key_String.MD5_KEY)); // 注册的时候加密防止数据库更改
					c.registerOutParameter("return", java.sql.Types.INTEGER);
					c.execute();
					int fage = c.getInt("return");
					System.out.println("呵呵" + fage);
					if (fage == 5) {
						conn.commit();
						conn.setAutoCommit(true);
						return 1;
					} else {
						conn.rollback();
						return 3;
					}
				}
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(past);
			DB.closeConn(conn);
		}
		return 2; // 異常
	}

	public static int update_User_Pass(Connection conn, String Tel, String Pass) {
		PreparedStatement past = null;
		try {
			past = conn.prepareStatement("update  User_Table  set  UserPass=?  where Tel =?  ");
			System.out.println(Tel);
			past.setString(1, Pass);
			past.setString(2, Tel);
			past.execute();
			return 1; // 插入用戶
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(past);
			DB.closeConn(conn);
		}
		return 0; // 異常
	}

	public static int update_User_Message(Connection conn, String Tel, String Pass) {
		PreparedStatement past = null;
		try {
			past = conn.prepareStatement("update  User_Table  set  UserName=?  where Tel =?  ");
			System.out.println(Tel);
			past.setString(1, Pass);
			past.setString(2, Tel);
			past.execute();
			return 1; // 插入用戶
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(past);
			DB.closeConn(conn);
		}
		return 0; // 異常
	}

	public static int User_add_address(Connection conn, String UserNo, String UserName, String Provincial, String Tel,
			String City, String District, String Detailaddress, String Default_fage, String fLont, String fLant,
			String label, String cStoreNo) {
		PreparedStatement past = null;
		try {
			conn.setAutoCommit(false);
			if (Default_fage.equals("1")) {
				PreparedStatement past0 = conn
						.prepareStatement("update   User_Address  set  Default_fage ='0' where  UserNo =?  ");
				past0.setString(1, UserNo);
				past0.execute();
				DB.closePreparedStatement(past0);
			}
			past = conn.prepareStatement(
					"INSERT INTO dbo.User_Address  (UserNo,UserName,Provincial,Tel,City,District,Detailaddress,Default_fage,Available,fLont,fLant,label,cStoreNo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
			past.setString(1, UserNo);
			past.setString(2, UserName);
			past.setString(3, Provincial);
			past.setString(4, Tel);
			past.setString(5, City);
			past.setString(6, District);
			past.setString(7, Detailaddress);
			past.setString(8, Default_fage);
			past.setString(9, "1");

			past.setString(10, fLont);
			past.setString(11, fLant);
			past.setString(12, label);
			past.setString(13, cStoreNo);
			System.out.println(label);
			int a = past.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closePreparedStatement(past);
			DB.closeConn(conn);
		}
		return 0; // 異常
	}

	public static int Update_address(Connection conn, String UserNo, String UserName, String Provincial, String Tel,
			String City, String District, String Detailaddress, String Default_fage, String AddressID, String fLont,
			String fLant, String label, String cStoreNo) {
		try {
			PreparedStatement past1 = conn
					.prepareStatement("update   User_Address set Available ='0'  where AddressID =? ");
			past1.setString(1, AddressID);
			past1.execute();
			DB.closePreparedStatement(past1);

			if (Default_fage.equals("1")) {
				PreparedStatement past0 = conn
						.prepareStatement("update   User_Address  set  Default_fage ='0' where  UserNo =?  ");
				past0.setString(1, UserNo);
				past0.execute();
				DB.closePreparedStatement(past0);
			}
			PreparedStatement past = conn.prepareStatement(
					"INSERT INTO dbo.User_Address  (UserNo,UserName,Provincial,Tel,City,District,Detailaddress,Default_fage,Available,fLont,fLant,label,cStoreNo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
			past.setString(1, UserNo);
			past.setString(2, UserName);
			past.setString(3, Provincial);
			past.setString(4, Tel);
			past.setString(5, City);
			past.setString(6, District);
			past.setString(7, Detailaddress);
			past.setString(8, Default_fage);
			past.setString(9, "1");

			past.setString(10, fLont);
			past.setString(11, fLant);
			past.setString(12, label);
			past.setString(13, cStoreNo);
			past.execute();
			DB.closePreparedStatement(past);
			return 1; // 插入用戶
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeConn(conn);
		}
		return 0; // 異常
	}

	public static int Set_address_def(Connection conn, String AddressID, String UserNo) {
		try {

			PreparedStatement past0 = conn
					.prepareStatement("update   User_Address  set  Default_fage ='0' where  UserNo =?  ");
			past0.setString(1, UserNo);
			past0.execute();
			DB.closePreparedStatement(past0);

			PreparedStatement past1 = conn
					.prepareStatement("update   User_Address  set  Default_fage ='1'  where AddressID =? ");
			past1.setString(1, AddressID);
			past1.execute();
			DB.closePreparedStatement(past1);
			return 1; // 插入用戶
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeConn(conn);
		}
		return 0; // 異常
	}

	public static JSONArray select_address(Connection conn, String UserNo) {
		ResultSet rs = null;
		PreparedStatement past1 = null;
		try {
			past1 = conn.prepareStatement(
					"select a.*,b.cStoreName , b.fLont as Store_fLont ,b.fLant as Store_fLant from User_Address  a left outer join dbo.Store_address_site b on  a.cStoreNo=b.cStoreNo where  a.UserNo=?  and a.Available='1' ");
			past1.setString(1, UserNo);
			rs = past1.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past1, conn);
		}
		return null; // 異常
	}

	public static List<City> select_cStore_address(Connection conn, String cStoreNo) {
		ResultSet rs = null;
		PreparedStatement past = null;
		try {
			past = conn.prepareStatement("select DISTINCT province,city  from Store_address_site where cStoreNo = ? and Available='1' ");
			past.setString(1, cStoreNo);
			rs = past.executeQuery();
			List<City> citys = new ArrayList<City>();
			Gson gson = new Gson();
			while (rs.next()) {
				City city = new City();
				String City = rs.getString("city");
				String province = rs.getString("province");
				city.setCity(City);
				city.setProvince(province);

				PreparedStatement past1 = conn.prepareStatement(
						"select DISTINCT  district from Store_address_site where cStoreNo = ? and city=?  ");
				past1.setString(1, cStoreNo);
				past1.setString(2, City);
				ResultSet rs1 = past1.executeQuery();
				List<Distrinct> distrincts = new ArrayList<Distrinct>();
				while (rs1.next()) {
					Distrinct distrinct = new Distrinct();
					String district = rs1.getString("district");
					distrinct.setDistrinct(district);

					PreparedStatement past2 = conn.prepareStatement(
							"select street  from Store_address_site where cStoreNo = ? and district=? and Available='1' ");
					past2.setString(1, cStoreNo);
					past2.setString(2, district);
					ResultSet rs2 = past2.executeQuery();
					System.out.println(cStoreNo + district);
					JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs2);
					System.out.println(array.toString());

					List<Street> streets = gson.fromJson(array.toString(), new TypeToken<List<Street>>() {
					}.getType());

					System.out.println(streets);
					distrinct.setStreets(streets);
					System.out.println(distrinct);
					distrincts.add(distrinct);
				}
				city.setDistrincts(distrincts);
				citys.add(city);
			}
			return citys;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null; // 异常
	}

	public static String select_Default_address_PayWay_Money(Connection conn, String UserNo, String money) {
		ResultSet rs = null;
		String freight = "0";
		try {
			PreparedStatement past1 = conn.prepareStatement(
					"select top 1  b.* from dbo.User_Table a , User_Address b where a.UserNo=b.UserNo and a.UserNo=?  and  a.cStoreNo=b.cStoreNo and Available='1' ");
			past1.setString(1, UserNo);
			rs = past1.executeQuery();
			JSONArray address_array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			DB.closeRs_Statement(rs, past1);

			// if (address_array.length() == 0) {
			// PreparedStatement past11 = conn
			// .prepareStatement("select top 1 * from User_Address where UserNo
			// = ? and Available='1' ");
			// past11.setString(1, UserNo);
			// rs = past11.executeQuery();
			// address_array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			// DB.closeRs_Statement(rs, past11);
			// }

			PreparedStatement past2 = conn
					.prepareStatement("select Pay_wayId,Describe,Public_key ,[Partner],Seller from dbo.Pay_way_Table ");
			rs = past2.executeQuery();
			JSONArray Pay_array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			DB.closeRs_Statement(rs, past2);

			PreparedStatement past3 = conn
					.prepareStatement(" select top 1 Freight  from  dbo.Oeder_freight_Table  where  ? < Goods_amount ");
			past3.setString(1, money);
			rs = past3.executeQuery();
			if (rs.next()) {
				freight = rs.getString("Freight");
			} else {
				freight = "0";
			}
			DB.closeRs_Statement(rs, past3);

			PreparedStatement past4 = conn.prepareStatement(
					"select b.cStoreNo,b.cStoreName,b.id,b.Address,b.Tel,b.cOperator,b.cOperatorNo, b.fLont,b.fLant from Simple_online.dbo.User_Table a, dbo.Store_address_site b  where UserNo = ? and a.cStoreNo=b.cStoreNo ");
			past4.setString(1, UserNo);
			rs = past4.executeQuery();
			JSONArray cStoreArray = ResultSet_To_JSON.resultSetToJsonArray(rs);
			// HashMap<String, JSONArray> map=new HashMap<String, JSONArray>();
			// map.put("array1", address_array);
			// map.put("array2", Pay_array);
			// map.put("array3", array3);
			DB.closeRs_Statement(rs, past4);

			/**-Ego-Start-*/
			String isFirstOrder = "1";
			if (DB_goods_operate.is_FirstOrder(GetConnection.getSpuerConn(), UserNo)) {
			    isFirstOrder = "1"; // 1 代表该订单是用户首次下单, 可以享受9折优惠
            } else {
			    isFirstOrder = "0"; // 0 代表用户已经下过订单, 不能再享受该优惠
            }
            /**-Ego-End-*/
			String str = "{\"resultStatus\":\"" + 1 + "\"," + "\"array1\":" + address_array.toString() + ","
					+ "\"array2\":" + Pay_array.toString() + "," + "\"array3\":" + cStoreArray.toString() + ","
					+ "\"freight\":\"" + freight + "\","
                    + "\"isFirstOrder\":\"" + isFirstOrder + "\"}";

			return str;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeConn(conn);
		}
		return null; // 异常
	}

	public static int Delete_address(Connection conn, String AddressID) {
		ResultSet rs = null;
		PreparedStatement past1 = null;
		try {
			past1 = conn.prepareStatement("update User_Address set Available='0' where AddressID = ?");
			past1.setString(1, AddressID);
			int a = past1.executeUpdate();
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past1, conn);
		}
		return 0; // 异常
	}

	public static int Return_goods(Connection conn, JSONObject obj, JSONArray array) {
		ResultSet rs = null;
		PreparedStatement past1 = null;
		try {
			conn.setAutoCommit(false);
			PreparedStatement past = conn.prepareStatement(
					"insert into Return_goods_Details (cSheetno,cGoodsNo,cGoodsName,price,fQunlity,flmoney,unit,cSpec) values(?,?,?,?,?,?,?,?) ");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj1 = array.getJSONObject(i);
				past.setString(1, obj1.getString("cSheetno"));
				past.setString(2, obj1.getString("cGoodsNo"));
				past.setString(3, obj1.getString("cGoodsName"));
				past.setString(4, obj1.getString("price"));
				past.setString(5, obj1.getString("fQunlity"));
				past.setString(6, obj1.getString("flmoney"));
				past.setString(7, obj1.getString("unit"));
				past.setString(8, obj1.getString("cSpec"));
				past.addBatch();
			}
			past.executeBatch();
			DB.closePreparedStatement(past);

			past1 = conn.prepareStatement(
					"insert into Return_goods (cSheetno,UserNo,dDate,cStoreNo,return_State,Start_time,End_time,service_type,reason) values(?,?,?,?,?,?,?,?,?) ");
			past1.setString(1, obj.getString("cSheetno"));
			past1.setString(2, obj.getString("UserNo"));
			past1.setString(3, String_Tool.DataBaseYear_Month_Day());
			past1.setString(4, obj.getString("cStoreNo"));
			past1.setString(5, "0"); // 刚插进去,没有处理
			past1.setString(6, String_Tool.DataBaseTime());
			past1.setString(7, String_Tool.DataBaseTime());
			past1.setString(8, obj.getString("service_type"));
			past1.setString(9, obj.getString("reason"));
			int a = past1.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past1, conn);
		}
		return 0; // 異常
	}
}
