package com.erHuo.dao;

import java.util.List;

import com.erHuo.model.Good;

public interface IGoodDao {
	/**
	 * 添加商品
	 * 
	 * @param good
	 * @return 返回添加的商品号
	 */
	public int add(Good good);

	/**
	 * 通过商品id号获取具体的商品信息
	 * 
	 * @param id
	 * @return
	 */
	public Good findById(int id);

	/**
	 * 通过页数获取具体页的商品
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Good> findByPage(int page, int size);

	/**
	 * 根据商品名获取商品
	 * 
	 * @param goodname 模糊搜索的商品名
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Good> findByName(String goodname, int page, int size);

	/**
	 * 通过用户id返回用户拥有的商品信息
	 * 
	 * @param userid
	 * @return
	 */
	public List<Good> findByUserId(int userid);

	/**
	 * 获取指定数量的随机商品
	 * 
	 * @param limit 返回数量
	 * @return 随机商品列表
	 */
	public List<Good> findRandomGoods(int limit);

	/**
	 * 获取热门商品
	 * 
	 * @param categoryId 类别ID（可选）
	 * @param limit      数量
	 * @return 热门商品列表
	 */
	public List<Good> findPopularGoods(Integer categoryId, int limit);

	/**
	 * 获取最新上架的商品
	 * 
	 * @param days  最近几天
	 * @param limit 数量
	 * @return 最新商品列表
	 */
	public List<Good> findNewArrivals(int days, int limit);

	public void delete(Good good);

	public void delete(int id);

	public void update(Good good);

	public List<Good> load(String goodname);

	public List<Good> loadByCategoryId(String goodname, int categoryId);

	public List<Good> loadWithCondition(String goodname, int categoryId, double minPrice, double maxPrice, int start,
			int end);

	public List<Good> loadByUser(int userId);

	public Good loadById(int id);

	public Good load(int id);

	public int collect(int userId, int goodId);

	public int unCollect(int userId, int goodId);

	public boolean isCollect(int userId, int goodId);

	public List<Good> loadByCollectUser(int userId);

	// 为推荐系统新增
	public void incrementViewCount(int goodId);

	public void incrementInquiryCount(int goodId);
}
