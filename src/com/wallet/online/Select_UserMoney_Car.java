package com.wallet.online;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import com.google.gson.Gson;
import DB.GetConnection;
import DB.Wallet_OthereUpda;
import bean.Money_Car;


@WebServlet(description = "查询用户的储值卡", urlPatterns = { "/Select_UserMoney_Car" })
public class Select_UserMoney_Car extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
  


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
		String data = request.getParameter("data");
		System.out.println(data);
		try {
			JSONObject obj = new JSONObject(data);
			String Buyer_id=obj.getString("Buyer_id");
			List<Money_Car>  list= Wallet_OthereUpda.Select_UserMoney_Car(GetConnection.getStoreWalletConn(),Buyer_id);
		    Gson gson=new Gson();
		    String str=gson.toJson(list);
		    if(list!=null&&list.size()>0){
		    	   out.print("{\"resultStatus\":1,\"data\":" + str + "}");
		    }
		    else{
		    	 out.print("{\"resultStatus\":0,\"data\":" + str + "}");
		    }
		    System.out.println("{\"resultStatus\":1,\"data\":" + str + "}");
		} catch (Exception e) {
			out.print("{\"resultStatus\":0,\"data\":\"" + ""+ "\"}");
			e.printStackTrace();
		}// 获得钱包表的链接
		out.flush();
		out.close();
	}

}
