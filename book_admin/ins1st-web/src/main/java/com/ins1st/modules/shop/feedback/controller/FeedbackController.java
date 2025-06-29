package com.ins1st.modules.shop.feedback.controller;


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
import com.ins1st.modules.shop.feedback.service.IFeedbackService;
import com.ins1st.modules.shop.feedback.entity.Feedback;
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
@RequestMapping("/feedback/feedback")
public class FeedbackController {

    private static final String PREFIX = "modules/shop/feedback/";

    @Autowired
    private IFeedbackService feedbackService;

    /**
    * 主页
    * @param model
    * @return
    */
    @Req4Model(value="/index")
    @NeedAuth(value = "shop:feedback:index")
    public String index(Model model){
      return PREFIX + "feedback_index.html";
    }

    /**
    * 查询集合
    * @param request
    * @return
    */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception{
        Feedback feedback = ControllerUtils.bindParams(request, Feedback.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(feedback);
        IPage<Feedback> page = feedbackService.page(new Page<>(feedback.getPage(), feedback.getLimit()), queryWrapper);
        return R.return4Page(page);
    }

    /**
    * 添加页
    * @param model
    * @return
    */
    @NeedAuth(value = "shop:feedback:add")
    @Req4Model(value="/add")
    public String add(Model model){
        return PREFIX + "feedback_add.html";
    }

    /**
    * 保存
    *
    * @param feedback
    * @return
    */
    @Req4Json(value = "/save",title="保存",parameters="id")
    public Object save(Feedback feedback) {
        boolean save = feedbackService.save(feedback);
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
    @NeedAuth(value = "shop:feedback:edit")
    public String edit(Model model,Integer id){
        Feedback feedback = feedbackService.getById(id);
        model.addAttribute("feedback",feedback);
        return PREFIX + "feedback_edit.html";
    }

    /**
    * 更新
    *
    * @param feedback
    * @return
    */
    @Req4Json(value = "/update" , title="更新",parameters="id")
    public Object update(Feedback feedback) {
        boolean update = feedbackService.updateById(feedback);
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
    @NeedAuth(value = "shop:feedback:del")
    public Object del(Integer id) {
        boolean del = feedbackService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
