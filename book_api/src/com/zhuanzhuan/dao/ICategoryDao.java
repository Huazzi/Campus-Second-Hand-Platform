package com.zhuanzhuan.dao;

import java.util.List;

import com.zhuanzhuan.model.Category;

/**
 * 商品分类数据访问接口
 */
public interface ICategoryDao {

    /**
     * 根据ID查询分类
     * 
     * @param id 分类ID
     * @return 分类对象
     */
    Category findById(int id);

    /**
     * 根据名称查询分类
     * 
     * @param name 分类名称
     * @return 分类对象
     */
    Category findByName(String name);

    /**
     * 获取所有分类
     * 
     * @return 分类列表
     */
    List<Category> findAll();

    /**
     * 根据父ID查询子分类
     * 
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<Category> findByParentId(int parentId);

    /**
     * 添加分类
     * 
     * @param category 分类对象
     * @return 新增分类的ID
     */
    int add(Category category);

    /**
     * 更新分类
     * 
     * @param category 分类对象
     */
    void update(Category category);

    /**
     * 删除分类
     * 
     * @param id 分类ID
     */
    void delete(int id);
}