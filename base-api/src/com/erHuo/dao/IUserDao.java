package com.erHuo.dao;

import com.erHuo.model.User;

public interface IUserDao {
	User findById(int userId);

	void add(User user);

	void update(User user);

	User loadByAppId(String openid);
}
