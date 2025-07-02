package com.zhuanzhuan.dao;

import java.util.List;

import com.zhuanzhuan.model.Cart;

public interface ICartDao {
	public int add(Cart cart);
	public List<Cart> load(int cartuser);
	public void delete(int id);
}
