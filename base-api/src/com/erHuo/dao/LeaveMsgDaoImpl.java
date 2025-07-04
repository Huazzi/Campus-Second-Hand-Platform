package com.erHuo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.erHuo.model.Good;
import com.erHuo.model.LeaveMsg;
import com.erHuo.model.User;
import com.erHuo.util.DBUtil;

public class LeaveMsgDaoImpl implements ILeaveMsgDao{

	@Override
	public int add(LeaveMsg leaveMsg) {
		
		Connection con = DBUtil.getConnection();
		String sql = "INSERT INTO `leave_msg` (`senduserid`, `reciveuserid`, `content`, `goodid`, `time`, `reply`) VALUES (?,?,?,?,?,?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = 0;
		try {
			ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, leaveMsg.getSend().getId());
			ps.setInt(2, leaveMsg.getReceive().getId());
			ps.setString(3, leaveMsg.getContent());
			ps.setInt(4, leaveMsg.getGood().getId());
			ps.setTimestamp(5, leaveMsg.getTime());
			ps.setInt(6, leaveMsg.getReply());
			System.out.println(ps.toString());
			
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			while(rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		return id;
	}

	@Override
	public List<LeaveMsg> load(int goodid,int reply) {
		Connection con = DBUtil.getConnection();
		String sql = "SELECT * FROM leave_msg WHERE goodid = ? and reply = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<LeaveMsg> leaveMsgs = new ArrayList<LeaveMsg>();
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, goodid);
			ps.setInt(2, reply);
			rs = ps.executeQuery();
			UserDaoImpl userDao = new UserDaoImpl();
			GoodDaoImpl goodDao = new GoodDaoImpl();
			while (rs.next()) {
				
				User send = userDao.findById(rs.getInt("senduserid"));
				User receive = userDao.findById(rs.getInt("reciveuserid"));
				Good good = goodDao.loadById(rs.getInt("goodid"));
				LeaveMsg leaveMsg = new LeaveMsg();
				leaveMsg.setId(rs.getInt("id"));
				leaveMsg.setSend(send);
				leaveMsg.setReceive(receive);
				leaveMsg.setGood(good);
				leaveMsg.setContent(rs.getString("content"));
				leaveMsg.setTime(rs.getTimestamp("time"));
				leaveMsg.setReply(rs.getInt("reply"));
				leaveMsgs.add(leaveMsg);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		return leaveMsgs;
	}

	@Override
	public int getLeaveMsgNum(int goodId) {
		Connection con = DBUtil.getConnection();
		String sql = "SELECT COUNT(*) FROM leave_msg WHERE goodid = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, goodId);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(con);
		}
		return count;
	}
}