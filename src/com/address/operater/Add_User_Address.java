package com.address.operater;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DB_user_operater;
import DB.GetConnection;
public class Add_User_Address extends HttpServlet {


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
			String UserNo=request.getParameter("UserNo");
			String Tel=request.getParameter("Tel");
            String UserName=request.getParameter("UserName");
            String Provincial=request.getParameter("Provincial");
            String City=request.getParameter("City");
            String District=request.getParameter("District");
            String Detailaddress=request.getParameter("Detailaddress");
            String Default_fage=request.getParameter("Default_fage");
            
            /*//多加三个字段
*/          String fLont=request.getParameter("fLont");
            String fLant=request.getParameter("fLant");
            String label=request.getParameter("label");
            String cStoreNo=request.getParameter("cStoreNo");
            
            System.out.println(UserNo);
            System.out.println(fLont);
            
            int str = DB_user_operater.User_add_address(GetConnection.getSpuerConn(),  UserNo, UserName,Provincial,Tel, City, District, Detailaddress, Default_fage,fLont,fLant,label,cStoreNo);
            out.print("{\"resultStatus\":\"" + str + "\"," + "\"dDate\":"+ str + "}");
			System.out.println("{\"resultStatus\":\"" + str + "\"," + "\"dDate\":"+ str + "}");
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":"+ 0 + "}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
