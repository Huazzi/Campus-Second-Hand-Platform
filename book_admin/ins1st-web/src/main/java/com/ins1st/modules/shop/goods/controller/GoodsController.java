package com.ins1st.modules.shop.goods.controller;


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
import com.ins1st.modules.shop.goods.service.IGoodsService;
import com.ins1st.modules.shop.goods.entity.Goods;
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
@RequestMapping("/goods/goods")
public class GoodsController {

    private static final String PREFIX = "modules/shop/goods/";

    @Autowired
    private IGoodsService goodsService;

    /**
    * 主页
    * @param model
    * @return
    */
    @Req4Model(value="/index")
    @NeedAuth(value = "shop:goods:index")
    public String index(Model model){
      return PREFIX + "goods_index.html";
    }

    /**
    * 查询集合
    * @param request
    * @return
    */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception{
        Goods goods = ControllerUtils.bindParams(request, Goods.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(goods);
        IPage<Goods> page = goodsService.page(new Page<>(goods.getPage(), goods.getLimit()), queryWrapper);
        return R.return4Page(page);
    }

    /**
    * 添加页
    * @param model
    * @return
    */
    @NeedAuth(value = "shop:goods:add")
    @Req4Model(value="/add")
    public String add(Model model){
        return PREFIX + "goods_add.html";
    }

    /**
    * 保存
    *
    * @param goods
    * @return
    */
    @Req4Json(value = "/save",title="保存",parameters="id")
    public Object save(Goods goods) {
        boolean save = goodsService.save(goods);
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
    @NeedAuth(value = "shop:goods:edit")
    public String edit(Model model,Integer id){
        Goods goods = goodsService.getById(id);
        model.addAttribute("goods",goods);
        return PREFIX + "goods_edit.html";
    }

    /**
    * 更新
    *
    * @param goods
    * @return
    */
    @Req4Json(value = "/update" , title="更新",parameters="id")
    public Object update(Goods goods) {
        boolean update = goodsService.updateById(goods);
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
    @NeedAuth(value = "shop:goods:del")
    public Object del(Integer id) {
        boolean del = goodsService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
