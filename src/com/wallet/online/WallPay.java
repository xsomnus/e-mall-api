package com.wallet.online;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import DB.GetConnection;
import DB.WalletUpda;
import ModelRas.HexUtil;
import ModelRas.RasHelp;
import Tool.Money_Authentication;
import bean.Ras;
import sun.misc.BASE64Decoder;

@WebServlet(description = "钱包支付", urlPatterns = {"/WallPay"})

public class WallPay extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String data0 = request.getParameter("name");
		System.out.println("钱包支付的值"+data0);
		String data1 = request.getParameter("name").replace(' ', '+').replace("\n", "").replace("\r", "");
		boolean a = false;
		byte[] data = null;
		String Device = null;
		String SaxDay = null;
		try {
			JSONObject obj = new JSONObject(data1);
			Device = obj.getString("device");
			SaxDay = obj.getString("sign").replace(' ', '+'); // 加密后的字符串
			String PublicKey = obj.getString("PublicKey");
			String singg = obj.getString("signgg").replace(' ', '+');
			if (Device.equals("android")) {
				data = HexUtil.decode(SaxDay);
				a = RasHelp.judge(data, PublicKey, singg);
			} else {
				BASE64Decoder decoder = new BASE64Decoder();
				data = decoder.decodeBuffer(SaxDay);
				a = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (a) { // 验证成功,就解密
			try {
				String str = RasHelp.getstr(data, Ras.PrivateKey);
				System.out.println(str);
				JSONObject obj = new JSONObject(str);
				String cStoreNo = obj.getString("cStoreNo");
				String WServerID_cStore = obj.getString("WServerID_cStore");
				String notify_url = obj.getString("notify_url");

				String fLastMoney = obj.getString("fLastMoney");
				String Buyer_id = obj.getString("Buyer_id");
				String pass = obj.getString("Paypass");

				String WServerID_buyer = obj.getString("WServerID_buyer");
				String cardno = obj.getString("cardno");

				String cSaleSheetNo = obj.getString("cSaleSheetNo");

				SimpleDateFormat fromat = new SimpleDateFormat("yyyy-MM-dd");
				String T_createtime = fromat.format(new Date());
				System.out.println(T_createtime);
				SimpleDateFormat fromat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String T_paytime = fromat1.format(new Date());
				System.out.println(T_paytime);
				if (Money_Authentication.Authentication_Money(GetConnection.getStoreWalletConn(), Buyer_id)) {
					if (WalletUpda.Transe(GetConnection.getStoreWalletConn(), Buyer_id, pass)) {
						Connection conn = GetConnection.getSpuerConn();
						PreparedStatement past1 = conn.prepareStatement("select * from Order_Table where cSheetno=? and Pay_state='0' ");//未支付
						past1.setString(1, cSaleSheetNo);
						ResultSet rs = past1.executeQuery();
						if (rs.next()) { //如果没有支付
							int fage = WalletUpda.WallPay(GetConnection.getStoreWalletConn(), "utf-8", "DanDan", Device,
									"2.0", "RSA", SaxDay, cStoreNo, WServerID_cStore, notify_url, "01", fLastMoney,
									Buyer_id, WServerID_buyer, cardno, T_createtime, T_paytime, "9", "1234",
									cSaleSheetNo, "sales", "产品信息", "0", "0");
							out.print("{\"resultStatus\":" + fage + "}");
						} else {
							out.print("{\"resultStatus\":" + -6 + "}"); // 已经支付
						}
						DB.DB.closeAll(rs, past1, null, conn);
					} else {
						out.print("{\"resultStatus\":" + -1 + "}"); // 支付密码错误
					}
				} else {
					out.print("{\"resultStatus\":" + -2 + "}");// 数据库信息遭篡改
				}
			} catch (Exception e1) {
				out.print("{\"resultStatus\":" + -4 + "}");// 数据库信息遭篡改
				e1.printStackTrace();
			}
			out.flush();
			out.close();
		}
	}
}
