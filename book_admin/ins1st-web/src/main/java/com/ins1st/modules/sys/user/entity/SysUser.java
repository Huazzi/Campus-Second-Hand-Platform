package com.ins1st.modules.sys.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ins1st.core.Page;

import java.io.Serializable;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author sun
 * @since 2019-05-09
 */
public class SysUser extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String userNick;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户电话
     */
    private String userMobile;

    /**
     * 用户性别
     */
    private String userSex;

    /**
     * 用户角色
     */
    private String roleIds;

    /**
     * 用户状态（1正常2冻结）
     */
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", userName=" + userName +
                ", userNick=" + userNick +
                ", userPwd=" + userPwd +
                ", userEmail=" + userEmail +
                ", userMobile=" + userMobile +
                ", userSex=" + userSex +
                ", roleIds=" + roleIds +
                ", status=" + status +
                "}";
    }
}
