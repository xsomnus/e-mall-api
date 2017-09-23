package com.wallet.online;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import DB.GetConnection;
import DB.WalletUpda;

@WebServlet(description = "查询余额", urlPatterns = { "/LastMoneyquery" })
public class LastMoneyquery extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8"); 
		response.setHeader("content-type","text/html;charset=UTF-8");
		String data=request.getParameter("name");
		PrintWriter out = response.getWriter();
		System.out.println(data);
		try {
			JSONObject obj=new JSONObject(data);
			String id=obj.getString("id");
			String money=WalletUpda.selectWallelastmoney(GetConnection.getStoreWalletConn(),id);
			System.out.println("到此");
			out.print("{\"resultStatus\":"+money+"}");
			System.out.println(money);
		} catch (Exception e) {
			e.printStackTrace();
		}// 获得钱包表的链接
		out.flush();
		out.close();
	}

}
