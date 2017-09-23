package com.firstpage.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import DB.DB;
import DB.GetConnection;
public class Select_collect_browse_num extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Connection conn=null;
		try {
			String browse_Num = "0";
			String collect_Num = "0";
			String UserNo = request.getParameter("UserNo");
			conn = GetConnection.getSpuerConn();
			PreparedStatement past = conn.prepareStatement("select COUNT (cGoodsNo) as browse_Num from dbo.User_browse_collection  where Operation='1'  and UserNo= ? ");
			past.setString(1, UserNo);
			ResultSet rs = past.executeQuery();
			if (rs.next()) {
				browse_Num = rs.getString("browse_Num");
			}
			DB.closePreparedStatement(past);
			DB.closeResultSet(rs);
			PreparedStatement past1 = conn.prepareStatement("select COUNT (cGoodsNo) as collect_Num from dbo.User_browse_collection  where Operation='2'  and UserNo= ? ");
			past1.setString(1, UserNo);
			ResultSet rs1 = past1.executeQuery();
			if (rs1.next()) {
				collect_Num = rs1.getString("collect_Num");
			}
			DB.closePreparedStatement(past1);
			DB.closeResultSet(rs1);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("browse_Num", browse_Num);
			map.put("collect_Num", collect_Num);
			out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":"+ new Gson().toJson(map) + "}");
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + ""+ "}");
			e.printStackTrace();
		}
		finally{
			DB.closeConn(conn);
		}
		out.flush();
		out.close();
	}

}
