package com.wallet.online;

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

@WebServlet(description = "查询支付方式", urlPatterns = { "/Select_Pay_Way" })
public class Select_Pay_Way extends HttpServlet {
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
		ResultSet rs=null;
		PreparedStatement past2=null;
		Connection conn=GetConnection.getSpuerConn();
		try {
			past2 = conn.prepareStatement("select Pay_wayId,Describe,Public_key ,[Partner],Seller from dbo.Pay_way_Table ");
			rs = past2.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			DB.closeRs_Statement(rs, past2);
            if(array!=null&&array.length()>0){
            	out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.toString()+ "}");
            }
            else{
            	out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + array.toString()+ "}");
            }
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + ""
					+ "}");
			e.printStackTrace();
		}
		finally{
			DB.closeRs_Con(rs,past2, conn);
		}
		out.flush();
		out.close();
	}
}
