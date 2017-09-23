package com.operater.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DB.DB_user_operater;
import DB.GetConnection;

public class User_Register extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String data = request.getParameter("data");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
            String Tel=request.getParameter("Tel");
            String passWord=request.getParameter("PassWord");
            String cStoreNo=request.getParameter("cStoreNo");
            
            System.out.println(Tel+"   "+ passWord);
			int str = DB_user_operater.User_register_in(GetConnection.getSpuerConn(), Tel,passWord,cStoreNo);
            out.print("{\"resultStatus\":\"" + str + "\"," + "\"dDate\":"+ str + "}");
			System.out.println("{\"resultStatus\":\"" + str + "\"," + "\"dDate\":\""+ str + "\"}");
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\""+ 0 + "\"}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
