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


@WebServlet(description = "¾ý²à¹ÜÀíÔ±µÇÂ¼", urlPatterns = { "/Jun_ceManagerLogin" })
public class Jun_ceManagerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		PreparedStatement past = null;
		Connection conn = null;
		String UserNo = request.getParameter("UserNo");
		String pass = request.getParameter("pass");
		System.out.println(UserNo);
		System.out.println(pass);
		try {
			conn = GetConnection.getSpuerConn();
			PreparedStatement past0 = conn.prepareStatement("select * from dbo.User_Login where (Tel=? or UserName=?) and UserPass=? ");
			past0.setString(1, UserNo);
			past0.setString(2, UserNo);
			past0.setString(3, pass);
			ResultSet rs = past0.executeQuery();
			if (rs.next()) {
				DB.closeRs_Con(rs, past0, null);
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":\"" + "µÇÂ¼³É¹¦" + "\"}");
				return;
			}
			else{
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + "µÇÂ¼Ê§°Ü" + "\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(null, past, conn);
		}
	}
}
