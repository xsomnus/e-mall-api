package com.order.operater;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DB.DB;
import DB.GetConnection;
import ModelRas.MD5key;
import Tool.MD5_Key_String;
import Tool.String_Tool;

@WebServlet(description = "确认收货", urlPatterns = { "/Affirm_Receipt_Goods" }) // 确认订单已经收到
public class Affirm_Receipt_Goods extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Connection conn = GetConnection.getSpuerConn();
		try {
			String cSheetno = request.getParameter("cSheetno");// 订单号
			// String cStoreNo = request.getParameter("cStoreNo");
			String cStoreNo = "000";
			String UserNo = request.getParameter("UserNo");
			System.out.println(UserNo);
			System.out.println(cSheetno);
			int a = 0;
			conn.setAutoCommit(false);

			PreparedStatement past0 = conn.prepareStatement("select Pay_state from dbo.Order_Table where cSheetno=? ");
			past0.setString(1, cSheetno);
			ResultSet rs0 = past0.executeQuery();
			while (rs0.next()) {
				String Pay_state = rs0.getString("Pay_state").replace(" ", "");
				System.out.println(Pay_state);
				if (Pay_state.equals("3")) {
					out.print("{\"resultStatus\":\"" + 3 + "\"," + "\"dDate\":\"" + 3 + "\"}");
					System.out.println("{\"resultStatus\":\"" + 3 + "\"," + "\"dDate\":\"" + 3 + "\"}");
					return;
				}
			}
			DB.closeResultSet(rs0);
			DB.closePreparedStatement(past0);

			PreparedStatement past = conn.prepareStatement("update Order_Table set Pay_state =?  where cSheetno=? ");
			past.setString(1, "3");
			past.setString(2, cSheetno);
			a = past.executeUpdate();
			DB.closePreparedStatement(past);

			PreparedStatement past01 = conn.prepareStatement("select * from Order_Table where cSheetno=? ");
			past01.setString(1, cSheetno);
			ResultSet rs = past01.executeQuery();
			if (rs.next()) {
				String All_Money = rs.getString("All_Money").replace(" ", ""); // 付款的钱
				String Reality_All_Money = rs.getString("Reality_All_Money").replace(" ", ""); // 配送实际金额
				BigDecimal all_money = new BigDecimal(All_Money);
				BigDecimal reality_All_Money = new BigDecimal(Reality_All_Money);
				BigDecimal back_money = all_money.subtract(reality_All_Money);
				System.out.println("要返回的钱数" + back_money);

				PreparedStatement pastw = conn
						.prepareStatement("update PS_Wallet.dbo.W_bBuyer set WMoney =WMoney+?  where Buyer_id=? ");
				pastw.setBigDecimal(1, back_money);
				pastw.setString(2, UserNo);
				pastw.execute();

				PreparedStatement pastw1 = conn
						.prepareStatement(" select Buyer_id, WMoney from  PS_Wallet.dbo.W_bBuyer  where Buyer_id=? ");
				pastw1.setString(1, UserNo);
				ResultSet rs2 = pastw1.executeQuery();
				if (rs2.next()) {
					String Buyer_id = rs2.getString("Buyer_id");
					String WMoney = rs2.getString("WMoney");
					String Money_MD5 = MD5key.getMD5Pass(Buyer_id + WMoney + MD5_Key_String.MD5_KEY);
					PreparedStatement pastw2 = conn
							.prepareStatement("update PS_Wallet.dbo.W_bBuyer set Money_MD5 =? where Buyer_id=?  ");
					pastw2.setString(1, Money_MD5);
					pastw2.setString(2, Buyer_id);
					a = pastw2.executeUpdate();
					if (a == 1) {
						PreparedStatement past1 = conn.prepareStatement(
								"insert into User_Affirm_Order_log (cSheetno,cStoreNo,UserNo,Date_time,flag,explain,return_money) values(?,?,?,?,?,?,?) ");
						past1.setString(1, cSheetno);
						past1.setString(2, cStoreNo);
						past1.setString(3, UserNo);
						past1.setString(4, String_Tool.DataBaseTime());
						String flag = "";
						String explain = "";
						if (all_money.compareTo(reality_All_Money) == 1) {
							flag = "" + 1;
							explain = "多退";
						} else if (all_money.compareTo(reality_All_Money) == -1) {
							flag = "" + -1;
							explain = "少补";
						} else if (all_money.compareTo(reality_All_Money) == -1) {
							flag = "" + 0;
							explain = "不退不补";
						}
						past1.setString(5, flag);
						past1.setString(6, explain);
						past1.setString(7, back_money.toString());
						System.out.println(back_money.toString());
						a = past1.executeUpdate();
						DB.closePreparedStatement(past1);
						out.print("{\"resultStatus\":\"" + a + "\"," + "\"dDate\":\"" + a + "\"}");
						System.out.println("{\"resultStatus\":\"" + a + "\"," + "\"dDate\":\"" + a + "\"}");
						conn.commit();
						conn.setAutoCommit(true);
					}
				} else {
					System.out.println("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + 0 + "\"}");
				}
			} else {
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + 0 + "\"}");
				System.out.println("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + 0 + "\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DB.closeConn(conn);
		}
	}
}
