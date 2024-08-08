package org.jeecg.modules.online.cgreport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecg.common.exception.JeecgBootException;

import org.jeecg.common.system.vo.DictModel;

import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.utils.OnlFormShowType;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportAPIService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.jeecg.modules.online.config.blackList.OnlReportQueryBlackListHandler;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.export.ExcelExportServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/* compiled from: OnlCgreportAPIService.java */
@Service("onlCgreportAPIService")
/* renamed from: org.jeecg.modules.online.cgreport.service.a.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/service/a/b.class */
public class OnlCgreportAPIService implements IOnlCgreportAPIService {

    /* renamed from: a */
    private static final Logger f476a = LoggerFactory.getLogger(OnlCgreportAPIService.class);

    @Autowired
    private OnlCgreportHeadServiceImpl onlCgreportHeadService;

    @Autowired
    private IOnlCgreportItemService onlCgreportItemService;

    @Autowired
    @Lazy
    private ISysBaseAPI sysBaseAPI;

    @Autowired
    private IOnlCgreportParamService onlCgreportParamService;

    @Autowired
    private OnlReportQueryBlackListHandler onlReportQueryBlackListHandler;

    /* renamed from: a */

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportAPIService
    public Map<String, Object> getDataById(String id, Map<String, Object> params) {
        return getData(id, null, params);
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportAPIService
    public Map<String, Object> getDataByCode(String code, Map<String, Object> params) {
        return getData(null, code, params);
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportAPIService
    public Map<String, Object> getData(String id, String code, Map<String, Object> params) {
        OnlCgreportHead onlCgreportHead = null;
        if (StrUtils.isNotEmpty(id)) {
            onlCgreportHead = this.onlCgreportHeadService.getById(id);
        } else if (StrUtils.isNotEmpty(code)) {
            LambdaQueryWrapper<OnlCgreportHead> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OnlCgreportHead::getCode, code);
            onlCgreportHead = this.onlCgreportHeadService.getOne(lambdaQueryWrapper);
        }
        if (onlCgreportHead == null) {
            throw exception("实体不存在");
        }
        try {
            return executeSelectSqlRoute(onlCgreportHead.getDbSource(), onlCgreportHead.getCgrSql().trim(), params, onlCgreportHead.getId());
        } catch (Exception e) {
            f476a.error(e.getMessage(), e);
            throw exception("SQL执行失败：" + e.getMessage());
        }
    }

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportAPIService
    public Map<String, Object> executeSelectSqlRoute(String dbKey, String sql, Map<String, Object> params, String headId) throws Exception {
        if (!this.onlReportQueryBlackListHandler.isPass(sql)) {
            throw exception(this.onlReportQueryBlackListHandler.getError());
        }
        if (StringUtils.isNotBlank(dbKey)) {
            return this.onlCgreportHeadService.executeSelectSqlDynamic(dbKey, sql, params, headId);
        }
        return this.onlCgreportHeadService.executeSelectSql(sql, headId, params);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v143, types: [java.util.List] */
    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportAPIService
    public Workbook getReportWorkbook(String reportId, Map<String, Object> params) {
        LambdaQueryWrapper<OnlCgreportItem> wrapper =  new LambdaQueryWrapper<OnlCgreportItem>().eq(OnlCgreportItem::getCgrheadId, reportId);
        wrapper.orderByAsc(OnlCgreportItem::getOrderNum);
        List<OnlCgreportItem> list = this.onlCgreportItemService.list(wrapper);
        ArrayList<ExcelExportEntity> arrayList = new ArrayList<>();
        HashMap<String,List<String>> hashMap = new HashMap<>(5);
        ArrayList<String> arrayList2 = new ArrayList<>();
        HashMap<String,Object> hashMap2 = new HashMap<>(5);
        for (OnlCgreportItem onlCgreportItem : list) {
            String fieldType = onlCgreportItem.getFieldType();
            String fieldName = onlCgreportItem.getFieldName();
            if ("1".equals(ConvertUtils.getString(onlCgreportItem.getIsShow()))) {
                ExcelExportEntity excelExportEntity = new ExcelExportEntity(onlCgreportItem.getFieldTxt(), fieldName, 15);
                m439a(onlCgreportItem, excelExportEntity);
                if (OnlFormShowType.DATE.equalsIgnoreCase(onlCgreportItem.getFieldType())) {
                    excelExportEntity.setFormat("yyyy-MM-dd");
                } else if ("datetime".equalsIgnoreCase(onlCgreportItem.getFieldType())) {
                    excelExportEntity.setFormat("yyyy-MM-dd HH:mm:ss");
                }
                String groupTitle = onlCgreportItem.getGroupTitle();
                if (StrUtils.isNotEmpty(groupTitle)) {
                    ArrayList<String> arrayList3 = new ArrayList();
                    if (hashMap.containsKey(groupTitle)) {
                        arrayList3 = (ArrayList<String>) hashMap.get(groupTitle);
                        arrayList3.add(fieldName);
                    } else {
                        arrayList.add(new ExcelExportEntity(groupTitle, groupTitle, true));
                        arrayList3.add(fieldName);
                    }
                    hashMap.put(groupTitle, arrayList3);
                    excelExportEntity.setColspan(true);
                }
                if (StrUtils.isNotEmpty(fieldType) && StrUtils.isEmpty(onlCgreportItem.getDictCode()) && ("Integer".equals(fieldType) || "Long".equals(fieldType))) {
                    excelExportEntity.setType(4);
                }
                arrayList.add(excelExportEntity);
            }
            if ("1".equals(ConvertUtils.getString(onlCgreportItem.getIsTotal()))) {
                arrayList2.add(fieldName);
            }
        }
        for (Map.Entry<String,List<String>> entry : hashMap.entrySet()) {
            String str = entry.getKey();
            List<String> list2 = entry.getValue();
            for (ExcelExportEntity excelExportEntity2 : arrayList) {
                if (str.equals(excelExportEntity2.getName()) && excelExportEntity2.isColspan()) {
                    excelExportEntity2.setSubColumnList(list2);
                }
            }
        }
        HSSFWorkbook hSSFWorkbook = new HSSFWorkbook();
        boolean z = true;
        int num = 1;
        params.put("pageSize", 10000);
        while (z) {
            Integer num2 = num;
            num = num + 1;
            params.put("pageNo", num2);
            List<Map<String,Object>> list3 = (List<Map<String,Object>>) getDataById(reportId, params).get("records");
            if (list3 == null || list3.isEmpty()) {
                z = false;
            } else {
                if (!arrayList2.isEmpty()) {
                    for (String str2 : arrayList2) {
                        BigDecimal bigDecimal = new BigDecimal("0.0");
                        for (Map<String, Object> stringStringMap : list3) {
                            String obj = (String) stringStringMap.get(str2);
                            if (obj.matches("-?\\d+(.\\d+)?")) {
                                bigDecimal = bigDecimal.add(new BigDecimal(obj));
                            }
                        }
                        hashMap2.put(str2, bigDecimal);
                    }
                    list3.add(hashMap2);
                }
                new ExcelExportServer().createSheetForMap(hSSFWorkbook, new ExportParams(), arrayList, list3);
            }
        }
        return hSSFWorkbook;
    }

    /* renamed from: a */
    private void m439a(OnlCgreportItem onlCgreportItem, ExcelExportEntity excelExportEntity) {
        List<DictModel> queryColumnDictList = this.onlCgreportHeadService.queryColumnDictList(ConvertUtils.getString(onlCgreportItem.getDictCode()), null, null);
        if (queryColumnDictList != null && !queryColumnDictList.isEmpty()) {
            ArrayList<String> arrayList = new ArrayList<>();
            for (DictModel dictModel : queryColumnDictList) {
                if (dictModel != null && dictModel.getValue() != null) {
                    if (dictModel.getValue().contains("_")) {
                        arrayList.add(dictModel.getText() + "_" + dictModel.getValue().replace("_", "---"));
                    } else {
                        arrayList.add(dictModel.getText() + "_" + dictModel.getValue());
                    }
                }
            }
            excelExportEntity.setReplace(arrayList.toArray(new String[arrayList.size()]));
        }
        String replaceVal = onlCgreportItem.getReplaceVal();
        if (StrUtils.isNotEmpty(replaceVal)) {
            excelExportEntity.setReplace(replaceVal.split(CgformUtil.COMMA_SEPARATOR));
        }
    }
}
