package com.zhuanzhuan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zhuanzhuan.model.Cart;
import com.zhuanzhuan.model.Good;
import com.zhuanzhuan.model.User;
import com.zhuanzhuan.util.DBUtil;

public class CartDaoImpl implements ICartDao {

	@Override
	public int add(Cart cart) {

		Connection con = DBUtil.getConnection();
		String sql = "INSERT INTO `cart` (`goodid`, `goodownerid`, `cartnum`, `cartprice`, `cartuser`, `carttime`) VALUES (?,?,?,?,?,?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = 0;
		try {
			ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, cart.getGoodid());
			ps.setInt(2, cart.getGoodownerid());
			ps.setInt(3, cart.getCartnum());
			ps.setString(4, cart.getCartprice());
			ps.setInt(5, cart.getCartuser());
			ps.setTimestamp(6, cart.getCarttime());

			System.out.println(ps.toString());

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
			DBUtil.close(con);
		}
		return id;
	}

	@Override
	public List<Cart> load(int cartuser) {
		Connection con = DBUtil.getConnection();
		String sql = "SELECT * FROM cart WHERE cartuser = ? ";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Cart> carts = new ArrayList<Cart>();
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, cartuser);
			rs = ps.executeQuery();
			CartDaoImpl cartDao = new CartDaoImpl();
			GoodDaoImpl goodDao = new GoodDaoImpl();
			UserDaoImpl userDao = new UserDaoImpl();
			while (rs.next()) {
				User owner = userDao.findById(rs.getInt("goodownerid"));
				User user = userDao.findById(rs.getInt("cartuser"));
				Good good = goodDao.loadById(rs.getInt("goodid"));
				Cart cart = new Cart();
				cart.setId(rs.getInt("id"));
				cart.setCartnum(rs.getInt("cartnum"));
				// cart.setGood(good);
				cart.setCarttime(rs.getTimestamp("carttime"));
				carts.add(cart);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		return carts;
	}

	@Override
	public void delete(int id) {
		Connection connection = DBUtil.getConnection();
		String sql = "DELETE FROM cart WHERE id = ?";
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
}
