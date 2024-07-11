package org.jeecg.modules.online.cgreport.service;

import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/service/IOnlCgreportAPIService.class */
public interface IOnlCgreportAPIService {
    Map<String, Object> getDataById(String str, Map<String, Object> map);

    Map<String, Object> getDataByCode(String str, Map<String, Object> map);

    Map<String, Object> getData(String str, String str2, Map<String, Object> map);

    Map<String, Object> executeSelectSqlRoute(String str, String str2, Map<String, Object> map, String str3) throws Exception;

    Workbook getReportWorkbook(String str, Map<String, Object> map);
}
