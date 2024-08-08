package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.dto.DataLogDTO;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.util.JeecgDataAutorUtils;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.common.util.SqlInjectionUtil;

import org.jeecg.modules.online.auth.service.IOnlAuthDataService;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.enums.CgformConstant;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.mapper.OnlineMapper;
import org.jeecg.modules.online.cgform.model.OnlQueryModel;
import org.jeecg.modules.online.cgform.model.SqlOrder;
import org.jeecg.modules.online.cgform.model.TreeModel;
import org.jeecg.modules.online.cgform.vo.LinkDown;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.constant.OnlineConst;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgreport.utils.CgReportSqlUtil;
import org.jeecg.modules.online.handler.ConditionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* compiled from: OnlCgformFieldServiceImpl.java */
@Service("onlCgformFieldServiceImpl")
/* renamed from: org.jeecg.modules.online.cgform.service.a.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/a/c.class */
public class OnlCgformFieldServiceImpl extends ServiceImpl<OnlCgformFieldMapper, OnlCgformField> implements IOnlCgformFieldService {

    @Autowired
    private OnlCgformFieldMapper onlCgformFieldMapper;

    @Autowired
    private OnlCgformHeadMapper cgformHeadMapper;

    @Autowired
    private IOnlAuthDataService onlAuthDataService;

    @Autowired
    private IOnlAuthPageService onlAuthPageService;

    @Autowired
    @Lazy
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private OnlineMapper onlineMapper;

    /* renamed from: c */
    private static final String f419c = "0";

    /* renamed from: b */
    private static final Logger logger = LoggerFactory.getLogger(OnlCgformFieldServiceImpl.class);

