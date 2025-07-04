package com.erHuo.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.erHuo.dao.CourseTextbookDaoImpl;
import com.erHuo.dao.GoodDaoImpl;
import com.erHuo.model.CourseTextbook;
import com.erHuo.model.Good;
import com.erHuo.util.DaoFactory;

/**
 * @Description: 课程教材智能推荐Servlet
 */
@WebServlet("/CourseRecommend")
public class CourseRecommendServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public CourseRecommendServlet() {
    super();
  }

  /**
   * @Description: 处理GET请求
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  /**
   * @Description: 处理POST请求
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json;charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();

    JsonObject jsonObject = new JsonObject();
    String action = request.getParameter("action");

    if (action == null) {
      jsonObject.addProperty("status", "error");
      jsonObject.addProperty("message", "缺少action参数");
      out.print(jsonObject.toString());
      return;
    }

    // 根据课程名称获取推荐教材
    if (action.equals("getRecommendByCourseName")) {
      String courseName = request.getParameter("courseName");
      if (courseName == null || courseName.isEmpty()) {
        jsonObject.addProperty("status", "error");
        jsonObject.addProperty("message", "缺少courseName参数");
        out.print(jsonObject.toString());
        return;
      }

      jsonObject = getRecommendByCourseName(courseName);

    } else if (action.equals("getRecommendByCourseList")) {
      // 根据课程列表获取推荐教材
      String courseListJson = request.getParameter("courseList");
      if (courseListJson == null || courseListJson.isEmpty()) {
        jsonObject.addProperty("status", "error");
        jsonObject.addProperty("message", "缺少courseList参数");
        out.print(jsonObject.toString());
        return;
      }

      try {
        Gson gson = new Gson();
        String[] courseList = gson.fromJson(courseListJson, String[].class);
        jsonObject = getRecommendByCourseList(courseList);
      } catch (Exception e) {
        jsonObject.addProperty("status", "error");
        jsonObject.addProperty("message", "courseList参数格式错误");
        out.print(jsonObject.toString());
        return;
      }

    } else if (action.equals("getRecommendBySemester")) {
      // 根据学期获取推荐教材
      String semesterStr = request.getParameter("semester");
      String major = request.getParameter("major");

      if (semesterStr == null || semesterStr.isEmpty()) {
        jsonObject.addProperty("status", "error");
        jsonObject.addProperty("message", "缺少semester参数");
        out.print(jsonObject.toString());
        return;
      }

      try {
        int semester = Integer.parseInt(semesterStr);
        jsonObject = getRecommendBySemester(semester, major);
      } catch (NumberFormatException e) {
        jsonObject.addProperty("status", "error");
        jsonObject.addProperty("message", "semester参数格式错误");
        out.print(jsonObject.toString());
        return;
      }

    } else if (action.equals("getAllCourses")) {
      // 获取所有课程名称
      jsonObject = getAllCourses();

    } else {
      jsonObject.addProperty("status", "error");
      jsonObject.addProperty("message", "未知的action参数: " + action);
    }

    out.print(jsonObject.toString());
  }

  /**
   * @Description: 根据课程名称获取推荐教材
   * @param courseName 课程名称
   * @return 推荐教材列表
   */
  private JsonObject getRecommendByCourseName(String courseName) {
    JsonObject jsonObject = new JsonObject();

    try {
      CourseTextbookDaoImpl courseTextbookDao = DaoFactory.getCourseTextbookDao();
      List<CourseTextbook> courseTextbooks = courseTextbookDao.loadByCourseName(courseName);

      JsonArray recommendedTextbooks = new JsonArray();
      GoodDaoImpl goodDao = DaoFactory.getGoodDao();

      for (CourseTextbook courseTextbook : courseTextbooks) {
        // 根据教材名称查找可用的二手教材
        List<Good> goods = goodDao.loadWithCondition(
            courseTextbook.getTextbookName(),
            101, // 教辅教材对应的分类ID
            0,
            10000,
            0,
            10);

        // 将查询结果添加到推荐列表
        for (Good good : goods) {
          recommendedTextbooks.add(good.toJson());
        }
      }

      jsonObject.addProperty("status", "success");
      jsonObject.add("recommendedTextbooks", recommendedTextbooks);
      jsonObject.addProperty("count", recommendedTextbooks.size());

    } catch (Exception e) {
      jsonObject.addProperty("status", "error");
      jsonObject.addProperty("message", "获取推荐教材失败: " + e.getMessage());
    }

    return jsonObject;
  }

  /**
   * @Description: 根据课程列表获取推荐教材
   * @param courseNames 课程名称列表
   * @return 推荐教材列表
   */
  private JsonObject getRecommendByCourseList(String[] courseNames) {
    JsonObject jsonObject = new JsonObject();

    try {
      CourseTextbookDaoImpl courseTextbookDao = DaoFactory.getCourseTextbookDao();
      GoodDaoImpl goodDao = DaoFactory.getGoodDao();

      // 存储所有推荐教材，按课程分组
      Map<String, List<Good>> courseRecommendations = new HashMap<>();
      // 存储所有推荐教材，不分组
      List<Good> allRecommendedGoods = new ArrayList<>();

      for (String courseName : courseNames) {
        List<CourseTextbook> courseTextbooks = courseTextbookDao.loadByCourseName(courseName);
        List<Good> courseGoods = new ArrayList<>();

        for (CourseTextbook courseTextbook : courseTextbooks) {
          // 根据教材名称查找可用的二手教材
          List<Good> goods = goodDao.loadWithCondition(
              courseTextbook.getTextbookName(),
              101, // 教辅教材对应的分类ID
              0,
              10000,
              0,
              5 // 每个教材最多推荐5本
          );

          courseGoods.addAll(goods);
          allRecommendedGoods.addAll(goods);
        }

        courseRecommendations.put(courseName, courseGoods);
      }

      // 构造返回结果
      JsonObject recommendationsByCourseName = new JsonObject();
      for (Map.Entry<String, List<Good>> entry : courseRecommendations.entrySet()) {
        String courseName = entry.getKey();
        List<Good> goods = entry.getValue();

        JsonArray goodsArray = new JsonArray();
        for (Good good : goods) {
          goodsArray.add(good.toJson());
        }

        recommendationsByCourseName.add(courseName, goodsArray);
      }

      // 添加所有推荐教材，不分组
      JsonArray allRecommendations = new JsonArray();
      for (Good good : allRecommendedGoods) {
        allRecommendations.add(good.toJson());
      }

      jsonObject.addProperty("status", "success");
      jsonObject.add("recommendationsByCourseName", recommendationsByCourseName);
      jsonObject.add("allRecommendations", allRecommendations);
      jsonObject.addProperty("count", allRecommendedGoods.size());

    } catch (Exception e) {
      jsonObject.addProperty("status", "error");
      jsonObject.addProperty("message", "获取推荐教材失败: " + e.getMessage());
    }

    return jsonObject;
  }

  /**
   * @Description: 根据学期和专业获取推荐教材
   * @param semester 学期
   * @param major    专业
   * @return 推荐教材列表
   */
  private JsonObject getRecommendBySemester(int semester, String major) {
    JsonObject jsonObject = new JsonObject();

    try {
      CourseTextbookDaoImpl courseTextbookDao = DaoFactory.getCourseTextbookDao();
      List<CourseTextbook> courseTextbooks;

      if (major != null && !major.isEmpty()) {
        courseTextbooks = courseTextbookDao.loadBySemesterAndMajor(semester, major);
      } else {
        courseTextbooks = courseTextbookDao.loadBySemester(semester);
      }

      JsonArray recommendedTextbooks = new JsonArray();
      GoodDaoImpl goodDao = DaoFactory.getGoodDao();

      for (CourseTextbook courseTextbook : courseTextbooks) {
        // 根据教材名称查找可用的二手教材
        List<Good> goods = goodDao.loadWithCondition(
            courseTextbook.getTextbookName(),
            101, // 教辅教材对应的分类ID
            0,
            10000,
            0,
            5 // 每个教材最多推荐5本
        );

        // 将查询结果添加到推荐列表
        for (Good good : goods) {
          JsonObject goodJson = good.toJson();
          goodJson.addProperty("courseName", courseTextbook.getCourseName());
          goodJson.addProperty("isRequired", courseTextbook.isRequired());
          goodJson.addProperty("recommendLevel", courseTextbook.getRecommendLevel());
          recommendedTextbooks.add(goodJson);
        }
      }

      jsonObject.addProperty("status", "success");
      jsonObject.add("recommendedTextbooks", recommendedTextbooks);
      jsonObject.addProperty("count", recommendedTextbooks.size());

    } catch (Exception e) {
      jsonObject.addProperty("status", "error");
      jsonObject.addProperty("message", "获取推荐教材失败: " + e.getMessage());
    }

    return jsonObject;
  }

  /**
   * @Description: 获取所有课程名称
   * @return 课程名称列表
   */
  private JsonObject getAllCourses() {
    JsonObject jsonObject = new JsonObject();

    try {
      CourseTextbookDaoImpl courseTextbookDao = DaoFactory.getCourseTextbookDao();
      List<CourseTextbook> allCourseTextbooks = courseTextbookDao.loadAll();

      // 使用Set去重
      Map<String, Boolean> courseMap = new HashMap<>();
      for (CourseTextbook courseTextbook : allCourseTextbooks) {
        courseMap.put(courseTextbook.getCourseName(), true);
      }

      JsonArray coursesArray = new JsonArray();
      for (String courseName : courseMap.keySet()) {
        coursesArray.add(new JsonPrimitive(courseName));
      }

      jsonObject.addProperty("status", "success");
      jsonObject.add("courses", coursesArray);

    } catch (Exception e) {
      jsonObject.addProperty("status", "error");
      jsonObject.addProperty("message", "获取课程列表失败: " + e.getMessage());
    }

    return jsonObject;
  }
}