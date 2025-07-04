package com.erHuo.model;

import com.google.gson.JsonObject;

/**
 * @Description: 课程与教材的对应关系模型
 */
public class CourseTextbook {

  private int id;
  private String courseName; // 课程名称
  private String textbookName; // 教材名称
  private String isbn; // ISBN号
  private String publisher; // 出版社
  private String author; // 作者
  private int semester; // 学期 (0-第一学期，1-第二学期)
  private String major; // 专业
  private int recommendLevel; // 推荐等级 (1-5，5为最高)
  private boolean isRequired; // 是否必修教材

  public CourseTextbook() {
  }

  public CourseTextbook(String courseName, String textbookName, String isbn, String publisher,
      String author, int semester, String major, int recommendLevel, boolean isRequired) {
    this.courseName = courseName;
    this.textbookName = textbookName;
    this.isbn = isbn;
    this.publisher = publisher;
    this.author = author;
    this.semester = semester;
    this.major = major;
    this.recommendLevel = recommendLevel;
    this.isRequired = isRequired;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getTextbookName() {
    return textbookName;
  }

  public void setTextbookName(String textbookName) {
    this.textbookName = textbookName;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getSemester() {
    return semester;
  }

  public void setSemester(int semester) {
    this.semester = semester;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public int getRecommendLevel() {
    return recommendLevel;
  }

  public void setRecommendLevel(int recommendLevel) {
    this.recommendLevel = recommendLevel;
  }

  public boolean isRequired() {
    return isRequired;
  }

  public void setRequired(boolean required) {
    isRequired = required;
  }

  public JsonObject toJson() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("id", id);
    jsonObject.addProperty("courseName", courseName);
    jsonObject.addProperty("textbookName", textbookName);
    jsonObject.addProperty("isbn", isbn);
    jsonObject.addProperty("publisher", publisher);
    jsonObject.addProperty("author", author);
    jsonObject.addProperty("semester", semester);
    jsonObject.addProperty("major", major);
    jsonObject.addProperty("recommendLevel", recommendLevel);
    jsonObject.addProperty("isRequired", isRequired);
    return jsonObject;
  }
}