package com.zhuanzhuan.model;

import java.sql.Timestamp;

import com.google.gson.JsonObject;

/**
 * @Description:
 * @author
 * @date
 */
public class Good {

	private int id;
	private String goodname;
	private User goodowner;
	private double price;
	private String description;
	private String images;
	private int num;
	private Timestamp time;
	private int leaveMsgNum;
	private int collectNum;
	private String isbn;
	private String chubanshe;
	private String author;
	private String chubantime;
	// 新增字段
	private int viewCount;
	private int inquiryCount;
	private String location;
	private int categoryId;

	public Good() {
		super();
	}

	public Good(String goodname, User goodowner, double price, String description, String images, int num,
			int categoryId,
			Timestamp time, int viewCount, int inquiryCount) {
		super();
		this.goodname = goodname;
		this.goodowner = goodowner;
		this.price = price;
		this.description = description;
		this.images = images;
		this.num = num;
		this.categoryId = categoryId;
		this.time = time;
		this.viewCount = viewCount;
		this.inquiryCount = inquiryCount;
		this.leaveMsgNum = 0;
		this.collectNum = 0;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getChubanshe() {
		return chubanshe;
	}

	public void setChubanshe(String chubanshe) {
		this.chubanshe = chubanshe;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getChubantime() {
		return chubantime;
	}

	public void setChubantime(String chubantime) {
		this.chubantime = chubantime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGoodname() {
		return goodname;
	}

	public void setGoodname(String goodname) {
		this.goodname = goodname;
	}

	public User getGoodowner() {
		return goodowner;
	}

	public void setGoodowner(User goodowner) {
		this.goodowner = goodowner;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String[] getImages() {
		return this.images.split(";");
	}

	public String getImagesStr() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getLeaveMsgNum() {
		return leaveMsgNum;
	}

	public void setLeaveMsgNum(int leaveMsgNum) {
		this.leaveMsgNum = leaveMsgNum;
	}

	public int getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(int collectNum) {
		this.collectNum = collectNum;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getInquiryCount() {
		return inquiryCount;
	}

	public void setInquiryCount(int inquiryCount) {
		this.inquiryCount = inquiryCount;
	}

	public JsonObject toJson() {

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("goodId", id);
		jsonObject.addProperty("goodName", goodname);
		jsonObject.add("goodOwner", goodowner != null ? goodowner.toJson() : null); // 添加 null 检查
		jsonObject.addProperty("price", price);
		jsonObject.addProperty("description", description);
		jsonObject.addProperty("images", images);
		jsonObject.addProperty("num", "num");
		jsonObject.addProperty("categoryId", categoryId);
		jsonObject.addProperty("time", time != null ? time.toString() : null); // 添加 null 检查
		jsonObject.addProperty("leave_msg", this.leaveMsgNum);
		jsonObject.addProperty("collect", this.collectNum);

		jsonObject.addProperty("isbn", this.isbn);
		jsonObject.addProperty("chubanshe", this.chubanshe);
		jsonObject.addProperty("author", this.author);
		jsonObject.addProperty("chubantime", this.chubantime);
		// 新增字段
		jsonObject.addProperty("viewCount", this.viewCount);
		jsonObject.addProperty("inquiryCount", this.inquiryCount);
		jsonObject.addProperty("location", this.location);

		return jsonObject;
	}

}
