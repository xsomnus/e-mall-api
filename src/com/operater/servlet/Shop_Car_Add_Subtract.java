package com.operater.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.DB;
import DB.DB_goods_operate;
import DB.GetConnection;
import org.json.JSONObject;

public class Shop_Car_Add_Subtract extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
            String UserNo=request.getParameter("UserNo");
            String cGoodsNo=request.getParameter("cGoodsNo");
            String cStoreNo="000";
            String fage=request.getParameter("fage").replace(" ",""); //0是减 ,1是加
            System.out.println(UserNo+" "+cGoodsNo+" "+fage);
            int str=0;
            if(Integer.parseInt(fage)==1){
                str = DB_goods_operate.add_(GetConnection.getSpuerConn(),UserNo,cGoodsNo,cStoreNo);
            }
            else{
            	str = DB_goods_operate.sub_(GetConnection.getSpuerConn(),UserNo,cGoodsNo,cStoreNo);
            }
            JSONObject o = new JSONObject();
            String goodsStr = null;
            //返回价格/商品编号/价格类型
			try {
                Connection conn = GetConnection.getSpuerConn();
                ResultSet rs  = null;
                PreparedStatement past = null;
                String sql = "SELECT a.bOnLine_Price as Last_Price, a.cGoodsNo, b.Num from posmanagement_main.dbo.t_cStoreGoods a, Shop_Car b where a.cGoodsNo = b.cGoodsNo AND a.cGoodsNo = ? and a.cStoreNo = ?";
                past = conn.prepareStatement(sql);
                past.setString(1, cGoodsNo);
                past.setString(2, cStoreNo);
                rs = past.executeQuery();
                while (rs.next()) {
                    String Last_Price = rs.getString("Last_Price");
                    String Num = rs.getString("Num");
                    o.put("Last_Price", Last_Price);
                    o.put("Num", Num);
                    o.put("saleType", "LS");
                    if(Double.parseDouble(Num)>=3){
                        PreparedStatement past2=conn.prepareStatement("SELECT cPrice from PF_GoodsPrice where cGoodsNo=? AND cLower <= ? AND cUpper>?");
                        past2.setString(1, cGoodsNo);
                        past2.setString(2, Num);
                        past2.setString(3, Num);
                        ResultSet rs2=past2.executeQuery();
                        if(rs2.next()){
                            String cPrice=rs2.getString("cPrice");
                            o.remove("Last_Price");
                            o.remove("saleType");
                            o.put("saleType","PF");
                            o.put("Last_Price", cPrice);
                        }
                        DB.closeResultSet(rs2);
                        DB.closePreparedStatement(past2);
                    }
                }
                goodsStr = o.toString();
            } catch (Exception e) {
                System.out.println(goodsStr);
                String tmp = "{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":"+ 0 + "}";
                System.out.println(tmp);
                out.print(tmp);
                e.printStackTrace();
                out.flush();
                out.close();
                return;
            }
            System.out.println(goodsStr);
			String tmp = "{\"resultStatus\":\"" + str + "\"," + "\"dDate\":"+ goodsStr + "}";
            System.out.println(tmp);
	         out.print(tmp);
		 	System.out.println(str);
		} catch (Exception e) {
            String tmp = "{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":"+ 0 + "}";
            System.out.println(tmp);
			out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":"+ 0 + "}");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
