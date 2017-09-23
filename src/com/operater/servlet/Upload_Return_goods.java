package com.operater.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import DB.DB_user_operater;
import DB.GetConnection;


@WebServlet(description = "ÍË»õ", urlPatterns = { "/Upload_Return_goods" })
public class Upload_Return_goods extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8"); 
		response.setHeader("content-type","text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String data1 = request.getParameter("data1");
		String data2 = request.getParameter("data2");
		try {
			JSONObject obj = new JSONObject(data1);
			JSONArray array=new JSONArray(data2);
			int code = DB_user_operater.Return_goods(GetConnection.getSpuerConn(),obj,array);
			System.out.println("{\"resultStatus\":\"" + code + "\"," + "\"dDate\":\""+ code+ "\"}");
			out.print("{\"resultStatus\":\"" + code + "\"," + "\"dDate\":\""+ code+ "\"}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
