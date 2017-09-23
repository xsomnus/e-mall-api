package com.wallet.online;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

import DB.GetConnection;
import DB.WalletUpda;
import ModelRas.MD5key;
import bean.OffinelineWallet;



@WebServlet(description = "线下钱包", urlPatterns = { "/OfflineWallPay" })
public class OfflineWallPay extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OfflineWallPay() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8"); 
		response.setHeader("content-type","text/html;charset=UTF-8");
		String str=request.getParameter("sendstr");
		System.out.println(str);
		try {
			JSONObject obj=new JSONObject(str);
			String paycode=obj.getString("PayCode");
			String money=obj.getString("Money");
			String StoreNo=obj.getString("cStoreNo");
			String cOSS_No=obj.getString("cOSS_No");
			String sign=obj.getString("Sign");
			String out_trade_no=obj.getString("out_trade_no");
			String oto_pid=obj.getString("oto_pid");
			System.out.println(paycode);
			System.out.println(money);
			System.out.println(StoreNo);
			System.out.println(cOSS_No);
			String info=paycode+money+StoreNo+cOSS_No;
			if(MD5key.getMD5Pass(info).equals(sign)){
				SimpleDateFormat a= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String data = a.format(new Date());
				OffinelineWallet  code=WalletUpda.OfflinePay(GetConnection.getStoreWalletConn(),paycode, money, StoreNo, cOSS_No); 
				if(!code.getPayCode().equals("1")){
					code.setErr_code(code.getPayCode()); //成功
				}
				else{
					code.setErr_code("");
	
				}
				code.setSalDate(data);
				code.setOut_trade_no(out_trade_no);
				code.setOto_pid(oto_pid);
				code.setErrMsg("");
				code.setBuyer_logon_Msg("");
				code.setBuyer_logon_id("");
				Gson gson = new Gson();
				String strcode = gson.toJson(code);
				System.out.println(strcode);
				out.print(strcode);
				
			}
			else{
				out.print("签名错误");
			}
		} catch (Exception e) {
			out.print("解析异常");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
