package com.wallet.online;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import DB.GetConnection;
import DB.Wallet_OthereUpda;
import Tool.Paging_Index_Sql;

@WebServlet(description = "查看储值卡转入转出,支付记录,和与他人转钱记录", urlPatterns = { "/Select_wallet_log" })

public class Select_wallet_log extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String Buyer_id = request.getParameter("Buyer_id");
		String  Number_of_pages=request.getParameter("Number_of_pages");
		String fage = request.getParameter("fage");
		System.out.println(Buyer_id);
		System.out.println(Number_of_pages);
		System.out.println(fage);
		try {
			String sql = "";
			if (fage.equals("1")) { // 钱包支付记录
				sql = "select * from W_TradeLog where Buyer_id=?  ";
				sql=Paging_Index_Sql.sql("T_paytime desc", sql, Integer.parseInt(Number_of_pages));
			} else if (fage.equals("2")) { // 钱包和储值卡转账记录
				sql = "select * from Moneycard_Wallet_Transfer where Buyer_id=?  ";
				sql=Paging_Index_Sql.sql("date_time desc", sql, Integer.parseInt(Number_of_pages));
			} else if (fage.equals("3")) { // 钱包和他人转账记录
				sql = "select * from W_TranferLog where Out_ID=?  ";
				sql=Paging_Index_Sql.sql("WTransfer_Datetime desc", sql, Integer.parseInt(Number_of_pages));
			}
			JSONArray array = Wallet_OthereUpda.Select_wallet_log(GetConnection.getStoreWalletConn(), sql, Buyer_id);// c
			if (array != null && array.length() > 0) {
				out.print("{\"resultStatus\":1,\"data\":" + array.toString() + "}");
				System.out.println("{\"resultStatus\":1,\"data\":" + array.toString() + "}");
			} else {
				out.print("{\"resultStatus\":0,\"data\":\"" +""+ "\"}");
				System.out.println("{\"resultStatus\":0,\"data\":\"" +""+ "\"}");
			}
		} catch (Exception e) {
			out.print("{\"resultStatus\":0,\"data\":\"" +""+ "\"}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
