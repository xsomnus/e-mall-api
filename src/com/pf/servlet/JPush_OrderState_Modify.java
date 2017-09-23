package com.pf.servlet;

import DB.DB;
import DB.GetConnection;
import Tool.ReadConfig;
import Tool.Utils.DBBridge;
import Tool.Utils.ServletUtils;
import cn.jpush.api.JPushClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by xwy_brh on 2017/8/3.
 */
@WebServlet(description = "订单状态改变之后, 发送推送", urlPatterns = { "/JPush_OrderState_Modify" })

public class JPush_OrderState_Modify extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setResponse(response);
        request.setCharacterEncoding("UTF-8");
        String orderSheetNo = request.getParameter("orderSheetNo");
        String orderState = request.getParameter("orderState");
        System.out.println(orderSheetNo);
        System.out.println(orderState);
//        Connection conn = GetConnection.getSpuerConn();
        String appKey = new ReadConfig().getprop().getProperty("JPush_appKey");
        String masterSecret = new ReadConfig().getprop().getProperty("JPush_masterSecret");
        try {

//            String userTel = DBBridge.Select_UserTel(conn, orderSheetNo);
            JPushClient client = new JPushClient(masterSecret, appKey);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//           DB.closeRs_Con(conn);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }
}
