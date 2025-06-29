package com.zhuanzhuan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.zhuanzhuan.model.AIChat;
import com.zhuanzhuan.util.DBUtil;

/**
 * AI聊天数据访问实现类
 */
public class AIChatDaoImpl implements IAIChatDao {

  /**
   * 添加AI聊天记录
   * 
   * @param aiChat AI聊天记录对象
   */
  @Override
  public void add(AIChat aiChat) {
    Connection connection = DBUtil.getConnection();
    PreparedStatement preparedStatement = null;
    try {
      String sql = "INSERT INTO ai_chat(user_id, question, answer, time, is_ai) VALUES(?, ?, ?, ?, ?)";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, aiChat.getUserId());
      preparedStatement.setString(2, aiChat.getQuestion());
      preparedStatement.setString(3, aiChat.getAnswer());
      preparedStatement.setTimestamp(4, aiChat.getTime());
      preparedStatement.setBoolean(5, aiChat.isAI());
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(preparedStatement);
      DBUtil.close(connection);
    }
  }

  /**
   * 根据用户ID加载聊天记录
   * 
   * @param userId 用户ID
   * @return 聊天记录列表
   */
  @Override
  public List<AIChat> loadByUserId(int userId) {
    List<AIChat> chats = new ArrayList<>();
    Connection connection = DBUtil.getConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      String sql = "SELECT * FROM ai_chat WHERE user_id = ? ORDER BY time ASC";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, userId);
      resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        AIChat chat = new AIChat();
        chat.setId(resultSet.getInt("id"));
        chat.setUserId(resultSet.getInt("user_id"));
        chat.setQuestion(resultSet.getString("question"));
        chat.setAnswer(resultSet.getString("answer"));
        chat.setTime(resultSet.getTimestamp("time"));
        chat.setAI(resultSet.getBoolean("is_ai"));

        chats.add(chat);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(resultSet);
      DBUtil.close(preparedStatement);
      DBUtil.close(connection);
    }

    return chats;
  }

  /**
   * 根据用户ID删除聊天记录
   * 
   * @param userId 用户ID
   */
  @Override
  public void deleteByUserId(int userId) {
    Connection connection = DBUtil.getConnection();
    PreparedStatement preparedStatement = null;

    try {
      String sql = "DELETE FROM ai_chat WHERE user_id = ?";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, userId);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DBUtil.close(preparedStatement);
      DBUtil.close(connection);
    }
  }
}