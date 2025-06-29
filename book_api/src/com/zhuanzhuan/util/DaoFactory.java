package com.zhuanzhuan.util;

import com.zhuanzhuan.dao.AIChatDaoImpl;
import com.zhuanzhuan.dao.CartDaoImpl;
import com.zhuanzhuan.dao.CourseTextbookDaoImpl;
import com.zhuanzhuan.dao.FeedbackDaoImpl;
import com.zhuanzhuan.dao.GoodDaoImpl;
import com.zhuanzhuan.dao.LeaveMsgDaoImpl;
import com.zhuanzhuan.dao.MessageDaoImpl;
import com.zhuanzhuan.dao.OrderDaoImpl;
import com.zhuanzhuan.dao.UserDaoImpl;

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
}
