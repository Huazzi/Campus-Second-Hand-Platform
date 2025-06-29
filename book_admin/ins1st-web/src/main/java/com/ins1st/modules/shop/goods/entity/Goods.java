package com.ins1st.modules.shop.goods.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.ins1st.core.Page;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * <p>
 * 
 * </p>
 *
 * @author ins1st
 * @since 2020-02-21
 */
public class Goods extends Page {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private String goodname;

	private Integer goodownerid;

	private BigDecimal price;

	private String description;

	private String image;

	private Integer num;

	private String catagory;

	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGoodname() {
		return goodname;
	}

	public void setGoodname(String goodname) {
		this.goodname = goodname;
	}

	public Integer getGoodownerid() {
		return goodownerid;
	}

	public void setGoodownerid(Integer goodownerid) {
		this.goodownerid = goodownerid;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getCatagory() {
		return catagory;
	}

	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Goods{" + "id=" + id + ", goodname=" + goodname + ", goodownerid=" + goodownerid + ", price=" + price
				+ ", description=" + description + ", image=" + image + ", num=" + num + ", catagory=" + catagory
				+ ", time=" + time + "}";
	}
}
