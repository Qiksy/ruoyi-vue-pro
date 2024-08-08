package org.jeecg.modules.online.cgform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.system.vo.LoginUser;

import org.jeecg.modules.online.auth.constant.AuthConstants;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlComplexModel;
import org.jeecg.modules.online.cgform.model.OnlForeignKey;
import org.jeecg.modules.online.cgform.model.ScopedSlots;
import org.jeecg.modules.online.cgform.model.TreeSelectColumn;
import org.jeecg.modules.online.cgform.model.HrefSlots;
import org.jeecg.modules.online.cgform.model.OnlColumn;
import org.jeecg.modules.online.cgform.vo.LinkDown;
import org.jeecg.modules.online.cgform.constant.ExtendJsonKey;
import org.jeecg.modules.online.cgform.constant.OnlineConst;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.utils.EnhanceJsUtil;
import org.jeecg.modules.online.cgform.utils.OnlFormShowType;
import org.jeecg.modules.online.cgform.utils.OnlSlotRender;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/* compiled from: OnlineServiceImpl.java */
@Service("onlineService")
/* renamed from: org.jeecg.modules.online.cgform.service.a.i */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/a/i.class */
public class OnlineServiceImpl implements IOnlineService {

    /* renamed from: a */
    private static final Logger f425a = LoggerFactory.getLogger(OnlineServiceImpl.class);

    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;

    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;

    @Autowired
    @Lazy
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private IOnlAuthPageService onlAuthPageService;

    /* renamed from: a */
    // 这里原本有个方法，删掉了

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public OnlComplexModel queryOnlineConfig(OnlCgformHead head, String username) {
        String id = head.getId();
        boolean m181a = CgformUtil.m181a(head);
        List<OnlCgformField> m381b = m381b(id);
        List<String> queryHideCode = this.onlAuthPageService.queryHideCode(id, true);
        List<OnlColumn> arrayList = new ArrayList<>();
        Map<String, List<DictModel>> hashMap = new HashMap<>(5);
        List<HrefSlots> arrayList2 = new ArrayList<>();
        List<OnlForeignKey> arrayList3 = new ArrayList<>();
        List<String> arrayList4 = new ArrayList<>();
        HashMap<String,Integer> hashMap2 = new HashMap<>(5);
        List<String> selectFieldList = head.getSelectFieldList();
        for (OnlCgformField onlCgformField : m381b) {
            String dbFieldName = onlCgformField.getDbFieldName();
            String mainTable = onlCgformField.getMainTable();
            String mainField = onlCgformField.getMainField();
            if (StrUtils.isNotEmpty(mainField) && StrUtils.isNotEmpty(mainTable)) {
                arrayList3.add(new OnlForeignKey(dbFieldName, mainField));
            }
            if (onlCgformField.getIsShowList() != null && 1 == onlCgformField.getIsShowList() && !"id".equals(dbFieldName) && !queryHideCode.contains(dbFieldName) && !arrayList4.contains(dbFieldName) && (selectFieldList == null || selectFieldList.size() <= 0 || selectFieldList.indexOf(dbFieldName) >= 0)) {
                OnlColumn m383a = m383a(onlCgformField, hashMap, arrayList2);
                hashMap2.put(onlCgformField.getDbFieldName(), 1);
                arrayList.add(m383a);
                String linkField = m383a.getLinkField();
                if (linkField != null && !linkField.isEmpty()) {
                    m379a(m381b, arrayList4, arrayList, dbFieldName, linkField);
                }
            }
        }
        m377a(arrayList, arrayList4);
        if (m181a) {
            List<OnlColumn> m384a = m384a(head, hashMap, arrayList2, hashMap2);
            if (!m384a.isEmpty()) {
                ArrayList<String> arrayList5 = new ArrayList<>();
                for (String str : hashMap2.keySet()) {
                    if (hashMap2.get(str) > 1) {
                        arrayList5.add(str);
                    }
                }
                for (OnlColumn onlColumn : m384a) {
                    String dataIndex = onlColumn.getDataIndex();
                    if (arrayList5.contains(dataIndex)) {
                        onlColumn.setDataIndex(onlColumn.getTableName() + "_" + dataIndex);
                    }
                    arrayList.add(onlColumn);
                }
            }
        }
        OnlComplexModel onlComplexModel = new OnlComplexModel();
        onlComplexModel.setCode(id);
        onlComplexModel.setTableType(head.getTableType());
        onlComplexModel.setFormTemplate(head.getFormTemplate());
        onlComplexModel.setDescription(head.getTableTxt());
        onlComplexModel.setCurrentTableName(head.getTableName());
        onlComplexModel.setPaginationFlag(head.getIsPage());
        onlComplexModel.setCheckboxFlag(head.getIsCheckbox());
        onlComplexModel.setScrollFlag(head.getScroll());
        onlComplexModel.setRelationType(head.getRelationType());
        onlComplexModel.setColumns(arrayList);
        onlComplexModel.setDictOptions(hashMap);
        onlComplexModel.setFieldHrefSlots(arrayList2);
        onlComplexModel.setForeignKeys(arrayList3);
        onlComplexModel.setHideColumns(queryHideCode);
        List<OnlCgformButton> queryButtonList = this.onlCgformHeadService.queryButtonList(id, true);
        ArrayList<OnlCgformButton> arrayList6 = new ArrayList<>();
        for (OnlCgformButton onlCgformButton : queryButtonList) {
            if (!queryHideCode.contains(onlCgformButton.getButtonCode())) {
                arrayList6.add(onlCgformButton);
            }
        }
        onlComplexModel.setCgButtonList(arrayList6);
        OnlCgformEnhanceJs queryEnhanceJs = this.onlCgformHeadService.queryEnhanceJs(id, CgformUtil.LIST);
        if (queryEnhanceJs != null && StrUtils.isNotEmpty(queryEnhanceJs.getCgJs())) {
            onlComplexModel.setEnhanceJs(EnhanceJsUtil.m272b(queryEnhanceJs.getCgJs(), queryButtonList));
        }
        if ("Y".equals(head.getIsTree())) {
            onlComplexModel.setPidField(head.getTreeParentIdField());
            onlComplexModel.setHasChildrenField(head.getTreeIdField());
            onlComplexModel.setTextField(head.getTreeFieldname());
        }
        return onlComplexModel;
    }

