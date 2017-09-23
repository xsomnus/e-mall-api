package com.address.operater;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import DB.DB_user_operater;
import DB.GetConnection;
import bean.City;

@WebServlet(description = "≤È—ØµÍ∆Ãµÿ÷∑", urlPatterns = { "/Select_Store_Address" })

public class Select_Store_Address extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
  
    public Select_Store_Address() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String cStoreNo = request.getParameter("cStoreNo");
			List<City>  citys = DB_user_operater.select_cStore_address(GetConnection.getSpuerConn(), cStoreNo);
			Gson gson=new Gson();
			if(citys.size()>0){
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + gson.toJson(citys).replace(" ", "")+ "}");
			}
			else{
				out.print("{\"resultStatus\":\"" + 0+ "\"," + "\"dDate\":" +  gson.toJson(citys).replace(" ", "")+ "}");
			}
			System.out.println("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" +  gson.toJson(citys).replace(" ", "")+ "}");
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0+ "\"," + "\"dDate\":\"" + 0+ "\"}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
