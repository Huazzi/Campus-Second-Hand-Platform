package com.zhuanzhuan.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * AI服务工具类
 * 负责与讯飞星火API通信
 */
public class AIService {
  // 讯飞星火API地址
  private static final String AI_API_URL = "https://spark-api-open.xf-yun.com/v1/chat/completions";

  // 讯飞星火API密钥，实际应用中应存储在配置文件或环境变量中
  private static final String API_KEY = "QVnxGEpbsJJATUrIwFlg:HjRIUlMNRjJlcsQhOioh"; // 填写 APIPassword

  // 选择使用的模型版本：可选 general(Lite版本), generalv3(Pro版本), pro-128k(Pro-128K版本),
  // generalv3.5(Max版本), 4.0Ultra(4.0 Ultra版本)
  private static final String MODEL = "4.0Ultra";

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

    try {
      // 调用讯飞星火API
      return callSparkAPI(question);
    } catch (Exception e) {
      e.printStackTrace();
      // 如果API调用失败，返回默认回答
      return "很抱歉，我现在无法回答这个问题。请尝试询问关于发布商品、购买流程、个人信息修改等问题，或联系人工客服。";
    }
  }

  /**
   * 调用讯飞星火API
   * 
   * @param question 用户问题
   * @return AI回答
   */
  private static String callSparkAPI(String question) throws Exception {
    // 构建API请求
    URL url = new URL(AI_API_URL);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
    conn.setDoOutput(true);

    // 构建请求体
    JsonObject requestBody = new JsonObject();
    requestBody.addProperty("model", MODEL);

    // 构建消息数组
    JsonArray messagesArray = new JsonArray();

    // 添加系统角色消息（可选）
    JsonObject systemMessage = new JsonObject();
    systemMessage.addProperty("role", "system");
    systemMessage.addProperty("content",
        "你是校园二手交易平台「二货来了」的智能客服助手，名叫小二。你的任务是帮助用户解答关于平台使用、商品发布、交易流程等问题。请使用礼貌友好的语气，回答要简洁明了。输出回答内容禁止使用Markdown语法。");
    messagesArray.add(systemMessage);

    // 添加用户问题
    JsonObject userMessage = new JsonObject();
    userMessage.addProperty("role", "user");
    userMessage.addProperty("content", question);
    messagesArray.add(userMessage);

    requestBody.add("messages", messagesArray);
    requestBody.addProperty("temperature", 0.7); // 控制答案的随机性
    requestBody.addProperty("max_tokens", 4096); // 回答的最大长度
    requestBody.addProperty("stream", false); // 非流式响应

    // 发送请求
    try (OutputStream os = conn.getOutputStream()) {
      byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
      os.write(input, 0, input.length);
    }

    // 读取响应
    StringBuilder response = new StringBuilder();
    int responseCode = conn.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) { // 成功响应
      try (BufferedReader br = new BufferedReader(
          new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }
      }
    } else { // 错误响应
      try (BufferedReader br = new BufferedReader(
          new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }
      }
      System.out.println("星火API错误响应: " + response.toString());
      return "抱歉，AI服务暂时不可用，请稍后再试。";
    }

    // 解析响应JSON
    JsonElement jsonElement;
    try (StringReader reader = new StringReader(response.toString())) {
      JsonParser parser = new JsonParser();
      jsonElement = parser.parse(reader);
    }
    JsonObject jsonResponse = jsonElement.getAsJsonObject();

    // 检查是否有错误码
    if (jsonResponse.has("code") && jsonResponse.get("code").getAsInt() != 0) {
      int errorCode = jsonResponse.get("code").getAsInt();
      String errorMsg = "AI服务出错，错误码: " + errorCode;
      if (jsonResponse.has("message")) {
        errorMsg += ", 错误信息: " + jsonResponse.get("message").getAsString();
      }
      System.out.println(errorMsg);
      return "抱歉，AI服务暂时不可用，请稍后再试。";
    }

    // 正常情况：从choices中提取回答内容
    if (jsonResponse.has("choices") && jsonResponse.getAsJsonArray("choices").size() > 0) {
      JsonObject choice = jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject();
      if (choice.has("message") && choice.getAsJsonObject("message").has("content")) {
        return choice.getAsJsonObject("message").get("content").getAsString();
      }
    }

    // 如果无法解析返回内容，返回默认回答
    return "非常感谢您的问题，我是智能客服小二，很高兴为您服务。请问还有什么可以帮助您的吗？";
  }
}