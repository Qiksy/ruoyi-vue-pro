package org.jeecg.modules.online.cgform.service;

import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.config.exception.BusinessException;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/IOnlCgformSqlService.class */
public interface IOnlCgformSqlService {
    void saveBatchOnlineTable(OnlCgformHead onlCgformHead, List<OnlCgformField> list, List<Map<String, Object>> list2) throws BusinessException;

    Map<String, String> saveOnlineImportDataWithValidate(OnlCgformHead onlCgformHead, List<OnlCgformField> list, List<Map<String, Object>> list2);

    void saveOrUpdateSubData(String str, OnlCgformHead onlCgformHead, List<OnlCgformField> list) throws BusinessException;
}
