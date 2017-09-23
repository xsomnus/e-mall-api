package com.other.operater;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.google.gson.Gson;

import DB.DB;
import DB.GetConnection;
import Tool.ResultSet_To_JSON;
import Tool.String_Tool;

/**
 * Servlet implementation class Jun_ceSelect_UserCheck
 */
@WebServlet("/Jun_ceSelect_UserCheck")
public class Jun_ceSelect_UserCheck extends HttpServlet {
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
		String dDate=request.getParameter("dDate");
		String fage=request.getParameter("fage"); //fage==1是已签到 0是为签到
		PreparedStatement past = null;
		Connection conn = null;
		try {
			conn = GetConnection.getSpuerConn();
			past = conn.prepareStatement("select * from  (select  COUNT(*) as wei from dbo.User_Table  where  UserNo not in(select UserNo from Jun_CeCheck_in where dDate=?)) a, (select  COUNT(*) as yi from dbo.User_Table  a, Jun_CeCheck_in b where a.UserNo=b.UserNo and b.dDate=?) b,(select  COUNT(*) as sum from dbo.User_Table)c");
			past.setString(1, dDate);
			past.setString(2, dDate);
			ResultSet rs = past.executeQuery();
			HashMap<String, String> map = new HashMap<String, String>();
			if (rs.next()) {
				String wei = rs.getString("wei");
				String yi = rs.getString("yi");
				String sum=rs.getString("sum");
				map.put("wei", wei);
				map.put("yi", yi);
				map.put("sum", sum);
			}
			DB.closeRs_Con(rs, past, null);
			Gson gson=new Gson();
			String sql="";
			if(fage.equals("1")){ //已签到
			  sql="select a.Date_time,a.UserNo,b.remarks,b.UserName,b.Tel,b.ImagePath from Jun_CeCheck_in a, User_Table  b where a.dDate=? and a.UserNo=b.UserNo ";
			}else{
				sql="select UserNo,UserName,Tel,ImagePath,remarks,Date_time='未签到' from dbo.User_Table  where UserNo not in(select UserNo from Jun_CeCheck_in where dDate=?)";
			}
			PreparedStatement past1 = conn.prepareStatement(sql);
			past1.setString(1, dDate);
			ResultSet rs1 = past1.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs1);
			if (array != null && array.length() > 0) {
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dData\":" + gson.toJson(map) +","+ "\"dData1\":" + array.toString() +"}");
			}else{
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dData\":" + gson.toJson(map) +","+ "\"dData1\":" + array.toString() +"}");
			}
			System.out.println("{\"resultStatus\":\"" + 0 + "\"," + "\"dData\":" + gson.toJson(map) +","+ "\"dData1\":" + array.toString() +"}");
			DB.closeRs_Con(rs1, past1, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(null, null, conn);
		}
	}
}
