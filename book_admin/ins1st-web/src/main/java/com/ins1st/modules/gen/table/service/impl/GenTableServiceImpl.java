package com.ins1st.modules.gen.table.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.ins1st.model.GenTable;
import com.ins1st.modules.gen.table.mapper.GenTableMapper;
import com.ins1st.modules.gen.table.service.IGenTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成表 服务实现类
 * </p>
 *
 * @author sun
 * @since 2019-05-16
 */
@Service("genTableService")
@Transactional
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements IGenTableService {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Autowired
    private GenTableMapper genTableMapper;

    @Override
    public List<Map<String, Object>> getTables(String dbName) {
        return genTableMapper.getTables(dbName);
    }

    @Override
    public List<Map<String, Object>> queryColumns4Table(GenTable genTable) {
        genTable = this.getById(genTable.getId());
        List<TableInfo> columns = getColumns(genTable.getTableName());
        List<Map<String, Object>> result = handleColumns2Map(columns);
        return result;
    }

    private List<TableInfo> getColumns(String tableName) {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setDriverName(driverClassName);
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(tableName);
        ConfigBuilder configBuilder = new ConfigBuilder(null, dsc, strategy, null, null);
        return configBuilder.getTableInfoList();
    }

    private List<Map<String, Object>> handleColumns2Map(List<TableInfo> tableInfos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TableInfo info : tableInfos) {
            for (TableField field : info.getFields()) {
                Map<String, Object> column = new LinkedHashMap<>();
                column.put("name", field.getName());
                column.put("keyFlag", field.isKeyFlag());
                column.put("dbType", field.getType());
                column.put("javaType", field.getColumnType().getType());
                column.put("comment", field.getComment());
                list.add(column);
            }
        }
        return list;
    }
}
