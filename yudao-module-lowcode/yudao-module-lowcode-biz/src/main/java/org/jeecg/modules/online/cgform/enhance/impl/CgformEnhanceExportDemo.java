package org.jeecg.modules.online.cgform.enhance.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.jeecg.common.service.ISysBaseAPI;
import org.jeecg.common.system.vo.SysCategoryModel;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.jeecg.common.util.online.ConvertUtils;

/* compiled from: CgformEnhanceExportDemo.java */
@Component("cgformEnhanceExportDemo")
/* renamed from: org.jeecg.modules.online.cgform.enhance.impl.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/enhance/impl/b.class */
public class CgformEnhanceExportDemo implements CgformEnhanceJavaListInter {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(CgformEnhanceExportDemo.class);

    @Autowired
    @Lazy
    ISysBaseAPI sysBaseAPI;

    @Autowired
    IOnlCgformFieldService onlCgformFieldService;

    @Override // org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter
    public void execute(String tableName, List<Map<String, Object>> data) throws BusinessException {
        OnlCgformField queryFormFieldByTableNameAndField;
        List queryTableDictByKeys;
        List<SysCategoryModel> queryAllSysCategory = this.sysBaseAPI.queryAllSysCategory();
        for (Map<String, Object> map : data) {
            String string = ConvertUtils.getString(map.get("fen_tree"));
            if (!ConvertUtils.isEmpty(string)) {
                List<SysCategoryModel> list = queryAllSysCategory.stream().filter(sysCategoryModel -> sysCategoryModel.getId().equals(string)).collect(Collectors.toList());
                if (!list.isEmpty()) {
                    map.put("fen_tree", list.get(0).getName());
                }
                String string2 = ConvertUtils.getString(map.get("sel_search"));
                if (!ConvertUtils.isEmpty(string2) && (queryFormFieldByTableNameAndField = this.onlCgformFieldService.queryFormFieldByTableNameAndField(tableName, "sel_search")) != null && !ConvertUtils.isEmpty(queryFormFieldByTableNameAndField.getDictTable()) && (queryTableDictByKeys = this.sysBaseAPI.queryTableDictByKeys(queryFormFieldByTableNameAndField.getDictTable(), queryFormFieldByTableNameAndField.getDictText(), queryFormFieldByTableNameAndField.getDictField(), new String[]{string2})) != null && !queryTableDictByKeys.isEmpty()) {
                    map.put("sel_search", queryTableDictByKeys.get(0));
                }
            }
        }
    }
}
