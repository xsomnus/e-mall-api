package utils.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import utils.service.impl.T_GroupTypeServiceImpl;
import utils.web.service.T_GroupTypeService;

public class lx extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		T_GroupTypeService service = new T_GroupTypeServiceImpl();
		List array=service.getAdvertisement();
		request.setAttribute("Advertise", array);
		System.out.println(array.toString());
		List<T_GroupTypeService> list = service.search();
		request.setAttribute("list", list);
		List<T_GroupTypeService> tujian = service.tuijian();
		System.out.println(tujian.toString());
		request.setAttribute("name", tujian);
		request.getRequestDispatcher("/my.jsp").forward(request, response);
	}

	
	/**
	 * Àà±ð±í
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
