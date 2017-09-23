package com.firstpage.servlet;

import DB.GetConnection;
import Tool.String_Tool;
import Tool.Utils.DBBridge;
import Tool.Utils.ServletUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by xwy_brh on 2017/7/19.
 */
@WebServlet( "/PF_Select_GoodsDetail")
public class PF_Select_GoodsDetail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setResponse(response);
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String userNo = request.getParameter("UserNo");
            String cGoodsNo = request.getParameter("cGoodsNo");
            System.out.println(cGoodsNo);
            System.out.println(userNo);
            JSONArray array = DBBridge.Select_Goods_Info(GetConnection.getSpuerConn(), cGoodsNo, userNo);
            if (array.length() >0 && array != null) {
                out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.toString().replace(" ","")+ "}");
            } else {
                out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + ""+ "}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + ""+ "}");
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }
}
