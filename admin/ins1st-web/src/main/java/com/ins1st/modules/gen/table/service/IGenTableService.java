package com.ins1st.modules.gen.table.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ins1st.model.GenTable;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成表 服务类
 * </p>
 *
 * @author sun
 * @since 2019-05-16
 */
public interface IGenTableService extends IService<GenTable> {


    /**
     * 查询库里所有表
     *
     * @param dbName
     * @return
     */
    public List<Map<String, Object>> getTables(String dbName);


    /**
     * 根据表名查询字段
     *
     * @param genTable
     * @return
     */
    public List<Map<String, Object>> queryColumns4Table(GenTable genTable);


}
