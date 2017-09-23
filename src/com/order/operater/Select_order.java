package com.order.operater;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DB_goods_operate;
import DB.GetConnection;

public class Select_order extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		doPost(request, response);
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
			System.out.println("查询订单");
			String  UserNo=request.getParameter("UserNo");
			String  fage=request.getParameter("fage");
			String  Number_of_pages=request.getParameter("Number_of_pages");
			String  Send_Way=request.getParameter("Send_Way");
			//String  cStoreNo=request.getParameter("cStoreNo");
			String  cStoreNo="000";
			System.out.println(UserNo);
			System.out.println(fage);
			System.out.println(Number_of_pages);
			System.out.println(Send_Way);
			String str = DB_goods_operate.select_Order(GetConnection.getSpuerConn(),fage,UserNo,Integer.parseInt(Number_of_pages),cStoreNo,Send_Way);
			out.print(str);
			System.out.println(str);
			System.out.println("查询订单-----结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