    /* renamed from: a */
    private void m377a(List<OnlColumn> list, List<String> list2) {
        Iterator<OnlColumn> it = list.iterator();
        while (it.hasNext()) {
            OnlColumn next = it.next();
            String dataIndex = next.getDataIndex();
            if (list2 != null && list2.contains(dataIndex) && StrUtils.isEmpty(next.getCustomRender())) {
                it.remove();
            }
        }
    }

    /* renamed from: a */
    private String[] m378a(String str) {
        String[] strArr = {"", ""};
        if (str != null && !str.isEmpty()) {
            JSONObject parseObject = JSON.parseObject(str);
            if (parseObject.containsKey(ExtendJsonKey.STORE)) {
                strArr[0] = StrUtils.camelToUnderline(parseObject.getString(ExtendJsonKey.STORE));
            }
            if (parseObject.containsKey(ExtendJsonKey.TEXT)) {
                strArr[1] = StrUtils.camelToUnderline(parseObject.getString(ExtendJsonKey.TEXT));
            }
        }
        return strArr;
    }

    /* renamed from: a */
    private void m379a(List<OnlCgformField> list, List<String> list2, List<OnlColumn> list3, String str, String str2) {
        if (StrUtils.isNotEmpty(str2)) {
            for (String str3 : str2.split(CgformUtil.COMMA_SEPARATOR)) {
                Iterator<OnlCgformField> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        OnlCgformField next = it.next();
                        String dbFieldName = next.getDbFieldName();
                        if (1 == next.getIsShowList() && str3.equals(dbFieldName)) {
                            list2.add(str3);
                            OnlColumn onlColumn = new OnlColumn(next.getDbFieldTxt(), dbFieldName);
                            onlColumn.setCustomRender(str);
                            list3.add(onlColumn);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public JSONObject queryOnlineFormObj(OnlCgformHead head, OnlCgformEnhanceJs onlCgformEnhanceJs) {
        JSONObject jSONObject = new JSONObject();
        String id = head.getId();
        String taskId = head.getTaskId();
        List<OnlCgformField> queryAvailableFields = this.onlCgformFieldService.queryAvailableFields(id, head.getTableName(), taskId, false);
        ArrayList<String> arrayList = new ArrayList<>();
        if (StrUtils.isEmpty(taskId)) {
            List<String> queryFormDisabledCode = this.onlAuthPageService.queryFormDisabledCode(head.getId());
            if (queryFormDisabledCode != null && !queryFormDisabledCode.isEmpty() && queryFormDisabledCode.get(0) != null) {
                arrayList.addAll(queryFormDisabledCode);
            }
        } else {
            List<String> queryDisabledFields = this.onlCgformFieldService.queryDisabledFields(head.getTableName(), taskId);
            if (queryDisabledFields != null && !queryDisabledFields.isEmpty() && queryDisabledFields.get(0) != null) {
                arrayList.addAll(queryDisabledFields);
            }
        }
        EnhanceJsUtil.m274a(onlCgformEnhanceJs, head.getTableName(), queryAvailableFields);
        TreeSelectColumn treeSelectColumn = null;
        if ("Y".equals(head.getIsTree())) {
            treeSelectColumn = new TreeSelectColumn();
            treeSelectColumn.setCodeField("id");
            treeSelectColumn.setFieldName(head.getTreeParentIdField());
            treeSelectColumn.setPidField(head.getTreeParentIdField());
            treeSelectColumn.setPidValue("0");
            treeSelectColumn.setHsaChildField(head.getTreeIdField());
            treeSelectColumn.setTableName(CgformUtil.m235f(head.getTableName()));
            treeSelectColumn.setTextField(head.getTreeFieldname());
        }
        JSONObject m192a = CgformUtil.m192a(queryAvailableFields, arrayList, treeSelectColumn);
        m192a.put(CgformUtil.TABLE, head.getTableName());
        m192a.put("describe", head.getTableTxt());
        jSONObject.put("schema", m192a);
        jSONObject.put("head", head);
        List<OnlCgformButton> queryFormValidButton = queryFormValidButton(id);
        if (queryFormValidButton != null && !queryFormValidButton.isEmpty()) {
            jSONObject.put("cgButtonList", queryFormValidButton);
        }
        if (onlCgformEnhanceJs != null && StrUtils.isNotEmpty(onlCgformEnhanceJs.getCgJs())) {
            onlCgformEnhanceJs.setCgJs(EnhanceJsUtil.m273c(onlCgformEnhanceJs.getCgJs(), queryFormValidButton));
            jSONObject.put("enhanceJs", EnhanceJsUtil.m271a(onlCgformEnhanceJs.getCgJs()));
        }
        return jSONObject;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    @Cacheable(value = {"sys:cache:online:form"}, key = "'erp'+ #head.id+'-'+#username")
    public JSONObject queryOnlineFormObj(OnlCgformHead head, String username) {
        return queryOnlineFormObj(head, this.onlCgformHeadService.queryEnhanceJs(head.getId(), CgformUtil.FORM));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public List<OnlCgformButton> queryFormValidButton(String headId) {
        List<OnlCgformButton> queryButtonList = this.onlCgformHeadService.queryButtonList(headId, false);
        List<OnlCgformButton> list = null;
        if (queryButtonList != null && !queryButtonList.isEmpty()) {
            List<String> queryFormHideButton = this.onlAuthPageService.queryFormHideButton(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId(), headId);
            list =  queryButtonList.stream().filter(onlCgformButton -> queryFormHideButton == null || !queryFormHideButton.contains(onlCgformButton.getButtonCode())).collect(Collectors.toList());
        }
        return list;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    @Cacheable(value = {"sys:cache:online:form"}, key = "#head.id+'-'+#username")
    public JSONObject queryOnlineFormItem(OnlCgformHead head, String username) {
        head.setTaskId(null);
        return m382a(head);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public JSONObject queryFlowOnlineFormItem(OnlCgformHead head, String username, String taskId) {
        head.setTaskId(taskId);
        return m382a(head);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    @Cacheable(value = {"sys:cache:online:form"}, key = "'enhancejs' + #code + '-' + #type")
    public String queryEnahcneJsString(String code, String type) {
        String str = "";
        OnlCgformEnhanceJs queryEnhanceJs = this.onlCgformHeadService.queryEnhanceJs(code, type);
        if (queryEnhanceJs != null && StrUtils.isNotEmpty(queryEnhanceJs.getCgJs())) {
            str = EnhanceJsUtil.m272b(queryEnhanceJs.getCgJs(), (List<OnlCgformButton>) null);
        }
        return str;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public JSONObject getOnlineVue3QueryInfo(String headId) {
        String subTableStr;
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getById(headId);
        boolean isJoinQuery = CgformUtil.m181a(onlCgformHead);
        List<String> searchFieldList = new ArrayList<>();
        JSONObject m380a = m380a(headId, searchFieldList, true, null);
        JSONObject jSONObject = m380a.getJSONObject(CgformUtil.PROPERTIES);
        m380a.put(CgformUtil.TITLE, onlCgformHead.getTableTxt());
        m380a.put(CgformUtil.TABLE, onlCgformHead.getTableName());
        m380a.put(CgformUtil.JOIN_QUERY, isJoinQuery);
        m380a.put(CgformUtil.SEARCH_FIELD_LIST, searchFieldList);
        if (CgformUtil.f262aE.equals(onlCgformHead.getTableType()) && (subTableStr = onlCgformHead.getSubTableStr()) != null && !"".equals(subTableStr)) {
            for (String str : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                OnlCgformHead onlCgformHead2 = this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
                if (onlCgformHead2 != null) {
                    JSONObject m380a2 = m380a(onlCgformHead2.getId(), searchFieldList, false, str);
                    m380a2.put(CgformUtil.TITLE, onlCgformHead2.getTableTxt());
                    m380a2.put(CgformUtil.VIEW, CgformUtil.TABLE);
                    jSONObject.put(str, m380a2);
                }
            }
        }
        return m380a;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineService
    public List<DictModel> getOnlineTableDictData(String table, String text, String code) {
        List list = null;
        try {
            List list2 = this.onlCgformFieldService.list(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, this.onlCgformHeadService.getTable(table).getId()).eq(OnlCgformField::getDbFieldName, text));
            if (list2 != null && !list2.isEmpty()) {
                OnlCgformField onlCgformField = (OnlCgformField) list2.get(0);
                String dictTable = onlCgformField.getDictTable();
                String dictField = onlCgformField.getDictField();
                String dictText = onlCgformField.getDictText();
                if (StrUtils.isNotEmpty(dictTable) && StrUtils.isNotEmpty(dictField) && StrUtils.isNotEmpty(dictText)) {
                    list = this.sysBaseAPI.queryTableDictItemsByCode(dictTable, dictText, dictField);
                } else if (StrUtils.isNotEmpty(dictField)) {
                    list = this.sysBaseAPI.queryDictItemsByCode(dictField);
                }
            }
        } catch (Exception e) {
            f425a.error("他表字段获取字典数据失败", e.getMessage());
        }
        List<DictModel> queryTableDictItemsByCode = this.sysBaseAPI.queryTableDictItemsByCode(table, text, code);
        if (list != null && !list.isEmpty()) {
            for (DictModel dictModel : queryTableDictItemsByCode) {
                String text2 = dictModel.getText();
                Iterator it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        DictModel dictModel2 = (DictModel) it.next();
                        if (dictModel2.getValue().equals(text2)) {
                            dictModel.setText(dictModel2.getText());
                            break;
                        }
                    }
                }
            }
        }
        return queryTableDictItemsByCode;
    }

    /* renamed from: a */
    private JSONObject m380a(String str, List<String> list, boolean z, String str2) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<OnlCgformField>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str);
        lambdaQueryWrapper.and(lambdaQueryWrapper2 -> {
            lambdaQueryWrapper2.eq(OnlCgformField::getIsShowList, 1).or().eq(OnlCgformField::getIsQuery, 1);
        });
        lambdaQueryWrapper.eq(OnlCgformField::getDbIsPersist, OnlineConst.isPersist);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list2 = this.onlCgformFieldService.list(lambdaQueryWrapper);
        for (OnlCgformField onlCgformField : list2) {
            onlCgformField.setFieldDefaultValue(null);
            if ("1".equals(onlCgformField.getQueryConfigFlag())) {
                onlCgformField.setFieldDefaultValue(onlCgformField.getQueryDefVal());
                onlCgformField.setDictField(onlCgformField.getQueryDictField());
                onlCgformField.setDictTable(onlCgformField.getQueryDictTable());
                onlCgformField.setDictText(onlCgformField.getQueryDictText());
                onlCgformField.setFieldShowType(onlCgformField.getQueryShowType());
            }
            if (1 == onlCgformField.getIsQuery()) {
                if (z) {
                    list.add(onlCgformField.getDbFieldName());
                } else {
                    list.add(str2 + "@" + onlCgformField.getDbFieldName());
                }
            }
        }
        JSONObject m192a = CgformUtil.m192a(list2, null, null);
        CgformUtil.m247b(m192a);
        return m192a;
    }

    /* renamed from: b */
    private List<OnlCgformField> m381b(String str) {
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformField::getCgformHeadId, str);
        lambdaQueryWrapper.orderByAsc(OnlCgformField::getOrderNum);
        return this.onlCgformFieldService.list(lambdaQueryWrapper);
    }

    /* renamed from: a */
    private JSONObject m382a(OnlCgformHead onlCgformHead) {
        List<String> queryFormDisabledCode;
        OnlCgformEnhanceJs queryEnhanceJs = this.onlCgformHeadService.queryEnhanceJs(onlCgformHead.getId(), CgformUtil.FORM);
        JSONObject queryOnlineFormObj = queryOnlineFormObj(onlCgformHead, queryEnhanceJs);
        queryOnlineFormObj.put("formTemplate", onlCgformHead.getFormTemplate());
        List<String> queryHideCode = this.onlAuthPageService.queryHideCode(onlCgformHead.getId(), true);
        if (queryHideCode != null && queryHideCode.contains(AuthConstants.UPDATE)) {
            queryOnlineFormObj.put(AuthConstants.FORM_DISABLE_UPDATE, true);
        }
        if (onlCgformHead.getTableType().intValue() == 2) {
            JSONObject jSONObject = queryOnlineFormObj.getJSONObject("schema");
            String subTableStr = onlCgformHead.getSubTableStr();
            if (StrUtils.isNotEmpty(subTableStr)) {
                ArrayList<OnlCgformHead> arrayList = new ArrayList<>();
                for (String str : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                    OnlCgformHead onlCgformHead2 = this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead2 != null) {
                        arrayList.add(onlCgformHead2);
                    }
                }
                if (!arrayList.isEmpty()) {
                    // from class: org.jeecg.modules.online.cgform.service.a.i.1
// java.util.Comparator
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    arrayList.sort((onlCgformHead3, onlCgformHead4) -> {
                        Integer tabOrderNum = onlCgformHead3.getTabOrderNum();
                        if (tabOrderNum == null) {
                            tabOrderNum = 0;
                        }
                        Integer tabOrderNum2 = onlCgformHead4.getTabOrderNum();
                        if (tabOrderNum2 == null) {
                            tabOrderNum2 = 0;
                        }
                        return tabOrderNum.compareTo(tabOrderNum2);
                    });
                    for (OnlCgformHead onlCgformHead3 : arrayList) {
                        List<OnlCgformField> queryAvailableFields = this.onlCgformFieldService.queryAvailableFields(onlCgformHead3.getId(), onlCgformHead3.getTableName(), onlCgformHead.getTaskId(), false);
                        EnhanceJsUtil.m275b(queryEnhanceJs, onlCgformHead3.getTableName(), queryAvailableFields);
                        JSONObject jSONObject2 = new JSONObject();
                        new ArrayList();
                        if (StrUtils.isNotEmpty(onlCgformHead.getTaskId())) {
                            queryFormDisabledCode = this.onlCgformFieldService.queryDisabledFields(onlCgformHead3.getTableName(), onlCgformHead.getTaskId());
                        } else {
                            queryFormDisabledCode = this.onlAuthPageService.queryFormDisabledCode(onlCgformHead3.getId());
                        }
                        if (1 == onlCgformHead3.getRelationType().intValue()) {
                            jSONObject2 = CgformUtil.m192a(queryAvailableFields, queryFormDisabledCode, (TreeSelectColumn) null);
                        } else {
                            jSONObject2.put("columns", CgformUtil.m222a(queryAvailableFields, queryFormDisabledCode));
                            jSONObject2.put("hideButtons", this.onlAuthPageService.queryListHideButton(null, onlCgformHead3.getId()));
                        }
                        jSONObject2.put("foreignKey", this.onlCgformFieldService.queryForeignKey(onlCgformHead3.getId(), onlCgformHead.getTableName()));
                        jSONObject2.put("id", onlCgformHead3.getId());
                        jSONObject2.put("describe", onlCgformHead3.getTableTxt());
                        jSONObject2.put("key", onlCgformHead3.getTableName());
                        jSONObject2.put(CgformUtil.VIEW, "tab");
                        jSONObject2.put("order", onlCgformHead3.getTabOrderNum());
                        jSONObject2.put("relationType", onlCgformHead3.getRelationType());
                        jSONObject2.put("formTemplate", onlCgformHead3.getFormTemplate());
                        jSONObject.getJSONObject(CgformUtil.PROPERTIES).put(onlCgformHead3.getTableName(), jSONObject2);
                    }
                }
            }
            if (queryEnhanceJs != null && StrUtils.isNotEmpty(queryEnhanceJs.getCgJs())) {
                queryOnlineFormObj.put("enhanceJs", EnhanceJsUtil.m271a(queryEnhanceJs.getCgJs()));
            }
        }
        return queryOnlineFormObj;
    }

    /* renamed from: a */
    private OnlColumn m383a(OnlCgformField onlCgformField, Map<String, List<DictModel>> map, List<HrefSlots> list) {
        JSONObject parseObject;
        String dbFieldName = onlCgformField.getDbFieldName();
        OnlColumn onlColumn = new OnlColumn(onlCgformField.getDbFieldTxt(), dbFieldName);
        onlColumn.setDbType(onlCgformField.getDbType());
        String dictField = onlCgformField.getDictField();
        String fieldShowType = onlCgformField.getFieldShowType();
        if (fieldShowType == null) {
            return onlColumn;
        }
        if (StrUtils.isNotEmpty(dictField) && !CgformUtil.f218M.equals(fieldShowType) && !CgformUtil.f238ag.equals(fieldShowType)) {
            List<DictModel> arrayList = new ArrayList<>();
            if (StrUtils.isNotEmpty(onlCgformField.getDictTable())) {
                arrayList = this.sysBaseAPI.queryTableDictItemsByCode(onlCgformField.getDictTable(), onlCgformField.getDictText(), dictField);
            } else if (StrUtils.isNotEmpty(onlCgformField.getDictField())) {
                arrayList = this.sysBaseAPI.queryDictItemsByCode(dictField);
            }
            map.put(dbFieldName, arrayList);
            onlColumn.setCustomRender(dbFieldName);
        }
        if (CgformUtil.f217L.equals(fieldShowType)) {
            map.put(dbFieldName, CgformUtil.m242b(onlCgformField));
            onlColumn.setCustomRender(dbFieldName);
        }
        if (CgformUtil.f239ah.equals(fieldShowType)) {
            onlColumn.setFieldType(fieldShowType);
        }
        if (CgformUtil.f238ag.equals(fieldShowType)) {
            onlColumn.setFieldType(fieldShowType);
            onlColumn.setHrefSlotName(onlCgformField.getDictTable());
        }
        if (CgformUtil.f224S.equals(fieldShowType)) {
            LinkDown linkDown = (LinkDown) JSONObject.parseObject(onlCgformField.getDictTable(), LinkDown.class);
            try {
                map.put(dbFieldName, this.sysBaseAPI.queryTableDictItemsByCode(linkDown.getTable(), linkDown.getTxt(), linkDown.getKey()));
                onlColumn.setCustomRender(dbFieldName);
                onlColumn.setLinkField(linkDown.getLinkField());
            } catch (Exception e) {
                f425a.warn("联动组件配置错误!", e.getMessage());
            }
        }
        if (CgformUtil.f222Q.equals(fieldShowType)) {
            String[] split = onlCgformField.getDictText().split(CgformUtil.COMMA_SEPARATOR);
            map.put(dbFieldName, this.sysBaseAPI.queryTableDictItemsByCode(onlCgformField.getDictTable(), split[2], split[0]));
            onlColumn.setCustomRender(dbFieldName);
        }
        if (CgformUtil.f223R.equals(fieldShowType)) {
            String dictText = onlCgformField.getDictText();
            if (StrUtils.isEmpty(dictText)) {
                map.put(dbFieldName, this.sysBaseAPI.queryFilterTableDictInfo(CgformUtil.f231Z, CgformUtil.f232aa, "ID", CgformUtil.m234e(onlCgformField.getDictField())));
                onlColumn.setCustomRender(dbFieldName);
            } else {
                onlColumn.setCustomRender("_replace_text_" + dictText);
            }
        }
        if ("sel_depart".equals(fieldShowType)) {
            String[] m378a = m378a(onlCgformField.getFieldExtendJson());
            map.put(dbFieldName, this.sysBaseAPI.queryTableDictItemsByCode(CgformUtil.SYS_DEPART, !m378a[1].isEmpty() ? m378a[1] : CgformUtil.DEPART_NAME, !m378a[0].isEmpty() ? m378a[0] : "ID"));
            onlColumn.setCustomRender(dbFieldName);
        }
        if ("sel_user".equals(onlCgformField.getFieldShowType())) {
            String[] m378a2 = m378a(onlCgformField.getFieldExtendJson());
            map.put(dbFieldName, this.sysBaseAPI.queryTableDictItemsByCode(CgformUtil.SYS_USER, m378a2[1].length() > 0 ? m378a2[1] : CgformUtil.REALNAME, m378a2[0].length() > 0 ? m378a2[0] : CgformUtil.USERNAME));
            onlColumn.setCustomRender(dbFieldName);
        }
        if (fieldShowType.contains("file")) {
            onlColumn.setScopedSlots(new ScopedSlots(OnlSlotRender.FILE_SLOT));
        } else if (fieldShowType.contains("image")) {
            onlColumn.setScopedSlots(new ScopedSlots(OnlSlotRender.IMG_SLOT));
        } else if (fieldShowType.contains(OnlFormShowType.EDITOR)) {
            onlColumn.setScopedSlots(new ScopedSlots(OnlSlotRender.HTML_SLOT));
        } else if (fieldShowType.equals(OnlFormShowType.DATE)) {
            onlColumn.setScopedSlots(new ScopedSlots(OnlSlotRender.DATE_SLOT));
        } else if (fieldShowType.equals(OnlFormShowType.PCA)) {
            onlColumn.setScopedSlots(new ScopedSlots(OnlSlotRender.PCA_SLOT));
        }
        if (StringUtils.isNotBlank(onlCgformField.getFieldHref())) {
            String str = "fieldHref_" + dbFieldName;
            onlColumn.setHrefSlotName(str);
            list.add(new HrefSlots(str, onlCgformField.getFieldHref()));
        }
        if ("1".equals(onlCgformField.getSortFlag())) {
            onlColumn.setSorter(true);
        }
        String fieldExtendJson = onlCgformField.getFieldExtendJson();
        if (StrUtils.isNotEmpty(fieldExtendJson)) {
            onlColumn.setFieldExtendJson(fieldExtendJson);
            if (fieldExtendJson.indexOf(ExtendJsonKey.SHOW_LENGTH) > 0 && (parseObject = JSON.parseObject(fieldExtendJson)) != null && parseObject.get(ExtendJsonKey.SHOW_LENGTH) != null) {
                onlColumn.setShowLength(Objects.requireNonNull(oConvertUtils.getInt(parseObject.get(ExtendJsonKey.SHOW_LENGTH))));
            }
        }
        return onlColumn;
    }

    /* renamed from: a */
    private List<OnlColumn> m384a(OnlCgformHead onlCgformHead, Map<String, List<DictModel>> map, List<HrefSlots> list, Map<String, Integer> map2) {
        String subTableStr;
        int intValue = onlCgformHead.getTableType();
        ArrayList<OnlColumn> arrayList = new ArrayList<>();
        if (intValue == 2 && (subTableStr = onlCgformHead.getSubTableStr()) != null && !subTableStr.isEmpty()) {
            for (String str : subTableStr.split(CgformUtil.COMMA_SEPARATOR)) {
                OnlCgformHead onlCgformHead2 = this.onlCgformHeadService.getOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
                if (onlCgformHead2 != null) {
                    List<String> queryHideCode = this.onlAuthPageService.queryHideCode(onlCgformHead2.getId(), true);
                    for (OnlCgformField onlCgformField : m381b(onlCgformHead2.getId())) {
                        if (1 == onlCgformField.getIsShowList() || 1 == onlCgformField.getIsQuery()) {
                            String dbFieldName = onlCgformField.getDbFieldName();
                            if (!queryHideCode.contains(dbFieldName) && !"id".equals(dbFieldName)) {
                                map2.merge(dbFieldName, 1, Integer::sum);
                                OnlColumn m383a = m383a(onlCgformField, map, list);
                                if (1 == onlCgformField.getIsShowList()) {
                                    m383a.setTableName(onlCgformHead2.getTableName());
                                    arrayList.add(m383a);
                                }
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }
}
