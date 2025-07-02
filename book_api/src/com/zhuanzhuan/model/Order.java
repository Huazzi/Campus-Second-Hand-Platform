package com.zhuanzhuan.model;

import java.util.Date;

import com.google.gson.JsonObject;

public class Order {

	private Integer id;
	private Integer goodid;
	private Integer goodownerid;
	private String price;
	private String total;
	private Integer num;
	private String category;
	private Integer userId;
	private Integer status;
	private Date ordertime;
	private User user;
	private Good good;

	public Order() {

	}

	public Order(Integer goodid, Integer goodownerid, String price,
			String total, Integer num, String category, Integer userId,
			Integer status, Date ordertime) {
		super();
		this.goodid = goodid;
		this.goodownerid = goodownerid;
		this.price = price;
		this.total = total;
		this.num = num;
		this.category = category;
		this.userId = userId;
		this.status = status;
		this.ordertime = ordertime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Good getGood() {
		return good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGoodid() {
		return goodid;
	}

	public void setGoodid(Integer goodid) {
		this.goodid = goodid;
	}

	public Integer getGoodownerid() {
		return goodownerid;
	}

	public void setGoodownerid(Integer goodownerid) {
		this.goodownerid = goodownerid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public JsonObject toJson() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", id);
		jsonObject.addProperty("goodid", goodid);
		jsonObject.addProperty("goodownerid", goodownerid);
		jsonObject.addProperty("price", price);
		jsonObject.addProperty("total", total);
		jsonObject.addProperty("num", num);
		jsonObject.addProperty("category", category);
		jsonObject.addProperty("num", "num");
		jsonObject.addProperty("userId", userId);
		jsonObject.addProperty("ordertime", ordertime.toString());
		jsonObject.addProperty("status", status);
		jsonObject.add("good", good.toJson());
		jsonObject.add("user", user.toJson());
		return jsonObject;
	}

}
