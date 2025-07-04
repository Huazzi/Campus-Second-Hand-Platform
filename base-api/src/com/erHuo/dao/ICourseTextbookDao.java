package com.erHuo.dao;

import java.util.List;

import com.erHuo.model.CourseTextbook;

/**
 * @Description: 课程与教材的对应关系DAO接口
 */
public interface ICourseTextbookDao {
  public int add(CourseTextbook courseTextbook);

  public void delete(int id);

  public void update(CourseTextbook courseTextbook);

  public CourseTextbook loadById(int id);

  public List<CourseTextbook> loadByCourseName(String courseName);

  public List<CourseTextbook> loadByTextbookName(String textbookName);

  public List<CourseTextbook> loadBySemester(int semester);

  public List<CourseTextbook> loadByMajor(String major);

  public List<CourseTextbook> loadBySemesterAndMajor(int semester, String major);

  public List<CourseTextbook> loadAll();
}