package com.zhuanzhuan.model;

import java.sql.Timestamp;

public class Cart {
	
	private int id;
	private int goodid;
	private int goodownerid;
	private int cartnum;
	private String cartprice;
	private int cartuser;
	private Timestamp carttime;
	
	public Cart(){
	}
	
	public Cart(int goodid,int goodownerid, int cartnum,
			String cartprice,int cartuser, Timestamp carttime) {
		this.goodid = goodid;
		this.goodownerid = goodownerid;
		this.cartnum = cartnum;
		this.cartprice = cartprice;
		this.cartuser = cartuser;
		this.carttime = carttime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGoodid() {
		return goodid;
	}

	public void setGoodid(int goodid) {
		this.goodid = goodid;
	}

	public int getGoodownerid() {
		return goodownerid;
	}

	public void setGoodownerid(int goodownerid) {
		this.goodownerid = goodownerid;
	}

	public int getCartnum() {
		return cartnum;
	}

	public void setCartnum(int cartnum) {
		this.cartnum = cartnum;
	}

	public String getCartprice() {
		return cartprice;
	}

	public void setCartprice(String cartprice) {
		this.cartprice = cartprice;
	}

	public int getCartuser() {
		return cartuser;
	}

	public void setCartuser(int cartuser) {
		this.cartuser = cartuser;
	}

	public Timestamp getCarttime() {
		return carttime;
	}

	public void setCarttime(Timestamp carttime) {
		this.carttime = carttime;
	}
	
	
}
