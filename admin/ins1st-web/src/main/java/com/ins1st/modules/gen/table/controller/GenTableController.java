package com.ins1st.modules.gen.table.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ins1st.annotation.NeedAuth;
import com.ins1st.annotation.Req4Json;
import com.ins1st.annotation.Req4Model;
import com.ins1st.core.R;
import com.ins1st.engine.GenEngine;
import com.ins1st.model.ColumnEntity;
import com.ins1st.model.GenTable;
import com.ins1st.modules.gen.table.service.IGenTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成表 前端控制器
 * </p>
 *
 * @author sun
 * @since 2019-05-16
 */
@Controller
@RequestMapping("/table/genTable")
public class GenTableController {

    private static final String PREFIX = "modules/gen/table/";

    @Value("${spring.datasource.db-name}")
    private String dbName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    private IGenTableService genTableService;

    /**
     * 主页
     *
     * @param model
     * @return
     */
    @Req4Model(value = "/index")
    @NeedAuth(value = "gen:table:index")
    public String index(Model model) {
        return PREFIX + "table_index.html";
    }

    /**
     * 查询集合
     *
     * @param genTable
     * @return
     */
    @Req4Json(value = "/queryList")
    public Object queryList(GenTable genTable) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(genTable);
        IPage<GenTable> page = genTableService.page(new Page<>(genTable.getPage(), genTable.getLimit()), queryWrapper);
        return R.return4Page(page);
    }

    /**
     * 添加页
     *
     * @param model
     * @return
     */
    @NeedAuth(value = "gen:table:add")
    @Req4Model(value = "/add")
    public String add(Model model) {
        model.addAttribute("tables", genTableService.getTables(dbName));
        return PREFIX + "table_add.html";
    }

    /**
     * 保存
     *
     * @param genTable
     * @return
     */
    @Req4Json(value = "/save", title = "保存代码生成表", parameters = "id")
    public Object save(GenTable genTable) {
        genTable.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        boolean save = genTableService.save(genTable);
        if (save) {
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
    @Req4Json(value = "/del", title = "删除代码生成表", parameters = "id")
    @NeedAuth(value = "gen:table:del")
    public Object del(Integer id) {
        boolean del = genTableService.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }


    /**
     * 配置字段页面
     *
     * @param model
     * @param genTable
     * @return
     */
    @Req4Model(value = "/toGen")
    public String toGen(Model model, GenTable genTable) {
        List<Map<String, Object>> columns = genTableService.queryColumns4Table(genTable);
        genTable = genTableService.getById(genTable.getId());
        model.addAttribute("columns", columns);
        model.addAttribute("genTable", genTable);
        return PREFIX + "toGen.html";
    }

    @Req4Json(value = "/start")
    public Object start(String arr, String tableId) {
        GenEngine genEngine = new GenEngine();
        GenTable genTable = genTableService.getById(tableId);
        genTable.setUrl(url);
        genTable.setUsername(username);
        genTable.setPassword(password);
        return genEngine.start(genTable, JSONArray.parseArray(arr).toJavaList(ColumnEntity.class));
    }
}
