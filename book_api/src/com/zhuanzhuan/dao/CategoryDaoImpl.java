package com.zhuanzhuan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zhuanzhuan.model.Category;
import com.zhuanzhuan.util.DBUtil;

/**
 * 商品分类数据访问实现类
 */
public class CategoryDaoImpl implements ICategoryDao {

    @Override
    public Category findById(int id) {
        Connection connection = DBUtil.getConnection();
        String sql = "SELECT * FROM categories WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Category category = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setParentId(rs.getInt("parent_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(connection);
        }
        return category;
    }

    @Override
    public Category findByName(String name) {
        Connection connection = DBUtil.getConnection();
        String sql = "SELECT * FROM categories WHERE name = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Category category = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            if (rs.next()) {
                category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setParentId(rs.getInt("parent_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(connection);
        }
        return category;
    }

    @Override
    public List<Category> findAll() {
        Connection connection = DBUtil.getConnection();
        String sql = "SELECT * FROM categories ORDER BY parent_id, id";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Category> categories = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setParentId(rs.getInt("parent_id"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(connection);
        }
        return categories;
    }

    @Override
    public List<Category> findByParentId(int parentId) {
        Connection connection = DBUtil.getConnection();
        String sql = "SELECT * FROM categories WHERE parent_id = ? ORDER BY id";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Category> categories = new ArrayList<>();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, parentId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setParentId(rs.getInt("parent_id"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(connection);
        }
        return categories;
    }

    @Override
    public int add(Category category) {
        Connection connection = DBUtil.getConnection();
        String sql = "INSERT INTO categories (name, parent_id) VALUES (?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        int id = 0;
        try {
            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getName());
            ps.setInt(2, category.getParentId());
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

    @Override
    public void update(Category category) {
        Connection connection = DBUtil.getConnection();
        String sql = "UPDATE categories SET name = ?, parent_id = ? WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, category.getName());
            ps.setInt(2, category.getParentId());
            ps.setInt(3, category.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps);
            DBUtil.close(connection);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = DBUtil.getConnection();
        String sql = "DELETE FROM categories WHERE id = ?";
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
}