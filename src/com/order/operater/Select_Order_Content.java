package com.order.operater;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import DB.DB_goods_operate;
import DB.GetConnection;

@WebServlet(description = "²éÑ¯¶©µ¥ÄÚÈÝ", urlPatterns = { "/Select_Order_Content" })

public class Select_Order_Content extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String cSheetNo = request.getParameter("cSheetNo");
			System.out.println(cSheetNo);
			JSONObject obj = DB_goods_operate.select_Order_Content(GetConnection.getSpuerConn(), cSheetNo);
			if (obj != null) {
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + obj.toString().replace(" ", "") + "}");
			} else {
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":\"" + "" + "\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
