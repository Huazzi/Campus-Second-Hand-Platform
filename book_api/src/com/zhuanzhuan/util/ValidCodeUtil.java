package com.zhuanzhuan.util;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValidCodeUtil {
	private static final Logger logger = LogManager.getLogger(ValidCodeUtil.class);

	// 短信发送成功的返回码
	private static final int SUCCESS_CODE = 0;
	// 通用错误码
	private static final int ERROR_CODE = -310099;

	/**
	 * 发送验证码短信
	 * 
	 * @param validCode   验证码
	 * @param mobilePhone 手机号
	 * @return 发送结果描述
	 */
	public static String sendSMS(String validCode, String mobilePhone) {
		logger.info("准备发送验证码短信到: {}, 验证码: {}", mobilePhone, validCode);

		try {
			// 短信内容
			String content = "您的验证码为" + validCode + "，在10分钟内输入有效，若非本人操作请忽略此短信。";

			// 模拟短信发送过程
			int result = mockSendSms(mobilePhone, content);

			if (result == SUCCESS_CODE)
				return "发送成功";
			else
				return "错误" + result;
		} catch (Exception e) {
			logger.error("发送验证码短信失败", e);
			return "错误" + ERROR_CODE;
		}
	}

	/**
	 * 发送订单流水号短信
	 * 
	 * @param orderser    订单号
	 * @param streamid    流水号
	 * @param mobilePhone 手机号
	 * @return 发送结果描述
	 */
	public static String sendSMS(String orderser, int streamid, String mobilePhone) {
		logger.info("准备发送订单短信到: {}, 订单号: {}, 流水号: {}", mobilePhone, orderser, streamid);

		try {
			// 短信内容
			String content = "您订单" + orderser + "中的商品对方已收到，交易流水号为" + streamid + "请保持电话畅通，谢谢您选择转转，我们将继续竭诚为您服务。";

			// 模拟短信发送过程
			int result = mockSendSms(mobilePhone, content);

			if (result == SUCCESS_CODE)
				return "发送成功";
			else
				return "错误" + result;
		} catch (Exception e) {
			logger.error("发送订单短信失败", e);
			return "错误" + ERROR_CODE;
		}
	}

	/**
	 * 模拟短信发送
	 * 
	 * @param mobile  手机号
	 * @param content 短信内容
	 * @return 发送结果码，0表示成功
	 */
	private static int mockSendSms(String mobile, String content) {
		// 验证手机号格式
		if (mobile == null || !mobile.matches("^1[3-9]\\d{9}$")) {
			logger.error("手机号格式不正确: {}", mobile);
			return -310001; // 模拟手机号格式错误码
		}

		// 打印短信内容，模拟实际发送
		logger.info("模拟发送短信 -> 手机号: {}, 内容: {}", mobile, content);

		// 生成随机的短信ID，模拟实际短信平台返回的流水号
		String messageId = generateMessageId();
		logger.info("短信发送成功，messageId: {}", messageId);

		// 模拟发送成功率95%
		return new Random().nextInt(100) < 95 ? SUCCESS_CODE : -310002;
	}

	/**
	 * 生成模拟的短信ID
	 * 
	 * @return 短信ID
	 */
	private static String generateMessageId() {
		return System.currentTimeMillis() + "" + new Random().nextInt(1000);
	}

	/**
	 * 生成随机验证码
	 * 
	 * @param length 验证码长度
	 * @return 随机验证码
	 */
	public static String generateValidCode(int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
}
