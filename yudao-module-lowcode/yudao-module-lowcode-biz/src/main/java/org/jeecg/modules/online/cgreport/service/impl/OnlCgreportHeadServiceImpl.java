package org.jeecg.modules.online.cgreport.service.impl;

import cn.hutool.core.util.ReUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.infra.dal.dataobject.db.DataSourceConfigDO;
import cn.iocoder.yudao.module.infra.service.db.DataSourceConfigService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import org.jeecg.common.config.LowCodeConfig;
import org.jeecg.common.service.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;

import org.jeecg.common.util.dynamic.db.DynamicDBUtil;
import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.common.util.online.SqlInjectionUtil;
import org.jeecg.common.util.sqlparse.JSqlParserUtils;
import org.jeecg.common.util.sqlparse.vo.SelectSqlInfo;
import org.jeecg.modules.online.cgform.enums.DataBaseEnum;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.mapper.OnlCgreportHeadMapper;
import org.jeecg.modules.online.cgreport.model.OnlCgreportModel;
import org.jeecg.modules.online.cgreport.constant.CgReportConstant;
import org.jeecg.modules.online.cgreport.utils.CgReportSqlFiledParseUtils;
import org.jeecg.modules.online.cgreport.utils.CgReportSqlUtil;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.jeecg.modules.online.config.exception.DBException;
import org.jeecg.modules.online.config.database.OnlineFieldConfig;
import org.jeecg.modules.online.config.template.DbTableUtil;
import org.jeecg.modules.online.handler.ConditionHandler;
import org.jeecg.modules.online.handler.SqlParamsHandler;
import org.jeecg.query.QueryGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/* compiled from: OnlCgreportHeadServiceImpl.java */
@Service("onlCgreportHeadServiceImpl")
/* renamed from: org.jeecg.modules.online.cgreport.service.a.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/service/a/c.class */
public class OnlCgreportHeadServiceImpl extends ServiceImpl<OnlCgreportHeadMapper, OnlCgreportHead> implements IOnlCgreportHeadService {

    /* renamed from: b */
    private static final Logger logger;

    @Autowired
    private IOnlCgreportParamService onlCgreportParamService;

    @Autowired
    private IOnlCgreportItemService onlCgreportItemService;

    @Autowired
    private OnlCgreportHeadMapper mapper;

    @Autowired
    @Lazy
    private ISysBaseAPI sysBaseAPI;

    @Resource
    private LowCodeConfig lowCodeConfig;


    @Resource
    private DataSourceConfigService dataSourceConfigService;

    /* renamed from: a */
    static final /* synthetic */ boolean isHasService;

    /* renamed from: a */

