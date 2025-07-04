package com.ins1st.modules.shop.users.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ins1st.core.R;
import com.ins1st.util.ControllerUtils;
import com.ins1st.annotation.NeedAuth;
import com.ins1st.annotation.Req4Json;
import com.ins1st.annotation.Req4Model;
import com.ins1st.modules.shop.users.service.IUsersService;
import com.ins1st.modules.shop.users.entity.Users;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ins1st
 * @since 2020-02-21
 */
@Controller
@RequestMapping("/users/users")
public class UsersController {

    private static final String PREFIX = "modules/shop/users/";

    @Autowired
    private IUsersService usersService;

    /**
    * 主页
    * @param model
    * @return
    */
    @Req4Model(value="/index")
    @NeedAuth(value = "shop:users:index")
    public String index(Model model){
      return PREFIX + "users_index.html";
    }

    /**
    * 查询集合
    * @param request
    * @return
    */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception{
        Users users = ControllerUtils.bindParams(request, Users.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(users);
        IPage<Users> page = usersService.page(new Page<>(users.getPage(), users.getLimit()), queryWrapper);
        return R.return4Page(page);
    }

    /**
    * 添加页
    * @param model
    * @return
    */
    @NeedAuth(value = "shop:users:add")
    @Req4Model(value="/add")
    public String add(Model model){
        return PREFIX + "users_add.html";
    }

    /**
    * 保存
    *
    * @param users
    * @return
    */
    @Req4Json(value = "/save",title="保存",parameters="id")
    public Object save(Users users) {
        boolean save = usersService.save(users);
        if (save) {
            return R.success("保存成功");
        }
        return R.error("保存失败");
    }

    /**
    * 修改页
    * @param model
    * @return
    */
    @Req4Model(value="/edit")
    @NeedAuth(value = "shop:users:edit")
    public String edit(Model model,Integer id){
        Users users = usersService.getById(id);
        model.addAttribute("users",users);
        return PREFIX + "users_edit.html";
    }

    /**
    * 更新
    *
    * @param users
    * @return
    */
    @Req4Json(value = "/update" , title="更新",parameters="id")
    public Object update(Users users) {
        boolean update = usersService.updateById(users);
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
    @Req4Json(value = "/del",title="删除",parameters="id")
    @NeedAuth(value = "shop:users:del")
    public Object del(Integer id) {
        boolean del = usersService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
