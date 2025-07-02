package com.zhuanzhuan.dao;

import java.util.List;
import com.zhuanzhuan.model.AIChat;

/**
 * AI聊天数据访问接口
 */
public interface IAIChatDao {
  /**
   * 添加AI聊天记录
   * 
   * @param aiChat
   */
  void add(AIChat aiChat);

  /**
   * 根据用户ID加载聊天记录
   * 
   * @param userId
   * @return 聊天记录列表
   */
  List<AIChat> loadByUserId(int userId);

  /**
   * 清除用户的聊天记录
   * 
   * @param userId
   */
  void deleteByUserId(int userId);
}