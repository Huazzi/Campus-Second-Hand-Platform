package com.zhuanzhuan.util;

/**
 * AIService测试类
 * 用于快速测试AIService工具类功能
 */
public class AIServiceTest {

  public static void main(String[] args) {
    System.out.println("开始测试AIService...");

    // 测试预设问题
    System.out.println("测试预设问题:");
    testQuestion("如何发布二手物品");
    testQuestion("如何联系卖家");

    // 测试API调用
    System.out.println("\n测试API调用:");
    testQuestion("二货来了平台有什么特色功能？");
    testQuestion("我想卖一本书，需要注意什么？");

    System.out.println("\nAIService测试完成");
  }

  private static void testQuestion(String question) {
    System.out.println("\n问题: " + question);
    try {
      long startTime = System.currentTimeMillis();
      String answer = AIService.getAnswer(question);
      long endTime = System.currentTimeMillis();

      System.out.println("回答: " + answer);
      System.out.println("响应时间: " + (endTime - startTime) + "ms");
    } catch (Exception e) {
      System.out.println("测试出错: " + e.getMessage());
      e.printStackTrace();
    }
  }
}