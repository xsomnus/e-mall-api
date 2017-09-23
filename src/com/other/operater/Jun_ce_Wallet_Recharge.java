package com.other.operater;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DB.DB;
import DB.GetConnection;
import Tool.MD5_Key_String;
import Tool.MD5key;

@WebServlet(description = "钧策钱包充值", urlPatterns = { "/Jun_ce_Wallet_Recharge" })
public class Jun_ce_Wallet_Recharge extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		PreparedStatement past = null;
		Connection conn = null;
		String UserNo = request.getParameter("UserNo");
		String money = request.getParameter("money");
		System.out.println(UserNo);
		System.out.println(money);
		ResultSet rs = null;
		try {
			conn = GetConnection.getSpuerConn();
			conn.setAutoCommit(false);
			past = conn.prepareStatement("select WMoney  from PS_Wallet.dbo.W_bBuyer where  Buyer_id =? ");
			past.setString(1, UserNo);
			rs = past.executeQuery();
			if (rs.next()) {
				String WMoney = rs.getString("WMoney");
				BigDecimal m1 = new BigDecimal(WMoney);
				BigDecimal m2 = new BigDecimal(money);

				BigDecimal almoney = m1.add(m2);
				PreparedStatement past1 = conn
						.prepareStatement("update  PS_Wallet.dbo.W_bBuyer  set WMoney=?   where  Buyer_id =? ");
				past1.setString(1, "" + almoney);
				past1.setString(2, UserNo);
				int a = past1.executeUpdate();

				PreparedStatement past21 = conn
						.prepareStatement("select  WMoney from  PS_Wallet.dbo.W_bBuyer where  Buyer_id =? ");
				past21.setString(1, UserNo);
				ResultSet rs2 = past21.executeQuery();
				while (rs2.next()) {
					String WMoney1 = rs2.getString("WMoney");
					PreparedStatement past3 = conn
							.prepareStatement("update  PS_Wallet.dbo.W_bBuyer set Money_MD5=?  where  Buyer_id =? ");
					String str = MD5key.getMD5Pass(UserNo + WMoney1 + MD5_Key_String.MD5_KEY);
					past3.setString(1, str);
					past3.setString(2, UserNo);
					a = past3.executeUpdate();
					DB.closePreparedStatement(past3);
				}
				DB.closeResultSet(rs2);
				DB.closePreparedStatement(past21);

				System.out.println(MD5key.getMD5Pass(UserNo + almoney + MD5_Key_String.MD5_KEY));
				System.out.println("影响函数" + a);
			} else {
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + "测试用加密不对" + "\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(null, past, conn);
		}
	}
}
