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
import DB.WalletUpda;

@WebServlet(description = "电话确认", urlPatterns = { "/Tel_verification_code" })
public class Tel_verification_code extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Tel_verification_code() {
        super();
     
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8"); 
		response.setHeader("content-type","text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String data = request.getParameter("data");
		System.out.println(data);
		try {
			JSONObject obj = new JSONObject(data);
			String tel = obj.getString("tel");
			String str = WalletUpda.getTel_verification_code(GetConnection.getStoreWalletConn(),tel);
			if(str!=null){
				out.print(str);
			}
			else{
				out.print("{\"resultStatus\":\"" +-1 + "\","+ "\"data\":\"" + "" + "\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
