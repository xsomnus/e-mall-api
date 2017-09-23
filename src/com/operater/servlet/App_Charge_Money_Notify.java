package com.operater.servlet;

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
import Tool.MD5_Key_String;
import Tool.MD5key;
import Tool.String_Tool;

@WebServlet(description = "app充钱通知", urlPatterns = { "/App_Charge_Money_Notify"})
public class App_Charge_Money_Notify extends HttpServlet {
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
		Connection conn = GetConnection.getSpuerConn();
		PreparedStatement past = null;
		try {
			conn.setAutoCommit(false);
			int a = 0;
			String Pay_way = "";
			String Pay_wayId = request.getParameter("Pay_wayId");
			
			if (Pay_wayId.equals("1")) {
				Pay_way = "支付宝充值";
			} else if (Pay_wayId.equals("2")) {
				Pay_way = "微信充值";
			}
			String UserNo = request.getParameter("UserNo"); // 用户标识
			String buyer_pay_amount = request.getParameter("buyer_pay_amount");//实际付款金额总额
			String excess_Money = request.getParameter("excess_Money"); //充值多送的额度
			String encryption = request.getParameter("Signature");// 加密MD5
																	// (UserNo+buyer_pay_amount+"warelucent")
			System.out.println("手机通知");
			System.out.println(Pay_way);
			System.out.println(UserNo);
			System.out.println(buyer_pay_amount);
			System.out.println(encryption);
			System.out.println(MD5key.getMD5Pass(UserNo + buyer_pay_amount + "warelucent").equals(encryption));
			if (MD5key.getMD5Pass(UserNo + buyer_pay_amount + "warelucent").equals(encryption)) {
				
				PreparedStatement past0=conn.prepareStatement("select top 1 Pay_Money,excess_Money from PS_Wallet.dbo.Wallet_recharge_strategy where Pay_Money<= ? order by Pay_Money desc");
				past0.setString(1, buyer_pay_amount);
				ResultSet rs0=past0.executeQuery();
				if(rs0.next()){
					excess_Money=rs0.getString("excess_Money");
				}
				else{
					excess_Money="0";
				}
				past = conn.prepareStatement("select WMoney  from PS_Wallet.dbo.W_bBuyer where  Buyer_id =? ");
				past.setString(1, UserNo);
				ResultSet rs = past.executeQuery();
				if (rs.next()) {
					String WMoney = rs.getString("WMoney");
					BigDecimal m1 = new BigDecimal(WMoney);
					BigDecimal m2 = new BigDecimal(buyer_pay_amount);
					BigDecimal m3 = new BigDecimal(excess_Money);
					BigDecimal almoney=m1.add(m2).add(m3);
					PreparedStatement past1 = conn.prepareStatement("update  PS_Wallet.dbo.W_bBuyer  set WMoney=?   where  Buyer_id =? ");
					past1.setString(1, ""+almoney);
					past1.setString(2, UserNo);
					a = past1.executeUpdate();
					
					PreparedStatement past21 = conn.prepareStatement("select  WMoney from  PS_Wallet.dbo.W_bBuyer where   Buyer_id =? ");
					past21.setString(1, UserNo);
					ResultSet rs2 = past21.executeQuery();
					while(rs2.next()){
						String WMoney1=rs2.getString("WMoney");
						PreparedStatement past3 = conn.prepareStatement("update  PS_Wallet.dbo.W_bBuyer set Money_MD5=?  where  Buyer_id =? ");
						String str = MD5key.getMD5Pass(UserNo + WMoney1+ MD5_Key_String.MD5_KEY);
						past3.setString(1, str);
						past3.setString(2, UserNo);
						a = past3.executeUpdate();
						DB.closePreparedStatement(past3);
					}
					DB.closeResultSet(rs2);
					DB.closePreparedStatement(past21);
					
					
					System.out.println(MD5key.getMD5Pass(UserNo + almoney + MD5_Key_String.MD5_KEY));
					System.out.println("影响函数"+a);
					if (a == 1) {
						PreparedStatement past2 = conn.prepareStatement(
								"INSERT into PS_Wallet.dbo.Wallet_recharge_Log (cSheetNo,Buyer_id,dDate,Money,Pay_Way,excess_Money,dtime) values (?,?,?,?,?,?,?) ");
						past2.setString(1, String_Tool.reformat() + UserNo);
						past2.setString(2, UserNo);
						past2.setString(3, String_Tool.DataBaseYear_Month_Day());
						past2.setString(4, buyer_pay_amount);
						past2.setString(5, Pay_way);
						past2.setString(6, excess_Money);
						past2.setString(7, String_Tool.DataBaseTime());
						past2.execute();
						DB.closePreparedStatement(past2);
						conn.commit();
						conn.setAutoCommit(true);
						out.print("{\"resultStatus\":\"" + a + "\"," + "\"dDate\":\"" + a + "\"}");
						System.out.println("{\"resultStatus\":\"" + a + "\"," + "\"dDate\":\"" + a + "\"}");
					} else {
						conn.rollback();
						out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + "测试用加密不对" + "\"}");
						System.out.println("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + "测试用加密不对" + "\"}");
					}
				} else {
					conn.rollback();
					out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + "测试用加密不对" + "\"}");
					System.out.println("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + "测试用加密不对" + "\"}");
				}
			} else {
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + "测试用加密不对" + "\"}");
				System.out.println("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + "测试用加密不对" + "\"}");
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			out.print("{\"resultStatus\":\"" + -1 + "\"," + "\"dDate\":\"" + 0 + "\"}");
			e.printStackTrace();
		} finally {
			DB.closeStatement_Rs(past, conn);
		}
	}
}
