package com.wallet.online;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import DB.GetConnection;
import DB.WalletUpda;


@WebServlet(description = "等到支付码,生成二维码", urlPatterns = { "/GetCode" })
public class GetCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8"); 
		response.setHeader("content-type","text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String data = request.getParameter("name");
		System.out.println(data);
			try {
				JSONObject obj = new JSONObject(data);
				String id=obj.getString("UserId");
		
				String app_system=obj.getString("app_system");
				String app_version=obj.getString("app_version");
			
			    String paycode=WalletUpda.getCode(GetConnection.getStoreWalletConn(),id,app_system,app_version);
			    out.print(" {\"resultStatus\":1,\"paycode\":" + paycode + "}");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    out.flush();
		    out.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String data = request.getParameter("name");
		response.setContentType("text/html;charset=UTF-8"); 
		response.setHeader("content-type","text/html;charset=UTF-8");
		System.out.println(data);
		try {
			JSONObject obj = new JSONObject(data);
			String id=obj.getString("UserId");
			String app_system=obj.getString("app_system");
			String app_version=obj.getString("app_version");
		    String paycode=WalletUpda.getCode(GetConnection.getStoreWalletConn(),id,app_system,app_version);
		    
		    System.out.println(" {\"resultStatus\":1,\"paycode\":" + paycode + "}");
		    out.print(" {\"resultStatus\":1,\"paycode\":" + paycode + "}");
		    out.flush();
		    out.close();
		}
		catch (Exception e) {
			   out.print(" {\"resultStatus\":0,\"paycode\":" +-1+ "}");
			System.out.println("");
		}
		out.flush();
		out.close();
	}

}
