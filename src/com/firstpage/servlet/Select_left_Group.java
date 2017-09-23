package com.firstpage.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import DB.DB;
import DB.GetConnection;
import Tool.ResultSet_To_JSON;
public class Select_left_Group extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Connection conn = GetConnection.getPos_ManagermainConn();
		PreparedStatement past=null;
		ResultSet rs=null;
		try {
			String cParentNo=request.getParameter("cParentNo");
			past = conn.prepareStatement("select cParentNo, cGroupTypeName,cGroupTypeNo from dbo.T_GroupType where cParentNo=? ");
			past.setString(1, cParentNo);
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


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Connection conn = GetConnection.getPos_ManagermainConn();
		PreparedStatement past=null;
		ResultSet rs=null;
		try {
			String cParentNo=request.getParameter("cParentNo");
			System.out.println(cParentNo);
			past = conn.prepareStatement("select cParentNo, cGroupTypeName,cGroupTypeNo from dbo.T_GroupType where cParentNo=? ");
			past.setString(1, cParentNo);
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
