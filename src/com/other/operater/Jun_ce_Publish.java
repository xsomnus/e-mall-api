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
import cn.jpush.example.JPushClientExample;

@WebServlet(description = "居策发布公告", urlPatterns = { "/Jun_ce_Publish" })
public class Jun_ce_Publish extends HttpServlet {
	
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
		String title = request.getParameter("title");
		String contentdetails = request.getParameter("contentdetails");
		System.out.println(title);
		System.out.println(contentdetails);
		try {
			conn = GetConnection.getSpuerConn();
			past = conn.prepareStatement("insert into Announcement (title,contentdetails,starttime,endtime,dDatetime,dDate) values(?,?,?,?,?,?)");
			past.setString(1, title);
			past.setString(2, contentdetails);
			past.setString(3, String_Tool.DataBaseTime());
			past.setString(4, String_Tool.DataBaseTime());
			past.setString(5, String_Tool.DataBaseTime());
			past.setString(6, String_Tool.DataBaseYear_Month_Day());
			int a = past.executeUpdate();
			if (a == 1) {
				out.print("{\"resultStatus\":\"" + a + "\"," + "\"dDate\":\"" + "发布成功" + "\"}");
				JPushClientExample.testSend(title, contentdetails); //均策推送通知
			} else {
				out.print("{\"resultStatus\":\"" + a + "\"," + "\"dDate\":\"" + "发布失败" + "\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(null, past, conn);
		}
	}
}
