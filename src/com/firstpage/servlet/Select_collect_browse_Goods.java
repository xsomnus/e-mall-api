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

public class Select_collect_browse_Goods extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
			String fage = request.getParameter("fage"); //1是浏览2是收藏
			String Number_of_pages=request.getParameter("Number_of_pages");
			String cStoreNo=request.getParameter("cStoreNo");
			System.out.println("用户编号"+UserNo);
			JSONArray array = DB_goods_operate.Select_collect_browse_Goods(GetConnection.getSpuerConn(), UserNo,fage,Number_of_pages,cStoreNo);
			if(array!=null&&array.length()>0){
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":"+ array.toString().replace(" ", "") + "}");
			}
			else{
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":"+ array.toString().replace(" ", "") + "}");
			}
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + ""
					+ "}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
