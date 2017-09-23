package com.advertisement.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
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


@WebServlet(description = "ÈÈÏúÅÅÐÐ", urlPatterns = { "/Hot_sales_ranking" })
public class Hot_sales_ranking extends HttpServlet {
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
		Connection conn=null;
		CallableStatement c=null;
		ResultSet rs=null;
		try {
			conn=GetConnection.getSpuerConn();
			c=conn.prepareCall("{call Hot_sales_ranking ()}");
			rs=c.executeQuery();
			JSONArray  array =ResultSet_To_JSON.resultSetToJsonArray(rs);
			if(array.length()>0){
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.toString().replace(" ", "")+ "}");
			}
			else{
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + array.toString().replace(" ", "")+ "}");
			}
			System.out.println("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + array.toString().replace(" ", "")+ "}");
	       
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DB.closeRs_Con(rs, c, conn);
		}
		out.flush();
		out.close();
	}

}
