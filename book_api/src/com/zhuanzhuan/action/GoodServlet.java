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
import com.zhuanzhuan.model.Good;
import com.zhuanzhuan.util.DaoFactory;

/**
 * Servlet implementation class GoodServlet
 */
@WebServlet("/GoodServlet")
public class GoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GoodServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("loadBy");
		GoodDaoImpl goodDao = new GoodDaoImpl();

		PrintWriter writer = response.getWriter();
		response.setContentType("application/json");

		if ("id".equals(type)) {
			String action = request.getParameter("action");
			if ("delete".equals(action)) {

				int id = Integer.parseInt(request.getParameter("id"));
				// 根据id查找到商品信息，将数量修改为-1
				GoodDaoImpl gooddao = DaoFactory.getGoodDao();
				Good good = gooddao.load(id);
				good.setNum(-1);
				try {
					gooddao.update(good);
					writer.print("{\"status\":\"OK\",\"goodId\":\"" + id + "\"}");
				} catch (Exception e) {
					writer.print("{\"status\":\"ERROR\",\"goodId\":\"" + id + "\"}");
				}

			} else {

				int id = 0;
				try {
					id = Integer.parseInt(request.getParameter("id"));
				} catch (Exception e) {
					writer.print("{\"err\":\"系统内部错误\"}");
					return;
				}
				Good good = goodDao.load(id); // 根据id查找商品
				if (good == null) { // 出错
					writer.print("{\"err\":\"系统内部错误\"}");
				} else {
					writer.print(good.toJson().toString());
				}
			}
		} else if ("condition".equals(type)) {
			// 接收传递过来的关键字
			double small_price = 0; // 默认最小价格为0
			double big_price = 2147483647; // Ϊ2147483647; (最大为int最大值)
			String key = null; // ȡؼ (获取关键字)
			String catagory = null; // (分类)
			int start = 0; // 对应前端的 pageIndex - 1 ?
			int end = 1000; // 对应前端的 pageSize ?
			try {
				key = request.getParameter("key").trim();
			} catch (Exception e) {
				key = "";
			}

			try {
				catagory = request.getParameter("catagory").trim();
			} catch (Exception e) {
				catagory = "";
			}
			try {
				small_price = Double.parseDouble(request.getParameter("small_price"));
			} catch (Exception e) {
				System.out.println("��С�۸��������");
			}
			try {
				big_price = Double.parseDouble(request.getParameter("big_price"));
			} catch (Exception e) {
				System.out.println("���۸��������");
			}
			try {
				start = Integer.parseInt(request.getParameter("start"));
			} catch (Exception e) {
				System.out.println("���۸��������");
			}
			try {
				end = Integer.parseInt(request.getParameter("end"));
			} catch (Exception e) {
				System.out.println("���۸��������");
			}

			System.out.println("key:" + key);
			System.out.println("catagory:" + catagory);
			System.out.println("small:" + small_price);
			System.out.println("big:" + big_price);
			// 返回商品JSON数据
			List<Good> goods = goodDao.loadWithCondition(key, catagory, small_price, big_price, start, end);
			System.out.println(goods.size());
			JsonArray jsonArray = new JsonArray();
			for (Good good : goods) {
				jsonArray.add(good.toJson());
			}
			System.out.println(jsonArray.toString());
			writer.println(jsonArray.toString());
		} else if ("userId".equals(type)) {

			String userId_str = request.getParameter("userId");
			int userId = Integer.parseInt(userId_str);
			List<Good> goods = goodDao.loadByUser(userId);

			JsonArray jsonArray = new JsonArray();
			for (Good good : goods) {
				jsonArray.add(good.toJson());
			}
			System.out.println(jsonArray.toString());
			writer.println(jsonArray.toString());
		} else if ("CollectUser".equals(type)) {
			String userId_str = request.getParameter("userId");
			int userId = Integer.parseInt(userId_str);
			List<Good> goods = goodDao.loadByCollectUser(userId);

			JsonArray jsonArray = new JsonArray();
			for (Good good : goods) {
				jsonArray.add(good.toJson());
			}
			System.out.println(jsonArray.toString());
			writer.println(jsonArray.toString());
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
