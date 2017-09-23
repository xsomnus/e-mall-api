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
import DB.Wallet_OthereUpda;
import Tool.Money_Authentication;


@WebServlet(description = "转钱到钱包", urlPatterns = { "/Move_Car_money_to_wallet" })
public class Move_Car_money_to_wallet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public Move_Car_money_to_wallet() {
        super();
      
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String data = request.getParameter("data");
		System.out.println(data);
		try {
			JSONObject obj = new JSONObject(data);
			String Buyer_id = obj.getString("Buyer_id");
			String ID_Card = obj.getString("ID_Card");
			String money = obj.getString("money");
			String PayPwd = obj.getString("PayPwd");
			String app_system = obj.getString("app_system");
			if (Money_Authentication.Authentication_Money(
					GetConnection.getStoreWalletConn(), Buyer_id)) {
//				if (Money_Authentication
//						.Authen_offline_MoneyCar(
//								GetStoreWalletConn.getStoreWalletconn(cOSS_No),
//								ID_Card)) 
				{
					int fage = Wallet_OthereUpda.Move_Car_Money_to_wallet(
							GetConnection.getStoreWalletConn(),
							Buyer_id, ID_Card, money, PayPwd, app_system);
					out.print("{\"resultStatus\":\"" + fage + "\"}"); // 1是成功,0是异常,
																		// //
																		// -4-没有找到收款人
																		// 2, //
																		// -余额不足,
																		// //
																		// 3-卡信息有误,线下没有此卡
				}
				
			} else {
				out.print("{\"resultStatus\":\"" + -2 + "\"}"); // 钱包的钱遭篡改
			}
		} catch (Exception e) {
			out.print("{\"resultStatus\":\"" + -1 + "\"}"); // 解析异常,服务器异常

			e.printStackTrace();
		}// 获得钱包表的链接
		out.flush();
		out.close();
	}

}
