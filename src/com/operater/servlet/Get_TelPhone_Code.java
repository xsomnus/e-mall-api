package com.operater.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cloopen.rest.sdk.CCPRestSDK;
import DB.DB_user_operater;
import DB.GetConnection;

public class Get_TelPhone_Code extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
	
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		doPost(request, response);
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
			String tel=request.getParameter("Tel");
			String reg_or_update=request.getParameter("reg_or_update"); 	 // 0是注册或得验证码,1是修改或得验证码
			System.out.println(tel);
			System.err.println(reg_or_update);
			if (reg_or_update.equals("0")) { // 注册
				if (DB_user_operater.User_Has_Tel(GetConnection.getSpuerConn(), tel)) { // 已经有此用户
					out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\"" + ""+ "\"}"); // 0是此手机号已经注册,
					System.out.println("{\"resultStatus\":" + 0 + "," + "\"dDate\":\"" + ""+ "\"}");
				}
				else {
					HashMap<String, Object> result = null;
					CCPRestSDK restAPI = new CCPRestSDK();
					restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
					restAPI.setAccount("8a216da85660607901566d0e77d4061c","aad5b6cf1a134e74aaaba45f72b550c7");// 初始化主帐号和主帐号TOKEN
					restAPI.setAppId("8a216da85660607901566d0e78300622");// 初始化应用ID
					int str = (int) ((Math.random() + 1) * 1000);
					result = restAPI.sendTemplateSMS(tel, "109213", new String[] {"" + str, "5分钟" });
					// 正常返回输出data包体信息（map）
					out.print("{\"resultStatus\":\"" + result.get("statusCode")+ "\"," + "\"dDate\":\"" + str + "\"}");
					System.out.println("{\"resultStatus\":\""+ result.get("statusCode") + "\"," + "\"dDate\":\""+ str + "\"}");
				}
			} else if (reg_or_update.equals("1")) {   //修改密码
				if (DB_user_operater.User_Has_Tel(GetConnection.getSpuerConn(), tel)) {
					HashMap<String, Object> result = null;
					CCPRestSDK restAPI = new CCPRestSDK();
					restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
					restAPI.setAccount("8a216da85660607901566d0e77d4061c","aad5b6cf1a134e74aaaba45f72b550c7");// 初始化主帐号和主帐号TOKEN
					restAPI.setAppId("8a216da85660607901566d0e78300622");// 初始化应用ID
					int str = (int) ((Math.random() + 1) * 1000);
					result = restAPI.sendTemplateSMS(tel, "109213", new String[] {""+str, "5分钟" });

					System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
					out.print("{\"resultStatus\":\"" + result.get("statusCode")
							+ "\"," + "\"data\":\"" + str + "\"}");
				} else {
					out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + "\"\""
							+ "}"); // 0是没有此用户,没有此手机号
				}
			}
		} catch (Exception e) {
			out.print("{\"resultStatus\":" + -1 + "}");
		}
	}
}
