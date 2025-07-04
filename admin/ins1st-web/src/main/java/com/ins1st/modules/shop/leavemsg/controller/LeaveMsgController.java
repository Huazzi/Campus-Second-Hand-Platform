package com.ins1st.modules.shop.leavemsg.controller;


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
import com.ins1st.modules.shop.leavemsg.service.ILeaveMsgService;
import com.ins1st.modules.shop.leavemsg.entity.LeaveMsg;
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
@RequestMapping("/leavemsg/leaveMsg")
public class LeaveMsgController {

    private static final String PREFIX = "modules/shop/leavemsg/";

    @Autowired
    private ILeaveMsgService leaveMsgService;

    /**
    * 主页
    * @param model
    * @return
    */
    @Req4Model(value="/index")
    @NeedAuth(value = "shop:leavemsg:index")
    public String index(Model model){
      return PREFIX + "leavemsg_index.html";
    }

    /**
    * 查询集合
    * @param request
    * @return
    */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception{
        LeaveMsg leaveMsg = ControllerUtils.bindParams(request, LeaveMsg.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(leaveMsg);
        IPage<LeaveMsg> page = leaveMsgService.page(new Page<>(leaveMsg.getPage(), leaveMsg.getLimit()), queryWrapper);
        return R.return4Page(page);
    }

    /**
    * 添加页
    * @param model
    * @return
    */
    @NeedAuth(value = "shop:leavemsg:add")
    @Req4Model(value="/add")
    public String add(Model model){
        return PREFIX + "leavemsg_add.html";
    }

    /**
    * 保存
    *
    * @param leaveMsg
    * @return
    */
    @Req4Json(value = "/save",title="保存",parameters="id")
    public Object save(LeaveMsg leaveMsg) {
        boolean save = leaveMsgService.save(leaveMsg);
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
    @NeedAuth(value = "shop:leavemsg:edit")
    public String edit(Model model,Integer id){
        LeaveMsg leaveMsg = leaveMsgService.getById(id);
        model.addAttribute("leaveMsg",leaveMsg);
        return PREFIX + "leavemsg_edit.html";
    }

    /**
    * 更新
    *
    * @param leaveMsg
    * @return
    */
    @Req4Json(value = "/update" , title="更新",parameters="id")
    public Object update(LeaveMsg leaveMsg) {
        boolean update = leaveMsgService.updateById(leaveMsg);
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
    @NeedAuth(value = "shop:leavemsg:del")
    public Object del(Integer id) {
        boolean del = leaveMsgService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
