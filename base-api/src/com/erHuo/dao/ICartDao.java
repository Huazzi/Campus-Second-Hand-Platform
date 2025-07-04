package com.erHuo.dao;

import java.util.List;

import com.erHuo.model.Cart;

public interface ICartDao {
	public int add(Cart cart);
	public List<Cart> load(int cartuser);
	public void delete(int id);
}
