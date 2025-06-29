package com.zhuanzhuan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zhuanzhuan.model.CourseTextbook;
import com.zhuanzhuan.util.DBUtil;

/**
 * @Description: 课程教材关联DAO实现类
 */
public class CourseTextbookDaoImpl implements ICourseTextbookDao {

  /**
   * @Description 添加课程教材关联
   * @param courseTextbook 课程教材关联对象
   * @return 添加的课程教材关联的ID
   */
  @Override
  public int add(CourseTextbook courseTextbook) {
    Connection connection = DBUtil.getConnection();
    String sql = "INSERT INTO course_textbook (course_name, textbook_name, isbn, publisher, author, semester, major, recommend_level, is_required) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement ps = null;
    ResultSet rs = null;
    int id = 0;
    try {
      ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
      ps.setString(1, courseTextbook.getCourseName());
      ps.setString(2, courseTextbook.getTextbookName());
      ps.setString(3, courseTextbook.getIsbn());
      ps.setString(4, courseTextbook.getPublisher());
      ps.setString(5, courseTextbook.getAuthor());
      ps.setInt(6, courseTextbook.getSemester());
      ps.setString(7, courseTextbook.getMajor());
      ps.setInt(8, courseTextbook.getRecommendLevel());
      ps.setBoolean(9, courseTextbook.isRequired());

      ps.executeUpdate();
      rs = ps.getGeneratedKeys();
      if (rs.next()) {
        id = rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(rs);
      DBUtil.close(ps);
      DBUtil.close(connection);
    }
    return id;
  }

  /**
   * @Description 删除课程教材关联
   * @param id 课程教材关联ID
   */
  @Override
  public void delete(int id) {
    Connection connection = DBUtil.getConnection();
    String sql = "DELETE FROM course_textbook WHERE id = ?";
    PreparedStatement ps = null;
    try {
      ps = connection.prepareStatement(sql);
      ps.setInt(1, id);
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(ps);
      DBUtil.close(connection);
    }
  }

  /**
   * @Description 更新课程教材关联
   * @param courseTextbook 课程教材关联对象
   */
  @Override
  public void update(CourseTextbook courseTextbook) {
    Connection connection = DBUtil.getConnection();
    String sql = "UPDATE course_textbook SET course_name=?, textbook_name=?, isbn=?, publisher=?, author=?, semester=?, major=?, recommend_level=?, is_required=? WHERE id=?";
    PreparedStatement ps = null;
    try {
      ps = connection.prepareStatement(sql);
      ps.setString(1, courseTextbook.getCourseName());
      ps.setString(2, courseTextbook.getTextbookName());
      ps.setString(3, courseTextbook.getIsbn());
      ps.setString(4, courseTextbook.getPublisher());
      ps.setString(5, courseTextbook.getAuthor());
      ps.setInt(6, courseTextbook.getSemester());
      ps.setString(7, courseTextbook.getMajor());
      ps.setInt(8, courseTextbook.getRecommendLevel());
      ps.setBoolean(9, courseTextbook.isRequired());
      ps.setInt(10, courseTextbook.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(ps);
      DBUtil.close(connection);
    }
  }

  /**
   * @Description 根据ID加载课程教材关联
   * @param id 课程教材关联ID
   * @return 课程教材关联对象
   */
  @Override
  public CourseTextbook loadById(int id) {
    Connection connection = DBUtil.getConnection();
    String sql = "SELECT * FROM course_textbook WHERE id = ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    CourseTextbook courseTextbook = null;
    try {
      ps = connection.prepareStatement(sql);
      ps.setInt(1, id);
      rs = ps.executeQuery();
      if (rs.next()) {
        courseTextbook = extractCourseTextbookFromResultSet(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(rs);
      DBUtil.close(ps);
      DBUtil.close(connection);
    }
    return courseTextbook;
  }

  /**
   * @Description 根据课程名称加载课程教材关联
   * @param courseName 课程名称
   * @return 课程教材关联对象列表
   */
  @Override
  public List<CourseTextbook> loadByCourseName(String courseName) {
    Connection connection = DBUtil.getConnection();
    String sql = "SELECT * FROM course_textbook WHERE course_name LIKE ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<CourseTextbook> courseTextbooks = new ArrayList<>();
    try {
      ps = connection.prepareStatement(sql);
      ps.setString(1, "%" + courseName + "%");
      rs = ps.executeQuery();
      while (rs.next()) {
        courseTextbooks.add(extractCourseTextbookFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(rs);
      DBUtil.close(ps);
      DBUtil.close(connection);
    }
    return courseTextbooks;
  }

  /**
   * @Description 根据教材名称加载课程教材关联
   * @param textbookName 教材名称
   * @return 课程教材关联对象列表
   */
  @Override
  public List<CourseTextbook> loadByTextbookName(String textbookName) {
    Connection connection = DBUtil.getConnection();
    String sql = "SELECT * FROM course_textbook WHERE textbook_name LIKE ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<CourseTextbook> courseTextbooks = new ArrayList<>();
    try {
      ps = connection.prepareStatement(sql);
      ps.setString(1, "%" + textbookName + "%");
      rs = ps.executeQuery();
      while (rs.next()) {
        courseTextbooks.add(extractCourseTextbookFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(rs);
      DBUtil.close(ps);
      DBUtil.close(connection);
    }
    return courseTextbooks;
  }

  /**
   * @Description 根据ISBN加载课程教材关联
   * @param isbn ISBN
   * @return 课程教材关联对象列表
   */
  @Override
  public List<CourseTextbook> loadBySemester(int semester) {
    Connection connection = DBUtil.getConnection();
    String sql = "SELECT * FROM course_textbook WHERE semester = ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<CourseTextbook> courseTextbooks = new ArrayList<>();
    try {
      ps = connection.prepareStatement(sql);
      ps.setInt(1, semester);
      rs = ps.executeQuery();
      while (rs.next()) {
        courseTextbooks.add(extractCourseTextbookFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(rs);
      DBUtil.close(ps);
      DBUtil.close(connection);
    }
    return courseTextbooks;
  }

  /**
   * @Description 根据专业加载课程教材关联
   * @param major 专业
   * @return 课程教材关联对象列表
   */
  @Override
  public List<CourseTextbook> loadByMajor(String major) {
    Connection connection = DBUtil.getConnection();
    String sql = "SELECT * FROM course_textbook WHERE major LIKE ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<CourseTextbook> courseTextbooks = new ArrayList<>();
    try {
      ps = connection.prepareStatement(sql);
      ps.setString(1, "%" + major + "%");
      rs = ps.executeQuery();
      while (rs.next()) {
        courseTextbooks.add(extractCourseTextbookFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(rs);
      DBUtil.close(ps);
      DBUtil.close(connection);
    }
    return courseTextbooks;
  }

  /**
   * @Description 根据学期和专业加载课程教材关联
   * @param semester 学期
   * @param major    专业
   * @return 课程教材关联对象列表
   */
  @Override
  public List<CourseTextbook> loadBySemesterAndMajor(int semester, String major) {
    Connection connection = DBUtil.getConnection();
    String sql = "SELECT * FROM course_textbook WHERE semester = ? AND major LIKE ?";
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<CourseTextbook> courseTextbooks = new ArrayList<>();
    try {
      ps = connection.prepareStatement(sql);
      ps.setInt(1, semester);
      ps.setString(2, "%" + major + "%");
      rs = ps.executeQuery();
      while (rs.next()) {
        courseTextbooks.add(extractCourseTextbookFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(rs);
      DBUtil.close(ps);
      DBUtil.close(connection);
    }
    return courseTextbooks;
  }

  /**
   * @Description 加载所有课程教材关联
   * @return 课程教材关联对象列表
   */
  @Override
  public List<CourseTextbook> loadAll() {
    Connection connection = DBUtil.getConnection();
    String sql = "SELECT * FROM course_textbook";
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<CourseTextbook> courseTextbooks = new ArrayList<>();
    try {
      ps = connection.prepareStatement(sql);
      rs = ps.executeQuery();
      while (rs.next()) {
        courseTextbooks.add(extractCourseTextbookFromResultSet(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(rs);
      DBUtil.close(ps);
      DBUtil.close(connection);
    }
    return courseTextbooks;
  }

  /**
   * @Description 从ResultSet中提取课程教材关联对象
   * @param rs ResultSet对象
   * @return 课程教材关联对象
   */
  private CourseTextbook extractCourseTextbookFromResultSet(ResultSet rs) throws SQLException {
    CourseTextbook courseTextbook = new CourseTextbook();
    courseTextbook.setId(rs.getInt("id"));
    courseTextbook.setCourseName(rs.getString("course_name"));
    courseTextbook.setTextbookName(rs.getString("textbook_name"));
    courseTextbook.setIsbn(rs.getString("isbn"));
    courseTextbook.setPublisher(rs.getString("publisher"));
    courseTextbook.setAuthor(rs.getString("author"));
    courseTextbook.setSemester(rs.getInt("semester"));
    courseTextbook.setMajor(rs.getString("major"));
    courseTextbook.setRecommendLevel(rs.getInt("recommend_level"));
    courseTextbook.setRequired(rs.getBoolean("is_required"));
    return courseTextbook;
  }
}