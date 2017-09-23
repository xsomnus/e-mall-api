package com.address.operater;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DB_user_operater;
import DB.GetConnection;

public class Set_Address_Default extends HttpServlet {


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
			String AddressID=request.getParameter("AddressID").trim();
			String UserNo=request.getParameter("UserNo").trim();
			int str = DB_user_operater.Set_address_def(GetConnection.getSpuerConn(),AddressID,UserNo);
            out.print("{\"resultStatus\":\"" + str + "\"," + "\"dDate\":\""+ str + "\"}");
			System.out.println("{\"resultStatus\":\"" + str + "\"," + "\"dDate\":\""+ str + "\"}");
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\""+ 0 + "\"}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
