package com.operater.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DB;
import DB.GetConnection;
import Tool.String_Tool;

@WebServlet(description = "支付宝回掉", urlPatterns = { "/Pay_State" })
public class Pay_State extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Connection conn=GetConnection.getSpuerConn();
		try {
			
			String out_trade_no=request.getParameter("out_trade_no");//原支付请求的商户订单号
			String buyer_id=request.getParameter("buyer_id");          //买家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字
			String buyer_logon_id=request.getParameter("buyer_logon_id");   //买家支付宝账号
			String seller_id=request.getParameter("seller_id");//卖家支付宝用户号
			String buyer_pay_amount=request.getParameter("buyer_pay_amount");//付款金额
			PreparedStatement past = conn.prepareStatement("update  dbo.Order_Table set Pay_state = '1' ,Pay_wayId='1' where cSheetno=? ");
			past.setString(1, out_trade_no);
			int a=past.executeUpdate();
			DB.closePreparedStatement(past);
			
			System.out.println("支付宝通知"+out_trade_no);
			System.out.println(buyer_id);
			System.out.println(buyer_logon_id);
			
			System.out.println(seller_id);
			System.out.println(buyer_pay_amount);


			PreparedStatement past1=conn.prepareStatement("INSERT into Pay_Log (datetime,cSheetno,buyer_id,buyer_logon_id,seller_id,buyer_pay_amount,Pay_Way) values(?,?,?,?,?,?,?) ");
			past1.setString(1, String_Tool.DataBaseTime());
			past1.setString(2, out_trade_no);
			past1.setString(3, buyer_id);
			past1.setString(4, buyer_logon_id);
			past1.setString(5, seller_id);
			past1.setString(6, buyer_pay_amount);
			past1.setString(7, "支付宝");
			past1.execute();
			DB.closePreparedStatement(past1);
			//out.print("{\"resultStatus\":\"" + a + "\"," + "\"dDate\":\""+ a + "\"}");
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		finally{
			DB.closeConn(conn);
		}
		
	}

}
