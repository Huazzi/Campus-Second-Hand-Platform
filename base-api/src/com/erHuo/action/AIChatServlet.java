package com.erHuo.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.erHuo.dao.AIChatDaoImpl;
import com.erHuo.model.AIChat;
import com.erHuo.util.HttpUtil;
import com.erHuo.util.DaoFactory;

/**
 * AI聊天处理Servlet
 */
@WebServlet("/AIChat")
public class AIChatServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Gson gson = new Gson(); // 使用Gson对象进行JSON解析

  public AIChatServlet() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("application/json;charset=utf-8");
    response.setHeader("Access-Control-Allow-Origin", "*");
    PrintWriter writer = response.getWriter();

    String type = request.getParameter("type");

    if ("history".equals(type)) {
      String userIdParam = request.getParameter("userId");

      // 参数校验
      if (userIdParam == null || userIdParam.trim().isEmpty() || "undefined".equals(userIdParam)) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        writer.print("{\"error\":\"userId parameter is required\"}");
        return;
      }

      try {
        int userId = Integer.parseInt(userIdParam);
        AIChatDaoImpl aiChatDao = DaoFactory.getAIChatDao();
        List<AIChat> chatHistory = aiChatDao.loadByUserId(userId);

        JsonArray jsonArray = new JsonArray();
        for (AIChat chat : chatHistory) {
          jsonArray.add(chat.toJson());
        }
        writer.print(jsonArray.toString());

      } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        writer.print("{\"error\":\"userId must be a number\"}");
      } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        writer.print("{\"error\":\"Server error\"}");
        e.printStackTrace();
      }
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json;charset=utf-8");
    response.setHeader("Access-Control-Allow-Origin", "*");
    PrintWriter writer = response.getWriter();

    StringBuilder sb = new StringBuilder();
    BufferedReader reader = request.getReader();
    String line;
    while ((line = reader.readLine()) != null) {
      sb.append(line);
    }

    try {
      // 使用Gson解析JSON请求
      JsonObject jsonRequest = gson.fromJson(sb.toString(), JsonObject.class);
      String type = jsonRequest.get("type").getAsString();

      if ("chat".equals(type)) {
        int userId = jsonRequest.get("userId").getAsInt();
        String question = jsonRequest.get("question").getAsString();

        AIChatDaoImpl aiChatDao = DaoFactory.getAIChatDao();
        AIChat userQuestion = new AIChat(userId, question, "", new Timestamp(System.currentTimeMillis()), false);
        aiChatDao.add(userQuestion);

        // 调用AI服务获取回答
        // 这里HttpUtil.sendPost方法会调用AI服务并返回回答
        String answer = HttpUtil.sendPost("http://localhost:8001/api/v1/chat", "{\"question\": \"" + question + "\"}");

        AIChat aiAnswer = new AIChat(userId, question, answer, new Timestamp(System.currentTimeMillis()), true);
        aiChatDao.add(aiAnswer);

        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("answer", answer);
        writer.print(jsonResponse.toString());
      } else {
        writer.print("{\"error\":\"Invalid request type\"}");
      }
    } catch (Exception e) {
      e.printStackTrace();
      writer.print("{\"error\":\"" + e.getMessage() + "\"}");
    }
  }
}
