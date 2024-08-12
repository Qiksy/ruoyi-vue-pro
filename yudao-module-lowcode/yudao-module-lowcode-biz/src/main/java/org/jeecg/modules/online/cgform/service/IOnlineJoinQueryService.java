package org.jeecg.modules.online.cgform.service;

import java.util.List;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlQueryModel;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/IOnlineJoinQueryService.class */
public interface IOnlineJoinQueryService {
    Map<String, Object> pageList(OnlCgformHead onlCgformHead, Map<String, Object> map, boolean z);

    Map<String, Object> pageList(OnlCgformHead onlCgformHead, Map<String, Object> map);

    OnlQueryModel getQueryInfo(OnlCgformHead onlCgformHead, Map<String, Object> map, boolean z);

    OnlQueryModel getQueryInfo(OnlCgformHead onlCgformHead, Map<String, Object> map, boolean z, boolean z2);

    XSSFWorkbook handleOnlineExport(OnlCgformHead onlCgformHead, Map<String, Object> map);

//    void addAllSubTableDate(String str, Map<String, Object> map, List<Map<String, Object>> list, List<ExcelExportEntity> list2, boolean z);
}
