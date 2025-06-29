package com.zhuanzhuan.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhuanzhuan.dao.FeedbackDaoImpl;
import com.zhuanzhuan.dao.GoodDaoImpl;
import com.zhuanzhuan.dao.OrderDaoImpl;
import com.zhuanzhuan.model.Feedback;
import com.zhuanzhuan.model.Good;
import com.zhuanzhuan.model.Order;
import com.zhuanzhuan.util.DaoFactory;

/**
 * Servlet implementation class AddFeedback
 */
@WebServlet("/AddOrder")
public class AddOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		
		String userId = request.getParameter("userId");
		String goodid = request.getParameter("goodid");
		GoodDaoImpl goodDao = DaoFactory.getGoodDao();
		Good good = goodDao.load(Integer.valueOf(goodid));
		Integer goodownerid = good.getGoodowner().getId();
		String price = String.valueOf(good.getPrice());
		String num = request.getParameter("num");
		double total =Float.valueOf(num)*good.getPrice();
		String catagory =good.getCatagory();
		
		OrderDaoImpl OrderDao = DaoFactory.getOrderDao();
		Order order = new Order();
		order.setGoodid(Integer.valueOf(goodid));
		order.setGoodownerid(goodownerid);
		order.setNum(Integer.valueOf(num));
		order.setPrice(price);
		order.setTotal(String.valueOf(total));
		order.setUserId(Integer.valueOf(userId));
		order.setCatagory(catagory);
		int status = OrderDao.add(order);
		if (status > 0) {
			writer.print("{\"status\":\"OK\"}");
		}else {
			writer.print("{\"status\":\"ERROR\",\"err\":\"未知错误\"}");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
