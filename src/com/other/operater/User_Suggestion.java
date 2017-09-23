package com.other.operater;
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

@WebServlet(description = "Òâ¼û·´À¡", urlPatterns = { "/User_Suggestion" })

public class User_Suggestion extends HttpServlet {
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
		PreparedStatement past=null;
		Connection conn=null;
		try {
			conn=GetConnection.getSpuerConn();
			String UserNo= request.getParameter("UserNo");
			String Suggestions= request.getParameter("Suggestions");
			String sql="insert into User_Suggestions (UserNo,dDate,Suggestions) values(?,?,?)";
			past=conn.prepareStatement(sql);
			past.setString(1, UserNo);
			past.setString(2, String_Tool.DataBaseTime());
			past.setString(3, Suggestions);
			past.execute();
			out.print("{\"resultStatus\":\"" + 1 + "\""+ "}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DB.closeRs_Con(null, past, conn);
		}
	}
}
