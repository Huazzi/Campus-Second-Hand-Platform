package ${package.Controller};


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
import com.ins1st.modules.${cfg.moduleName}.${cfg.bizName}.service.I${cfg.entity}Service;
import ${package.Entity}.${entity};
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    private static final String PREFIX = "modules/${cfg.moduleName}/${cfg.bizName}/";

    @Autowired
    private I${cfg.entity}Service ${cfg.entityP}Service;

    /**
    * 主页
    * @param model
    * @return
    */
    @Req4Model(value="/index")
    @NeedAuth(value = "${cfg.moduleName}:${cfg.bizName}:index")
    public String index(Model model){
      return PREFIX + "${cfg.bizName}_index.html";
    }

    /**
    * 查询集合
    * @param request
    * @return
    */
    @Req4Json(value = "/queryList")
    public Object queryList(HttpServletRequest request) throws Exception{
        ${cfg.entity} ${cfg.entityP} = ControllerUtils.bindParams(request, ${cfg.entity}.class);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(${cfg.entityP});
        IPage<${cfg.entity}> page = ${cfg.entityP}Service.page(new Page<>(${cfg.entityP}.getPage(), ${cfg.entityP}.getLimit()), queryWrapper);
        return R.return4Page(page);
    }

    /**
    * 添加页
    * @param model
    * @return
    */
    @NeedAuth(value = "${cfg.moduleName}:${cfg.bizName}:add")
    @Req4Model(value="/add")
    public String add(Model model){
        return PREFIX + "${cfg.bizName}_add.html";
    }

    /**
    * 保存
    *
    * @param ${cfg.entityP}
    * @return
    */
    @Req4Json(value = "/save",title="保存${table.comment!}",parameters="id")
    public Object save(${cfg.entity} ${cfg.entityP}) {
        boolean save = ${cfg.entityP}Service.save(${cfg.entityP});
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
    @NeedAuth(value = "${cfg.moduleName}:${cfg.bizName}:edit")
    public String edit(Model model,Integer id){
        ${cfg.entity} ${cfg.entityP} = ${cfg.entityP}Service.getById(id);
        model.addAttribute("${cfg.entityP}",${cfg.entityP});
        return PREFIX + "${cfg.bizName}_edit.html";
    }

    /**
    * 更新
    *
    * @param ${cfg.entityP}
    * @return
    */
    @Req4Json(value = "/update" , title="更新${table.comment!}",parameters="id")
    public Object update(${cfg.entity} ${cfg.entityP}) {
        boolean update = ${cfg.entityP}Service.updateById(${cfg.entityP});
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
    @Req4Json(value = "/del",title="删除${table.comment!}",parameters="id")
    @NeedAuth(value = "${cfg.moduleName}:${cfg.bizName}:del")
    public Object del(Integer id) {
        boolean del = ${cfg.entityP}Service.removeById(id);
        if (del) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

}
</#if>
