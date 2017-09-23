package com.wallet.online;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DB.GetConnection;
import DB.WalletUpda;
import Tool.MD5key;


@WebServlet(description = "修改钱包信息", urlPatterns = { "/Update_Wallet_Message" })
public class Update_Wallet_Message extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
			String UserNo=request.getParameter("UserNo");
            String Password=request.getParameter("Password");
            String md5=request.getParameter("sing");
            System.out.println(UserNo);
            System.out.println(Password);
            if(MD5key.getMD5Pass(UserNo+Password+"ware").equals(md5)){
              int str = WalletUpda.UpdatePayMenssage(GetConnection.getSpuerConn(),UserNo, Password);
              out.print("{\"resultStatus\":\"" + str + "\"," + "\"dDate\":\""+ str + "\"}");
              System.out.println("{\"resultStatus\":\"" + str + "\"," + "\"dDate\":\""+ "" + "\"}");
            }
            else{
            	 out.print("{\"resultStatus\":\"" + -1 + "\"," + "\"dDate\":\""+ -1 + "\"}");
            	 System.out.println("{\"resultStatus\":\"" +-1 + "\"," + "\"dDate\":\""+ "" + "\"}");
            }
			System.out.println("{\"resultStatus\":\"" + -1 + "\"," + "\"dDate\":\""+ "" + "\"}");
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\""+ 0 + "\"}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
