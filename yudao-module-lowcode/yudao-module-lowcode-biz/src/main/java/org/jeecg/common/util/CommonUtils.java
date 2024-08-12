package org.jeecg.common.util;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.creator.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import org.jeecg.common.util.online.ConvertUtils;
import org.thymeleaf.spring6.context.SpringContextUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

public class CommonUtils {

    /**
     * 根据数据源key获取DataSourceProperty
     * @param sourceKey
     * @return
     */
    public static DataSourceProperty getDataSourceProperty(String sourceKey){
        DynamicDataSourceProperties prop = SpringUtil.getBean(DynamicDataSourceProperties.class);
        Map<String, DataSourceProperty> map = prop.getDatasource();
        DataSourceProperty db = (DataSourceProperty)map.get(sourceKey);
        return db;
    }

    /** 当前系统数据库类型 */
    private static String DB_TYPE = "";
    private static DbType dbTypeEnum = null;

    /**
     * 全局获取平台数据库类型（对应mybaisPlus枚举）
     * @return
     */
    public static DbType getDatabaseTypeEnum() {
        if (ConvertUtils.isNotEmpty(dbTypeEnum)) {
            return dbTypeEnum;
        }
        try {
            DataSource dataSource = SpringUtil.getBean(DataSource.class);
            dbTypeEnum = JdbcUtils.getDbType(dataSource.getConnection().getMetaData().getURL());
            return dbTypeEnum;
        } catch (SQLException e) {
            return null;
        }
    }

    //todo 上传文件，返回path
    public static String uploadOnlineImage(byte[] data,String basePath,String bizPath,String uploadType){

        return null;
    }
}
