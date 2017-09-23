package DB;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import ModelRas.MD5key;
import Tool.MD5_Key_String;
import Tool.Money_Authentication;
import Tool.ResultSet_To_JSON;
import Tool.String_Tool;
import bean.Money_Car;

public class Wallet_OthereUpda {

	public static int Select_Money_Car(Connection conn, String Tel, String No) { // 查询线下有没有这个储值卡
		PreparedStatement past = null;
		ResultSet rs = null;
		CallableStatement c = null;
		try {
			int retCode = 0;
			past = conn.prepareStatement("select * from On_Line_MoneyCard where Tel=? and ID_Card =? ");
			past.setString(1, Tel);
			past.setString(2, No);
			rs = past.executeQuery();
			if (rs.next()) {
				retCode = 3;// 此储值卡已经添加
			} else {
				c = conn.prepareCall("{call Select_OffLine_Has_this_Money_car (?,?,?)}");
				c.setString(1, Tel);
				c.setString(2, No);
				c.registerOutParameter("return", java.sql.Types.LONGNVARCHAR);
				c.execute();
				retCode = c.getInt("return");
			}
			return retCode;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeAll(rs, past, c, conn);
		}
		return -1;
	}

	public static int Add_Money_Car(Connection conn, String Tel,String ID_Card, String Buyer_id) {
		PreparedStatement past = null;
		ResultSet rs = null;
		CallableStatement c = null;
		try {
			int retCode = 0;
			past = conn.prepareStatement("select * from On_Line_MoneyCard where Tel=? and ID_Card =? ");
			past.setString(1, Tel);
			past.setString(2, ID_Card);
			rs = past.executeQuery();
			if (rs.next()) {
				retCode = 3;// 此储值卡已经添加
			}
			else {
				c = conn.prepareCall("{call Add_Money_car (?,?,?,?,?)}");
				c.setString(1, Tel);
				c.setString(2, ID_Card);
				c.setString(3, Buyer_id);
				c.setString(4,MD5key.getMD5Pass(Tel + ID_Card + Buyer_id+ MD5_Key_String.MD5_KEY)); // 防止修改数据库的ID_Card
				c.registerOutParameter("return", java.sql.Types.LONGNVARCHAR);
				c.execute();
				retCode = c.getInt("return");
			}
			return retCode;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeAll(rs, past, c, conn);
		}
		return -1;
	}

