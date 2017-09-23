package com.wallet.online;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import DB.GetConnection;
import DB.WalletUpda;
import bean.RealName;


/**
 * Servlet implementation class Authentication
 */
@WebServlet(description = "身份验证", urlPatterns = { "/Authentication" })
public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authentication() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8"); 
		response.setHeader("content-type","text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String data = request.getParameter("name");
		try {
			JSONObject obj = new JSONObject(data);
			String Tel = obj.getString("Tel");
			RealName realName = WalletUpda.Authentication(GetConnection.getStoreWalletConn(),Tel);//
			if (realName != null) {
				System.out.println(realName.getBuyerName());
				Gson gson = new Gson();
				String str = gson.toJson(realName);
				out.print(" {\"resultStatus\":1,\"dDate\":" + str + "}");
			} else {
				out.print("{\"resultStatus\":0}");
			}
		} catch (Exception e) {

		}
		out.flush();
		out.close();
	}

}
