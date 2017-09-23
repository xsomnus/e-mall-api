package com.strategy.operater;
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
import org.json.JSONArray;
import DB.DB;
import DB.GetConnection;
import Tool.Paging_Index_Sql;
import Tool.ResultSet_To_JSON;

@WebServlet(description = "查询会员信息", urlPatterns = { "/Select_VipMessage" })
public class Select_VipMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Connection conn = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String fCurValue = "0.0";
		String Tel = request.getParameter("Tel");
		String Number_of_pages = request.getParameter("Number_of_pages");
		System.out.println(Tel);
		System.out.println(Number_of_pages);

		try {
			conn = GetConnection.getPos_ManagermainConn();
			PreparedStatement past = conn.prepareStatement("select cVipNo, fCurValue from t_Vip where cTel=?");
			past.setString(1, Tel);
			rs = past.executeQuery();
			if (rs.next()) {
				String cVipNo = rs.getString("cVipNo");
				fCurValue = rs.getString("fCurValue");
				System.out.println(cVipNo);
				String sql = "select dSaleDate,sum(fVipScore_cur) as fVipScore_cur,cVipNo from  t_SaleSheetDetail where cVipNo=? group by dSaleDate,cVipNo";
				PreparedStatement past1 = conn.prepareStatement(
						Paging_Index_Sql.sql(" dSaleDate desc", sql, Integer.parseInt(Number_of_pages))
								+ "ORDER BY  dSaleDate desc");
				past1.setString(1, cVipNo);
				rs1 = past1.executeQuery();
			}
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs1);
			if (array != null && array.length() > 0) {
				out.print("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.toString() + ","
						+ "\"fCurValue\":\"" + fCurValue + "\"}");
				System.out.println("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.toString() + ","
						+ "\"fCurValue\":\"" + fCurValue + "\"}");
			} else {
				out.print("{\"resultStatus\":\"" + 0 + "\"," + "\"dDate\":" + array.toString() + "," + "\"fCurValue\":"
						+ fCurValue + "}");
				System.out.println("{\"resultStatus\":\"" + 1 + "\"," + "\"dDate\":" + array.toString() + ","
						+ "\"fCurValue\":\"" + fCurValue + "\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(null, null, conn);
		}
	}
}
