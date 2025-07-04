package com.erHuo.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.google.gson.JsonObject;

/**
 * AI聊天模型类
 */
public class AIChat {
  private int id;
  private int userId;
  private String question;
  private String answer;
  private Timestamp time;
  private boolean isAI; // true表示AI消息，false表示用户消息

  public AIChat() {
  }

  public AIChat(int userId, String question, String answer, Timestamp time, boolean isAI) {
    this.userId = userId;
    this.question = question;
    this.answer = answer;
    this.time = time;
    this.isAI = isAI;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public boolean isAI() {
    return isAI;
  }

  public void setAI(boolean isAI) {
    this.isAI = isAI;
  }

  public String getTimeStr(String pattern) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    String timeStr = sdf.format(this.time);
    return timeStr;
  }

  public JsonObject toJson() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("id", this.id);
    jsonObject.addProperty("userId", this.userId);

    if (this.isAI) {
      jsonObject.addProperty("content", this.answer);
    } else {
      jsonObject.addProperty("content", this.question);
    }

    jsonObject.addProperty("time", this.time.getTime());
    jsonObject.addProperty("isAI", this.isAI);

    return jsonObject;
  }
}