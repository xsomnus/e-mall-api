package com.order.operater;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import DB.DB_goods_operate;
import DB.GetConnection;
import Tool.String_Tool;

public class Upload_order extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
            String  dData=request.getParameter("dData");
            System.out.println("提交订单的商品"+dData);
            JSONArray array=new JSONArray(dData);
            String AddressID=request.getParameter("AddressID");
            System.out.println("地址IP"+AddressID);
            String UserNo=request.getParameter("UserNo");
            String Notes=request.getParameter("Notes");
            String freight=request.getParameter("Freight");
           // String cStoreNo=request.getParameter("cStoreNo");
            String cStoreNo="000";
            String Send_Way	=request.getParameter("Send_Way");
            if(Send_Way.equals("1")){
            	freight="0.0";
            }
            String Cover_Fresh=request.getParameter("Cover_Fresh");
            String Start_time=request.getParameter("Start_time");
            String End_time=request.getParameter("End_time"); 
            String Send_cStoreNo=request.getParameter("Send_cStoreNo");
            if(String_Tool.isEmpty(Send_cStoreNo)||String_Tool.isEmpty(AddressID)){
            	out.print("{\"resultStatus\":\"" + -1 + "\"," + "\"dDate\":\""+ 0 + "\"}"); //不能为空
            	return ;
            }
			String str = DB_goods_operate.upload_order(GetConnection.getSpuerConn(),UserNo, array,AddressID,Notes,freight,cStoreNo,Send_Way,Cover_Fresh,Start_time,End_time,Send_cStoreNo);
			if(str!=null){
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":\""+ str + "\"}");
			}
			else{
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\""+ str + "\"}");
			}
			System.out.println(str);
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":"+ 0 + "}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
