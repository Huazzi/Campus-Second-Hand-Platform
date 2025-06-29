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
	private String catagory;
	private Timestamp time;
	private int leaveMsgNum;
	private int collectNum;
	private String isbn;
	private String chubanshe;
	private String author;
	private String chubantime;
	
	public Good() {}
	public Good(String goodname, User goodowner, double price, String description, String images, int num,
			String catagory, Timestamp time) {
		super();
		this.goodname = goodname;
		this.goodowner = goodowner;
		this.price = price;
		this.description = description;
		this.images = images;
		this.num = num;
		this.catagory = catagory;
		this.time = time;
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
	public String getCatagory() {
		return catagory;
	}
	public void setCatagory(String catagory) {
		this.catagory = catagory;
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
	public JsonObject toJson() {
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("goodId", id);
		jsonObject.addProperty("goodName", goodname);
		jsonObject.add("goodOwner", goodowner.toJson());
		jsonObject.addProperty("price", price);
		jsonObject.addProperty("description",description);
		jsonObject.addProperty("images", images);
		jsonObject.addProperty("num", "num");
		jsonObject.addProperty("catagory", catagory);
		jsonObject.addProperty("time", time.toString());
		jsonObject.addProperty("leave_msg", this.leaveMsgNum);
		jsonObject.addProperty("collect", this.collectNum);
		
		jsonObject.addProperty("isbn", this.isbn);
		jsonObject.addProperty("chubanshe", this.chubanshe);
		jsonObject.addProperty("author", this.author);
		jsonObject.addProperty("chubantime", this.chubantime);
		
		return jsonObject;
	}
	
}
