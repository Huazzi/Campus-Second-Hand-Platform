package com.erHuo.model;

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
	private double originalPrice; // 原价
	private String description;
	private String conditionStatus; // 商品成色
	private String functionStatus; // 功能状态
	private String purchaseTime; // 购买时间
	private String tradeMethod; // 交易方式
	private String brand; // 品牌
	private String model; // 型号
	private String specifications; // 规格参数
	private String tags; // 商品标签
	private boolean isNegotiable; // 是否可议价
	private String images;
	private int num;
	private Timestamp time;
	private Timestamp createdAt; // 创建时间
	private Timestamp updatedAt; // 更新时间
	private int leaveMsgNum;
	private int collectNum;
	// 图书专用字段
	private String isbn;
	private String chubanshe;
	private String author;
	private String chubantime;
	// 统计字段
	private int viewCount;
	private int inquiryCount;
	private String location;
	private int categoryId;

	public Good() {
		super();
	}

	public Good(String goodname, User goodowner, double price, String description, String images, int num,
			int categoryId, Timestamp time, int viewCount, int inquiryCount) {
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
		// 初始化新字段为默认值
		this.originalPrice = 0.0;
		this.conditionStatus = "";
		this.functionStatus = "";
		this.purchaseTime = "";
		this.tradeMethod = "";
		this.brand = "";
		this.model = "";
		this.specifications = "";
		this.tags = "";
		this.isNegotiable = false;
		this.createdAt = new Timestamp(System.currentTimeMillis());
		this.updatedAt = new Timestamp(System.currentTimeMillis());
	}

	// 添加一个更完整的构造函数
	public Good(String goodname, User goodowner, double price, double originalPrice, String description,
			String conditionStatus, String functionStatus, String purchaseTime, String tradeMethod,
			String brand, String model, String images, int num, int categoryId, Timestamp time,
			int viewCount, int inquiryCount) {
		this(goodname, goodowner, price, description, images, num, categoryId, time, viewCount, inquiryCount);
		this.originalPrice = originalPrice;
		this.conditionStatus = conditionStatus;
		this.functionStatus = functionStatus;
		this.purchaseTime = purchaseTime;
		this.tradeMethod = tradeMethod;
		this.brand = brand;
		this.model = model;
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

	/**
	 * 将 Good 对象转换为 JSON 格式
	 *
	 * @return JsonObject
	 */
	public JsonObject toJson() {

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("goodId", id);
		jsonObject.addProperty("goodName", goodname);
		jsonObject.add("goodOwner", goodowner != null ? goodowner.toJson() : null); // 添加 null 检查
		jsonObject.addProperty("price", price);
		jsonObject.addProperty("description", description);
		jsonObject.addProperty("images", images);
		jsonObject.addProperty("num", this.num);
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

	// 新增字段的getter和setter方法
	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getConditionStatus() {
		return conditionStatus;
	}

	public void setConditionStatus(String conditionStatus) {
		this.conditionStatus = conditionStatus;
	}

	public String getFunctionStatus() {
		return functionStatus;
	}

	public void setFunctionStatus(String functionStatus) {
		this.functionStatus = functionStatus;
	}

	public String getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(String purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public String getTradeMethod() {
		return tradeMethod;
	}

	public void setTradeMethod(String tradeMethod) {
		this.tradeMethod = tradeMethod;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public boolean isNegotiable() {
		return isNegotiable;
	}

	public void setNegotiable(boolean isNegotiable) {
		this.isNegotiable = isNegotiable;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
