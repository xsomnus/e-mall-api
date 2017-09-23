package com.address.operater;

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

import DB.GetConnection;
import Tool.ResultSet_To_JSON;


@WebServlet(description = "查询所有门店的地址", urlPatterns = { "/Select_All_Store_Address" })
public class Select_All_Store_Address extends HttpServlet {
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
		Connection conn=null;
		PreparedStatement past=null;
		ResultSet rs=null;
		try {
			 conn=GetConnection.getSpuerConn();
			 past=conn.prepareStatement("select * from Store_address_site");
			 rs=past.executeQuery();
			JSONArray array=ResultSet_To_JSON.resultSetToJsonArray(rs);
			if(array!=null&&array.length()>0){
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.toString().replace(" ", "")+ "}");
				System.out.println("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.toString().replace(" ", "")+ "}");
			}
			else{
				out.print("{\"resultStatus\":\"" + 0+ "\"," + "\"dDate\":" +  array.toString().replace(" ", "")+ "}");
			}
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0+ "\"," + "\"dDate\":\"" + 0+ "\"}");
			e.printStackTrace();
		}
		finally{
			DB.DB.closeRs_Con(rs, past, conn);
		}
		out.flush();
		out.close();
	}
}
