package com.advertisement.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import DB.DB_goods_operate;
import DB.GetConnection;
import bean.Shop_Car;

public class Html_Select_Shop_Car extends HttpServlet {


	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String UserNo = request.getParameter("UserNo");
			String Number_of_pages = request.getParameter("Number_of_pages");
			String cStoreNo = request.getParameter("cStoreNo");
			JSONArray array = DB_goods_operate.select_BuyerCar(GetConnection.getSpuerConn(), UserNo, Number_of_pages,cStoreNo);
			System.out.println(array.toString());
			Gson gson = new Gson();
			List<Shop_Car> list = gson.fromJson(array.toString(), new TypeToken<List<Shop_Car>>() {
			}.getType());
			request.setAttribute("Shop_Car_list", list);
			System.out.println(list);
			request.getRequestDispatcher("/shop_car.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String UserNo = request.getParameter("UserNo");
			String Number_of_pages = request.getParameter("Number_of_pages");
			String cStoreNo = request.getParameter("cStoreNo");
			JSONArray array = DB_goods_operate.select_BuyerCar(GetConnection.getSpuerConn(), UserNo, Number_of_pages,
					cStoreNo);
			Gson gson = new Gson();
			List<Shop_Car> list = gson.fromJson(array.toString(), new TypeToken<List<Shop_Car>>() {
			}.getType());
			request.setAttribute("Shop_Car_list", list);
			System.out.println(list.size());
			request.getRequestDispatcher("/shop_car.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
