package com.ins1st.modules.shop.message.controller;


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
import com.ins1st.modules.shop.message.service.IMessageService;
import com.ins1st.modules.shop.message.entity.Message;
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
@RequestMapping("/message/message")
public class MessageController {

    private static final String PREFIX = "modules/shop/message/";

    @Autowired
    private IMessageService messageService;

    /**
    * 主页
    * @param model
    * @return
    */
    @Req4Model(value="/index")
    @NeedAuth(value = "shop:message:index")
    public String index(Model model){
      return PREFIX + "message_index.html";
    }

    /**
    * 查询集合
    * @param request
    * @return
    */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception{
        Message message = ControllerUtils.bindParams(request, Message.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(message);
        IPage<Message> page = messageService.page(new Page<>(message.getPage(), message.getLimit()), queryWrapper);
        return R.return4Page(page);
    }

    /**
    * 添加页
    * @param model
    * @return
    */
    @NeedAuth(value = "shop:message:add")
    @Req4Model(value="/add")
    public String add(Model model){
        return PREFIX + "message_add.html";
    }

    /**
    * 保存
    *
    * @param message
    * @return
    */
    @Req4Json(value = "/save",title="保存",parameters="id")
    public Object save(Message message) {
        boolean save = messageService.save(message);
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
    @NeedAuth(value = "shop:message:edit")
    public String edit(Model model,Integer id){
        Message message = messageService.getById(id);
        model.addAttribute("message",message);
        return PREFIX + "message_edit.html";
    }

    /**
    * 更新
    *
    * @param message
    * @return
    */
    @Req4Json(value = "/update" , title="更新",parameters="id")
    public Object update(Message message) {
        boolean update = messageService.updateById(message);
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
    @NeedAuth(value = "shop:message:del")
    public Object del(Integer id) {
        boolean del = messageService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