    /* renamed from: a */
    public static ExecutorService threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60,
            TimeUnit.SECONDS, new SynchronousQueue<>());

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public Map<String, Object> queryAutolistPage(OnlCgformHead head, Map<String, Object> params, List<String> needList) {
        OnlQueryModel queryInfo = getQueryInfo(head, params, needList);
        String sql = queryInfo.getSql();
        Map<String, Object> params2 = queryInfo.getParams();
        List<OnlCgformField> fieldList = queryInfo.getFieldList();
        HashMap<String,Object> hashMap = new HashMap<>(5);
        int pageSize = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
        if (Integer.valueOf(pageSize) == -521) {
            List<Map<String, Object>> selectByCondition = this.onlineMapper.selectByCondition(sql, params2);
            if (selectByCondition == null || selectByCondition.isEmpty()) {
                hashMap.put("total", 0);
                hashMap.put("fieldList", fieldList);
            } else {
                hashMap.put("total", selectByCondition.size());
                hashMap.put("fieldList", fieldList);
                hashMap.put("records", CgformUtil.m227d(selectByCondition));
            }
        } else {
            int pageNo = params.get("pageNo") == null ? 1 : Integer.parseInt(params.get("pageNo").toString());
            IPage<Map<String, Object>> selectPageByCondition = this.onlineMapper.selectPageByCondition(
                    new Page<>(pageNo, pageSize), sql, params2);
            hashMap.put("total", selectPageByCondition.getTotal());
            List<Map<String, Object>> m227d = CgformUtil.m227d(selectPageByCondition.getRecords());
            handleLinkTableDictData(head.getId(), m227d);
            hashMap.put("records", m227d);
        }
        return hashMap;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public Map<String, Object> queryAutoTreeNoPage(String tbname, String headId, Map<String, Object> params, List<String> needList, String pidField) {
        HashMap<String,Object> hashMap = new HashMap<>(5);
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, headId);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list = list(lambdaQueryWrapper);
        List<OnlCgformField> queryAvailableFields = queryAvailableFields(headId, tbname, true, list, needList);
        StringBuffer stringBuffer = new StringBuffer();
        CgformUtil.m182a(tbname, queryAvailableFields, stringBuffer);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<SysPermissionDataRuleModel> queryUserOnlineAuthData = this.onlAuthDataService.queryUserOnlineAuthData(loginUser.getId(), headId);
        if (queryUserOnlineAuthData != null && !queryUserOnlineAuthData.isEmpty()) {
            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(loginUser.getUsername()));
        }
        ConditionHandler conditionHandler = new ConditionHandler("t.");
        conditionHandler.setTableName(tbname);
        conditionHandler.setNeedList(needList);
        conditionHandler.setSubTableStr("");
        String m7a = conditionHandler.m7a(CgformUtil.m252g((List<OnlCgformField>) list), params, queryUserOnlineAuthData);
        Map<String, Object> sqlParams = conditionHandler.getSqlParams();
        if (!m7a.trim().isEmpty()) {
            stringBuffer.append(" t ").append(CgformUtil.f188i).append(m7a);
        }
        stringBuffer.append(m305a(list, params));
        int pageSize = params.get("pageSize") == null ? 10 : Integer.parseInt(params.get("pageSize").toString());
        if (pageSize == -521) {
            List<Map<String, Object>> selectByCondition = this.onlineMapper.selectByCondition(stringBuffer.toString(), sqlParams);
            if ("true".equals(params.get("hasQuery"))) {
                ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
                for (Map<String, Object> map : selectByCondition) {
                    String m253a = CgformUtil.m253a(map, pidField);
                    if (m253a != null && !f419c.equals(m253a)) {
                        Map<String, Object> m299a = m299a(m253a, tbname, headId, needList, pidField);
                        if (m299a != null && m299a.size() > 0 && !arrayList.contains(m299a)) {
                            arrayList.add(m299a);
                        }
                    } else if (!arrayList.contains(map)) {
                        arrayList.add(map);
                    }
                }
                selectByCondition = arrayList;
            }
            if (selectByCondition == null || selectByCondition.isEmpty()) {
                hashMap.put("total", 0);
                hashMap.put("fieldList", queryAvailableFields);
            } else {
                hashMap.put("total", selectByCondition.size());
                hashMap.put("fieldList", queryAvailableFields);
                hashMap.put("records", CgformUtil.m227d(selectByCondition));
            }
        } else {
            int pageNo = params.get("pageNo") == null ? 1 : Integer.parseInt(params.get("pageNo").toString());

            IPage<Map<String, Object>> selectPageByCondition = this.onlineMapper.selectPageByCondition(
                    new Page<>(pageNo,
                            pageSize), stringBuffer.toString(), sqlParams);
            hashMap.put("total", selectPageByCondition.getTotal());
            hashMap.put("records", CgformUtil.m227d(selectPageByCondition.getRecords()));
        }
        return hashMap;
    }

    /* renamed from: a */
    private Map<String, Object> m299a(String str, String str2, String str3, List<String> list, String str4) {
        HashMap<String,Object> hashMap = new HashMap<>(5);
        hashMap.put("id", str);
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str3);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list2 = list(lambdaQueryWrapper);
        List<OnlCgformField> queryAvailableFields = queryAvailableFields(str3, str2, true, list2, list);
        StringBuffer stringBuffer = new StringBuffer();
        CgformUtil.m182a(str2, queryAvailableFields, stringBuffer);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<SysPermissionDataRuleModel> queryUserOnlineAuthData = this.onlAuthDataService.queryUserOnlineAuthData(loginUser.getId(), str3);
        if (queryUserOnlineAuthData != null && !queryUserOnlineAuthData.isEmpty()) {
            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(loginUser.getUsername()));
        }
        ConditionHandler conditionHandler = new ConditionHandler("t.");
        conditionHandler.setTableName(str2);
        conditionHandler.setNeedList(list);
        conditionHandler.setSubTableStr("");
        String m7a = conditionHandler.m7a(CgformUtil.m252g((List<OnlCgformField>) list2), hashMap, queryUserOnlineAuthData);
        Map<String, Object> sqlParams = conditionHandler.getSqlParams();
        stringBuffer.append(" t ").append(CgformUtil.f188i).append(" id = '").append(str).append("' ");
        if (!m7a.trim().isEmpty()) {
            stringBuffer.append(CgReportSqlUtil.AND).append(m7a);
        }
        List<Map<String, Object>> selectByCondition = this.onlineMapper.selectByCondition(stringBuffer.toString(), sqlParams);
        if (selectByCondition != null && selectByCondition.size() > 0) {
            Map<String, Object> map = selectByCondition.get(0);
            if (map != null && map.get(str4) != null && !f419c.equals(map.get(str4))) {
                return m299a(map.get(str4).toString(), str2, str3, list, str4);
            }
            return map;
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void saveFormData(String code, String tbname, JSONObject json, boolean isCrazy) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        List<OnlCgformField> list = list(lambdaQueryWrapper);
        if (isCrazy) {
            Map<String, Object> m231c = CgformUtil.m231c(tbname, list, json);
            addOnlineInsertDataLog(tbname, m231c.get("id").toString());
            this.baseMapper.executeInsertSQL(m231c);
        } else {
            Map<String, Object> m196a = CgformUtil.m196a(tbname, list, json);
            addOnlineInsertDataLog(tbname, m196a.get("id").toString());
            this.baseMapper.executeInsertSQL(m196a);
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void saveTreeFormData(String code, String tbname, JSONObject json, String hasChildField, String pidField) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        List<OnlCgformField> list = list(lambdaQueryWrapper);
        for (OnlCgformField onlCgformField : list) {
            if (hasChildField.equals(onlCgformField.getDbFieldName()) && onlCgformField.getIsShowForm().intValue() != 1) {
                onlCgformField.setIsShowForm(1);
                json.put(hasChildField, f419c);
            } else if (pidField.equals(onlCgformField.getDbFieldName()) && StrUtils.isEmpty(json.get(pidField))) {
                onlCgformField.setIsShowForm(1);
                json.put(pidField, f419c);
            }
        }
        Map<String, Object> m196a = CgformUtil.m196a(tbname, list, json);
        addOnlineInsertDataLog(tbname, m196a.get("id").toString());
        this.baseMapper.executeInsertSQL(m196a);
        if (!f419c.equals(json.getString(pidField))) {
            UpdateWrapper<?> updateWrapper = new UpdateWrapper<>();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(hasChildField, "1");
            updateWrapper.eq("id", json.getString(pidField));
            m316a(tbname, jSONObject, updateWrapper);
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void saveFormData(List<OnlCgformField> fieldList, String tbname, JSONObject json) {
        this.onlCgformFieldMapper.executeInsertSQL(CgformUtil.m196a(tbname, fieldList, json));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void editFormData(String code, String tbname, JSONObject json, boolean isCrazy) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        List<OnlCgformField> list = list(lambdaQueryWrapper);
        if (isCrazy) {
            Map<String, Object> m232d = CgformUtil.m232d(tbname, list, json);
            addOnlineUpdateDataLog(tbname, m232d.get("id").toString(), list, json);
            this.onlCgformFieldMapper.executeUpdatetSQL(m232d);
        } else {
            Map<String, Object> m197b = CgformUtil.m197b(tbname, list, json);
            addOnlineUpdateDataLog(tbname, m197b.get("id").toString(), list, json);
            this.onlCgformFieldMapper.executeUpdatetSQL(m197b);
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void editTreeFormData(String code, String tbname, JSONObject json, String hasChildField, String pidField) {
        Integer m317b;
        String m235f = CgformUtil.m235f(tbname);
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", json.getString("id"));
        String obj = CgformUtil.m224a(m315a(m235f, null, queryWrapper, JSONObject.class)).get(pidField).toString();
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        List<OnlCgformField> list = list(lambdaQueryWrapper);
        for (OnlCgformField onlCgformField : list) {
            if (pidField.equals(onlCgformField.getDbFieldName()) && StrUtils.isEmpty(json.get(pidField))) {
                onlCgformField.setIsShowForm(1);
                json.put(pidField, f419c);
            }
        }
        Map<String, Object> m197b = CgformUtil.m197b(tbname, list, json);
        addOnlineUpdateDataLog(tbname, m197b.get("id").toString(), list, json);
        this.baseMapper.executeUpdatetSQL(m197b);
        if (!obj.equals(json.getString(pidField))) {
            UpdateWrapper<?> updateWrapper = new UpdateWrapper<>();
            JSONObject jSONObject = new JSONObject();
            if (!f419c.equals(obj) && ((m317b = m317b(m235f, pidField, obj)) == null || m317b == 0)) {
                jSONObject.put(hasChildField, f419c);
                updateWrapper.eq("id", obj);
                m316a(m235f, jSONObject, updateWrapper);
            }
            if (!f419c.equals(json.getString(pidField))) {
                jSONObject.put(hasChildField, "1");
                updateWrapper.eq("id", json.getString(pidField));
                m316a(m235f, jSONObject, updateWrapper);
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public Map<String, Object> queryFormData(String code, String tbname, String id) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        lambdaQueryWrapper.eq(OnlCgformField::getIsShowForm, 1);
        return  m314a(tbname, CgformUtil.m198a( list(lambdaQueryWrapper), id), JSONObject.class);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    @Transactional(rollbackFor = {Exception.class})
    public void deleteAutoListMainAndSub(OnlCgformHead head, String ids) {
        List<OnlCgformField> list;
        if (head.getTableType() == 2) {
            String id = head.getId();
            String tableName = head.getTableName();
            ArrayList<Map<String,String>> arrayList = new ArrayList();
            if (StrUtils.isNotEmpty(head.getSubTableStr())) {
                for (String str : head.getSubTableStr().split(CgformUtil.COMMA_SEPARATOR)) {
                    OnlCgformHead onlCgformHead = this.cgformHeadMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead != null && (list = list(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId()).eq(OnlCgformField::getMainTable, head.getTableName()))) != null && !list.isEmpty()) {
                        OnlCgformField onlCgformField = (OnlCgformField) list.get(0);
                        HashMap<String,String> hashMap = new HashMap<>(5);
                        hashMap.put("linkField", onlCgformField.getDbFieldName());
                        hashMap.put("mainField", onlCgformField.getMainField());
                        hashMap.put("tableName", str);
                        hashMap.put("linkValueStr", "");
                        arrayList.add(hashMap);
                    }
                }
                LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, id);
                List<OnlCgformField> list2 = list(lambdaQueryWrapper);
                for (String str2 : ids.split(CgformUtil.COMMA_SEPARATOR)) {
                    if (str2.indexOf("@") > 0) {
                        str2 = str2.substring(0, str2.indexOf("@"));
                    }
                    Map<String, Object> map = m314a(tableName, CgformUtil.m198a(list2, str2), JSONObject.class);

                    for (Map<String,String> map2 : arrayList) {
                        Object obj = map.get(map2.get("mainField").toLowerCase());
                        if (obj == null) {
                            obj = map.get(map2.get("mainField").toUpperCase());
                        }
                        if (obj != null) {
                            map2.put("linkValueStr", map2.get("linkValueStr") + obj + ",");
                        }
                    }
                }
                for (Map<String,String> map3 : arrayList) {
                    deleteAutoList(map3.get("tableName"), map3.get("linkField"), map3.get("linkValueStr"));
                }
            }
            deleteAutoListById(head.getTableName(), ids);
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void deleteAutoListById(String tbname, String ids) {
        deleteAutoList(tbname, "id", ids);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void deleteAutoList(String tbname, String linkField, String linkValue) {
        if (linkValue != null && !linkValue.isEmpty()) {
            String[] split = linkValue.split(CgformUtil.COMMA_SEPARATOR);
            ArrayList<Object> arrayList = new ArrayList<>();
            for (String str : split) {
                if (str != null && !str.isEmpty()) {
                    if (str.indexOf("@") > 0) {
                        str = str.substring(0, str.indexOf("@"));
                    }
                    arrayList.add(str);
                }
            }
            QueryWrapper<?> queryWrapper = new QueryWrapper<>();
            queryWrapper.in(SqlInjectionUtil.getSqlInjectField(linkField), arrayList.toArray());
            m318a(CgformUtil.m235f(tbname), queryWrapper);
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<Map<String, String>> getAutoListQueryInfo(String code) {
        String subTableStr;
        OnlCgformHead onlCgformHead = this.cgformHeadMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getId, code));
        List<Map<String, String>> arrayList = new ArrayList<>();
        boolean m181a = CgformUtil.m181a(onlCgformHead);
        int m306a = m306a(onlCgformHead, arrayList, 0, m181a);
        Integer tableType = onlCgformHead.getTableType();
        if (m181a && tableType != null && 2 == tableType && (subTableStr = onlCgformHead.getSubTableStr()) != null && !subTableStr.isEmpty()) {
            for (String str : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                OnlCgformHead onlCgformHead2 = this.cgformHeadMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
                if (onlCgformHead2 != null) {
                    m306a = m306a(onlCgformHead2, arrayList, m306a, true);
                }
            }
        }
        return arrayList;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<OnlCgformField> queryFormFields(String code, boolean isform) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, code);
        if (isform) {
            lambdaQueryWrapper.eq(OnlCgformField::getIsShowForm, 1);
        }
        return list(lambdaQueryWrapper);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<OnlCgformField> queryFormFieldsByTableName(String tableName) {
        OnlCgformHead onlCgformHead = this.cgformHeadMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, tableName));
        if (onlCgformHead != null) {
            LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId());
            return list(lambdaQueryWrapper);
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public OnlCgformField queryFormFieldByTableNameAndField(String tableName, String fieldName) {
        OnlCgformHead onlCgformHead = this.cgformHeadMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, tableName));
        if (onlCgformHead != null) {
            LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId());
            lambdaQueryWrapper.eq(OnlCgformField::getDbFieldName, fieldName);
            if (list(lambdaQueryWrapper) != null && !list(lambdaQueryWrapper).isEmpty()) {
                return list(lambdaQueryWrapper).get(0);
            }
            return null;
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public Map<String, Object> queryFormData(List<OnlCgformField> fieldList, String tbname, String id) {
        return  m314a(tbname, CgformUtil.m198a(fieldList, id), JSONObject.class);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public Map<String, Object> generateMockData(String tableName) {
        List<OnlCgformField> queryFormFieldsByTableName = queryFormFieldsByTableName(tableName);
        Map<String, Object> hashMap = new HashMap<>();
        if (queryFormFieldsByTableName == null || queryFormFieldsByTableName.isEmpty()) {
            return hashMap;
        }
        for (OnlCgformField onlCgformField : queryFormFieldsByTableName) {
            hashMap.put(onlCgformField.getDbFieldName(), "");
        }
        return hashMap;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<Map<String, Object>> querySubFormData(List<OnlCgformField> fieldList, String tbname, String linkField, String value) {
        Collection collection = m314a(tbname, CgformUtil.m199a(fieldList, linkField, value), Collection.class);
        return new ArrayList<Map<String, Object>>(collection);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<String> selectOnlineHideColumns(String tbname) {
        String id = ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId();
        return m300a(this.baseMapper.selectOnlineHideColumns(id, "online:" + tbname + ":%"));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<OnlCgformField> queryAvailableFields(String cgFormId, String tbname, String taskId, boolean isList) {
        List<String> selectFlowAuthColumns;
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, cgFormId);
        if (isList) {
            lambdaQueryWrapper.eq(OnlCgformField::getIsShowList, 1);
        } else {
            lambdaQueryWrapper.eq(OnlCgformField::getIsShowForm, 1);
        }
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list = list(lambdaQueryWrapper);
        String str = "online:" + tbname + "%";
        String id = ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId();
        List<String> arrayList = new ArrayList<>();
        if (StrUtils.isEmpty(taskId)) {
            List<String> queryHideCode = this.onlAuthPageService.queryHideCode(id, cgFormId, isList);
            if (queryHideCode != null && !queryHideCode.isEmpty() && queryHideCode.get(0) != null) {
                arrayList.addAll(queryHideCode);
            }
        } else if (StrUtils.isNotEmpty(taskId) && (selectFlowAuthColumns = this.baseMapper.selectFlowAuthColumns(tbname, taskId, "1")) != null && !selectFlowAuthColumns.isEmpty() && selectFlowAuthColumns.get(0) != null) {
            arrayList.addAll(selectFlowAuthColumns);
        }
        if (arrayList.isEmpty()) {
            return list;
        }
        ArrayList<OnlCgformField> arrayList2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            OnlCgformField onlCgformField = list.get(i);
            if (m302b(onlCgformField.getDbFieldName(), arrayList)) {
                arrayList2.add(onlCgformField);
            }
        }
        return arrayList2;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<String> queryDisabledFields(String tbname) {
        String id = ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId();
        return m300a((this.baseMapper).selectOnlineDisabledColumns(id, "online:" + tbname + "%"));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<String> queryDisabledFields(String tbname, String taskId) {
        if (StrUtils.isEmpty(taskId)) {
            return null;
        }
        return m300a(( this.baseMapper).selectFlowAuthColumns(tbname, taskId, "2"));
    }

    /* renamed from: a */
    private List<String> m300a(List<String> list) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (list == null || list.isEmpty() || list.get(0) == null) {
            return arrayList;
        }
        for (String str : list) {
            if (!StrUtils.isEmpty(str)) {
                String substring = str.substring(str.lastIndexOf(":") + 1);
                if (!StrUtils.isEmpty(substring)) {
                    arrayList.add(substring);
                }
            }
        }
        return arrayList;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<OnlCgformField> queryAvailableFields(String tbname, boolean isList, List<OnlCgformField> List, List<String> needList) {
        String id = ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId();
        return m301a(this.baseMapper.selectOnlineHideColumns(id, "online:" + tbname + "%"), isList, List, needList);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<OnlCgformField> queryAvailableFields(String cgformId, String tbname, boolean isList, List<OnlCgformField> List, List<String> needList) {
        return m301a(this.onlAuthPageService.queryListHideColumn(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId(), cgformId), isList, List, needList);
    }

    private java.util.List<OnlCgformField> m301a(java.util.List<java.lang.String> var1, boolean var2, java.util.List<OnlCgformField> var3, java.util.List<String> var4) {
       ArrayList<OnlCgformField> var5 = new ArrayList<>();
        boolean var6 = true;
        if (var1 == null || var1.isEmpty() || var1.get(0) == null) {
            var6 = false;
        }

        for (OnlCgformField var8 : var3) {
            String var9 = var8.getDbFieldName();
            if (var4 != null && var4.contains(var9)) {
                var8.setIsQuery(1);
                var5.add(var8);
            } else {
                if (var2) {
                    if (var8.getIsShowList() != 1) {
                        if (StrUtils.isNotEmpty(var8.getMainTable()) && StrUtils.isNotEmpty(var8.getMainField())) {
                            var5.add(var8);
                        }
                        continue;
                    }
                } else if (var8.getIsShowForm() != 1) {
                    continue;
                }

                if (var6) {
                    if (this.m302b(var9, var1)) {
                        var5.add(var8);
                    }
                } else {
                    var5.add(var8);
                }
            }
        }

        return var5;
    }

    /* renamed from: b */
    private boolean m302b(String str, List<String> list) {
        boolean z = true;
        for (int i = 0; i < list.size(); i++) {
            String str2 = list.get(i);
            if (!StrUtils.isEmpty(str2)) {
                String substring = str2.substring(str2.lastIndexOf(":") + 1);
                if (!StrUtils.isEmpty(substring) && substring.equals(str)) {
                    z = false;
                }
            }
        }
        return z;
    }

    /* renamed from: a */
    public boolean m303a(String str, List<OnlCgformField> list) {
        boolean z = false;
        Iterator<OnlCgformField> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (StrUtils.camelToUnderline(str).equals(it.next().getDbFieldName())) {
                z = true;
                break;
            }
        }
        return z;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void executeInsertSQL(Map<String, Object> params) {
        this.baseMapper.executeInsertSQL(params);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void executeUpdatetSQL(Map<String, Object> params) {
        this.baseMapper.executeUpdatetSQL(params);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<TreeModel> queryDataListByLinkDown(LinkDown linkDown) {
        linkDown.setTable(SqlInjectionUtil.getSqlInjectTableName(linkDown.getTable()));
        linkDown.setKey(SqlInjectionUtil.getSqlInjectField(linkDown.getKey()));
        linkDown.setTxt(SqlInjectionUtil.getSqlInjectField(linkDown.getTxt()));
        linkDown.setIdField(SqlInjectionUtil.getSqlInjectField(linkDown.getIdField()));
        linkDown.setPidField(SqlInjectionUtil.getSqlInjectField(linkDown.getPidField()));
        if (StrUtils.isNotEmpty(linkDown.getLinkField())) {
            linkDown.setLinkField(SqlInjectionUtil.getSqlInjectField(new String[0]));
        }
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(linkDown.getKey() + " as store", linkDown.getTxt() + " as label", linkDown.getIdField() + " as id", linkDown.getPidField() + " as pid");
        if (StrUtils.isNotEmpty(linkDown.getPidValue())) {
            queryWrapper.eq(linkDown.getPidField(), linkDown.getPidValue());
        } else if (StrUtils.isNotEmpty(linkDown.getCondition())) {
            SqlInjectionUtil.filterContent(linkDown.getCondition());
            queryWrapper.apply(linkDown.getCondition());
        }

        List<JSONObject> list = m314a(linkDown.getTable(), queryWrapper, List.class);
        return list.stream().map(jSONObject -> jSONObject.toJavaObject(TreeModel.class)).collect(Collectors.toList());
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void updateTreeNodeNoChild(String tableName, String filed, String id) {
        this.baseMapper.executeUpdatetSQL(CgformUtil.m233a(tableName, filed, id));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public String queryTreeChildIds(OnlCgformHead head, String ids) {
        String treeParentIdField = head.getTreeParentIdField();
        String tableName = head.getTableName();
        String[] split = ids.split(CgformUtil.COMMA_SEPARATOR);
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : split) {
            if (str != null && !stringBuffer.toString().contains(str)) {
                if (!stringBuffer.toString().isEmpty()) {
                    stringBuffer.append(CgformUtil.COMMA_SEPARATOR);
                }
                stringBuffer.append(str);
                m304a(str, treeParentIdField, tableName, stringBuffer);
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public String queryTreePids(OnlCgformHead head, String ids) {
        String treeParentIdField = head.getTreeParentIdField();
        String tableName = head.getTableName();
        StringBuffer stringBuffer = new StringBuffer();
        String[] split = ids.split(CgformUtil.COMMA_SEPARATOR);
        for (String str : split) {
            if (str != null) {
                String m235f = CgformUtil.m235f(tableName);
                QueryWrapper<?> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", str);
                String obj = CgformUtil.m224a(m315a(m235f, null, queryWrapper, JSONObject.class)).get(treeParentIdField).toString();
                List<Map<String, Object>> queryListBySql = queryListBySql(m235f, null, treeParentIdField, obj, "'" + String.join("','", split) + "'");
                if ((queryListBySql == null || queryListBySql.isEmpty()) && !Arrays.asList(split).contains(obj) && !stringBuffer.toString().contains(obj)) {
                    stringBuffer.append(obj).append(CgformUtil.COMMA_SEPARATOR);
                }
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public String queryForeignKey(String cgFormId, String mainTable) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, cgFormId);
        lambdaQueryWrapper.eq(OnlCgformField::getMainTable, mainTable);
        List<OnlCgformField> list = list(lambdaQueryWrapper);
        if (list != null && !list.isEmpty()) {
            return list.get(0).getMainField();
        }
        return null;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public List<Map<String, Object>> queryListBySql(String tableName, String fields, String pidField, String metaPid, String inIds) {
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        if (StrUtils.isNotEmpty(pidField)) {
            queryWrapper.eq(SqlInjectionUtil.getSqlInjectField(pidField), metaPid);
        }
        if (StrUtils.isNotEmpty(inIds)) {
            queryWrapper.in("id", Arrays.asList(inIds.split(CgformUtil.COMMA_SEPARATOR)));
        }
        Collection<Map<String, Object>> collection = m315a(tableName, fields, queryWrapper, Collection.class);

        return new ArrayList<>(collection);
    }

    /* renamed from: a */
    private StringBuffer m304a(String str, String str2, String str3, StringBuffer stringBuffer) {
        List<Map<String, Object>> queryListBySql = queryListBySql(str3, null, str2, str, null);
        if (queryListBySql != null && !queryListBySql.isEmpty()) {
            for (Map<String, Object> stringObjectMap : queryListBySql) {
                Map<String, Object> m224a = CgformUtil.m224a(stringObjectMap);
                if (!stringBuffer.toString().contains(m224a.get("id").toString())) {
                    stringBuffer.append(CgformUtil.COMMA_SEPARATOR).append(m224a.get("id"));
                }
                m304a(m224a.get("id").toString(), str2, str3, stringBuffer);
            }
        }
        return stringBuffer;
    }

    /* renamed from: a */
    private String m305a(List<OnlCgformField> list, Map<String, Object> map) {
        String string;
        Object obj = map.get("column");
        ArrayList<SqlOrder> arrayList = new ArrayList<>();
        if (obj != null && !"id".equals(obj.toString())) {
            String obj2 = obj.toString();
            Object obj3 = map.get("order");
            String str = "desc";
            if (obj3 != null) {
                str = obj3.toString();
            }
            arrayList.add(new SqlOrder(obj2, str));
        } else {
            for (OnlCgformField onlCgformField : list) {
                if ("1".equals(onlCgformField.getSortFlag())) {
                    String fieldExtendJson = onlCgformField.getFieldExtendJson();
                    SqlOrder sqlOrder = new SqlOrder(onlCgformField.getDbFieldName());
                    if (fieldExtendJson != null && !fieldExtendJson.isEmpty() && (string = JSON.parseObject(fieldExtendJson).getString(ExtendJsonKey.ORDER_RULE)) != null && !"".equals(string)) {
                        sqlOrder.setRule(string);
                        arrayList.add(sqlOrder);
                    }
                }
            }
            if (arrayList.isEmpty()) {
                arrayList.add(SqlOrder.m295a());
            }
        }
        List<String> arrayList2 = new ArrayList<>();
        for (SqlOrder sqlOrder2 : arrayList) {
            if (m303a(sqlOrder2.getColumn(), list)) {
                arrayList2.add(sqlOrder2.getRealSql());
            }
        }
        return " ORDER BY " + String.join(CgformUtil.COMMA_SEPARATOR, arrayList2);
    }

    /* renamed from: a */
    private int m306a(OnlCgformHead onlCgformHead, List<Map<String, String>> list, int i, boolean z) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId());
        lambdaQueryWrapper.eq(OnlCgformField::getIsQuery, 1);
        lambdaQueryWrapper.eq(OnlCgformField::getDbIsPersist, OnlineConst.isPersist);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        for (OnlCgformField onlCgformField : list(lambdaQueryWrapper)) {
            HashMap<String,String> hashMap = new HashMap<>(5);
            hashMap.put("label", onlCgformField.getDbFieldTxt());
            if (z) {
                hashMap.put("field", onlCgformHead.getTableName() + "@" + onlCgformField.getDbFieldName());
            } else {
                hashMap.put("field", onlCgformField.getDbFieldName());
            }
            hashMap.put("dbField", onlCgformField.getDbFieldName());
            hashMap.put("mode", onlCgformField.getQueryMode());
            if (StrUtils.isNotEmpty(onlCgformField.getFieldExtendJson())) {
                hashMap.put("fieldExtendJson", onlCgformField.getFieldExtendJson());
            }
            if ("1".equals(onlCgformField.getQueryConfigFlag())) {
                String queryShowType = onlCgformField.getQueryShowType();
                hashMap.put("config", "1");
                hashMap.put(CgformUtil.VIEW, queryShowType);
                hashMap.put("defValue", onlCgformField.getQueryDefVal());
                if (CgformUtil.f223R.equals(queryShowType)) {
                    hashMap.put("pcode", onlCgformField.getQueryDictField());
                } else if (CgformUtil.f222Q.equals(queryShowType)) {
                    String[] split = onlCgformField.getQueryDictText().split(CgformUtil.COMMA_SEPARATOR);
                    hashMap.put("dict", onlCgformField.getQueryDictTable() + "," + split[2] + "," + split[0]);
                    hashMap.put("pidField", split[1]);
                    hashMap.put("hasChildField", split[3]);
                    hashMap.put("pidValue", onlCgformField.getQueryDictField());
                } else {
                    hashMap.put("dictTable", onlCgformField.getQueryDictTable());
                    hashMap.put("dictCode", onlCgformField.getQueryDictField());
                    hashMap.put("dictText", onlCgformField.getQueryDictText());
                }
            } else {
                String fieldShowType = onlCgformField.getFieldShowType();
                hashMap.put(CgformUtil.VIEW, fieldShowType);
                hashMap.put("mode", onlCgformField.getQueryMode());
                if (CgformUtil.f223R.equals(fieldShowType)) {
                    hashMap.put("pcode", onlCgformField.getDictField());
                } else if (CgformUtil.f222Q.equals(fieldShowType)) {
                    String[] split2 = onlCgformField.getDictText().split(CgformUtil.COMMA_SEPARATOR);
                    hashMap.put("dict", onlCgformField.getDictTable() + "," + split2[2] + "," + split2[0]);
                    hashMap.put("pidField", split2[1]);
                    hashMap.put("hasChildField", split2[3]);
                    hashMap.put("pidValue", onlCgformField.getDictField());
                } else {
                    hashMap.put("dictTable", onlCgformField.getDictTable());
                    hashMap.put("dictCode", onlCgformField.getDictField());
                    hashMap.put("dictText", onlCgformField.getDictText());
                }
            }
            i++;
            if (i > 2) {
                hashMap.put("hidden", "1");
            }
            list.add(hashMap);
        }
        return i;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public void clearCacheOnlineConfig() {
        //清除缓存
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public OnlQueryModel getQueryInfo(OnlCgformHead head, Map<String, Object> params, List<String> needList) {
        List<OnlCgformField> queryAvailableFields;
        String sqlInjectTableName = SqlInjectionUtil.getSqlInjectTableName(head.getTableName());
        String id = head.getId();
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, id);
        lambdaQueryWrapper.eq(OnlCgformField::getDbIsPersist, OnlineConst.isPersist);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list = list(lambdaQueryWrapper);

        List<String> selectFieldList = head.getSelectFieldList();
        if (selectFieldList != null && !selectFieldList.isEmpty()) {
            queryAvailableFields = m312a(id, list, selectFieldList, needList);
        } else {
            queryAvailableFields = queryAvailableFields(id, sqlInjectTableName, true, list, needList);
        }
        StringBuffer stringBuffer = new StringBuffer();
        CgformUtil.m182a(sqlInjectTableName, queryAvailableFields, stringBuffer);
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<SysPermissionDataRuleModel> queryUserOnlineAuthData = this.onlAuthDataService.queryUserOnlineAuthData(loginUser.getId(), id);
        if (queryUserOnlineAuthData != null && !queryUserOnlineAuthData.isEmpty()) {
            JeecgDataAutorUtils.installUserInfo(this.sysBaseAPI.getCacheUser(loginUser.getUsername()));
        }
        ConditionHandler conditionHandler = new ConditionHandler("t.");
        conditionHandler.setTableName(sqlInjectTableName);
        conditionHandler.setNeedList(needList);
        conditionHandler.setSubTableStr(head.getSubTableStr());
        String m7a = conditionHandler.m7a(CgformUtil.m252g((List<OnlCgformField>) list), params, queryUserOnlineAuthData);
        Map<String, Object> sqlParams = conditionHandler.getSqlParams();
        if (!m7a.trim().isEmpty()) {
            stringBuffer.append(" t ").append(CgformUtil.f188i).append(m7a);
        }
        stringBuffer.append(m305a((List<OnlCgformField>) list, params));
        OnlQueryModel onlQueryModel = new OnlQueryModel(stringBuffer.toString(), sqlParams);
        onlQueryModel.setFieldList(queryAvailableFields);
        return onlQueryModel;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void addOnlineInsertDataLog(String tableName, String dataId) {
        threadPoolExecutor.execute(() -> {
            this.sysBaseAPI.saveDataLog(new DataLogDTO(tableName, dataId, " 创建了记录", "comment"));
        });
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void addOnlineUpdateDataLog(String tableName, String dataId, List<OnlCgformField> fieldList, JSONObject json) {
        String m235f = CgformUtil.m235f(tableName);
        LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(OnlCgformHead::getTableType, OnlCgformHead::getTableTxt, OnlCgformHead::getSubTableStr);
        lambdaQueryWrapper.eq(OnlCgformHead::getTableName, m235f);
        OnlCgformHead onlCgformHead = (OnlCgformHead) this.cgformHeadMapper.selectOne(lambdaQueryWrapper);
        if (onlCgformHead == null) {
            return;
        }
        HashSet<String> hashSet = new HashSet<>();
        if (StrUtils.isNotEmpty(onlCgformHead.getSubTableStr())) {
            hashSet.addAll(Arrays.stream(onlCgformHead.getSubTableStr().split(CgformUtil.COMMA_SEPARATOR)).collect(Collectors.toSet()));
        }
        Map<String, Object> m309a = m309a(m235f, dataId);
        if (m309a != null) {
            StringBuilder sb = new StringBuilder();
            if (!hashSet.isEmpty()) {
                for (String s : hashSet) {
                    String m235f2 = CgformUtil.m235f(s);
                    String m308a = m308a(m235f2, json.getJSONArray(m235f2), m235f, dataId);
                    if (StrUtils.isNotEmpty(m308a)) {
                        sb.append(m308a).append("；");
                    }
                }
            }
            threadPoolExecutor.execute(() -> {
                DataLogDTO dataLogDTO = new DataLogDTO(m235f, dataId, "comment");
                StringBuilder sb2 = new StringBuilder(m307a((List<OnlCgformField>) fieldList, json, (Map<String, Object>) m309a));
                if (!sb.isEmpty()) {
                    sb2.append("；").append(sb);
                }
                String str = Arrays.stream(sb2.toString().split("；")).filter(str2 -> StrUtils.isNotEmpty(str2.trim())).collect(Collectors.joining("；"));
                if (StrUtils.isNotEmpty(str)) {
                    dataLogDTO.setContent(str);
                    this.sysBaseAPI.saveDataLog(dataLogDTO);
                }
            });
        }
    }

    /* renamed from: a */
    private String m307a(List<OnlCgformField> list, JSONObject jSONObject, Map<String, Object> map) {
        String str;
        LocalDateTime localDateTime;
        StringBuilder stringBuffer = new StringBuilder();
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (OnlCgformField onlCgformField : list) {
            String dbFieldName = onlCgformField.getDbFieldName();
            if (null != dbFieldName && !"id".equalsIgnoreCase(dbFieldName) && !CgformUtil.f206A.equalsIgnoreCase(dbFieldName) && !CgformUtil.f203x.equalsIgnoreCase(dbFieldName) && !CgformUtil.f205z.equalsIgnoreCase(dbFieldName) && !CgformUtil.f202w.equalsIgnoreCase(dbFieldName) && !CgformUtil.f204y.equalsIgnoreCase(dbFieldName)) {
                String dbType = onlCgformField.getDbType();
                if (!"blob".equalsIgnoreCase(dbType) && !ExtendJsonKey.TEXT.equalsIgnoreCase(dbType) && (!StrUtils.isEmpty(jSONObject.get(dbFieldName)) || !StrUtils.isEmpty(map.get(dbFieldName)))) {
                    String str2 = "空";
                    if (jSONObject.get(dbFieldName) == null) {
                        str = "空";
                    } else {
                        String obj = jSONObject.get(dbFieldName).toString();
                        if (obj.isEmpty()) {
                            str = "空";
                        } else {
                            str = obj;
                        }
                    }
                    if ("Datetime".equalsIgnoreCase(dbType)) {
                        if (map.get(dbFieldName) != null) {
                            Object obj2 = map.get(dbFieldName);
                            if (obj2 instanceof Timestamp) {
                                localDateTime = LocalDateTime.ofInstant(((Timestamp) obj2).toInstant(), ZoneId.systemDefault());
                            } else {
                                localDateTime = (LocalDateTime) map.get(dbFieldName);
                            }
                            str2 = ofPattern.format(localDateTime);
                        }
                    } else if ("BigDecimal".equalsIgnoreCase(dbType) || "double".equalsIgnoreCase(dbType)) {
                        Integer dbPointLength = onlCgformField.getDbPointLength();
                        if (map.get(dbFieldName) != null) {
                            str2 = map.get(dbFieldName).toString();
                        }
                        if (jSONObject.get(dbFieldName) == null || jSONObject.get(dbFieldName).toString().isEmpty()) {
                            str = "空";
                        } else {
                            String obj3 = jSONObject.get(dbFieldName).toString();
                            if (!obj3.contains(".")) {
                                obj3 = obj3 + ".";
                            }
                            if ((obj3.length() - 1) - obj3.indexOf(".") < dbPointLength) {
                                do {
                                    obj3 = obj3 + "0";
                                } while ((obj3.length() - 1) - obj3.indexOf(".") < dbPointLength);
                            }
                            str = obj3;
                        }
                    } else if (map.get(dbFieldName) != null) {
                        str2 = map.get(dbFieldName).toString();
                        if (str2.isEmpty()) {
                            str2 = "空";
                        }
                    }
                    if (!str2.equals(str) && (!"double".equalsIgnoreCase(dbType) || map.get(dbFieldName) == null || jSONObject.get(dbFieldName) == null || new BigDecimal(map.get(dbFieldName).toString()).compareTo(new BigDecimal(jSONObject.get(dbFieldName).toString())) != 0)) {
                        stringBuffer.append("  将名称为【" + onlCgformField.getDbFieldTxt() + "】的字段内容 " + str2 + " 修改为 " + str + "；  ");
                    }
                }
            }
        }
        return stringBuffer.toString();
    }

    /* renamed from: a */
    private String m308a(String str, JSONArray jSONArray, String str2, String str3) {
        OnlCgformField onlCgformField;
        if (jSONArray == null) {
            return "";
        }
        LambdaQueryWrapper<OnlCgformHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(OnlCgformHead::getId, OnlCgformHead::getTableTxt, OnlCgformHead::getRelationType);
        lambdaQueryWrapper.eq(OnlCgformHead::getTableName, str);
        OnlCgformHead onlCgformHead = this.cgformHeadMapper.selectOne(lambdaQueryWrapper);
        if (onlCgformHead == null) {
            return "";
        }
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId());
        List<OnlCgformField> list = list(lambdaQueryWrapper2);
        if (list == null || list.size() == 0 || (onlCgformField = list.stream().filter(onlCgformField2 -> str2.equals(onlCgformField2.getMainTable())).findFirst().orElse(null)) == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(onlCgformField.getDbFieldName(), str3);
        List<Object> list2 = m315a(str, null, queryWrapper, List.class);
        if (list2 == null || list2.isEmpty()) {
            if (jSONArray.isEmpty()) {
                return "";
            }
            sb.append("新增了").append(jSONArray.size()).append("条数据，");
        } else if (jSONArray.isEmpty()) {
            sb.append("删除了").append(list2.size()).append("条数据，");
        } else {
            if (CgformConstant.f337e.equals(onlCgformHead.getRelationType())) {
                String m307a = m307a(list.stream().filter(onlCgformField3 -> onlCgformField3 != onlCgformField).collect(Collectors.toList()), jSONArray.getJSONObject(0), ((JSONObject) list2.get(0)).getInnerMap());
                if (StrUtils.isEmpty(m307a)) {
                    return "";
                }
                sb.append("子表[").append(onlCgformHead.getTableTxt()).append("]：");
                return sb.append(m307a).toString();
            }
            AtomicInteger atomicInteger = new AtomicInteger();
            int i = 0;
            int i2 = 0;
            Set<String> set = list2.stream().map(obj -> ((JSONObject) obj).getString("id")).collect(Collectors.toSet());
            HashSet<String> hashSet = new HashSet<>();
            for (int i3 = 0; i3 < jSONArray.size(); i3++) {
                String string = jSONArray.getJSONObject(i3).getString("id");
                if (StrUtils.isEmpty(string)) {
                    atomicInteger.getAndIncrement();
                } else {
                    hashSet.add(string);
                }
            }
            HashSet<String> hashSet2 = new HashSet<>();
            hashSet2.addAll(set);
            hashSet2.addAll(hashSet);
            hashSet2.removeIf(oConvertUtils::isEmpty);
            for (String str4 : hashSet2) {
                if (set.contains(str4) && !hashSet.contains(str4)) {
                    i2++;
                } else {
                    JSONObject jSONObject = null;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= jSONArray.size()) {
                            break;
                        }
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i4);
                        if (!str4.equals(jSONObject2.getString("id"))) {
                            i4++;
                        } else {
                            jSONObject = jSONObject2;
                            break;
                        }
                    }
                    JSONObject jSONObject3 = (JSONObject) list2.stream().filter(obj2 -> {
                        return str4.equals(((JSONObject) obj2).getString("id"));
                    }).findFirst().orElse(null);
                    if (jSONObject != null && jSONObject3 != null) {
                        Iterator<OnlCgformField> it = list.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            OnlCgformField onlCgformField4 = it.next();
                            if (onlCgformField4 != onlCgformField) {
                                String dbFieldName = onlCgformField4.getDbFieldName();
                                if (!"id".equalsIgnoreCase(dbFieldName) && !CgformUtil.f206A.equalsIgnoreCase(dbFieldName) && !CgformUtil.f203x.equalsIgnoreCase(dbFieldName) && !CgformUtil.f205z.equalsIgnoreCase(dbFieldName) && !CgformUtil.f202w.equalsIgnoreCase(dbFieldName) && !CgformUtil.f204y.equalsIgnoreCase(dbFieldName)) {
                                    String string2 = jSONObject3.getString(dbFieldName);
                                    String string3 = jSONObject.getString(dbFieldName);
                                    if (StrUtils.isNotEmpty(string2) && StrUtils.isNotEmpty(string3) && !string2.equals(string3)) {
                                        i++;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (atomicInteger.get() > 0) {
                sb.append("新增了").append(atomicInteger.get()).append("条数据，");
            }
            if (i > 0) {
                sb.append("修改了").append(i).append("条数据，");
            }
            if (i2 > 0) {
                sb.append("删除了").append(i2).append("条数据，");
            }
        }
        if (!sb.isEmpty()) {
            sb.insert(0, "]：").insert(0, onlCgformHead.getTableTxt()).insert(0, "子表[");
        }
        String sb2 = sb.toString();
        if (sb2.endsWith("，")) {
            sb2 = sb2.substring(0, sb2.length() - 1);
        }
        return sb2;
    }

    /* renamed from: a */
    private Map<String, Object> m309a(String str, String str2) {
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", str2);
        return m315a(str,  null, queryWrapper, JSONObject.class);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    @Cacheable(value = {OnlineConst.CACHE_ONLINE_LINK_TABLE}, key = "#table+#textString+#code")
    public List<Map<String, Object>> queryLinkTableDictList(String table, String textString, String code) {
        return queryListBySql(table, textString + "," + code, null, null, null);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformFieldService
    public void handleLinkTableDictData(String headId, List<Map<String, Object>> dataList) {
        List<OnlCgformField> selectList;
        if (dataList != null && !dataList.isEmpty() && (selectList = this.onlCgformFieldMapper.selectList(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, headId).in(OnlCgformField::getFieldShowType, new String[]{CgformUtil.f239ah, CgformUtil.f238ag}))) != null && selectList.size() > 0) {
            HashMap<String,List<String>> hashMap = new HashMap<>();
            for (OnlCgformField onlCgformField : selectList) {
                if (CgformUtil.f239ah.equals(onlCgformField.getFieldShowType())) {
                    String dictTable = onlCgformField.getDictTable();
                    List<String> list =  hashMap.get(dictTable);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(onlCgformField.getDbFieldName() + "," + onlCgformField.getDictText());
                    hashMap.put(dictTable, list);
                }
            }
            for (OnlCgformField onlCgformField2 : selectList) {
                if (CgformUtil.f238ag.equals(onlCgformField2.getFieldShowType())) {
                    String dictTable2 = onlCgformField2.getDictTable();
                    String dictText = onlCgformField2.getDictText();
                    String dictField = onlCgformField2.getDictField();
                    Map<String, List<DictModel>> m311b = m311b(m310a(dictTable2, dictText, dictField));
                    //这里会忽略缓存
                    List<Map<String, Object>> queryLinkTableDictList = queryLinkTableDictList(dictTable2, dictText, dictField);
                    String lowerCase = onlCgformField2.getDbFieldName().toLowerCase();
                    String str = dictText.split(CgformUtil.COMMA_SEPARATOR)[0];
                    for (Map<String, Object> map : dataList) {
                        Object obj = map.get(lowerCase);
                        if (obj != null && !"".equals(obj.toString())) {
                            map.put(lowerCase + "_dictText", m313a(m311b, queryLinkTableDictList, str, dictField, obj));
                            List<String> list2 =  hashMap.get(lowerCase);
                            if (list2 != null && !list2.isEmpty()) {
                                for (String s : list2) {
                                    String[] split = s.split(CgformUtil.COMMA_SEPARATOR);
                                    map.put(split[0].toLowerCase(), m313a(m311b, queryLinkTableDictList, split[1], dictField, obj));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* renamed from: a */
    private List<OnlCgformField> m310a(String str, String str2, String str3) {
        OnlCgformHead onlCgformHead = this.cgformHeadMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
        if (onlCgformHead == null) {
            throw exception("实体未找到");
        }
        if (StrUtils.isEmpty(str2) || StrUtils.isEmpty(str3)) {
            throw exception("关联记录字典参数不正确");
        }
        String[] split = str2.split(CgformUtil.COMMA_SEPARATOR);
        List<String> arrayList = new ArrayList<>();
        arrayList.add(str3);
        arrayList.addAll(Arrays.asList(split));
        return this.onlCgformFieldMapper.selectList(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId()).in(OnlCgformField::getDbFieldName, arrayList));
    }

    /* renamed from: b */
    private Map<String, List<DictModel>> m311b(List<OnlCgformField> list) {
        HashMap<String,List<DictModel>> hashMap = new HashMap<>();
        for (OnlCgformField onlCgformField : list) {
            String dictTable = onlCgformField.getDictTable();
            String dictText = onlCgformField.getDictText();
            String dictField = onlCgformField.getDictField();
            if (CgformUtil.m185c(onlCgformField.getFieldShowType())) {
                if (StrUtils.isNotEmpty(dictTable) && StrUtils.isNotEmpty(dictText) && StrUtils.isNotEmpty(dictField)) {
                    hashMap.put(onlCgformField.getDbFieldName(), this.sysBaseAPI.queryTableDictItemsByCode(dictTable, dictText, dictField));
                } else if (StrUtils.isNotEmpty(dictField)) {
                    hashMap.put(onlCgformField.getDbFieldName(), this.sysBaseAPI.queryDictItemsByCode(dictField));
                }
            }
        }
        return hashMap;
    }

    /* renamed from: a */
    private List<OnlCgformField> m312a(String str, List<OnlCgformField> list, List<String> list2, List<String> list3) {
        String id = ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId();
        List<OnlCgformField> arrayList = new ArrayList<>();
        for (OnlCgformField onlCgformField : list) {
            if (list2.contains(onlCgformField.getDbFieldName())) {
                arrayList.add(onlCgformField);
            }
        }
        return m301a(this.onlAuthPageService.queryListHideColumn(id, str), true, arrayList, list3);
    }

    /* renamed from: a */
    private String m313a(Map<String, List<DictModel>> map, List<Map<String, Object>> list, String str, String str2, Object obj) {
        List<DictModel> list2;
        String obj2 = obj.toString();
        List<String> arrayList = new ArrayList<>();
        for (String str3 : obj2.split(CgformUtil.COMMA_SEPARATOR)) {
            String str4 = "";
            for (Map<String, Object> map2 : list) {
                if (str3.equals(CgformUtil.m253a(map2, str2))) {
                    str4 = CgformUtil.m253a(map2, str);
                    if (str4 != null && (list2 = map.get(str)) != null && list2.size() > 0) {
                        for (DictModel dictModel : list2) {
                            if (dictModel.getValue().equals(str4)) {
                                str4 = dictModel.getText();
                            }
                        }
                    }
                }
            }
            if (StrUtils.isNotEmpty(str4)) {
                arrayList.add(str4);
            }
        }
        if (!arrayList.isEmpty()) {
            return String.join(CgformUtil.COMMA_SEPARATOR, arrayList);
        }
        return "";
    }

    /* renamed from: a */
    public <T> T m314a(String str, QueryWrapper<?> queryWrapper, Class<T> cls) {
        String sqlInjectTableName = SqlInjectionUtil.getSqlInjectTableName(str);
        if (cls == JSONObject.class) {
            return (T) this.baseMapper.doSelect(sqlInjectTableName, queryWrapper);
        }
        return (T) this.baseMapper.doSelectList(sqlInjectTableName, queryWrapper);
    }

    /* renamed from: a */
    public <T> T m315a(String str, String str2, QueryWrapper<?> queryWrapper, Class<T> cls) {
        String str3;
        if (StrUtils.isNotEmpty(str2)) {
            str3 = SqlInjectionUtil.getSqlInjectField(str2);
        } else {
            str3 = "*";
        }
        queryWrapper.select(str3);
        return (T) m314a(str, queryWrapper, cls);
    }

    /* renamed from: a */
    private int m316a(String str, JSONObject jSONObject, UpdateWrapper<?> updateWrapper) {
        String sqlInjectTableName = SqlInjectionUtil.getSqlInjectTableName(str);
        for (String s : jSONObject.keySet()) {
            String sqlInjectField = SqlInjectionUtil.getSqlInjectField(s);
            updateWrapper.set(sqlInjectField, jSONObject.get(sqlInjectField));
        }
        return this.baseMapper.doUpdate(sqlInjectTableName, updateWrapper);
    }

    /* renamed from: b */
    private Integer m317b(String str, String str2, String str3) {
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(new String[]{"count(1)"});
        if (StrUtils.isNotEmpty(str2)) {
            queryWrapper.eq(SqlInjectionUtil.getSqlInjectField(str2), str3);
        }
        return (Integer) m314a(str, queryWrapper, Integer.class);
    }

    /* renamed from: a */
    private Integer m318a(String str, QueryWrapper<?> queryWrapper) {
        return this.baseMapper.doDelete(SqlInjectionUtil.getSqlInjectTableName(str), queryWrapper);
    }
}