	public static List<Money_Car> Select_UserMoney_Car(Connection conn,
			String Buyer_id) { // 查询用户的储值卡
		ResultSet rs = null;
		CallableStatement c=null;
		try {
			c = conn.prepareCall("{call Select_UserMoney_Car (?)}");
			c.setString(1, Buyer_id);
			rs = c.executeQuery();
			List<Money_Car> list = new ArrayList<Money_Car>();
			while (rs.next()) {
				String Tel = rs.getString("Tel");
				String ID_Card = rs.getString("ID_Card");
				String Money = rs.getString("Money");
				Money_Car money_Car = new Money_Car();
				money_Car.setID_Card(ID_Card);
				money_Car.setTel(Tel);
				money_Car.setMoney(Money);
				list.add(money_Car);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DB.closeAll(rs, null, c, conn);
			DB.closeConn(conn);
		}
		return null;
	}

	public static int Move_Car_Money_to_wallet(Connection conn,
			String Buyer_id, String ID_Card, String money, String PayPwd,
			String app_system) { // //
		// 把储值卡的钱转到钱包
		int retCode = 0; // 在数据库中是密码错误,支付异常
		ResultSet rs = null;
		CallableStatement c = null;
		try {
			conn.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			c = conn.prepareCall("{call Move_Car_money_to_waller (?,?,?,?,?)}");
			c.setString(1, Buyer_id);
			c.setString(2, ID_Card);
			c.setString(3, money);
			c.setString(4, PayPwd);
			c.registerOutParameter("return", java.sql.Types.LONGNVARCHAR);
			c.execute();
			retCode = c.getInt("return");
			if (retCode == 1) {
				int fage = Money_Authentication
						.Updata_Money_Key(conn, Buyer_id);// 把钱包的密钥改一下
				int count = Money_Authentication.add_moneycar_wallet_log(conn, // 添加转账记录
						money, "1", Buyer_id, ID_Card, app_system);
//				int of = Money_Authentication.Updata_offline_MoneyCar(conn,
//						ID_Card); // 把线下储值卡的密钥改一下
				if (count == 1 && fage == 1 ) {
					conn.commit();
					conn.setAutoCommit(true);
					return 1;
				}
			} else {
				return retCode;
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, c, conn);
		}
		return -1;
	}

	public static int Move_Wallet_Money_to_car(Connection conn,
			String Buyer_id, String ID_Card, String money, String PayPwd,
			String app_system) { // 把钱包的钱转到储值卡
		CallableStatement c = null;
		try {
			conn.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			c = conn.prepareCall("{call Move_Wallet_money_to_Car (?,?,?,?,?)}");
			c.setString(1, Buyer_id);
			c.setString(2, ID_Card);
			c.setString(3, money);
			c.setString(4, PayPwd);
			c.registerOutParameter("return", java.sql.Types.LONGNVARCHAR);
			c.execute();
			int retCode = c.getInt("return");
			System.out.println(retCode);
			if (retCode == 1) {
				int fage = Money_Authentication
						.Updata_Money_Key(conn, Buyer_id); // 0是吧钱包的钱转到储值卡
				int count = Money_Authentication.add_moneycar_wallet_log(conn,
						money, "0", Buyer_id, ID_Card, app_system);
//				int of = Money_Authentication.Updata_offline_MoneyCar(conn,
//						ID_Card); // 把线下储值卡的密钥改一下
				if (count == 1 && fage == 1) {
					conn.commit();
					conn.setAutoCommit(true);
					return 1;
				}
			} else {
				return retCode;
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(c, conn);
		}
		return -1;
	}

	public static int Add_money_To_Money_car(Connection conn, String Tel,
			String ID_Card, String money, String sign) {
		PreparedStatement past = null;
		ResultSet rs = null;
		try {
			past = conn
					.prepareStatement("select Tel,ID_Card,Money,Money_MD5 from Off_Line_MoneyCard where ID_Card=? and Tel= ?");
			past.setString(1, ID_Card);
			past.setString(2, Tel);
			rs = past.executeQuery();
			if (rs.next()) {
				String iD_Card = rs.getString("ID_Card");
				String old_money = rs.getString("Money");
				String Money_MD5 = rs.getString("Money_MD5");
				
				String str = MD5key.getMD5Pass(Tel + iD_Card + old_money
						+ MD5_Key_String.MD5_KEY);
				System.out.println(str);
				System.out.println(str.equals(Money_MD5));
				if (str.equals(Money_MD5)) {
					String now_money = String_Tool
							.String_IS_Four((Double.parseDouble(old_money) + Double
									.parseDouble(money)));

					PreparedStatement past1 = conn
							.prepareStatement("UPDATE Off_Line_MoneyCard set Money=?,Money_MD5=? where Tel =?");

					past1.setString(1, now_money);
					past1.setString(
							2,
							MD5key.getMD5Pass(Tel + iD_Card + now_money
									+ MD5_Key_String.MD5_KEY));
					past1.setString(3, Tel);
					int a = past1.executeUpdate();
					DB.closePreparedStatement(past1);
					return a;       //修改的数字
				} else {
					return -2; // 信息遭篡改
				}
			} else {
				return -1; // 没有此人
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -4;
	}
	
	public static JSONArray Select_wallet_log(Connection conn,String sql,String Buyer_id){
	
		ResultSet rs=null;
		PreparedStatement past=null;
		try{
			past=conn.prepareStatement(sql);
			past.setString(1, Buyer_id);
			rs=past.executeQuery();
			return ResultSet_To_JSON.resultSetToJsonArray(rs);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
