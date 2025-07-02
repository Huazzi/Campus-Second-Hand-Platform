package com.zhuanzhuan.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.zhuanzhuan.dao.GoodDaoImpl;
import com.zhuanzhuan.dao.OrderDaoImpl;
import com.zhuanzhuan.model.Good;
import com.zhuanzhuan.model.Order;
import com.zhuanzhuan.util.DaoFactory;

/**
 * ������ʾ��Ʒ��������
 * ����request���ԣ�
 * 	id����Ʒid
 * ����session���ԣ�
 * 	user����ǰ��¼�û�
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String type = request.getParameter("loadBy");
		OrderDaoImpl orderdao=DaoFactory.getOrderDao();
		PrintWriter writer = response.getWriter();
		response.setContentType("application/json");
		
		if ("id".equals(type)) {
			
			String action = request.getParameter("action");
			if ("delete".equals(action)) {
				int id=Integer.parseInt(request.getParameter("id"));
				//根据id查找到订单信息
			 	try{
			 		orderdao.delete(id);
			 		writer.print("{\"status\":\"OK\",\"orderId\":\"" + id + "\"}");
			 	}catch(Exception e){
			 		writer.print("{\"status\":\"ERROR\",\"orderId\":\"" + id + "\"}");
			 	}
			}else if("update".equals(action)){
				int id=Integer.parseInt(request.getParameter("id"));
				int status=Integer.parseInt(request.getParameter("status"));
				//根据id查找到订单信息
				Order order = new Order();
				order.setId(id);
				order.setStatus(status);
			 	try{
			 		orderdao.update(order);
			 		writer.print("{\"status\":\"OK\",\"orderId\":\"" + id + "\"}");
			 	}catch(Exception e){
			 		writer.print("{\"status\":\"ERROR\",\"orderId\":\"" + id + "\"}");
			 	}
			}
		}else if("condition".equals(type)){
			String userId = null;	
			String status = null;			
			try{
				userId = request.getParameter("userId").trim();
			}catch(Exception e){
				userId = "";
			}
			try{	
				status = request.getParameter("status").trim();
			}catch(Exception e){
				status = "";
			}
			
			System.out.println("userId:" + userId);
			System.out.println("status:" + status);
			int userIdint = Integer.parseInt(request.getParameter("userId"));
			int statusint = Integer.parseInt(request.getParameter("status"));
			List<Order> orders = orderdao.loadByUser(userIdint,statusint);		
			
			//返回商品JSON数据
			System.out.println(orders.size());
			JsonArray jsonArray = new JsonArray();
			for(Order order:orders) {
				jsonArray.add(order.toJson());
			}
			System.out.println(jsonArray.toString());
			writer.println(jsonArray.toString());
		}else if("owner".equals(type)){
			String userId = null;	
			String status = null;			
			try{
				userId = request.getParameter("userId").trim();
			}catch(Exception e){
				userId = "";
			}
			try{	
				status = request.getParameter("status").trim();
			}catch(Exception e){
				status = "";
			}
			
			System.out.println("userId:" + userId);
			System.out.println("status:" + status);
			int userIdint = Integer.parseInt(request.getParameter("userId"));
			int statusint = Integer.parseInt(request.getParameter("status"));
			List<Order> orders = orderdao.loadbyOwner(userIdint);		
			
			//返回商品JSON数据
			System.out.println(orders.size());
			JsonArray jsonArray = new JsonArray();
			for(Order order:orders) {
				jsonArray.add(order.toJson());
			}
			System.out.println(jsonArray.toString());
			writer.println(jsonArray.toString());
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
