package com.operater.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DB_user_operater;
import DB.GetConnection;


@WebServlet(description = "修改用户信息", urlPatterns = { "/Update_User_Message" })
public class Update_User_Message extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public Update_User_Message() {
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
			String Tel=request.getParameter("Tel");
			
		    String Pass=request.getParameter("Pass");
		    System.out.println(Tel);
		    System.out.println(Pass);
		    int  str =DB_user_operater.update_User_Message(GetConnection.getSpuerConn(),Tel,Pass);
		    out.print("{\"resultStatus\":\"" + str + "\"," + "\"dDate\":"+ 0 + "}");
			System.out.println(str);
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":"+ 0 + "}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
