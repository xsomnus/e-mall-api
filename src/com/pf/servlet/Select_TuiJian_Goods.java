package com.pf.servlet;

import DB.GetConnection;
import Tool.Utils.DBBridge;
import Tool.Utils.ServletUtils;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * Created by xwy_brh on 2017/8/2.
 */
@WebServlet( "/Select_TuiJian_Goods")
public class Select_TuiJian_Goods extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setResponse(response);
        request.setCharacterEncoding("UTF-8");

        String userNo = request.getParameter("UserNo");
        Connection conn = GetConnection.getSpuerConn();
        PrintWriter out = response.getWriter();
        String resultStr = "";
        try {
            JSONArray arr = DBBridge.Select_Recommended_Goods(conn, userNo);

            if (arr != null && arr.length() >0) {
                resultStr = "{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + arr.toString().replace(" ","")+ "}";

            } else {
                resultStr = "{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + arr.toString().replace(" ","")+ "}";
            }
        } catch (Exception e) {
            resultStr = "{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + ""+ "}";
        }
        out.print(resultStr);
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
