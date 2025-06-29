package com.ins1st.modules.sys.dict.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ins1st.annotation.NeedAuth;
import com.ins1st.annotation.Req4Json;
import com.ins1st.annotation.Req4Model;
import com.ins1st.core.R;
import com.ins1st.modules.sys.dict.entity.SysDict;
import com.ins1st.modules.sys.dict.service.ISysDictService;
import com.ins1st.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author sun
 * @since 2019-05-14
 */
@Controller
@RequestMapping("/sys/sysDict")
public class SysDictController {

    private static final String PREFIX = "modules/sys/dict/";

    @Autowired
    private ISysDictService sysDictService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/index")
    @NeedAuth(value = "sys:dict:index")
    public String index(Model model) {
        return PREFIX + "dict_index.html";
    }

    /**
     * 查询集合
     *
     * @param request
     * @return
     */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception {
        SysDict sysDict = ControllerUtils.bindParams(request, SysDict.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(sysDict);
        IPage<SysDict> page = sysDictService.page(new Page<>(sysDict.getPage(), sysDict.getLimit()), queryWrapper);
        return R.return4Page(page);
    }

    /**
     * 添加页
     *
     * @param model
     * @return
     */
    @NeedAuth(value = "sys:dict:add")
    @Req4Model(value = "/add")
    public String add(Model model) {
        return PREFIX + "dict_add.html";
    }

    /**
     * 保存
     *
     * @param sysDict
     * @return
     */
    @Req4Json(value = "/save", title = "保存字典表", parameters = "id")
    public Object save(SysDict sysDict) {
        boolean save = sysDictService.save(sysDict);
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
    @Req4Model(value = "/edit")
    @NeedAuth(value = "sys:dict:edit")
    public String edit(Model model, Integer id) {
        SysDict sysDict = sysDictService.getById(id);
        model.addAttribute("sysDict", sysDict);
        return PREFIX + "dict_edit.html";
    }

    /**
     * 更新
     *
     * @param sysDict
     * @return
     */
    @Req4Json(value = "/update", title = "更新字典表", parameters = "id")
    public Object update(SysDict sysDict) {
        boolean update = sysDictService.updateById(sysDict);
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
    @Req4Json(value = "/del", title = "删除字典表", parameters = "id")
    public Object del(Integer id) {
        boolean del = sysDictService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
