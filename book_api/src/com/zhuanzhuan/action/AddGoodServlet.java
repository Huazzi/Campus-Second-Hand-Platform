package com.zhuanzhuan.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 将Tomcat文件上传API替换为Commons FileUpload的标准API
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.zhuanzhuan.dao.GoodDaoImpl;
import com.zhuanzhuan.dao.UserDaoImpl;
import com.zhuanzhuan.dao.CategoryDaoImpl;
import com.zhuanzhuan.model.Good;
import com.zhuanzhuan.model.User;
import com.zhuanzhuan.model.Category;
import com.zhuanzhuan.util.DaoFactory;

/**
 * Servlet implementation class AddGoodServlet
 */
@WebServlet("/AddGoodServlet")
public class AddGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddGoodServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();

		if (ServletFileUpload.isMultipartContent(request)) {

			DiskFileItemFactory factory = new DiskFileItemFactory();

			ServletFileUpload upload = new ServletFileUpload(factory);

			upload.setHeaderEncoding("UTF-8");
			String uploadPath = request.getServletContext().getRealPath(".") + File.separator + "img" + File.separator
					+ "goods";

			int goodId = 0;
			try {

				Map<String, List<FileItem>> map = upload.parseParameterMap(request);
				System.out.println(map.toString());
				List<FileItem> formItems = map.get("goodimg");
				List<FileItem> items = map.get("goodid");
				if (items != null && !items.isEmpty()) {
					for (FileItem item : items) {

						String goodIdStr = item.getString("utf-8");
						goodId = Integer.parseInt(goodIdStr);
					}
				}
				if (formItems != null && !formItems.isEmpty()) {

					StringBuilder imgStr = new StringBuilder();
					//
					for (FileItem item : formItems) {
						//
						if (!item.isFormField()) {
							String fileName = new File(item.getName()).getName();

							System.out.println(fileName);
							imgStr.append(fileName).append(";");
							String filePath = uploadPath + File.separator + fileName;
							File storeFile = new File(filePath);

							System.out.println(filePath);

							item.write(storeFile);
							request.setAttribute("message",
									"存储成功！");
						}
					}

					GoodDaoImpl goodDao = DaoFactory.getGoodDao();
					Good good = goodDao.loadById(goodId);
					good.setImages(good.getImagesStr() + imgStr);
					goodDao.update(good);
				}
				writer.print("{\"status\":\"OK\"}");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		else {

			String description = request.getParameter("description");
			// String forepriceStr = request.getParameter("foreprice");
			String goodName = request.getParameter("goodName");
			// String method = request.getParameter("method");
			String priceStr = request.getParameter("price");
			String ownerIdStr = request.getParameter("ownerId");
			String numStr = request.getParameter("num");
			String catagory = request.getParameter("catagory");

			String isbn = request.getParameter("isbn");
			String chubanshe = request.getParameter("chubanshe");
			String author = request.getParameter("author");
			String chubantime = request.getParameter("chubantime");

			CategoryDaoImpl categoryDao = DaoFactory.getCategoryDao();
			Category category = categoryDao.findByName(catagory);
			int categoryId = category.getId();
			UserDaoImpl userDao = DaoFactory.getUserDao();
			User goodOwner = null;

			try {
				// double forprice = Double.parseDouble(forepriceStr);
				double price = Double.parseDouble(priceStr);
				int num = Integer.parseInt(numStr);
				int ownerId = Integer.parseInt(ownerIdStr);
				goodOwner = userDao.findById(ownerId);

				GoodDaoImpl goodDao = DaoFactory.getGoodDao();
				int viewCount = 0; // 浏览量
				int inquiryCount = 0; // 询问次数
				Good good = new Good(goodName, goodOwner, price, description, "", num, categoryId,
						new Timestamp(System.currentTimeMillis()), viewCount, inquiryCount);
				good.setIsbn(isbn);
				good.setChubanshe(chubanshe);
				good.setAuthor(author);
				good.setChubantime(chubantime);
				int goodid = goodDao.add(good);

				writer.write("{\"status\":\"OK\",\"goodid\":" + goodid + "}");
			} catch (Exception e) {
				writer.write("{status:'ERROR'}");
				e.printStackTrace();
			}

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
