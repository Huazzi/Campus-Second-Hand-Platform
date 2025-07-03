package com.zhuanzhuan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zhuanzhuan.model.Good;
import com.zhuanzhuan.model.User;
import com.zhuanzhuan.util.DBUtil;
import com.zhuanzhuan.util.DaoFactory;

/**
 * @Description 商品数据访问对象实现类
 */
public class GoodDaoImpl implements IGoodDao {
	/**
	 * @Description 添加商品
	 * @param good 商品对象
	 * @return 商品ID
	 */
	@Override
	public int add(Good good) {
		Connection connection = DBUtil.getConnection();
		String sql = "INSERT INTO goods (goodname,goodownerid,price,description,image,num,time,isbn,chubanshe,author,chubantime,category_id,location,view_count,inquiry_count) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = 0;
		try {
			ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, good.getGoodname());
			ps.setInt(2, good.getGoodowner().getId());
			ps.setDouble(3, good.getPrice());
			ps.setString(4, good.getDescription());
			ps.setString(5, good.getImagesStr());
			ps.setInt(6, good.getNum());
			ps.setTimestamp(7, good.getTime());
			ps.setString(8, good.getIsbn());
			ps.setString(9, good.getChubanshe());
			ps.setString(10, good.getAuthor());
			ps.setString(11, good.getChubantime());
			ps.setInt(12, good.getCategoryId());
			ps.setString(13, good.getLocation());
			ps.setInt(14, 0); // 初始浏览量为0
			ps.setInt(15, 0); // 初始询问量为0

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			while (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return id;
	}

	@Override
	public Good findById(int id) {
		return loadById(id);
	}

	@Override
	public void delete(Good good) {
		System.out.println("Delete: " + good.getGoodname());
	}

	@Override
	public void update(Good good) {
		Connection connection = DBUtil.getConnection();
		String sql = "UPDATE goods SET "
				+ "goodname=?,"
				+ "price=?,"
				+ "description=?,"
				+ "image=?,"
				+ "num=?,"
				+ "category_id=?,"
				+ "time=? "
				+ "WHERE id=?";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, good.getGoodname());
			ps.setDouble(2, good.getPrice());
			ps.setString(3, good.getDescription());
			ps.setString(4, good.getImagesStr());
			ps.setInt(5, good.getNum());
			ps.setInt(6, good.getCategoryId());
			ps.setTimestamp(7, good.getTime());
			ps.setInt(8, good.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
	}

	@Override
	public List<Good> load(String goodname) {
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT *, (SELECT COUNT(*) FROM collect WHERE goodId = g.id) AS collect, (SELECT COUNT(*) FROM leave_msg WHERE goodid = g.id) AS leave_msg FROM goods g WHERE goodname like ? and num > 0";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Good> goods = new ArrayList<Good>();
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, "%" + goodname + "%");
			rs = ps.executeQuery();
			while (rs.next()) {

				UserDaoImpl userDao = DaoFactory.getUserDao();
				User user = userDao.findById(rs.getInt("goodownerid"));

				Good good = new Good();
				good.setId(rs.getInt("id"));
				good.setGoodname(rs.getString("goodname"));
				good.setGoodowner(user);
				good.setPrice(rs.getDouble("price"));
				good.setDescription(rs.getString("description"));
				good.setImages(rs.getString("image"));
				good.setNum(rs.getInt("num"));
				good.setCategoryId(rs.getInt("category_id"));
				good.setTime(rs.getTimestamp("time"));
				good.setLeaveMsgNum(rs.getInt("leave_msg"));
				good.setCollectNum(rs.getInt("collect"));
				good.setViewCount(rs.getInt("view_count"));
				good.setInquiryCount(rs.getInt("inquiry_count"));
				good.setLocation(rs.getString("location"));
				goods.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return goods;
	}

	@Override
	public void delete(int id) {
		Connection connection = DBUtil.getConnection();
		String sql = "DELETE FROM goods WHERE id = ?";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
	}

	/**
	 * @Description 根据ID查询
	 * @param id ID
	 * @return 对象
	 */
	@Override
	public Good loadById(int id) {
		Connection connection = DBUtil.getConnection();
		// 使用LEFT JOIN关联表并添加状态和数量过滤
		String sql = "SELECT g.*, " +
				"COUNT(DISTINCT l.id) AS leave_msg, " +
				"COUNT(DISTINCT c.userId) AS collect " +
				"FROM goods g " +
				"LEFT JOIN leave_msg l ON g.id = l.goodid " +
				"LEFT JOIN collect c ON g.id = c.goodId " +
				"WHERE g.id = ? AND g.status = 0 AND g.num > 0 " +
				"GROUP BY g.id";

		PreparedStatement ps = null;
		ResultSet rs = null;
		Good good = null;

		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) { // 使用if确保只处理单条记录
				UserDaoImpl userDao = DaoFactory.getUserDao();
				User user = userDao.findById(rs.getInt("goodownerid"));

				good = new Good();
				good.setId(rs.getInt("id"));
				good.setGoodname(rs.getString("goodname"));
				good.setGoodowner(user);
				good.setPrice(rs.getDouble("price"));
				good.setDescription(rs.getString("description"));
				good.setImages(rs.getString("image"));
				good.setNum(rs.getInt("num"));
				good.setCategoryId(rs.getInt("category_id"));
				good.setTime(rs.getTimestamp("time"));
				good.setLeaveMsgNum(rs.getInt("leave_msg"));
				good.setCollectNum(rs.getInt("collect"));

				// 安全获取可选字段
				try {
					good.setViewCount(rs.getInt("view_count"));
					good.setInquiryCount(rs.getInt("inquiry_count"));
					good.setLocation(rs.getString("location"));
				} catch (SQLException e) {
					// 忽略字段不存在的情况
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return good;
		// Connection connection = DBUtil.getConnection();
		// String sql = "SELECT
		// id,goodname,goodownerid,price,description,image,num,category_id,time,
		// COUNT(DISTINCT lid) leave_msg,COUNT(DISTINCT userId) collect FROM goods WHERE
		// id = ?";
		// // 根据分类id查找分类名等信息
		// String sql2 = "SELECT category_name FROM categories WHERE id = ?";
		// PreparedStatement ps = null;
		// ResultSet rs = null;
		// Good good = null;
		// try {
		// ps = connection.prepareStatement(sql);
		// ps.setInt(1, id);
		// rs = ps.executeQuery();
		// while (rs.next()) {
		//
		// UserDaoImpl userDao = DaoFactory.getUserDao();
		// User user = userDao.findById(rs.getInt("goodownerid"));
		//
		// good = new Good();
		// good.setId(rs.getInt("id"));
		// good.setGoodname(rs.getString("goodname"));
		// good.setGoodowner(user);
		// good.setPrice(rs.getDouble("price"));
		// good.setDescription(rs.getString("description"));
		// good.setImages(rs.getString("image"));
		// good.setNum(rs.getInt("num"));
		// good.setCategoryId(rs.getInt("catagory_id"));
		// good.setTime(rs.getTimestamp("time"));
		// good.setLeaveMsgNum(rs.getInt("leave_msg"));
		// good.setCollectNum(rs.getInt("collect"));
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// } finally {
		// DBUtil.close(rs);
		// DBUtil.close(ps);
		// DBUtil.close(connection);
		// }
		// return good;
	}

	@Override
	public Good load(int id) {
		// 直接调用优化后的 loadById 方法
		return this.loadById(id);
	}

	/**
	 * @Description 根据条件查询商品
	 * @param goodname   名称
	 * @param categoryId 分类id
	 * @return 列表
	 */
	@Override
	public List<Good> loadByCategoryId(String goodname, int categoryId) {
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT *, (SELECT COUNT(*) FROM collect WHERE goodId = g.id) AS collect, (SELECT COUNT(*) FROM leave_msg WHERE goodid = g.id) AS leave_msg FROM goods g WHERE goodname like ? and category_id = ? and num > 0";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Good> goods = new ArrayList<Good>();
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, "%" + goodname + "%");
			ps.setInt(2, categoryId);
			rs = ps.executeQuery();
			while (rs.next()) {

				UserDaoImpl userDao = DaoFactory.getUserDao();
				User user = userDao.findById(rs.getInt("goodownerid"));

				Good good = new Good();
				good.setId(rs.getInt("id"));
				good.setGoodname(rs.getString("goodname"));
				good.setGoodowner(user);
				good.setPrice(rs.getDouble("price"));
				good.setDescription(rs.getString("description"));
				good.setImages(rs.getString("image"));
				good.setNum(rs.getInt("num"));
				good.setCategoryId(rs.getInt("category_id"));
				good.setTime(rs.getTimestamp("time"));
				good.setLeaveMsgNum(rs.getInt("leave_msg"));
				good.setCollectNum(rs.getInt("collect"));
				goods.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return goods;
	}

	/**
	 * @Description 根据条件查询商品
	 * @param goodName   名称
	 * @param categoryId 分类ID
	 * @param minPrice   最低价格
	 * @param maxPrice   最高价格
	 * @return 列表
	 */
	public List<Good> loadWithCondition(String goodName, int categoryId, double minPrice, double maxPrice, int start,
			int end) {
		Connection connection = DBUtil.getConnection();
		String sql;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Good> goods = new ArrayList<Good>();

		try {
			// 如果分类ID为0，则不筛选分类
			// 修改后的SQL部分（确保LIMIT参数非负）
			if (categoryId == 0) {
				sql = "SELECT id,goodname,goodownerid,price,description,image,num,category_id,time,view_count,inquiry_count,location, " +
						"(SELECT COUNT(*) FROM collect WHERE goodId = g.id) AS collect, " +
						"(SELECT COUNT(*) FROM leave_msg WHERE goodid = g.id) AS leave_msg " +
						"FROM goods g " +
						"WHERE goodname like ? and price > ? and price < ? and num > 0 " +
						"ORDER BY time DESC LIMIT ?, ?";

				ps = connection.prepareStatement(sql);
				ps.setString(1, "%" + goodName + "%");
				ps.setDouble(2, minPrice);
				ps.setDouble(3, maxPrice);
				ps.setInt(4, start);
				ps.setInt(5, Math.max(end - start, 0)); // 确保LIMIT值非负
			} else {
				sql = "SELECT id,goodname,goodownerid,price,description,image,num,category_id,time,view_count,inquiry_count,location, " +
						"(SELECT COUNT(*) FROM collect WHERE goodId = g.id) AS collect, " +
						"(SELECT COUNT(*) FROM leave_msg WHERE goodid = g.id) AS leave_msg " +
						"FROM goods g " +
						"WHERE goodname like ? and category_id = ? and price > ? and price < ? and num > 0 " +
						"ORDER BY time DESC LIMIT ?, ?";

				ps = connection.prepareStatement(sql);
				ps.setString(1, "%" + goodName + "%");
				ps.setInt(2, categoryId);
				ps.setDouble(3, minPrice);
				ps.setDouble(4, maxPrice);
				ps.setInt(5, start);
				ps.setInt(6, Math.max(end - start, 0)); // 确保LIMIT值非负
			}

			rs = ps.executeQuery();
			while (rs.next()) {
				UserDaoImpl userDao = DaoFactory.getUserDao();
				User user = userDao.findById(rs.getInt("goodownerid"));

				Good good = new Good();
				good.setId(rs.getInt("id"));
				good.setGoodname(rs.getString("goodname"));
				good.setGoodowner(user);
				good.setPrice(rs.getDouble("price"));
				good.setDescription(rs.getString("description"));
				good.setImages(rs.getString("image"));
				good.setNum(rs.getInt("num"));
				good.setCategoryId(rs.getInt("category_id"));
				good.setTime(rs.getTimestamp("time"));
				good.setLeaveMsgNum(rs.getInt("leave_msg"));
				good.setCollectNum(rs.getInt("collect"));

				try {
					good.setViewCount(rs.getInt("view_count"));
					good.setInquiryCount(rs.getInt("inquiry_count"));
					good.setLocation(rs.getString("location"));
				} catch (SQLException e) {
					// 如果字段不存在，则忽略
					System.out.println("某些字段不存在，已忽略: " + e.getMessage());
				}

				goods.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return goods;
	}

	/**
	 * @Description: 根据用户ID查询
	 * @param userId 用户ID
	 * @return 列表
	 */
	@Override
	public List<Good> loadByUser(int userId) {

		Connection connection = DBUtil.getConnection();
		String sql = "SELECT id,goodname,goodownerid,price,description,image,num,category_id,time, COUNT(DISTINCT lid) leave_msg,COUNT(DISTINCT userId) collect FROM goods WHERE goodownerid = ? AND num > 0 GROUP BY id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Good> goods = new ArrayList<Good>();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {

				UserDaoImpl userDao = DaoFactory.getUserDao();
				User user = userDao.findById(rs.getInt("goodownerid"));

				Good good = new Good();
				good.setId(rs.getInt("id"));
				good.setGoodname(rs.getString("goodname"));
				good.setGoodowner(user);
				good.setPrice(rs.getDouble("price"));
				good.setDescription(rs.getString("description"));
				good.setImages(rs.getString("image"));
				good.setNum(rs.getInt("num"));
				good.setCategoryId(rs.getInt("category_id"));
				good.setTime(rs.getTimestamp("time"));
				good.setLeaveMsgNum(rs.getInt("leave_msg"));
				good.setCollectNum(rs.getInt("collect"));
				goods.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return goods;
	}

	@Override
	public List<Good> findByUserId(int userid) {
		return loadByUser(userid);
	}

	@Override
	public int collect(int userId, int goodId) {

		Connection connection = DBUtil.getConnection();
		String sql = "INSERT INTO `collect` (`userId`, `goodId`) VALUES (?, ?)";
		PreparedStatement ps = null;
		int status = 0;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, goodId);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return status;
	}

	@Override
	public int unCollect(int userId, int goodId) {

		int status = 0;
		Connection connection = DBUtil.getConnection();
		String sql = "DELETE FROM `collect` WHERE (`userId`=?) AND (`goodId`=?)";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, goodId);
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return status;
	}

	@Override
	public boolean isCollect(int userId, int goodId) {
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT * FROM collect WHERE userId = ? AND goodId = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, goodId);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return false;
	}

	@Override
	public List<Good> loadByCollectUser(int userId) {
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT id,goodname,goodownerid,price,description,image,num,category_id,time, COUNT(DISTINCT lid) leave_msg,COUNT(DISTINCT collect.userId) collect FROM goods JOIN collect ON goods.id = collect.goodId WHERE collect.userId = ? AND num > 0 GROUP BY id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Good> goods = new ArrayList<Good>();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {

				UserDaoImpl userDao = DaoFactory.getUserDao();
				User user = userDao.findById(rs.getInt("goodownerid"));

				Good good = new Good();
				good.setId(rs.getInt("id"));
				good.setGoodname(rs.getString("goodname"));
				good.setGoodowner(user);
				good.setPrice(rs.getDouble("price"));
				good.setDescription(rs.getString("description"));
				good.setImages(rs.getString("image"));
				good.setNum(rs.getInt("num"));
				good.setCategoryId(rs.getInt("category_id"));
				good.setTime(rs.getTimestamp("time"));
				good.setLeaveMsgNum(rs.getInt("leave_msg"));
				good.setCollectNum(rs.getInt("collect"));
				goods.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return goods;
	}

	/**
	 * 获取指定数量的随机商品
	 * 
	 * @param limit 返回数量
	 * @return 随机商品列表
	 */
	@Override
	public List<Good> findRandomGoods(int limit) {
		List<Good> goodsList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT g.*, COUNT(c.userId) as collectNum "
					+ "FROM goods g LEFT JOIN collect c ON g.id = c.goodId "
					+ "WHERE g.status = 0 AND g.num > 0 "
					+ "GROUP BY g.id "
					+ "ORDER BY RAND() LIMIT ?";

			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, limit);
			rs = ps.executeQuery();

			while (rs.next()) {
				Good good = processGoodResultSet(rs);
				goodsList.add(good);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return goodsList;
	}

	/**
	 * 获取热门商品
	 * 
	 * @param categoryId 类别ID（可选）
	 * @param limit      数量
	 * @return 热门商品列表
	 */
	@Override
	public List<Good> findPopularGoods(Integer categoryId, int limit) {
		List<Good> goodsList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("SELECT g.*, COUNT(c.userId) as collectNum ")
					.append("FROM goods g LEFT JOIN collect c ON g.id = c.goodId ")
					.append("WHERE g.status = 0 AND g.num > 0 ");

			// 如果有类别ID，添加类别过滤条件
			if (categoryId != null) {
				sqlBuilder.append("AND g.category_id = ? ");
			}

			sqlBuilder.append("GROUP BY g.id ")
					.append("ORDER BY (g.view_count * 2 + g.inquiry_count * 3 + COUNT(c.userId) * 5) DESC ")
					.append("LIMIT ?");

			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sqlBuilder.toString());

			int paramIndex = 1;
			if (categoryId != null) {
				ps.setInt(paramIndex++, categoryId);
			}
			ps.setInt(paramIndex, limit);

			rs = ps.executeQuery();

			while (rs.next()) {
				Good good = processGoodResultSet(rs);
				goodsList.add(good);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return goodsList;
	}

	/**
	 * 获取最新上架的商品
	 * 
	 * @param days  最近几天
	 * @param limit 数量
	 * @return 最新商品列表
	 */
	@Override
	public List<Good> findNewArrivals(int days, int limit) {
		List<Good> goodsList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// String sql = "SELECT g.*, COUNT(c.userId) as collectNum "
			// + "FROM goods g LEFT JOIN collect c ON g.id = c.goodId "
			// + "WHERE g.status = 0 AND g.num > 0 "
			// + "AND g.time >= DATE_SUB(NOW(), INTERVAL ? DAY) "
			// + "GROUP BY g.id "
			// + "ORDER BY g.time DESC LIMIT ?";
			String sql = "SELECT g.*, COUNT(c.userId) as collectNum "
					+ "FROM goods g LEFT JOIN collect c ON g.id = c.goodId "
					+ "WHERE g.status = 0 AND g.num > 0 "
					+ "AND g.time >= DATE_SUB(NOW(), INTERVAL ? DAY) "
					+ "GROUP BY g.id, g.goodname, g.price, g.description, g.image, "
					+ "g.category_id, g.status, g.num, g.time, g.userId " // 添加所有goods表的列
					+ "ORDER BY g.time DESC LIMIT ?";

			conn = DBUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, days);
			ps.setInt(2, limit);
			rs = ps.executeQuery();

			while (rs.next()) {
				Good good = processGoodResultSet(rs);
				goodsList.add(good);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return goodsList;
	}

	/**
	 * 处理商品结果集
	 * 提取通用的处理结果集的逻辑到一个私有方法
	 */
	private Good processGoodResultSet(ResultSet rs) throws SQLException {
		Good good = new Good();
		good.setId(rs.getInt("id"));
		good.setGoodname(rs.getString("goodname"));
		good.setPrice(rs.getDouble("price"));
		good.setDescription(rs.getString("description"));
		good.setImages(rs.getString("image"));
		good.setNum(rs.getInt("num"));
		good.setTime(rs.getTimestamp("time"));
		good.setCategoryId(rs.getInt("category_id"));
		good.setViewCount(rs.getInt("view_count"));
		good.setInquiryCount(rs.getInt("inquiry_count"));
		good.setLocation(rs.getString("location"));
		good.setIsbn(rs.getString("isbn"));
		good.setChubanshe(rs.getString("chubanshe"));
		good.setAuthor(rs.getString("author"));
		good.setChubantime(rs.getString("chubantime"));

		// 设置收藏数
		if (rs.getMetaData().getColumnCount() > 0) {
			try {
				good.setCollectNum(rs.getInt("collectNum"));
			} catch (SQLException e) {
				// 忽略错误，如果没有该字段
			}
		}

		// 获取商品所有者信息
		int goodownerId = rs.getInt("goodownerid");
		IUserDao userDao = DaoFactory.getUserDao();
		good.setGoodowner(userDao.findById(goodownerId));

		// 获取留言数量
		ILeaveMsgDao leaveMsgDao = DaoFactory.getLeaveMsgDao();
		good.setLeaveMsgNum(leaveMsgDao.getLeaveMsgNum(good.getId()));

		return good;
	}

	@Override
	public List<Good> findByPage(int page, int size) {
		// 分页查询商品信息
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT g.id, g.goodname, g.goodownerid, g.price, g.description, g.image, g.num, c.name as category_name, g.time, "
				+ "COUNT(DISTINCT l.id) leave_msg, COUNT(DISTINCT col.userId) collect "
				+ "FROM goods g "
				+ "JOIN categories c ON g.category_id = c.id "
				+ "LEFT JOIN leave_msg l ON g.id = l.goodid "
				+ "LEFT JOIN collect col ON g.id = col.goodId "
				+ "GROUP BY g.id, g.time "
				+ "ORDER BY g.time DESC "
				+ "LIMIT ?,?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Good> goods = new ArrayList<>();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, (page - 1) * size);
			ps.setInt(2, size);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserDaoImpl userDao = DaoFactory.getUserDao();
				User user = userDao.findById(rs.getInt("goodownerid"));

				Good good = new Good();
				good.setId(rs.getInt("id"));
				good.setGoodname(rs.getString("goodname"));
				good.setGoodowner(user);
				good.setPrice(rs.getDouble("price"));
				good.setDescription(rs.getString("description"));
				good.setImages(rs.getString("image"));
				good.setNum(rs.getInt("num"));
				good.setCategoryId(rs.getInt("category_id"));
				// good.setCategoryName(rs.getString("category_name"));
				good.setTime(rs.getTimestamp("time"));
				good.setLeaveMsgNum(rs.getInt("leave_msg"));
				good.setCollectNum(rs.getInt("collect"));
				goods.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return goods;
	}

	@Override
	public List<Good> findByName(String goodname, int page, int size) {
		// 根据商品名分页查询
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT g.id, g.goodname, g.goodownerid, g.price, g.description, g.image, g.num, c.name as category_name, g.time, "
				+ "COUNT(DISTINCT lid) leave_msg, COUNT(DISTINCT collect.userId) collect "
				+ "FROM goods g "
				+ "JOIN categories c ON g.category_id = c.id "
				+ "LEFT JOIN collect ON g.id = collect.goodId "
				+ "WHERE g.goodname LIKE ? GROUP BY g.id, g.time "
				+ "ORDER BY g.time DESC "
				+ "LIMIT ?,?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Good> goods = new ArrayList<>();
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, "%" + goodname + "%");
			ps.setInt(2, (page - 1) * size);
			ps.setInt(3, size);
			rs = ps.executeQuery();
			while (rs.next()) {
				UserDaoImpl userDao = DaoFactory.getUserDao();
				User user = userDao.findById(rs.getInt("goodownerid"));

				Good good = new Good();
				good.setId(rs.getInt("id"));
				good.setGoodname(rs.getString("goodname"));
				good.setGoodowner(user);
				good.setPrice(rs.getDouble("price"));
				good.setDescription(rs.getString("description"));
				good.setImages(rs.getString("image"));
				good.setNum(rs.getInt("num"));
				good.setCategoryId(rs.getInt("category_id"));
				good.setTime(rs.getTimestamp("time"));
				good.setLeaveMsgNum(rs.getInt("leave_msg"));
				good.setCollectNum(rs.getInt("collect"));
				goods.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return goods;
	}

	@Override
	public void incrementViewCount(int goodId) {
		Connection connection = DBUtil.getConnection();
		String sql = "UPDATE goods SET view_count = view_count + 1 WHERE id = ?";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, goodId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
	}

	@Override
	public void incrementInquiryCount(int goodId) {

	}
}
