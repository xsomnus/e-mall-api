package com.firstpage.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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


@WebServlet(description = "²éÑ¯¶¥²¿µÄ", urlPatterns = { "/Select_Top_Group" })
public class Select_Top_Group extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Connection conn = GetConnection.getPos_ManagermainConn();
		PreparedStatement past=null;
		ResultSet rs=null;
		try {

			past = conn.prepareStatement(" select ImagePath,cParentNo,cGroupTypeName, cGroupTypeNo, cCategoryAlias from dbo.T_GroupType where cParentNo='--' and bOnLine='1' ");
		
			rs = past.executeQuery();
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
			DB.closePreparedStatement(past);
			DB.closeConn(conn);
		}
		out.flush();
		out.close();
	}
}
