package com.erHuo.dao;

import java.util.List;

import com.erHuo.model.LeaveMsg;

public interface ILeaveMsgDao {
	public int add(LeaveMsg leaveMsg);
	public List<LeaveMsg> load(int goodId,int reply);
	public int getLeaveMsgNum(int goodId);
}
