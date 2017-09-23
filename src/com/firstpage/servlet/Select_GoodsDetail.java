package com.firstpage.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DB.DB_goods_operate;
import DB.GetConnection;

public class Select_GoodsDetail extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String UserNo = request.getParameter("UserNo");
			String cGoodsNo = request.getParameter("cGoodsNo");
			String cStoreNo=request.getParameter("cStoreNo");
			cStoreNo="000";
			System.out.println("…Ã∆∑œÍ«È");
			System.out.println(UserNo);
			System.out.println(cGoodsNo);
			String str = DB_goods_operate.Select_GoodsDetail(GetConnection.getSpuerConn(), UserNo,cGoodsNo,cStoreNo);
			out.print(str);

			System.out.println(str);
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + ""+ "}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
