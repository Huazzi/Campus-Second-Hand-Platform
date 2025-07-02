package com.ins1st.modules.shop.feedback.entity;

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
public class Feedback extends Page {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private String content;

	private String contact;

	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Feedback{" + "id=" + id + ", content=" + content + ", contact=" + contact + ", time=" + time + "}";
	}
}
