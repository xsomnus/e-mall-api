package com.order.operater;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DB_goods_operate;
import DB.GetConnection;

@WebServlet(description = "查询订单自提", urlPatterns = { "/Select_Order_PickInStore" })
public class Select_Order_PickInStore extends HttpServlet {
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
		try {
			System.out.println("查询订单");
			String UserNo=request.getParameter("UserNo");
			String fage=request.getParameter("fage");//1
			String Number_of_pages=request.getParameter("Number_of_pages"); //1
			String cStoreNo=request.getParameter("cStoreNo");//1
			System.out.println(UserNo);
			System.out.println(fage);
			System.out.println(Number_of_pages);
			String str = DB_goods_operate.select_order_PickInStore(GetConnection.getSpuerConn(), fage,UserNo,Integer.parseInt(Number_of_pages),cStoreNo);
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
