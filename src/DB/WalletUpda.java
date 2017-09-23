package DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONObject;

import com.google.gson.Gson;

import ModelRas.MD5key;
import Tool.MD5_Key_String;
import Tool.SDKTestSendTemplateSMS;
import Tool.String_Tool;
import bean.OfficeCode;
import bean.OffinelineWallet;
import bean.RealName;

public class WalletUpda {

	public static String onlyWalletLoginJudge(Connection conn, String Buyer_id, String SignCodeNumber) {
		ResultSet rs = null;
		CallableStatement c = null;
		try {
			c = conn.prepareCall("{call p_O_WBuyerLoginOne(?,?)}");
			c.setString(1, Buyer_id);
			c.setString(2, SignCodeNumber);
			rs = c.executeQuery();
			while (rs.next()) {
				String fage = rs.getString("Buyer_id");
				return fage;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, c, conn);
		}
		return null;

	}

	public static int RegisterbuyUser(Connection conn, String Buyer_id, // 注册钱包
			String BuyerName, String BuyerPwd, String PayPwd, String Tel, String Email, String cRealName, String Cid) {
		CallableStatement c = null;
		try {
			c = conn.prepareCall("{call p_O_WalletReg (?,?,?,?,?,?,?,?,?,?)}");

			c.setString(1, Buyer_id);
			c.setString(2, BuyerName);
			c.setString(3, BuyerPwd);// 用户密码
			c.setString(4, PayPwd); // 支付密码
			c.setString(5, Tel);
			c.setString(6, Email);
			c.setString(7, cRealName);
			c.setString(8, Cid);
			c.setString(9, MD5key.getMD5Pass(Buyer_id + "0.0000" + MD5_Key_String.MD5_KEY)); // 注册的时候加密防止数据库更改
			c.registerOutParameter("return", java.sql.Types.INTEGER);
			c.execute();
			int fage = c.getInt("return");
			return fage;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(c, conn);
		}
		return 0;
	}

	public static String selectWallelastmoney(Connection conn, String id) { // 查询余额
		ResultSet rs = null;
		CallableStatement c = null;
		try {
			c = conn.prepareCall("{call p_O_GetWalletLeftMoney(?)}");
			c.setString(1, id);
			rs = c.executeQuery();
			if (rs.next()) {
				System.out.println("哈哈查到了");
				String lastmoney = rs.getString("WMoney");
				return lastmoney;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, c, conn);
		}
		return "" + 0.00;
	}

	public static String getTel_verification_code(Connection conn, String tel) {

		String sql = "select * from W_bBuyer where Tel=?";
		ResultSet rs = null;
		PreparedStatement prs = null;
		String str = null;
		try {
			prs = conn.prepareStatement(sql);
			prs.setString(1, tel);
			rs = prs.executeQuery();
			if (rs.next()) {
				HashMap<String, Object> result = SDKTestSendTemplateSMS.GET_Tel_SMS(tel);
				str = "{\"resultStatus\":\"" + result.get("statusCode") + "\"," + "\"data\":\"" + result.get("telcode")
						+ "\"}";
			} else {
				str = "{\"resultStatus\":\"" + 0 + "\"," + "\"data\":\"" + "" + "\"}";
			}
			return str;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closePreparedStatement(prs);
			DB.closeConn(conn);
		}

	}

