package com.firstpage.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import DB.DB_goods_operate;
import DB.GetConnection;

public class Select_right_goods extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String cGroupTypeNo = request.getParameter("cGroupTypeNo");
			String Number_of_pages = request.getParameter("Number_of_pages");
			String cStoreNo = request.getParameter("cStoreNo");
			String UserNo = request.getParameter("UserNo"); // 根据不同的人返回不同的价格
			System.out.println(Number_of_pages);
			JSONArray array = DB_goods_operate.JSON(GetConnection.getSpuerConn(), cGroupTypeNo, Number_of_pages,cStoreNo, UserNo);
			if (array != null && array.length() > 0) {
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.toString().replace(" ", "") + "}");
			} else {
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + array.toString().replace(" ", "") + "}");
			}
			System.out.println(array.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
