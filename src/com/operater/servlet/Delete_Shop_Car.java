package com.operater.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DB_goods_operate;
import DB.GetConnection;

public class Delete_Shop_Car extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
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
		try {
			String UserNo=request.getParameter("UserNo");
		    String cGoodsNo=request.getParameter("cGoodsNo");
		    String cStoreNo=request.getParameter("cStoreNo");
		    cStoreNo="000";
		    int  str =DB_goods_operate.delete_Shop_car(GetConnection.getSpuerConn(),UserNo,cGoodsNo,cStoreNo);
		    out.print("{\"resultStatus\":\"" + str + "\"," + "\"dDate\":"+ 0 + "}");
			System.out.println(str);
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":"+ 0 + "}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