	// 修改手机号
	public static int UpdateTelnum(Connection conn, String BuyName, String BuyPwd, String NewTel, String OldTel) {
		CallableStatement c = null;
		PreparedStatement past = null; // 查询
		PreparedStatement past1;// 更新
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
			c = conn.prepareCall("{call p_O_GetUpdateTel (?,?,?,?,?)}");
			c.setString(1, BuyName);
			c.setString(2, BuyPwd);
			c.setString(3, NewTel);// 新手机号
			c.setString(4, OldTel); // 旧手机号
			c.registerOutParameter("return", java.sql.Types.INTEGER);
			c.execute();
			int fage = c.getInt("return");
			System.out.println(fage);
			if (fage == 3) {
				past = conn.prepareStatement("select WMoney,Buyer_id from W_bBuyer where Tel=?");
				past.setString(1, NewTel);
				rs = past.executeQuery();
				if (rs.next()) {
					String Out_WMoney = rs.getString("WMoney");
					String Buyer_id = rs.getString("Buyer_id");
					past1 = conn.prepareStatement("UPDATE W_bBuyer set Money_MD5=? where Tel =?");
					past1.setString(1, MD5key.getMD5Pass(Buyer_id + Out_WMoney + MD5_Key_String.MD5_KEY));
					past1.setString(2, NewTel);
					int a = past1.executeUpdate();
					DB.closePreparedStatement(past1);
					if (a == 1) {
						conn.commit();
						conn.setAutoCommit(true);
						return 3;// 3代表成功
					}
				}
			} else {
				return fage;
			}
			return fage;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closeAll(rs, past, c, conn);
		}
		return 0;
	}

	public static int UpdatePayMenssage(Connection conn, String Tel, // 修改密码
			String Password) {
		PreparedStatement past = null;
		try {
			past = conn.prepareStatement("update PS_Wallet.dbo.W_bBuyer set PayPwd=? where Buyer_id =? ");
			past.setString(1, Password);
			past.setString(2, Tel);
			return past.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement_Rs(past, conn);
		}
		return 0;
	}

	public static int UpdatePass(Connection conn, String BuyName, // 修改密码
			String OldPwd, String NewPwd, String UpType) {
		CallableStatement c = null;
		try {
			c = conn.prepareCall("{call p_O_WalletUpPwd (?,?,?,?,?)}");
			c.setString(1, BuyName);
			c.setString(2, OldPwd);
			c.setString(3, NewPwd);// 新手机号
			c.setString(4, UpType); // 旧手机号
			c.registerOutParameter("return", java.sql.Types.INTEGER);
			c.execute();
			int fage = c.getInt("return");
			System.out.println("" + fage);
			conn.close();
			return fage;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(c, conn);
		}
		return 0;
	}

	// public static String getTel_verification_code(Connection conn, String
	// tel) {
	//
	// String sql = "select * from W_bBuyer where Tel=?";
	// ResultSet rs = null;
	// PreparedStatement prs = null;
	// String str = null;
	// try {
	// prs = conn.prepareStatement(sql);
	// prs.setString(1, tel);
	// rs = prs.executeQuery();
	// if (rs.next()) {
	// HashMap<String, Object> result = SDKTestSendTemplateSMS
	// .GET_Tel_SMS(tel);
	//
	// str = "{\"resultStatus\":\"" + result.get("statusCode") + "\","
	// + "\"data\":\"" + result.get("telcode") + "\"}";
	// } else {
	// str = "{\"resultStatus\":\"" + 0 + "\"," + "\"data\":\"" + ""
	// + "\"}";
	// }
	// return str;
	//
	// } catch (SQLException e) {
	//
	// e.printStackTrace();
	// return null;
	// } finally {
	// DB.closeResultSet(rs);
	// DB.closePreparedStatement(prs);
	// DB.closeConn(conn);
	// }
	//
	// }

	public static String Wallet_Not_Login(Connection conn, String tel, String pass) {
		CallableStatement c = null;
		try {
			c = conn.prepareCall("{call not_login(?,?,?)}");
			c.setString(1, tel);
			c.setString(2, pass);
			c.registerOutParameter("return", java.sql.Types.VARCHAR);
			c.execute();
			String fage = "" + c.getInt("return");
			return fage;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(c, conn);
		}
		return tel;
	}

	// 查询真实姓名
	public static RealName Authentication(Connection conn, String Tel) {// 查询真实姓名
		ResultSet rs = null;
		CallableStatement c = null;
		try {
			c = conn.prepareCall("{call p_O_GetTelRealName(?)}");
			c.setString(1, Tel);
			rs = c.executeQuery();
			RealName relaname = null;
			if (rs.next()) {
				String Buyer_id = rs.getString("Buyer_id");
				String BuyerName = rs.getString("BuyerName");
				String cRealName = rs.getString("cRealName");
				relaname = new RealName();
				relaname.setBuyer_id(Buyer_id);
				relaname.setBuyerName(BuyerName);
				relaname.setcRealName(cRealName);
				System.out.println("查到真实姓名");
				System.out.println(relaname.getcRealName());
				System.out.println(relaname.getBuyer_id());
				System.out.println(relaname.getBuyerName());
			}
			return relaname;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, c, conn);
		}
		return null;
	}

	// 转账之前验证真实姓名成功 //转账和支付之前都根据 账户号,和密码验证是不是有本人
	public static boolean Transe(Connection conn, String out_id, String Payoutpwd) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			System.out.println(out_id);
			System.out.println(Payoutpwd);
			pstmt = conn.prepareStatement("select Buyer_id from W_bBuyer where Buyer_id=? and PayPwd=?");
			pstmt.setString(1, out_id);
			pstmt.setString(2, Payoutpwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("验证真实姓名成功");
				return true;
			} else {
				System.out.println("没有此人");
				return false;
			}

		} catch (Exception e) {
			return false;
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// 转账
	public static int Transfermoney(Connection conn, String input_charset, String app_id, String app_system,
			String app_version, String signtype, String sign, String WTransferNo, String WTransfer_Datetime,
			String In_Account, String In_ID, String In_Name, String In_WServerID, String In_Account_type,
			String Out_Account, String Out_ID, String Out_Name, String Out_WServerID, String Out_Account_type,
			String WTransfer_Money) {
		CallableStatement c = null;
		try {
			System.out.println("转入" + In_ID);
			System.out.println("转出" + Out_ID);
			if (In_ID.equals(Out_ID)) {
				return 5;
			}
			conn.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			c = conn.prepareCall("{call p_O_WTrans (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			c.setString(1, input_charset);
			c.setString(2, app_id);
			c.setString(3, app_system);
			c.setString(4, app_version);
			c.setString(5, signtype);
			c.setString(6, sign);
			c.setString(7, WTransferNo);
			c.setString(8, WTransfer_Datetime);
			c.setString(9, In_Account);
			c.setString(10, In_ID);
			c.setString(11, In_Name);
			c.setString(12, In_WServerID);
			c.setString(13, In_Account_type);
			c.setString(14, Out_Account);
			c.setString(15, Out_ID);
			c.setString(16, Out_Name);
			c.setString(17, Out_WServerID);
			c.setString(18, Out_Account_type);
			c.setString(19, WTransfer_Money);
			c.registerOutParameter("return", java.sql.Types.INTEGER);
			c.execute();
			int fage = c.getInt("return");
			if (fage == 1) { // 转账成功以后,进行 把数据进行加密
				// 修改让转账的id的加密码
				String In_WMoney = null;
				PreparedStatement pare10 = conn.prepareStatement("select WMoney from W_bBuyer where Buyer_id=?");
				pare10.setString(1, In_ID); // 要转进的Buyer_id;
				ResultSet rs10 = pare10.executeQuery();
				if (rs10.next()) {
					In_WMoney = rs10.getString("WMoney");
				}
				DB.closeResultSet(rs10);
				DB.closePreparedStatement(pare10);
				PreparedStatement pare11 = conn.prepareStatement("UPDATE W_bBuyer set Money_MD5=? where Buyer_id =?");
				pare11.setString(1, MD5key.getMD5Pass(In_ID + In_WMoney + MD5_Key_String.MD5_KEY));
				pare11.setString(2, In_ID);
				int in = pare11.executeUpdate();
				DB.closePreparedStatement(pare11);

				// 修改自己的ID的money的加密码
				String Out_WMoney = null;
				PreparedStatement pare20 = conn.prepareStatement("select WMoney from W_bBuyer where Buyer_id=?");
				pare20.setString(1, Out_ID); // 要转进的Buyer_id;
				ResultSet rs20 = pare20.executeQuery();
				if (rs20.next()) {
					Out_WMoney = rs20.getString("WMoney");
				}
				DB.closeResultSet(rs20);
				DB.closePreparedStatement(pare20);
				PreparedStatement pare21 = conn.prepareStatement("UPDATE W_bBuyer set Money_MD5=? where Buyer_id =?");
				pare21.setString(1, MD5key.getMD5Pass(Out_ID + Out_WMoney + MD5_Key_String.MD5_KEY));
				pare21.setString(2, Out_ID);
				int out = pare21.executeUpdate();
				DB.closePreparedStatement(pare21);
				if (out == 1 && in == 1) {

					System.out.println("输出" + out + "输进" + in);
					conn.commit();
					conn.setAutoCommit(true);
					return 1;
				} else {
					return fage;
				}
			}
		} catch (SQLException e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(c, conn);
		}
		return 0;
	}

	// 支付
	public static int WallPay(Connection conn, String input_charset, String app_id, String app_system,
			String app_version, String signtype, String sign, String cStoreNo, String WServerID_cStore,
			String notify_url, String payment_type, String fLastMoney, String Buyer_id, String WServerID_buyer,
			String cardno, String T_createtime, String T_paytime, String valid_delay, String CertifyCode,
			String cSaleSheetNo, String subject, String cSheetDetail, String dNotifyCount, String bNotifyStatus) {
		CallableStatement c = null;
		try {
			conn.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			c = conn.prepareCall("{call [p_O_WPay] (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			c.setString(1, input_charset);
			c.setString(2, app_id);
			c.setString(3, app_system);
			c.setString(4, app_version);
			c.setString(5, signtype);
			c.setString(6, sign);
			c.setString(7, cStoreNo);
			c.setString(8, WServerID_cStore);
			c.setString(9, notify_url);
			c.setString(10, payment_type);
			c.setString(11, fLastMoney);
			c.setString(12, Buyer_id);
			c.setString(13, WServerID_buyer);
			c.setString(14, cardno);
			c.setString(15, T_createtime);
			c.setString(16, T_paytime);
			c.setString(17, valid_delay);
			c.setString(18, CertifyCode);
			c.setString(19, cSaleSheetNo);
			c.setString(20, subject);
			c.setString(21, cSheetDetail);
			c.setString(22, dNotifyCount);
			c.setString(23, bNotifyStatus);
			c.registerOutParameter("return", java.sql.Types.INTEGER);
			c.execute();
			int fage = c.getInt("return");
			System.out.println("支付的验证情况" + fage);
			if (fage == 1) {
				// 修改自己的ID的money的加密码
				String Out_WMoney = null;
				PreparedStatement pare20 = conn.prepareStatement("select WMoney from W_bBuyer where Buyer_id=?");
				pare20.setString(1, Buyer_id); // 要转进的Buyer_id;
				ResultSet rs20 = pare20.executeQuery();
				if (rs20.next()) {
					Out_WMoney = rs20.getString("WMoney");// 用着进行把用户的id和钱加密
				}
				DB.closeResultSet(rs20);
				DB.closePreparedStatement(pare20);
				PreparedStatement pare21 = conn.prepareStatement("UPDATE W_bBuyer set Money_MD5=? where Buyer_id =?");
				pare21.setString(1, MD5key.getMD5Pass(Buyer_id + Out_WMoney + MD5_Key_String.MD5_KEY));
				pare21.setString(2, Buyer_id);
				pare21.executeUpdate();
				DB.closePreparedStatement(pare21);

				PreparedStatement pare22 = conn
						.prepareStatement("UPDATE Simple_online.dbo.Order_Table set Pay_wayId='3',Pay_state = '1' where cSheetno =?");
				pare22.setString(1, cSaleSheetNo);
				pare22.executeUpdate();
				DB.closePreparedStatement(pare22);

				conn.commit();
				conn.setAutoCommit(true);
			}
			else{
				conn.rollback();
			}
			return fage;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DB.closeRs_Con(c, conn);
		}
		return 0;
	}

	// 得到状态码
	public static String getCode(Connection conn, String ID, String app_system, String app_version) {
		CallableStatement c = null;
		try {
			c = conn.prepareCall("{call p_O_GetPayCodeNumber(?,?,?,?)}");
			c.setString(1, ID);
			c.setString(2, app_system);
			c.setString(3, app_version);
			c.registerOutParameter("PayCode", java.sql.Types.LONGVARCHAR);
			c.execute();
			String PayCode = c.getString("PayCode");
			System.out.println("得到状态码的接口" + PayCode);
			return PayCode;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(c, conn);
		}
		return null;
	}

	// 线下支付
	public static OffinelineWallet OfflinePay(Connection conn, String PayCode, String Money, String cStoreNo,
			String cOSS_No) {
		CallableStatement c = null;
		try {
			c = conn.prepareCall("{call p_O_WTransToPoS(?,?,?,?,?,?,?,?,?,?)}");
			c.setString(1, PayCode);
			c.setString(2, Money);
			c.setString(3, cStoreNo);
			c.setString(4, cOSS_No);
			c.registerOutParameter("return", java.sql.Types.INTEGER);
			c.registerOutParameter("SheetNo", java.sql.Types.LONGNVARCHAR);
			c.registerOutParameter("BgnWMoney", java.sql.Types.LONGNVARCHAR);
			c.registerOutParameter("SalMoney", java.sql.Types.LONGNVARCHAR);
			c.registerOutParameter("EndWMoney", java.sql.Types.LONGNVARCHAR);
			c.registerOutParameter("SalDate", java.sql.Types.LONGNVARCHAR);
			c.registerOutParameter("Buyer_id", java.sql.Types.VARCHAR);
			c.execute();
			String Code = c.getString("return"); // 支付码 2余额不足 3支付码有误或超时，
			String SheetNo = c.getString("SheetNo");
			String BgnWMoney = c.getString("BgnWMoney");
			String SalMoney = c.getString("SalMoney");
			String EndWMoney = c.getString("EndWMoney");
			String SalDate = c.getString("SalDate");
			String Buyer_id = c.getString("Buyer_id");
			OffinelineWallet offlinewallet = new OffinelineWallet(Code, SheetNo, BgnWMoney, SalMoney, EndWMoney,
					SalDate);
			if (Code.equals("1")) {
				String Out_WMoney = null;
				PreparedStatement pare20 = conn.prepareStatement("select WMoney from W_bBuyer where Buyer_id=?");
				pare20.setString(1, Buyer_id); // 要转进的Buyer_id;
				ResultSet rs20 = pare20.executeQuery();
				if (rs20.next()) {
					Out_WMoney = rs20.getString("WMoney");// 用着进行把用户的id和钱加密
				}
				DB.closeResultSet(rs20);
				DB.closePreparedStatement(pare20);
				PreparedStatement pare21 = conn.prepareStatement("UPDATE W_bBuyer set Money_MD5=? where Buyer_id =?");
				pare21.setString(1, MD5key.getMD5Pass(Buyer_id + Out_WMoney + MD5_Key_String.MD5_KEY));
				pare21.setString(2, Buyer_id);
				pare21.executeUpdate();
				DB.closePreparedStatement(pare21);
			}

			return offlinewallet;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(c, conn);
		}
		return null;
	}

	public static String OfflinePayYesOrNO(Connection conn, String PayCode) {
		CallableStatement c = null;
		try {
			c = conn.prepareCall("{call p_GetPayCodeStatus(?,?)}");
			c.setString(1, PayCode);
			c.registerOutParameter("retCode", java.sql.Types.LONGNVARCHAR);
			c.execute();
			String retCode = c.getString("retCode");
			OfficeCode off = new OfficeCode(retCode, retCode);
			conn.close();
			Gson gson = new Gson();
			String str = gson.toJson(off);
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(c, conn);
		}
		return null;
	}

	public static int Wallet_Sales_Return(Connection conn, JSONObject obj) {
		PreparedStatement past = null;
		try {
			String cSheetno = obj.getString("cSheetno");
			String UserNo   = obj.getString("UserNo");
			String cStoreNo=  obj.getString("cStoreNo");
			
			past = conn.prepareStatement("insert into Return_goods (cSheetno,UserNo,dDate,cStoreNo,return_State) values(?,?,?,?,?)"); //
			past.setString(1, cSheetno);
			past.setString(2, UserNo);
			past.setString(3, String_Tool.DataBaseYear_Month_Day());
			past.setString(4, cStoreNo);
			past.setString(5, "0");
			past.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(null, past, conn);
		}
		return 0;
	}
	
	public static int Wallet_Wallet_recharge_Log(Connection conn,String Buyer_id,String Money,String excess_Money,String Pay_Way) {
		PreparedStatement past = null;
		try {
			past = conn.prepareStatement("insert into Wallet_recharge_Log (cSheetno,UserNo,dDate,cStoreNo,return_State) values(?,?,?,?,?)"); //
//			past.setString(1, cSheetno);
//			past.setString(2, UserNo);
//			past.setString(3, String_Tool.DataBaseYear_Month_Day());
//			past.setString(4, cStoreNo);
			past.setString(5, "0");
			past.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(null, past, conn);
		}
		return 0;
	}
}
