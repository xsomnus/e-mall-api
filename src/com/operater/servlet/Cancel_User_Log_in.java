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
import Tool.MD5key;
import Tool.String_Tool;

@WebServlet(description = "取消登录", urlPatterns = { "/Cancel_User_Log_in" })
public class Cancel_User_Log_in extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Cancel_User_Log_in() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String UserNo = request.getParameter("UserNo");
			String pass = request.getParameter("Pass");
			String encryption = request.getParameter("Signature");// 加密MD5	// (UserNo+Pass+"warelucent")
			
			System.out.println(UserNo);
			System.out.println(pass);
			System.out.println(encryption);
			System.out.println(MD5key.getMD5Pass(UserNo + pass));
			if (MD5key.getMD5Pass(UserNo + pass).equals(encryption)) {
				String fage = DB_user_operater.Cancel_Log_in(GetConnection.getSpuerConn(), UserNo, pass);
				if (String_Tool.isEmpty(fage)) {
					out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + fage + "}");
				} else {
					out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + fage + "}");
				}
			} else {
				out.print("{\"resultStatus\":\"" + -1 + "\"," + "\"dDate\":" + "加密不对(测试用)" + "}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
