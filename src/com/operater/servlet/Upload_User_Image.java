package com.operater.servlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DB.DB;
import DB.GetConnection;
import Tool.RenamePolicyCos;

import com.oreilly.servlet.MultipartRequest;

public class Upload_User_Image extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("   This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		File fileDir = new File(this.getServletContext().getRealPath("/User_Img"));
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		// 设置上传文件的大小，超过这个大小 将抛出IOException异常，默认大小是10M。
		int inmaxPostSize = 100 * 1024 * 1024;
		MultipartRequest multirequest = null;

		// 上传文件重命名策略
		RenamePolicyCos myRenamePolicyCos = new RenamePolicyCos();
		try {
			// MultipartRequest()有8种构造函数！
			multirequest = new MultipartRequest(request, fileDir.getAbsolutePath(), inmaxPostSize, "UTF-8",
					myRenamePolicyCos); // GBK中文编码模式上传文件
			String UserNo = multirequest.getParameter("UserNo");// 获取普通信息
			System.out.println("用户编号" + UserNo);
			Enumeration<String> filedFileNames = multirequest.getFileNames();
			String filedName = null;
			if (null != filedFileNames) {
				while (filedFileNames.hasMoreElements()) {
					filedName = filedFileNames.nextElement();// 文件文本框的名称
					// 获取该文件框中上传的文件，即对应到上传到服务器中的文件
					File uploadFile = multirequest.getFile(filedName);
					if (null != uploadFile && uploadFile.length() > 0) {
						System.out.println(uploadFile.getName());
						System.out.println(uploadFile.getPath());
						System.out.println(uploadFile.length());
					}
					// 获取未重命名的文件名称
					String Originalname = multirequest.getOriginalFileName(filedName);
					System.out.println(Originalname);

					Connection conn = GetConnection.getSpuerConn();
					String sql = "Update User_Table set ImagePath =?  where  UserNo=?";
					PreparedStatement past = conn.prepareStatement(sql);
					past.setString(1, "User_Img/" + Originalname);
					past.setString(2, UserNo);
					past.executeUpdate();
					DB.closePreparedStatement(past);
					DB.closeConn(conn);
				}
			}
		} catch (Exception e) {
			out.print(" {\"resultStatus\":\"-1" + "\"}");
			e.printStackTrace();
		} finally {
			out.print(" {\"resultStatus\":\"1" + "\"}");
			out.flush();
			out.close();
		}
	}
}
