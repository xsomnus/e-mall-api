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


@WebServlet(description = "取消订单", urlPatterns = { "/Cancel_User_Order" })
public class Cancel_User_Order extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Cancel_User_Order() {
        super();
    }

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
		    String cSheetno=request.getParameter("cSheetno");
		    System.out.println("单号"+cSheetno);
		    int  str =DB_goods_operate.cancel_Order(GetConnection.getSpuerConn(),cSheetno);
		    System.out.println(cSheetno);
		    if(str>0){
		    	str=1;
		    }
		    out.print("{\"resultStatus\":\"" + str + "\"," + "\"dDate\":\""+ 0 + "\"}");
			System.out.println(str);
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\""+ 0 + "\"}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
