package com.zhuanzhuan.util;

import java.util.HashMap;
import java.util.Map;

/**
 * AIService模拟类
 * 用于测试，不依赖外部API和额外库
 */
public class MockAIService {
  // 预设的回答，用于演示或API不可用时
  private static final Map<String, String> PREDEFINED_ANSWERS = new HashMap<>();

  static {
    // 初始化一些预设回答
    PREDEFINED_ANSWERS.put("如何发布二手物品", "发布二手物品很简单，只需点击小程序底部的「发布」按钮，填写物品名称、价格、描述和上传图片等信息，然后点击发布即可。");
    PREDEFINED_ANSWERS.put("如何联系卖家", "您可以在商品详情页点击「联系卖家」按钮，进入聊天界面与卖家沟通。");
    PREDEFINED_ANSWERS.put("如何修改个人信息", "进入「我的」页面，点击头像或昵称，即可进入个人信息页面进行修改。");
    PREDEFINED_ANSWERS.put("如何查看我的订单", "在「我的」页面中，您可以点击「我的订单」查看所有购买记录。");
    PREDEFINED_ANSWERS.put("如何删除已发布的商品", "在「我的」页面中，点击「我发布的」，找到要删除的商品，长按或点击右上角的「···」按钮，选择删除即可。");
    PREDEFINED_ANSWERS.put("如何退款", "目前平台支持线下交易，如需退款请直接与卖家协商。如有纠纷，可以联系平台客服处理。");
    PREDEFINED_ANSWERS.put("忘记密码怎么办", "您可以在登录页面点击「忘记密码」，通过绑定的手机号或邮箱进行密码重置。");
    PREDEFINED_ANSWERS.put("如何加入购物车", "浏览商品时，点击商品详情页的「加入购物车」按钮即可。");
    PREDEFINED_ANSWERS.put("如何搜索商品", "在首页顶部的搜索框中输入关键词，点击搜索即可查找相关商品。");
    PREDEFINED_ANSWERS.put("平台收费标准", "我们的平台目前不收取任何手续费，交易完全免费。");
  }

  /**
   * 根据用户问题获取AI回答
   * 
   * @param question 用户问题
   * @return AI回答
   */
  public static String getAnswer(String question) {
    // 首先检查预定义回答
    for (Map.Entry<String, String> entry : PREDEFINED_ANSWERS.entrySet()) {
      if (question.contains(entry.getKey())) {
        return entry.getValue();
      }
    }

    // 模拟API调用结果
    return "您好！我是二货来了的智能客服助手——小二。您的问题很有趣，但我目前无法提供完整答案。您可以尝试询问其他问题，如发布商品、查看订单等。";
  }

  /**
   * 简单的测试方法
   */
  public static void main(String[] args) {
    System.out.println("开始测试MockAIService...");

    // 测试预设问题
    testQuestion("如何发布二手物品");
    testQuestion("如何联系卖家");
    testQuestion("如何删除已发布的商品");

    // 测试非预设问题
    testQuestion("二货来了平台有什么特色功能？");
    testQuestion("如何找到最便宜的二手书？");

    System.out.println("\nMockAIService测试完成");
  }

  private static void testQuestion(String question) {
    System.out.println("\n问题: " + question);
    long startTime = System.currentTimeMillis();
    String answer = getAnswer(question);
    long endTime = System.currentTimeMillis();

    System.out.println("回答: " + answer);
    System.out.println("响应时间: " + (endTime - startTime) + "ms");
  }
}