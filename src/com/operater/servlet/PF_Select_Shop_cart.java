package com.operater.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Tool.Utils.ServletUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import DB.DB;
import DB.GetConnection;
import Tool.String_Tool;


@WebServlet(description = "²éÑ¯¹ºÎï³µ", urlPatterns = { "/PF_Select_Shop_cart" })
public class PF_Select_Shop_cart extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setResponse(response);
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn=null;
        ResultSet rs=null;
        PreparedStatement past=null;
        try {
            String sql =  "SELECT TOP 10 *  FROM (SELECT ROW_NUMBER() OVER (ORDER BY  Date_time desc) AS RowNumber,* FROM (select a.Date_time,a.cGoodsNo,a.Num,c.bOnLine_Price as Last_Price,b.cGoodsImagePath,b.cGoodsName,b.bfresh,b.bWeight from dbo.Shop_Car a,posmanagement_main.dbo.T_goods b,posmanagement_main.dbo.t_cStoreGoods c where  a.cGoodsNo=b.cGoodsNo and c.cGoodsNo=b.cGoodsNo and a.UserNo=? and a.cStoreNo=?  ) c ) as A WHERE RowNumber > 10*(?-1)   ";
            System.out.println(sql);
            String UserNo = request.getParameter("UserNo");
            String Number_of_pages=request.getParameter("Number_of_pages");
            String cStoreNo = request.getParameter("cStoreNo");
            if (String_Tool.isEmpty(cStoreNo)) {
                cStoreNo = "000";
            }
            conn=GetConnection.getSpuerConn();
            past= conn.prepareStatement(sql);
            past.setString(1, UserNo);
            past.setString(2, cStoreNo);
            past.setString(3, Number_of_pages);
            rs=past.executeQuery();
            JSONArray array=new JSONArray();

            while(rs.next()){
                JSONObject obj=new JSONObject();

                String Date_time=rs.getString("Date_time");
                String Num=rs.getString("Num");
                String cGoodsNo=rs.getString("cGoodsNo");
                String Last_Price=rs.getString("Last_Price");
                String cGoodsImagePath=rs.getString("cGoodsImagePath");
                String cGoodsName=rs.getString("cGoodsName");
                String bfresh=rs.getString("bfresh");
                String bWeight=rs.getString("bWeight");
                obj.put("Date_time", Date_time);
                obj.put("Num", Num);
                obj.put("cGoodsNo", cGoodsNo);
                obj.put("Last_Price", Last_Price);
                obj.put("cGoodsImagePath",cGoodsImagePath);
                obj.put("cGoodsName",cGoodsName);
                obj.put("bFresh",bfresh);
                obj.put("bWeight",bWeight);
                obj.put("saleType","LS");
                if(Double.parseDouble(Num)>3){
                    PreparedStatement past1=conn.prepareStatement("select cPrice from PF_GoodsPrice where cGoodsNo=? AND cLower <= ? AND cUpper>?");
                    past1.setString(1, cGoodsNo);
                    past1.setString(2, Num);
                    past1.setString(3, Num);
                    ResultSet rs1=past1.executeQuery();
                    if(rs1.next()){
                        String cPrice=rs1.getString("cPrice");
                        obj.remove("Last_Price");
                        obj.remove("saleType");
                        obj.put("saleType","PF");
                        obj.put("Last_Price", cPrice);
                    }
                    DB.closeResultSet(rs1);
                    DB.closePreparedStatement(past1);
                }
                array.put(obj);
            }
            if(array!=null&&array.length()>0){
                out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":"+ array.toString() + "}");
            }
            else{
                out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":"+ array.toString() + "}");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            DB.closeRs_Con(rs, past, conn);
        }
        out.flush();
        out.close();
    }

}