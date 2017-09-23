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
import DB.DB;
import DB.GetConnection;
import ModelRas.MD5key;
import Tool.String_Tool;

@WebServlet(description = "君侧签到", urlPatterns = { "/Jun_ceCheck_in" })
public class Jun_ceCheck_in extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
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
		String Sign = request.getParameter("Sign");
		try {
			if (String_Tool.isEmpty(UserNo) || String_Tool.isEmpty(Sign)) {
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + "用户不能为null" + "\"}");
				return;
			}
			if (!Sign.equals(MD5key.getMD5Pass(UserNo + "ware"))) {
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + "签名出错" + "\"}");
				return;
			}
			conn = GetConnection.getSpuerConn();
			
			PreparedStatement past00 = conn.prepareStatement("select * from dbo.User_Table where UserNo=? ");
			past00.setString(1, UserNo);
			ResultSet rs0 = past00.executeQuery();
			if(!rs0.next()){//如果没有此手机号
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + "此手机号不存在" + "\"}");
				DB.closeRs_Con(rs0, past00, null);
				return ;
			}
			PreparedStatement past0 = conn.prepareStatement("select * from Jun_CeCheck_in where UserNo=? and dDate=?");
			past0.setString(1, UserNo);
			past0.setString(2, String_Tool.DataBaseYear_Month_Day());
			ResultSet rs = past0.executeQuery();
			if (rs.next()) {
				DB.closeRs_Con(rs, past0, null);
				out.print("{\"resultStatus\":\"" + 2 + "\"," + "\"dDate\":\"" + "今天已经签到" + "\"}");
				return;
			}
			past = conn.prepareStatement("insert into Jun_CeCheck_in (UserNo,Date_time,dDate) values(?,?,?)");
			past.setString(1, UserNo);
			past.setString(2, String_Tool.DataBaseTime());
			past.setString(3, String_Tool.DataBaseYear_Month_Day());
			int a = past.executeUpdate();
			out.print("{\"resultStatus\":\"" + a + "\"," + "\"dDate\":\"" + a + "\"}");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(null, past, conn);
		}
	}
}