    static {
        isHasService = !OnlCgreportHeadServiceImpl.class.desiredAssertionStatus();
        logger = LoggerFactory.getLogger(OnlCgreportHeadServiceImpl.class);
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public Map<String, Object> executeSelectSql(String sql, String onlCgreportHeadId, Map<String, Object> params) throws SQLException {
        List<OnlCgreportItem> list;
        IPage<Map<String, Object>> selectPageByCondition;
        String str = null;
        try {
            str = DbTableUtil.getDatabaseType();
        } catch (DBException e) {
            e.printStackTrace();
        }
        LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgreportParam::getCgrheadId, onlCgreportHeadId);
        SqlParamsHandler<OnlCgreportParam> sqlParamsHandler = new SqlParamsHandler<>(params, this.onlCgreportParamService.list(lambdaQueryWrapper));
        String sql2 = sqlParamsHandler.m47a(sql);
        Map<String, Object> selfSqlParams = sqlParamsHandler.getSelfSqlParams();
        HashMap<String,Object> hashMap = new HashMap<>(5);
        Page<Map<String, Object>> page = new Page<>(ConvertUtils.getInt(params.get("pageNo"), 1), ConvertUtils.getInt(params.get("pageSize"), 10));
        LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(OnlCgreportItem::getCgrheadId, onlCgreportHeadId);
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str2 : params.keySet().toArray(new String[0])) {
            if (str2.startsWith(CgReportConstant.FORCE)) {
                String substring = str2.substring(CgReportConstant.FORCE.length());
                arrayList.add(substring);
                params.put(substring, params.get(str2));
            }
        }
        if (!arrayList.isEmpty()) {
            lambdaQueryWrapper2.in(OnlCgreportItem::getFieldName, arrayList);
            list = this.onlCgreportItemService.list(lambdaQueryWrapper2);
            if (list.size() < arrayList.size()) {
                String str3 = "id";
                boolean anyMatch = arrayList.stream().anyMatch(str3::equalsIgnoreCase);
                boolean anyMatch2 = list.stream().anyMatch(onlCgreportItem -> {
                    return "id".equalsIgnoreCase(onlCgreportItem.getFieldName());
                });
                if (anyMatch && !anyMatch2) {
                    OnlCgreportItem onlCgreportItem2 = new OnlCgreportItem();
                    onlCgreportItem2.setFieldName("id");
                    onlCgreportItem2.setFieldType(CgReportConstant.STRING);
                    onlCgreportItem2.setSearchMode("single");
                    onlCgreportItem2.setIsSearch(1);
                    list.add(onlCgreportItem2);
                }
            } else {
                list.forEach(onlCgreportItem3 -> {
                    onlCgreportItem3.setIsSearch(1);
                });
            }
        } else {
            lambdaQueryWrapper2.eq(OnlCgreportItem::getIsSearch, 1);
            list = this.onlCgreportItemService.list(lambdaQueryWrapper2);
        }
        String sql3 = QueryGenerator.convertSystemVariables(sql2);
        ArrayList<OnlineFieldConfig> arrayList2 = new ArrayList<>();
        for (OnlCgreportItem onlCgreportItem : list) {
            arrayList2.add(new OnlineFieldConfig(onlCgreportItem));
        }
        ConditionHandler conditionHandler = new ConditionHandler("jeecg_rp_temp.", str);
        String m6a = conditionHandler.m6a(arrayList2, params);
        Map<String, Object> sqlParams = conditionHandler.getSqlParams();
        if (ReUtil.contains(" order\\s+by ", sql3.toLowerCase()) && "SQLSERVER".equalsIgnoreCase(str)) {
            throw exception("SqlServer不支持SQL内排序!");
        }
        String str4 = "select * from (" + sql3 + ") jeecg_rp_temp ";
        if (!m6a.trim().isEmpty()) {
            str4 = str4 + " where " + m6a;
        }
        Object obj = params.get("column");
        if (obj != null) {
            String valueOf = String.valueOf(params.get("order"));
            String[] split = String.valueOf(obj).split(CgformUtil.COMMA_SEPARATOR);
            for (int i = 0; i < split.length; i++) {
                split[i] = SqlInjectionUtil.getSqlInjectField(split[i]);
            }
            str4 = str4 + " order by jeecg_rp_temp." + String.join(" " + valueOf + ", jeecg_rp_temp.", split) + " " + valueOf;
        }
        SqlInjectionUtil.specialFilterContentForOnlineReport(str4);
        if (!selfSqlParams.isEmpty()) {
            sqlParams.putAll(selfSqlParams);
        }
        if (Boolean.parseBoolean(String.valueOf(params.get("getAll")))) {
            List<Map<String, Object>> selectByCondition = this.mapper.selectByCondition(str4, sqlParams);
            selectPageByCondition = new Page<>();
            selectPageByCondition.setRecords(selectByCondition);
            selectPageByCondition.setTotal(selectByCondition.size());
        } else {
            selectPageByCondition = this.mapper.selectPageByCondition(page, str4, sqlParams);
        }
        hashMap.put("total", selectPageByCondition.getTotal());
        hashMap.put("records", CgformUtil.m227d(selectPageByCondition.getRecords()));
        return hashMap;
    }

    @SneakyThrows
    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public Map<String, Object> executeSelectSqlDynamic(String dbKey, String sql, Map<String, Object> params, String onlCgreportHeadId) {
        int i = ConvertUtils.getInt(params.get("pageNo"), 1);
        int i2 = ConvertUtils.getInt(params.get("pageSize"), 10);
        DataSourceConfigDO cacheDynamicDataSourceModel = dataSourceConfigService.getDataSourceConfig(Long.valueOf(dbKey)); //获取数据库配置
        if (ReUtil.contains(" order\\s+by ", sql.toLowerCase()) && "3".equalsIgnoreCase(cacheDynamicDataSourceModel.getDbType())) {
            throw exception("SqlServer不支持SQL内排序!");
        }
        LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgreportParam::getCgrheadId, onlCgreportHeadId);
        SqlParamsHandler<OnlCgreportParam> sqlParamsHandler = new SqlParamsHandler<>(params, this.onlCgreportParamService.list(lambdaQueryWrapper));
        String sql2 = sqlParamsHandler.m48b(sql);
        Map<String, Object> selfSqlParams = sqlParamsHandler.getSelfSqlParams();
        LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(OnlCgreportItem::getCgrheadId, onlCgreportHeadId);
        lambdaQueryWrapper2.eq(OnlCgreportItem::getIsSearch, 1);
        List<OnlCgreportItem> list = this.onlCgreportItemService.list(lambdaQueryWrapper2);
        String sql3 = QueryGenerator.convertSystemVariables(sql2);
        ArrayList<OnlineFieldConfig> arrayList = new ArrayList<>();
        for (OnlCgreportItem onlCgreportItem : list) {
            arrayList.add(new OnlineFieldConfig(onlCgreportItem));
        }
        ConditionHandler conditionHandler = new ConditionHandler("jeecg_rp_temp.", DataBaseEnum.getDataBaseNameByValue(cacheDynamicDataSourceModel.getDbType()));
        conditionHandler.setDaoType(ConditionHandler.f61a);
        String m6a = conditionHandler.m6a(arrayList, params);
        Map<String, Object> sqlParams = conditionHandler.getSqlParams();
        String str = "select * from (" + sql3 + ") jeecg_rp_temp ";
        if (!m6a.trim().isEmpty()) {
            str = str + " where " + m6a;
        }
        String m432c = CgReportSqlUtil.m432c(str);
        Object obj = params.get("column");
        if (obj != null) {
            str = str + " order by jeecg_rp_temp." + obj.toString() + " " + params.get("order").toString();
        }
        if (!selfSqlParams.isEmpty()) {
            sqlParams.putAll(selfSqlParams);
        }
        HashMap<String,Object> hashMap = new HashMap<>(5);
        logger.info("多数据源 报表查询sqlParam=>\r\n" + sqlParams.toString());
        hashMap.put("total", DynamicDBUtil.queryCount(dbKey, m432c, sqlParams).get("total"));
        hashMap.put("records", CgformUtil.m227d(CgReportSqlUtil.m434a(String.valueOf(params.get("getAll")), dbKey, str, i, i2, sqlParams)));
        return hashMap;
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    @Transactional(rollbackFor = {Exception.class})
    @CacheEvict(value = {"sys:cache:online:rp"}, allEntries = true, beforeInvocation = true)
    public CommonResult<?> editAll(OnlCgreportModel values) {
        OnlCgreportHead head = values.getHead();
        if (((OnlCgreportHead) super.getById(head.getId())) == null) {
            return error("未找到对应实体");
        }
        super.updateById(head);
        LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgreportItem::getCgrheadId, head.getId());
        this.onlCgreportItemService.remove(lambdaQueryWrapper);
        LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(OnlCgreportParam::getCgrheadId, head.getId());
        this.onlCgreportParamService.remove(lambdaQueryWrapper2);
        for (OnlCgreportParam onlCgreportParam : values.getParams()) {
            onlCgreportParam.setCgrheadId(head.getId());
        }
        for (OnlCgreportItem onlCgreportItem : values.getItems()) {
            onlCgreportItem.setFieldName(onlCgreportItem.getFieldName().trim().toLowerCase());
            onlCgreportItem.setCgrheadId(head.getId());
        }
        this.onlCgreportItemService.saveBatch(values.getItems());
        this.onlCgreportParamService.saveBatch(values.getParams());
        return  success("全部修改成功");
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    @Transactional(rollbackFor = {Exception.class})
    public CommonResult<?> delete(String id) {
        if (super.removeById(id)) {
            LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OnlCgreportItem::getCgrheadId, id);
            this.onlCgreportItemService.remove(lambdaQueryWrapper);
            LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
            lambdaQueryWrapper2.eq(OnlCgreportParam::getCgrheadId, id);
            this.onlCgreportParamService.remove(lambdaQueryWrapper2);
        }
        return success("删除成功");
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    @Transactional(rollbackFor = {Exception.class})
    public CommonResult<?> bathDelete(String[] ids) {
        for (String str : ids) {
            if (super.removeById(str)) {
                LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(OnlCgreportItem::getCgrheadId, str);
                this.onlCgreportItemService.remove(lambdaQueryWrapper);
                LambdaQueryWrapper<OnlCgreportParam> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
                lambdaQueryWrapper2.eq(OnlCgreportParam::getCgrheadId, str);
                this.onlCgreportParamService.remove(lambdaQueryWrapper2);
            }
        }
        return success("删除成功");
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public List<String> getSqlFields(String sql, String dbKey) throws Exception {
        List<String> m441a;
        if (StringUtils.isNotBlank(dbKey)) {
            m441a = m441a(sql, dbKey);
        } else {
            m441a = m441a(sql, null);
        }
        return m441a;
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public List<String> getSqlParams(String sql) {
        if (ConvertUtils.isEmpty(sql)) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\$\\{\\w+\\}").matcher(sql);
        while (matcher.find()) {
            String group = matcher.group();
            arrayList.add(group.substring(group.indexOf("{") + 1, group.indexOf("}")));
        }
        return arrayList;
    }

    /* renamed from: a */
    private List<String> m441a(String str, String dbKey) throws Exception {
        if (ConvertUtils.isEmpty(str)) {
            return null;
        }
        String trim = str.replace("[^><]=", CgReportConstant.EQUAL).trim();
        if (trim.endsWith(";")) {
            trim = trim.substring(0, trim.length() - 1);
        }
        String m429a = CgReportSqlUtil.m429a(QueryGenerator.convertSystemVariables(trim));
        SelectSqlInfo parseSelectSqlInfo = JSqlParserUtils.parseSelectSqlInfo(m429a);
        if (!isHasService && parseSelectSqlInfo == null) {
            throw new AssertionError();
        }
        //todo 准备恢复
        if (parseSelectSqlInfo != null && this.lowCodeConfig.getFirewall() != null && this.lowCodeConfig.getFirewall().getDataSourceSafe() && parseSelectSqlInfo.isSelectAll()) {
            throw exception("不允许使用 *");
        }
        Set<String> set = null;
        if (StringUtils.isNotBlank(dbKey)) {
            DataSourceConfigDO cacheDynamicDataSourceModel = dataSourceConfigService.getDataSourceConfig(Long.valueOf(dbKey));
            if (ReUtil.contains(" order\\s+by ", m429a.toLowerCase()) && "3".equalsIgnoreCase(cacheDynamicDataSourceModel.getDbType())) {
                throw exception("SqlServer不支持SQL内排序!");
            }
            Map<String, Object> m433a = CgReportSqlUtil.m433a(dbKey, m429a);
            if (m433a == null) {
                if (!m429a.contains("*")) {
                    try {
                        m433a = CgReportSqlFiledParseUtils.m426a(m429a);
                    } catch (Exception e) {
                    }
                }
                if (m433a == null) {
                    throw exception("该报表sql没有数据");
                }
            }
            set = m433a.keySet();
        } else {
            String databaseType = DbTableUtil.getDatabaseType();
            if (ReUtil.contains(" order\\s+by ", m429a.toLowerCase()) && "SQLSERVER".equalsIgnoreCase(databaseType)) {
                throw exception("SqlServer不支持SQL内排序!");
            }
            List<Map<String, Object>> records = this.mapper.executeParseSql(new Page<>(1L, 1L), m429a).getRecords();
            if (records.isEmpty()) {
                if (!m429a.contains("*")) {
                    try {
                        set = CgReportSqlFiledParseUtils.m426a(m429a).keySet();
                    } catch (Exception e2) {
                    }
                }
                if (set == null) {
                    throw exception("该报表sql没有数据");
                }
            } else {
                set = records.get(0).keySet();
            }
        }
        if (set != null) {
            set.remove("ROW_ID");
        }
        return new ArrayList<>(set);
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public Map<String, Object> queryCgReportConfig(String reportId) {
        HashMap<String,Object> hashMap = new HashMap<>(5);
        Map<String, Object> queryCgReportMainConfig = this.mapper.queryCgReportMainConfig(reportId);
        List<Map<String, Object>> queryCgReportItems = this.mapper.queryCgReportItems(reportId);
        List<OnlCgreportParam> queryCgReportParams = this.mapper.queryCgReportParams(reportId);
        if (DbTableUtil.m489a()) {
            hashMap.put(CgReportConstant.MAIN, CgformUtil.m224a(queryCgReportMainConfig));
            hashMap.put(CgReportConstant.ITEMS, CgformUtil.m227d(queryCgReportItems));
        } else {
            hashMap.put(CgReportConstant.MAIN, queryCgReportMainConfig);
            hashMap.put(CgReportConstant.ITEMS, queryCgReportItems);
        }
        hashMap.put(CgReportConstant.PARAMS, queryCgReportParams);
        return hashMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v36, types: [java.util.List] */
    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    @Deprecated
    public List<DictModel> queryDictSelectData(String sql, String keyword) {
        this.sysBaseAPI.dictTableWhiteListCheckBySql(sql);
        List<org.jeecg.common.system.vo.DictModel> arrayList = new ArrayList<>();
        Page<Map<String, Object>> page = new Page<>();
        page.setSearchCount(false);
        page.setCurrent(1L);
        page.setSize(10L);
        String sql2 = sql.trim();
        int lastIndexOf = sql2.lastIndexOf(";");
        if (lastIndexOf == sql2.length() - 1) {
            sql2 = sql2.substring(0, lastIndexOf);
        }
        QueryWrapper<OnlCgreportHeadMapper> queryWrapper = new QueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            String str = "%" + keyword + "%";
            queryWrapper.like("temp.value", str).or().like("temp.text", str);
        }
        List<Map<String, Object>> list = this.baseMapper.selectPageBySql(page, sql2, queryWrapper).getRecords().stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (!list.isEmpty()) {
            String jsonString = JSON.toJSONString(list);
            arrayList = JSON.parseArray(jsonString, DictModel.class);
        }
        return arrayList;
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    @Cacheable(value = {"sys:cache:online:rp"}, key = "'column-v2-'+#code+'-'+#queryDict")
    public Map<String, Object> queryColumnInfo(String code, boolean queryDict) {
        HashMap<String,Object> hashMap = new HashMap<>(5);
        QueryWrapper<OnlCgreportItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cgrhead_id", code).eq("is_show", 1).orderByAsc("order_num");
        List<OnlCgreportItem> list = this.onlCgreportItemService.list(queryWrapper);
        JSONArray jSONArray = new JSONArray();
        JSONArray jSONArray2 = new JSONArray();
        HashMap<String,Object> hashMap2 = new HashMap<>(5);
        boolean z = false;
        for (OnlCgreportItem onlCgreportItem : list) {
            JSONObject jSONObject = new JSONObject(4);
            jSONObject.put("id", onlCgreportItem.getId());
            jSONObject.put(CgformUtil.TITLE, onlCgreportItem.getFieldTxt());
            jSONObject.put("dataIndex", onlCgreportItem.getFieldName());
            jSONObject.put("fieldType", onlCgreportItem.getFieldType());
            jSONObject.put("align", CgformUtil.CENTER);
            jSONObject.put("sorter", "true");
            jSONObject.put("isTotal", onlCgreportItem.getIsTotal());
            jSONObject.put("groupTitle", onlCgreportItem.getGroupTitle());
            if (ConvertUtils.isNotEmpty(onlCgreportItem.getGroupTitle())) {
                z = true;
            }
            String fieldType = onlCgreportItem.getFieldType();
            if ("Integer".equals(fieldType) || "Date".equals(fieldType) || "Long".equals(fieldType)) {
                jSONObject.put("sorter", "true");
            }
            if (StringUtils.isNotBlank(onlCgreportItem.getFieldHref())) {
                String str = "fieldHref_" + onlCgreportItem.getFieldName();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("customRender", str);
                jSONObject.put("scopedSlots", jSONObject2);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("slotName", str);
                jSONObject3.put("href", onlCgreportItem.getFieldHref());
                jSONArray.add(jSONObject3);
            }
            String dictCode = onlCgreportItem.getDictCode();
            if (dictCode != null && !dictCode.isEmpty()) {
                if (queryDict) {
                    hashMap2.put(onlCgreportItem.getFieldName(), queryColumnDict(onlCgreportItem.getDictCode(), null, null));
                    jSONObject.put("customRender", onlCgreportItem.getFieldName());
                } else {
                    jSONObject.put("dictCode", dictCode);
                }
            }
            jSONArray2.add(jSONObject);
        }
        if (queryDict) {
            hashMap.put("dictOptions", hashMap2);
        }
        hashMap.put("columns", jSONArray2);
        hashMap.put("fieldHrefSlots", jSONArray);
        hashMap.put("isGroupTitle", z);
        return hashMap;
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public List<DictModel> queryColumnDict(String dictCode, JSONArray records, String fieldName) {
        List<DictModel> list = null;
        if (ConvertUtils.isNotEmpty(dictCode)) {
            if (dictCode.trim().toLowerCase().indexOf("select ") == 0 && (fieldName == null || !records.isEmpty())) {
                String dictCode2 = dictCode.trim();
                int lastIndexOf = dictCode2.lastIndexOf(";");
                if (lastIndexOf == dictCode2.length() - 1) {
                    dictCode2 = dictCode2.substring(0, lastIndexOf);
                }
                this.sysBaseAPI.dictTableWhiteListCheckBySql(dictCode2);
                String str = "SELECT * FROM (" + dictCode2 + ") temp ";
                if (records != null) {
                    HashSet<String> hashSet = new HashSet<>();
                    for (int i = 0; i < records.size(); i++) {
                        String string = records.getJSONObject(i).getString(fieldName);
                        if (StringUtils.isNotBlank(string)) {
                            hashSet.add(string);
                        }
                    }
                    str = str + "WHERE temp.value IN (" + ("'" + StringUtils.join(hashSet, "','") + "'") + ")";
                }
                List<Map<String, Object>> executeSqlDict = ((OnlCgreportHeadMapper) this.baseMapper).executeSqlDict(str);
                if (executeSqlDict != null && !executeSqlDict.isEmpty()) {
                    list = JSON.parseArray(JSON.toJSONString(executeSqlDict), DictModel.class);
                }
            } else {
                list = this.sysBaseAPI.queryDictItemsByCode(dictCode);
            }
        }
        return list;
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService
    public List<DictModel> queryColumnDictList(String dictCode, List<Map<String, Object>> records, String fieldName) {
        String m253a;
        List<DictModel> list = null;
        if (ConvertUtils.isNotEmpty(dictCode)) {
            String dictCode2 = dictCode.trim();
            if (dictCode2.toLowerCase().indexOf("select ") == 0 && (fieldName == null || !records.isEmpty())) {
                if (dictCode2.endsWith(";")) {
                    dictCode2 = dictCode2.substring(0, dictCode2.length() - 1);
                }
                this.sysBaseAPI.dictTableWhiteListCheckBySql(dictCode2);
                QueryWrapper<?> queryWrapper = new QueryWrapper<>();
                if (records != null && records.size() < 100) {
                    HashSet<String> hashSet = new HashSet<>();
                    for (int i = 0; i < records.size(); i++) {
                        Map<String, Object> map = records.get(i);
                        if (map != null && (m253a = CgformUtil.m253a(map, fieldName)) != null) {
                            hashSet.add(m253a);
                        }
                    }
                    if (!hashSet.isEmpty()) {
                        queryWrapper.in("temp.value", hashSet);
                    }
                }
                list = getBaseMapper().queryDictListBySql(dictCode2, queryWrapper);
            } else {
                list = this.sysBaseAPI.queryDictItemsByCode(dictCode2);
            }
        }
        return list;
    }
}
