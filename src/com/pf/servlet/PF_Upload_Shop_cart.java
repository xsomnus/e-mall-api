package com.pf.servlet;


import DB.DB;
import DB.GetConnection;
import Tool.String_Tool;
import Tool.Utils.DBBridge;
import Tool.Utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by xwy_brh on 2017/8/3.
 */
@WebServlet(description = "添加商品至购物车", urlPatterns = { "/PF_Upload_Shop_cart" })
public class PF_Upload_Shop_cart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setResponse(response);
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String userNo = request.getParameter("UserNo");
            String cGoodsNo = request.getParameter("cGoodsNo");
            String cStoreNo = request.getParameter("cStoreNo");
            if (String_Tool.isEmpty(cStoreNo)) {
                cStoreNo = "000";
            }
            String Num = request.getParameter("Num");
            boolean isSuccess = DBBridge.Add_Shop_Cart(GetConnection.getSpuerConn(), userNo, cGoodsNo, cStoreNo, Num);
            if (isSuccess) {
                out.print("{\"resultStatus\":\"" + 1 + "\""+ "}");
            } else {
                out.print("{\"resultStatus\":\"" + 0 + "\""+ "}");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
