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
import ModelRas.MD5key;
import Tool.ResultSet_To_JSON;
import Tool.String_Tool;


@WebServlet(description = "查询所有的用户", urlPatterns = { "/Jun_ce_Select_All_User" })
public class Jun_ce_Select_All_User extends HttpServlet {
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
		String dDate=request.getParameter("dDate");
		Connection conn = null;
		try {
	
			conn=GetConnection.getSpuerConn();
			PreparedStatement past0 = conn.prepareStatement("select a.UserName,a.Tel,Email,a.ImagePath,a.remarks from  dbo.User_Table a where a.UserNo not in (select UserNo from Jun_CeCheck_in  where dDate=?) ");
			past0.setString(1, dDate);
			ResultSet rs0 = past0.executeQuery();
			JSONArray array0=ResultSet_To_JSON.resultSetToJsonArray(rs0);
			DB.closeRs_Con(rs0, past0, null);
			
			PreparedStatement past1 = conn.prepareStatement("select b.UserName,b.Tel,Email,b.ImagePath,b.remarks from Jun_CeCheck_in a,dbo.User_Table b where  dDate=? and a.UserNo=b.UserNo ");
			past1.setString(1, dDate);
			ResultSet rs1 = past1.executeQuery();
			JSONArray array1=ResultSet_To_JSON.resultSetToJsonArray(rs1);
			DB.closeRs_Con(rs1, past1, null);
			out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array0.toString().replace(" ", "") +"," + "\"dDate1\":" + array1.toString().replace(" ", "") + "}");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(null, null, conn);
		}
	}
	

}
