package com.zhuanzhuan.dao;

import java.util.List;

import com.zhuanzhuan.model.Good;
import com.zhuanzhuan.model.Order;

public interface IOrderDao {
	public int add(Order order);
	public void delete(int id);
	public void update(Order order);
	public List<Order> loadByUser(int userId,int status);
	public List<Order> loadbyOwner(int id);
	public List<Order> loadByOrderUser(int userId);
}
