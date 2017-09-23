package com.firstpage.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import DB.DB;
import DB.GetConnection;
import Tool.ResultSet_To_JSON;


@WebServlet(description = "查询最近的店铺编号", urlPatterns = { "/Select_Nearest_cStoreNo" })
public class Select_Nearest_cStoreNo extends HttpServlet {
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
		Connection conn = GetConnection.getPos_ManagermainConn();
		CallableStatement c = null;
		ResultSet rs = null;
		try {
			String longitude=request.getParameter("longitude");
			String latitude=request.getParameter("latitude");
			c = conn.prepareCall("{call Select_Nearest_cStoreNo (?,?) }");
			c.setString(1, longitude);
			c.setString(2, latitude);
			rs = c.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			if(array!=null&&array.length()>0){
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":"+ array.toString() + "}");
			}
			else{
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":"+ array.toString() + "}");
			}
			System.out.println(array.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			DB.closeResultSet(rs);
			DB.closeConn(conn);
		}
		out.flush();
		out.close();
	}
}
