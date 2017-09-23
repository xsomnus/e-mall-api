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
import Tool.String_Tool;

@WebServlet(description = "²éÑ¯¹«¸æ", urlPatterns = { "/Announcement" })
public class Announcement extends HttpServlet {
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
		ResultSet rs = null;
		String fage = request.getParameter("fage");
		try {
			String sql = "";
			if (!String_Tool.isEmpty(fage)) {
				sql = "select top 10 ID,title,CONVERT(varchar(100), dDatetime , 120) as dDate,contentdetails from Announcement order by dDatetime desc";
			} else {
				sql = "select top 1 ID,title,contentdetails from Announcement order by dDatetime desc ";
			}
			conn = GetConnection.getSpuerConn();
			past = conn.prepareStatement(sql);
			rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			if (array != null && array.length() > 0) {
				if (!String_Tool.isEmpty(fage)) {
					out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.toString() + "}");
				} else {
					out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.getJSONObject(0).toString()+ "}");
				}
			} else {
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + 0 + "\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
	}
}
