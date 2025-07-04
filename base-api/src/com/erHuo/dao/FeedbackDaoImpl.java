package com.erHuo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.erHuo.model.Feedback;
import com.erHuo.util.DBUtil;

public class FeedbackDaoImpl implements IFeedbackDao {

	@Override
	public int addFeedback(Feedback feedback) {
		
		Connection con = DBUtil.getConnection();
		String sql = "INSERT INTO `feedback` (`content`, `contact`, `time`) VALUES (?, ?, ?)";
		PreparedStatement ps = null;
		int status = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, feedback.getContent());
			ps.setString(2, feedback.getContact());
			ps.setTimestamp(3, feedback.getTime());
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		
		return status;

	}

}
