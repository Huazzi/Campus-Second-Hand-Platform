package com.ins1st.modules.gen.table.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ins1st.model.GenTable;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成表 Mapper 接口
 * </p>
 *
 * @author sun
 * @since 2019-05-16
 */
public interface GenTableMapper extends BaseMapper<GenTable> {


    /**
     * 查询所有表
     *
     * @param dbName
     * @return
     */
    @Select("select TABLE_NAME as tableName,TABLE_COMMENT as tableComment from information_schema.`TABLES` where TABLE_SCHEMA = #{dbName}")
    public List<Map<String, Object>> getTables(String dbName);

}
