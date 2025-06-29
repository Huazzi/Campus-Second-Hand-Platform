package com.ins1st.engine;


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.ins1st.constant.GenTypeEnum;
import com.ins1st.contants.ExceptionEnum;
import com.ins1st.core.Page;
import com.ins1st.core.R;
import com.ins1st.exception.GenException;
import com.ins1st.model.ColumnEntity;
import com.ins1st.model.GenTable;
import com.ins1st.util.ColumnUtil;
import com.ins1st.util.FileUtil;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenEngine {

    private static final Logger log = LoggerFactory.getLogger(GenEngine.class);
    //private static final String ROOT_PATH = System.getProperty("user.dir");
   // private static final String TEMPLATE_PATH = System.getProperty("user.dir") + "/ins1st-generator/src/main/resources/templates";

   
    private static final String ROOT_PATH ="F:/计算机毕业设计202002/口腔小程序/ins1st";
    private static final String TEMPLATE_PATH = "F:/计算机毕业设计202002/口腔小程序/ins1st" + "/ins1st-generator/src/main/resources/templates";
    
    private static GroupTemplate groupTemplate;

    static {
        groupTemplate = initBeetlGroupTemplate();
    }

    public R start(GenTable genTable, List<ColumnEntity> genEntities) {
        log.info("初始化获取表: {}", genTable.getTableName());
        log.info("模块名: {}", genTable.getModuleName());
        log.info("业务名: {}", genTable.getBizName());
        try {
            Map<String, Object> map = init(genTable, genEntities);
            //mybatis-plus 生成
            genMybatisPlusCode(map);
            //生成前端文件夹
            createFront((String) map.get("moduleName"), (String) map.get("bizName"));
            //生成index_html
            createFrontCode(map, "index", "html");
            createFrontCode(map, "index", "js");
            createFrontCode(map, "add", GenTypeEnum.HTML.getName());
            createFrontCode(map, "add", GenTypeEnum.JS.getName());
            createFrontCode(map, "edit", GenTypeEnum.HTML.getName());
            createFrontCode(map, "edit", GenTypeEnum.JS.getName());
        } catch (Exception e) {
            throw new GenException(ExceptionEnum.GEN_ERROR);
        }

        return R.success("代码生成成功");
    }


    private Map<String, Object> init(GenTable genTable, List<ColumnEntity> genEntities) {
        genEntities.forEach(item -> {
            item.setCamel(ColumnUtil.underline2Camel(item.getName()));
        });
        Map<String, Object> map = new HashMap<>();
        map.put("entity", ColumnUtil.underline2Camel(genTable.getTableName(), false));
        map.put("entityP", ColumnUtil.underline2Camel(genTable.getTableName(), true));
        map.put("moduleName", genTable.getModuleName());
        map.put("bizName", genTable.getBizName());
        map.put("gens", genEntities);
        map.put("tableName", genTable.getTableName());
        map.put("tableNameP", ColumnUtil.underline2Camel(genTable.getTableName(), true));
        map.put("url", genTable.getUrl());
        map.put("username", genTable.getUsername());
        map.put("password", genTable.getPassword());
        return map;
    }

    private boolean genMybatisPlusCode(Map<String, Object> map) {
        AutoGenerator mpg = new AutoGenerator();
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(System.getProperty("user.dir") + "/ins1st-web/src/main/java/");
        gc.setAuthor("ins1st");
        gc.setFileOverride(true);
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                setMap(map);
            }
        };
        mpg.setCfg(cfg);
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl((String) map.get("url"));
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername((String) map.get("username"));
        dsc.setPassword((String) map.get("password"));
        mpg.setDataSource(dsc);
        PackageConfig pc = new PackageConfig();
        pc.setModuleName((String) map.get("bizName"));
        pc.setParent("com.ins1st.modules." + (String) map.get("moduleName"));
        mpg.setPackageInfo(pc);
        TemplateConfig templateConfig = new TemplateConfig();
        mpg.setTemplate(templateConfig);
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude((String) map.get("tableName"));
        strategy.setSuperEntityClass(Page.class);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
        return true;
    }


    /**
     * 创建前端文件夹
     *
     * @param moduleName
     * @param bizName
     */
    private void createFront(String moduleName, String bizName) {
        FileUtil.createDir(ROOT_PATH + "/ins1st-web/src/main/resources/templates/modules/" + moduleName + "/" + bizName);
        FileUtil.createDir(ROOT_PATH + "/ins1st-web/src/main/resources/static/modules/" + moduleName + "/" + bizName);
    }

    /**
     * 初始化beetl
     *
     * @return
     */
    private static GroupTemplate initBeetlGroupTemplate() {
        FileResourceLoader resourceLoader = new FileResourceLoader(TEMPLATE_PATH, "utf-8");
        Configuration cfg = null;
        GroupTemplate gt = null;
        try {
            cfg = Configuration.defaultConfiguration();
            gt = new GroupTemplate(resourceLoader, cfg);
        } catch (IOException e) {
            throw new GenException(ExceptionEnum.GEN_ERROR);
        }
        return gt;
    }


    /**
     * 创建前端代码
     *
     * @param map
     * @param temp html or js
     * @param type index or add or edit
     */
    private void createFrontCode(Map<String, Object> map, String temp, String type) {
        Writer out = null;
        String suffix = "_" + temp + "." + type;
        StringBuilder sb = new StringBuilder("");
        if (GenTypeEnum.HTML.getName().equals(type)) {
            sb.append(ROOT_PATH + "/ins1st-web/src/main/resources/templates/modules/"
                    + map.get("moduleName").toString() + "/" + map.get("bizName").toString() + "/"
                    + map.get("bizName").toString() + suffix);
        } else {
            sb.append(ROOT_PATH + "/ins1st-web/src/main/resources/static/modules/"
                    + map.get("moduleName").toString() + "/" + map.get("bizName").toString() + "/"
                    + map.get("bizName").toString() + suffix);
        }
        try {
            FileUtil.createFile(sb.toString());
            out = new FileWriter(new File(sb.toString()));
            GroupTemplate gt = initBeetlGroupTemplate();
            Template t = gt.getTemplate("/" + temp + "." + type + ".txt");
            t.binding("map", map);
            t.renderTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
