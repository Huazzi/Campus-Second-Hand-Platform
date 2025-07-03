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
import com.zhuanzhuan.dao.CategoryDaoImpl;
import com.zhuanzhuan.dao.GoodDaoImpl;
import com.zhuanzhuan.model.Category;
import com.zhuanzhuan.model.Good;
import com.zhuanzhuan.util.DaoFactory;

/**
 * GoodServlet
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
		String type = request.getParameter("loadBy"); // 获取请求参数
		GoodDaoImpl goodDao = DaoFactory.getGoodDao();

		PrintWriter writer = response.getWriter();
		response.setContentType("application/json");

		if ("id".equals(type)) {
			String action = request.getParameter("action");
			if ("delete".equals(action)) {
				int id = Integer.parseInt(request.getParameter("id"));
				// 根据id查找到商品信息，将数量修改为-1
				Good good = goodDao.loadById(id);
				good.setNum(-1);
				try {
					goodDao.update(good);
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
				// 增加浏览次数
				goodDao.incrementViewCount(id);

				// 根据id查找商品
				Good good = goodDao.loadById(id);
				if (good == null) { // 商品未找到或不满足条件
					// 返回一个空的JSON对象，让前端的判断逻辑触发“未找到商品信息”
					writer.print("{}");
				} else {
					// 直接返回商品信息的JSON
					writer.print(good.toJson().toString());
				}
			}
		} else if ("condition".equals(type)) {
			// 接收传递过来的关键字
			double small_price = 0; // 默认最小价格为0
			double big_price = 2147483647; // 最大为int最大值
			String key = null; // 获取关键字
			String categoryName = null; // 分类
			int start = 0; // 对应前端的 pageIndex - 1 ?
			int end = 1000; // 对应前端的 pageSize ?
			try {
				key = request.getParameter("key").trim();
			} catch (Exception e) {
				key = "";
			}
			try {
				categoryName = request.getParameter("category").trim();
			} catch (Exception e) {
				categoryName = "";
			}
			try {
				small_price = Double.parseDouble(request.getParameter("small_price"));
			} catch (Exception e) {
				System.out.println("最小价格为0");
			}
			try {
				big_price = Double.parseDouble(request.getParameter("big_price"));
			} catch (Exception e) {
				System.out.println("最大价格为2147483647");
			}
			try {
				start = Integer.parseInt(request.getParameter("start"));
			} catch (Exception e) {
				System.out.println("开始位置为0");
			}
			try {
				end = Integer.parseInt(request.getParameter("end"));
			} catch (Exception e) {
				System.out.println("结束位置为1000");
			}

			System.out.println("key:" + key); // 关键字
			System.out.println("category:" + categoryName); // 分类
			System.out.println("small:" + small_price); // 最小价格
			System.out.println("big:" + big_price); // 最大价格

			// 根据分类名获取分类ID
			CategoryDaoImpl categoryDao = DaoFactory.getCategoryDao();
			int categoryId = 0; // 默认分类ID为0

			// 只有当分类名不为空时才查询分类ID
			if (categoryName != null && !categoryName.isEmpty()) {
				Category categoryObj = categoryDao.findByName(categoryName);
				// 如果找到分类，则获取分类ID
				if (categoryObj != null) {
					categoryId = categoryObj.getId();
				}
			}

			System.out.println("分类ID:" + categoryId);

			// 返回商品JSON数据
			List<Good> goods = goodDao.loadWithCondition(key, categoryId, small_price, big_price, start, end);
			System.out.println(goods.size()); // 商品数量
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
		doGet(request, response);
	}

}
