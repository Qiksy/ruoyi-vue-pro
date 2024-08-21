package org.jeecg.common.service;

import cn.iocoder.yudao.framework.security.core.LoginUser;
import cn.iocoder.yudao.module.infra.dal.dataobject.db.DataSourceConfigDO;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.SysCategoryModel;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISysBaseAPI {


    /**
     * 4根据 code 查询数据库中存储的 DynamicDataSourceModel
     *
     * @param dbSourceCode
     * @return
     */
    DataSourceConfigDO getDynamicDbSourceByCode(String dbSourceCode);



    /**
     * 8查询数据权限
     * @param component 组件
     * @param username 用户名
     * @param requestPath 前段请求地址
     * @return
     */
    List<SysPermissionDataRuleModel> queryPermissionDataRule(String component, String requestPath, String username);




    /**
     * 10获取数据字典
     * @param code
     * @return
     */
    public List<DictModel> queryDictItemsByCode(String code);

    /**
     * 13获取表数据字典
     * @param tableFilterSql
     * @param text
     * @param code
     * @return
     */
    List<DictModel> queryTableDictItemsByCode(String tableFilterSql, String text, String code);





    /**
     * 12查询所有分类字典
     * @return
     */
    public List<SysCategoryModel> queryAllSysCategory();


    /**
     * 16查询表字典 支持过滤数据
     * @param table
     * @param text
     * @param code
     * @param filterSql
     * @return
     */
    public List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql);

    /**
     * 17查询指定table的 text code 获取字典，包含text和value
     * @param table
     * @param text
     * @param code
     * @param keyArray
     * @return
     */
    @Deprecated
    public List<String> queryTableDictByKeys(String table, String text, String code, String[] keyArray);


    /**
     * 检查查询sql的表和字段是否在白名单中
     *
     * @param selectSql
     * @return
     */
    boolean dictTableWhiteListCheckBySql(String selectSql);

    /**
     * 根据字典表或者字典编码，校验是否在白名单中
     *
     * @param tableOrDictCode 表名或dictCode
     * @param fields          如果传的是dictCode，则该参数必须传null
     * @return
     */
    boolean dictTableWhiteListCheckByDict(String tableOrDictCode, String... fields);

    Object queryEnableDictItemsByCode(String string2);
}
