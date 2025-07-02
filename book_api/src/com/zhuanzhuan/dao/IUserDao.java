package com.zhuanzhuan.dao;

import com.zhuanzhuan.model.User;

public interface IUserDao {
	public User findById(int userId);

	public void add(User user);

	public void update(User user);

	User loadByAppId(String oppenid);

}
