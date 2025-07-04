package com.ins1st.modules.sys.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ins1st.annotation.NeedAuth;
import com.ins1st.contants.RoleEnum;
import com.ins1st.contants.UserSexEnum;
import com.ins1st.contants.UserStatusEnum;
import com.ins1st.core.R;
import com.ins1st.modules.sys.role.entity.SysUserRole;
import com.ins1st.modules.sys.role.service.ISysRoleService;
import com.ins1st.modules.sys.role.service.ISysUserRoleService;
import com.ins1st.modules.sys.user.entity.SysUser;
import com.ins1st.modules.sys.user.service.ISysUserService;
import com.ins1st.util.ControllerUtils;
import com.ins1st.util.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author sun
 * @since 2019-05-09
 */
@Controller
@RequestMapping("/sys/sysUser")
public class SysUserController {

    private static final String PREFIX = "modules/sys/user/";

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/index")
    @NeedAuth(value = "sys:user:index")
    public String index(Model model) {
        model.addAttribute("status", UserStatusEnum.values());
        model.addAttribute("sex", UserSexEnum.values());
        return PREFIX + "user_index.html";
    }

    /**
     * 查询集合
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryList")
    @ResponseBody
    public Object queryList(HttpServletRequest request) throws Exception {
        SysUser sysUser = ControllerUtils.bindParams(request, SysUser.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(sysUser);
        IPage<SysUser> page = sysUserService.page(new Page<>(sysUser.getPage(), sysUser.getLimit()), queryWrapper);
        return R.return4Page(page);
    }

    /**
     * 添加页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    @NeedAuth(value = "sys:user:add")
    public String add(Model model) {
        model.addAttribute("status", UserStatusEnum.values());
        model.addAttribute("sex", UserSexEnum.values());
        QueryWrapper role = new QueryWrapper();
        role.eq("status", RoleEnum.ENABLE.getCode());
        model.addAttribute("roles", sysRoleService.list(role));
        return PREFIX + "user_add.html";
    }

    /**
     * 保存
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(SysUser sysUser) {
        sysUser.setUserPwd(CryptoUtils.generate(sysUser.getUserPwd()));
        boolean save = sysUserService.save(sysUser);
        handleUserRole(sysUser);
        if (save) {
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }

    /**
     * 修改页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit")
    @NeedAuth(value = "sys:user:edit")
    public String edit(Model model, Integer id) {
        SysUser sysUser = sysUserService.getById(id);
        model.addAttribute("sysUser", sysUser);
        model.addAttribute("roles", sysUserService.getRole4User(id));
        model.addAttribute("status", UserStatusEnum.values());
        model.addAttribute("sex", UserSexEnum.values());
        return PREFIX + "user_edit.html";
    }

    /**
     * 更新
     *
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SysUser sysUser) throws Exception {
        sysUser.setUserPwd(CryptoUtils.generate(sysUser.getUserPwd()));
        boolean update = sysUserService.updateById(sysUser);
        handleUserRole(sysUser);
        if (update) {
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/del")
    @ResponseBody
    public Object del(Integer id) {
        boolean del = sysUserService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    private void handleUserRole(SysUser sysUser) {
        QueryWrapper delUserRole = new QueryWrapper();
        delUserRole.eq("user_id", sysUser.getId());
        sysUserRoleService.remove(delUserRole);
        for (String roleId : sysUser.getRoleIds().split(",")) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(Integer.valueOf(roleId));
            sysUserRole.setUserId(sysUser.getId());
            sysUserRoleService.save(sysUserRole);
        }
    }

}
