package com.erHuo.action;

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

import com.erHuo.dao.GoodDaoImpl;
import com.erHuo.dao.UserDaoImpl;
import com.erHuo.dao.CategoryDaoImpl;
import com.erHuo.model.Good;
import com.erHuo.model.User;
import com.erHuo.model.Category;
import com.erHuo.util.DaoFactory;

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

			// 修改路径为绝对路径
			String uploadPath = getServletContext().getRealPath("/img/goods");
			// 确保目录存在
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
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

					// 遍历所有的文件项，保存文件并构建文件名字符串
					for (FileItem item : formItems) {
						if (!item.isFormField()) {
							String fileName = new File(item.getName()).getName();

							// 为避免文件名冲突，添加时间戳
							String timestamp = String.valueOf(System.currentTimeMillis());
							String fileExtension = "";
							int dotIndex = fileName.lastIndexOf('.');
							if (dotIndex > 0) {
								fileExtension = fileName.substring(dotIndex);
								fileName = fileName.substring(0, dotIndex) + "_" + timestamp + fileExtension;
							} else {
								fileName = fileName + "_" + timestamp;
							}

							System.out.println("保存文件: " + fileName);
							imgStr.append(fileName).append(";");

							String filePath = uploadPath + File.separator + fileName;
							File storeFile = new File(filePath);
							item.write(storeFile);

							System.out.println("文件保存路径: " + filePath);
						}
					}

					// 更新商品图片信息
					GoodDaoImpl goodDao = DaoFactory.getGoodDao();
					Good good = goodDao.loadById(goodId);
					String currentImages = good.getImagesStr();
					if (currentImages == null) {
						currentImages = "";
					}
					good.setImages(currentImages + imgStr.toString());
					goodDao.update(good);
				}
				writer.print("{\"status\":\"OK\"}");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		else {

			// 基本商品信息
			String description = request.getParameter("description");
			String goodName = request.getParameter("goodName");
			String priceStr = request.getParameter("price");
			String originalPriceStr = request.getParameter("originalPrice");
			String ownerIdStr = request.getParameter("ownerId");
			String numStr = request.getParameter("num");
			String catagory = request.getParameter("catagory");

			// 新增的通用商品字段
			String conditionStatus = request.getParameter("conditionStatus");
			String functionStatus = request.getParameter("functionStatus");
			String purchaseTime = request.getParameter("purchaseTime");
			String tradeMethod = request.getParameter("tradeMethod");
			String brand = request.getParameter("brand");
			String model = request.getParameter("model");
			String specifications = request.getParameter("specifications");
			String tags = request.getParameter("tags");
			String isNegotiableStr = request.getParameter("isNegotiable");

			// 图书专用字段（可选）
			String isbn = request.getParameter("isbn");
			String chubanshe = request.getParameter("chubanshe");
			String author = request.getParameter("author");
			String chubantime = request.getParameter("chubantime");

			// 兼容旧版本的字段映射
			if (conditionStatus == null || conditionStatus.trim().isEmpty()) {
				conditionStatus = request.getParameter("chubanshe");
			}
			if (functionStatus == null || functionStatus.trim().isEmpty()) {
				functionStatus = request.getParameter("author");
			}
			if (purchaseTime == null || purchaseTime.trim().isEmpty()) {
				purchaseTime = request.getParameter("chubantime");
			}

			CategoryDaoImpl categoryDao = DaoFactory.getCategoryDao();
			Category category = categoryDao.findByName(catagory);
			int categoryId = category.getId();
			UserDaoImpl userDao = DaoFactory.getUserDao();
			User goodOwner = null;

			try {
				// 解析数值参数
				double price = Double.parseDouble(priceStr);
				double originalPrice = 0.0;
				if (originalPriceStr != null && !originalPriceStr.trim().isEmpty()) {
					originalPrice = Double.parseDouble(originalPriceStr);
				}
				int num = Integer.parseInt(numStr);
				int ownerId = Integer.parseInt(ownerIdStr);
				boolean isNegotiable = "true".equals(isNegotiableStr) || "1".equals(isNegotiableStr);

				goodOwner = userDao.findById(ownerId);

				GoodDaoImpl goodDao = DaoFactory.getGoodDao();
				int viewCount = 0; // 浏览量
				int inquiryCount = 0; // 询问次数

				// 创建商品对象
				Good good = new Good(goodName, goodOwner, price, originalPrice, description,
						conditionStatus, functionStatus, purchaseTime, tradeMethod, brand, model,
						"", num, categoryId, new Timestamp(System.currentTimeMillis()), viewCount, inquiryCount);

				// 设置其他属性
				good.setSpecifications(specifications);
				good.setTags(tags);
				good.setNegotiable(isNegotiable);

				// 设置图书专用字段（如果有）
				if (isbn != null && !isbn.trim().isEmpty()) {
					good.setIsbn(isbn);
				}
				if (chubanshe != null && !chubanshe.trim().isEmpty()) {
					good.setChubanshe(chubanshe);
				}
				if (author != null && !author.trim().isEmpty()) {
					good.setAuthor(author);
				}
				if (chubantime != null && !chubantime.trim().isEmpty()) {
					good.setChubantime(chubantime);
				}

				int goodid = goodDao.add(good);

				writer.write("{\"status\":\"OK\",\"goodid\":" + goodid + "}");
			} catch (NumberFormatException e) {
				writer.write("{\"status\":\"ERROR\",\"message\":\"数字格式错误\"}");
				e.printStackTrace();
			} catch (Exception e) {
				writer.write("{\"status\":\"ERROR\",\"message\":\"服务器内部错误\"}");
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
