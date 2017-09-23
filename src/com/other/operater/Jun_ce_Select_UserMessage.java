package com.other.operater;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import DB.DB;
import DB.GetConnection;
import Tool.ResultSet_To_JSON;

/**
 * Servlet implementation class Jun_ce_Select_UserMessage
 */
@WebServlet(description = "查询用户信息", urlPatterns = { "/Jun_ce_Select_UserMessage" })
public class Jun_ce_Select_UserMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		PreparedStatement past = null;
		Connection conn = null;
		ResultSet rs =null;
		String UserNo = request.getParameter("UserNo");
		try {
			conn = GetConnection.getSpuerConn();
		    past = conn.prepareStatement("select a.UserNo,a.UserName,a.Tel,a.remarks, CONVERT(varchar(100), b.Date_time, 120) as Date_time from dbo.User_Table  a , dbo.Jun_CeCheck_in b where a.UserNo=b.UserNo and a.UserNo=? and CONVERT(varchar(10),b.Date_time,20)=CONVERT(varchar(100),GETDATE(),23) ");
			past.setString(1, UserNo);
			rs = past.executeQuery();
			JSONArray array=ResultSet_To_JSON.resultSetToJsonArray(rs);
			if (array!=null&&array.length()>0) {
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":"+array.toString() +"}");
			}
			else{
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":"+array.toString() + "}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
	}

}
