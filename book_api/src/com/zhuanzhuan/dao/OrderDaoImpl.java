package com.zhuanzhuan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.zhuanzhuan.model.Good;
import com.zhuanzhuan.model.Order;
import com.zhuanzhuan.model.User;
import com.zhuanzhuan.util.DBUtil;
import com.zhuanzhuan.util.DaoFactory;

public class OrderDaoImpl implements IOrderDao {

	@Override
	public int add(Order order) {
		// TODO Auto-generated method stub
		Connection connection = DBUtil.getConnection();
		String sql = "INSERT INTO orders(goodid,goodownerid,price,total,"
				+ "num,category, ordertime,userId,status) values (?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = 0;
		try {
			ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, order.getGoodid());
			ps.setInt(2, order.getGoodownerid());
			ps.setString(3, order.getPrice());
			ps.setString(4, order.getTotal());
			ps.setInt(5, order.getNum());
			ps.setString(6, order.getCategory());
			ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			ps.setInt(8, order.getUserId());
			ps.setInt(9, 0);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			while (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return id;
	}

	@Override
	public void delete(int id) {
		Connection connection = DBUtil.getConnection();
		String sql = "DELETE FROM orders WHERE id = ?";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
	}

	@Override
	public void update(Order order) {
		Connection connection = DBUtil.getConnection();
		String sql = "UPDATE orders SET "
				+ " status=? "
				+ " WHERE id= ?";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, order.getStatus());
			ps.setInt(2, order.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(ps);
			DBUtil.close(connection);
		}

	}

	@Override
	public List<Order> loadByUser(int userId, int status) {
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT * FROM orders WHERE userId =?  GROUP BY id";
		// String sql = "SELECT * FROM orders WHERE userId =? and status =? GROUP BY
		// id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Order> orders = new ArrayList<Order>();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			// ps.setInt(2, status);
			rs = ps.executeQuery();
			while (rs.next()) {

				UserDaoImpl userDao = DaoFactory.getUserDao();
				User user = userDao.findById(rs.getInt("goodownerid"));
				GoodDaoImpl goodDao = DaoFactory.getGoodDao();
				Good good = goodDao.loadById(rs.getInt("goodid"));
				Order order = new Order();
				order.setUser(user);
				order.setGood(good);
				order.setId(rs.getInt("id"));
				order.setGoodid(rs.getInt("goodid"));
				order.setGoodownerid(rs.getInt("goodownerid"));
				order.setPrice(rs.getString("price"));
				order.setNum(rs.getInt("num"));
				order.setTotal(rs.getString("total"));
				order.setCategory(rs.getString("category"));
				order.setOrdertime(rs.getDate("ordertime"));
				order.setStatus(rs.getInt("status"));
				orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return orders;
	}

	@Override
	public List<Order> loadbyOwner(int userId) {
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT * FROM orders WHERE goodownerid =?  GROUP BY id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Order> orders = new ArrayList<Order>();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			// ps.setInt(2, status);
			rs = ps.executeQuery();
			while (rs.next()) {

				UserDaoImpl userDao = DaoFactory.getUserDao();
				User user = userDao.findById(rs.getInt("goodownerid"));
				GoodDaoImpl goodDao = DaoFactory.getGoodDao();
				Good good = goodDao.loadById(rs.getInt("goodid"));
				Order order = new Order();
				order.setUser(user);
				order.setGood(good);
				order.setId(rs.getInt("id"));
				order.setGoodid(rs.getInt("goodid"));
				order.setGoodownerid(rs.getInt("goodownerid"));
				order.setPrice(rs.getString("price"));
				order.setNum(rs.getInt("num"));
				order.setTotal(rs.getString("total"));
				order.setCategory(rs.getString("category"));
				order.setOrdertime(rs.getDate("ordertime"));
				order.setStatus(rs.getInt("status"));
				orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		return orders;
	}

	@Override
	public List<Order> loadByOrderUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
