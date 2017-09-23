package com.order.operater;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import DB.DB;
import DB.GetConnection;
import Tool.ResultSet_To_JSON;
import Tool.String_Tool;

@WebServlet(description = "查询订单物流", urlPatterns = { "/Select_Order_Distribution" })
public class Select_Order_Distribution extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Connection conn = GetConnection.getSpuerConn();
		PreparedStatement past=null;
		ResultSet rs=null;
		try {
			String cSheetno = request.getParameter("cSheetno");// 订单号
			System.out.println("查看物流"+cSheetno);
		    past = conn.prepareStatement("select a.cSheetno,a.Date_time,a.Pay_state, b.cOperatorNo,b.cOperator,b.dDate from Order_Table a left outer join  Store_Receive_Order_Log b on a.cSheetno=b.cSheetno where  a.cSheetno=? ");
			past.setString(1, cSheetno);
			rs= past.executeQuery();
			JSONArray array=ResultSet_To_JSON.resultSetToJsonArray(rs);
			out.print(String_Tool.get_output_str(array));
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
	}
}
