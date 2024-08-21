package org.jeecg.common.service;


import cn.iocoder.yudao.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.SysCategoryModel;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysBaseApiImpl implements ISysBaseAPI{

    /**
     * 4根据 code 查询数据库中存储的 DynamicDataSourceModel
     *
     * @param dbSourceCode
     * @return
     */
    @Override
    public DataSourceConfigDO getDynamicDbSourceByCode(String dbSourceCode) {
        return null;
    }

    /**
     * 8查询数据权限
     *
     * @param component   组件
     * @param requestPath 前段请求地址
     * @param username    用户名
     * @return
     */
    @Override
    public List<SysPermissionDataRuleModel> queryPermissionDataRule(String component, String requestPath, String username) {
        return List.of();
    }

    /**
     * 10获取数据字典
     *
     * @param code
     * @return
     */
    @Override
    public List<DictModel> queryDictItemsByCode(String code) {
        return List.of();
    }

    /**
     * 13获取表数据字典
     *
     * @param tableFilterSql
     * @param text
     * @param code
     * @return
     */
    @Override
    public List<DictModel> queryTableDictItemsByCode(String tableFilterSql, String text, String code) {
        return List.of();
    }

    /**
     * 12查询所有分类字典
     *
     * @return
     */
    @Override
    public List<SysCategoryModel> queryAllSysCategory() {
        return List.of();
    }

    /**
     * 16查询表字典 支持过滤数据
     *
     * @param table
     * @param text
     * @param code
     * @param filterSql
     * @return
     */
    @Override
    public List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql) {
        return List.of();
    }

    /**
     * 17查询指定table的 text code 获取字典，包含text和value
     *
     * @param table
     * @param text
     * @param code
     * @param keyArray
     * @return
     */
    @Override
    public List<String> queryTableDictByKeys(String table, String text, String code, String[] keyArray) {
        return List.of();
    }

    /**
     * 检查查询sql的表和字段是否在白名单中
     *
     * @param selectSql
     * @return
     */
    @Override
    public boolean dictTableWhiteListCheckBySql(String selectSql) {
        return false;
    }

    /**
     * 根据字典表或者字典编码，校验是否在白名单中
     *
     * @param tableOrDictCode 表名或dictCode
     * @param fields          如果传的是dictCode，则该参数必须传null
     * @return
     */
    @Override
    public boolean dictTableWhiteListCheckByDict(String tableOrDictCode, String... fields) {
        return false;
    }

    @Override
    public Object queryEnableDictItemsByCode(String string2) {
        return null;
    }
}
