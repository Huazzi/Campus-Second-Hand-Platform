package com.ins1st.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ins1st.core.Page;

/**
 * <p>
 * 代码生成表
 * </p>
 *
 * @author sun
 * @since 2019-05-16
 */
public class GenTable extends Page {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据表名
     */
    private String tableName;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 业务名
     */
    private String bizName;

    /**
     * 创建时间
     */
    private String createTime;

    @TableField(exist = false)
    private String url;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "GenTable{" +
                "id=" + id +
                ", tableName=" + tableName +
                ", moduleName=" + moduleName +
                ", bizName=" + bizName +
                ", createTime=" + createTime +
                "}";
    }
}
