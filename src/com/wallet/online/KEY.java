package com.wallet.online;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ModelRas.RasHelp;
import bean.Ras;


@WebServlet(description = "»ñÈ¡¹«Ô¿", urlPatterns = { "/KEY" })
public class KEY extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public KEY() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8"); 
		response.setHeader("content-type","text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Ras.PublicKey = RasHelp.getPublicKey();
			Ras.PrivateKey = RasHelp.getPrivateKey();
			System.out.println(Ras.PublicKey);
			System.out.println(	Ras.PrivateKey);
			out.print("{\"resultStatus\":\""+Ras.PublicKey+"\"}");
			System.out.println("{\"resultStatus\":\""+Ras.PublicKey.replace("\n", "").replace("\r", "")+"\"}");
			
		} catch (Exception e) {
			out.print("{\"resultStatus\":"+0+"}");
		}

		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8"); 
		response.setHeader("content-type","text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Ras.PublicKey = RasHelp.getPublicKey();
			Ras.PrivateKey = RasHelp.getPrivateKey();
			System.out.println(Ras.PublicKey);
			System.out.println(	Ras.PrivateKey);
			out.print("{\"resultStatus\":\""+Ras.PublicKey+"\"}");
			System.out.println("{\"resultStatus\":\""+Ras.PublicKey.replace("\n", "").replace("\r", "")+"\"}");
			
		} catch (Exception e) {
			out.print("{\"resultStatus\":"+0+"}");
		}

		out.flush();
		out.close();
	}

}
