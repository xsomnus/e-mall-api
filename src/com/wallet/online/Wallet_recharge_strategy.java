package com.wallet.online;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import DB.DB;
import DB.GetConnection;
import Tool.ResultSet_To_JSON;

@WebServlet(description = "Ç®°ü³äÖµ²ßÂÔ", urlPatterns = { "/Wallet_recharge_strategy" })
public class Wallet_recharge_strategy extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Connection conn=GetConnection.getStoreWalletConn();
		PreparedStatement past=null;
		ResultSet rs=null;
		try {
			past=conn.prepareStatement("select Pay_Money,excess_Money from dbo.Wallet_recharge_strategy");
			rs=past.executeQuery();
		    JSONArray array=ResultSet_To_JSON.resultSetToJsonArray(rs);
		    if(array!=null&&array.length()>0){
		    	out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":"+ array.toString() + "}");
		    }
		    else{
		    	out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":\""+ "" + "\"}");
		    }
		} catch (Exception e) {
             e.printStackTrace();
		}
		DB.closeRs_Con(rs, past, conn);
		out.flush();
		out.close();
	}
}
