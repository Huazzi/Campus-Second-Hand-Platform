package com.erHuo.util;

import com.erHuo.dao.AIChatDaoImpl;
import com.erHuo.dao.CartDaoImpl;
import com.erHuo.dao.CategoryDaoImpl;
import com.erHuo.dao.CourseTextbookDaoImpl;
import com.erHuo.dao.FeedbackDaoImpl;
import com.erHuo.dao.GoodDaoImpl;
import com.erHuo.dao.LeaveMsgDaoImpl;
import com.erHuo.dao.MessageDaoImpl;
import com.erHuo.dao.OrderDaoImpl;
import com.erHuo.dao.UserDaoImpl;

/**
 * DAO工厂类
 * 负责创建和管理DAO实例
 */
public class DaoFactory {
	public static UserDaoImpl getUserDao() {
		return new UserDaoImpl();
	}

	public static GoodDaoImpl getGoodDao() {
		return new GoodDaoImpl();
	}

	public static MessageDaoImpl getMessageDao() {
		return new MessageDaoImpl();
	}

	public static LeaveMsgDaoImpl getLeaveMsgDao() {
		return new LeaveMsgDaoImpl();
	}

	public static FeedbackDaoImpl getFeedbackDao() {
		return new FeedbackDaoImpl();
	}

	public static CartDaoImpl getCartDao() {
		return new CartDaoImpl();
	}

	public static OrderDaoImpl getOrderDao() {
		return new OrderDaoImpl();
	}

	public static AIChatDaoImpl getAIChatDao() {
		return new AIChatDaoImpl();
	}

	public static CourseTextbookDaoImpl getCourseTextbookDao() {
		return new CourseTextbookDaoImpl();
	}

	public static CategoryDaoImpl getCategoryDao() {
		return new CategoryDaoImpl();
	}
}