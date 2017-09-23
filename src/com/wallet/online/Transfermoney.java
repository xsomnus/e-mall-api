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
import DB.GetConnection;
import DB.WalletUpda;
import ModelRas.HexUtil;
import ModelRas.RasHelp;
import Tool.Money_Authentication;
import bean.Ras;
import sun.misc.BASE64Decoder;

@WebServlet(description = "转账", urlPatterns = { "/Transfermoney" })
public class Transfermoney extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String privatekey = request.getParameter("privatekey");

		String data = request.getParameter("name").replace(' ', '+')
				.replace("\n", "").replace("\r", "");
		System.out.println(data);
		byte[] data2 = null;
		boolean a = false;
		String sign = null;
		String Device = null;
		try {
			JSONObject obj = new JSONObject(data);
			Device = obj.getString("device");
			sign = obj.getString("sign").replace(' ', '+'); // 客户端的加密后的字符串,安卓进行16进制转码了,ios没有
			String PublicKey = obj.getString("PublicKey").replace(' ', '+'); // 客户端的公钥
			String singg = obj.getString("signgg").replace(' ', '+'); // 签名

			System.out.println("客户端公钥" + PublicKey);
			System.out.println("加密后的字符串" + sign);
			System.out.println("签名" + singg);

			if (Device.equals("android")) {
				data2 = HexUtil.decode(sign);
				a = RasHelp.judge(data2, PublicKey, singg);
			} else {
				System.out.println(Device);
				BASE64Decoder decoder = new BASE64Decoder();
				data2 = decoder.decodeBuffer(sign);
				// a = RasHelp.judge(data2, PublicKey, singg);
				a = true; // 如果是iso的话直接设为true
			}
			System.out.println("验证" + a);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("异常");
		}
		if (a) { // 验证成功,就解密
			try {
				String str = RasHelp.getstr(data2, Ras.PrivateKey);
				JSONObject obj = new JSONObject(str);
				String In_ID = obj.getString("In_ID");
				String In_Name = obj.getString("In_Name");
				String Out_ID = obj.getString("Out_ID");
				String Out_Name = obj.getString("Out_Name");
				String Pass = obj.getString("Pass");
				String WTransfer_Money = obj.getString("WTransfer_Money");

				SimpleDateFormat fromat1 = new SimpleDateFormat("yyyyMMddHHmmss");
				String WTransferNo = fromat1.format(new Date());
				SimpleDateFormat fromat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String WTransfer_Datetime = fromat.format(new Date());
				
				if (Money_Authentication.Authentication_Money(                 //认证成功的时候才能转
						GetConnection.getStoreWalletConn(), In_ID)
						&& Money_Authentication.Authentication_Money(
								GetConnection.getStoreWalletConn(),
								Out_ID)) {
					if (WalletUpda.Transe(
							GetConnection.getStoreWalletConn(),
							Out_ID, Pass)) {
						int fage = WalletUpda.Transfermoney(
								GetConnection.getStoreWalletConn(),
								"utf-8", "digou", Device, "2.0", "RSA", sign,
								WTransferNo, WTransfer_Datetime, "1", In_ID,
								In_Name, "1", "1", "1", Out_ID, Out_Name, "1",
								"1", WTransfer_Money);
						out.print("{\"resultStatus\":" + fage + "}");
					} else {
						out.print("{\"resultStatus\":-1}");
					}
				} else {
					out.print("{\"resultStatus\":-2}");
				}
				out.flush();
				out.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			out.print("{\"resultStatus\":" + -2 + "}");
		}
	}
}
