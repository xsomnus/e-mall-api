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

public class Select_Search_Goods extends HttpServlet {

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
		JSONArray array=null;
		try {
			String Sort_type=request.getParameter("Sort_type");
		//	String cStoreNo=request.getParameter("cStoreNo");
    		String cStoreNo="000";
			String fage=request.getParameter("fage").trim();
			System.out.println("ËÑË÷Ò³Ãæ"+fage);
			System.out.println("ÀàÐÍ"+Sort_type);
			String Number_of_pages=request.getParameter("Number_of_pages").trim();
			if(Sort_type.equals("0")){
				 array = DB_goods_operate.goods_def(GetConnection.getPos_ManagermainConn(), fage, Number_of_pages,cStoreNo);
			}
			else if(Sort_type.equals("1")){
				 array = DB_goods_operate.goods_price(GetConnection.getPos_ManagermainConn(), fage, Number_of_pages,cStoreNo);
			}
			else if(Sort_type.equals("2")){
				 array = DB_goods_operate.goods_sale_num(GetConnection.getPos_ManagermainConn(), fage, Number_of_pages,cStoreNo);
			}
            else if(Sort_type.equals("3")){
				 array = DB_goods_operate.goods_browse_num(GetConnection.getPos_ManagermainConn(), fage, Number_of_pages,cStoreNo);

			}
            else if(Sort_type.equals("4")){
				 array = DB_goods_operate.goods_like_serach(GetConnection.getPos_ManagermainConn(), fage, Integer.parseInt(Number_of_pages),cStoreNo);
            }
			if(array!=null&&array.length()>0){
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":"+ array.toString().replace(" ", "") + "}");
			}
			else{
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":"+ array.toString() + "}");
			}
			System.out.println(array.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
