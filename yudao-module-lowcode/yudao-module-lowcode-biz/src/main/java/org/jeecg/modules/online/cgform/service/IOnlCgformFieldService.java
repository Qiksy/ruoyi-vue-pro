package org.jeecg.modules.online.cgform.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlQueryModel;
import org.jeecg.modules.online.cgform.model.TreeModel;
import org.jeecg.modules.online.cgform.vo.LinkDown;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/IOnlCgformFieldService.class */
public interface IOnlCgformFieldService extends IService<OnlCgformField> {
    Map<String, Object> queryAutolistPage(OnlCgformHead onlCgformHead, Map<String, Object> map, List<String> list);

    Map<String, Object> queryAutoTreeNoPage(String str, String str2, Map<String, Object> map, List<String> list, String str3);

    void deleteAutoListMainAndSub(OnlCgformHead onlCgformHead, String str);

    void deleteAutoListById(String str, String str2);

    void deleteAutoList(String str, String str2, String str3);

    void saveFormData(String str, String str2, JSONObject jSONObject, boolean z);

    void saveTreeFormData(String str, String str2, JSONObject jSONObject, String str3, String str4);

    void saveFormData(List<OnlCgformField> list, String str, JSONObject jSONObject);

    List<OnlCgformField> queryFormFields(String str, boolean z);

    List<OnlCgformField> queryFormFieldsByTableName(String str);

    OnlCgformField queryFormFieldByTableNameAndField(String str, String str2);

    void editTreeFormData(String str, String str2, JSONObject jSONObject, String str3, String str4);

    void editFormData(String str, String str2, JSONObject jSONObject, boolean z);

    Map<String, Object> queryFormData(String str, String str2, String str3);

    Map<String, Object> queryFormData(List<OnlCgformField> list, String str, String str2);

    Map<String, Object> generateMockData(String str);

    List<Map<String, Object>> querySubFormData(List<OnlCgformField> list, String str, String str2, String str3);

    List<Map<String, String>> getAutoListQueryInfo(String str);

    List<String> selectOnlineHideColumns(String str);

    List<OnlCgformField> queryAvailableFields(String str, String str2, String str3, boolean z);

    List<String> queryDisabledFields(String str);

    List<String> queryDisabledFields(String str, String str2);

    List<OnlCgformField> queryAvailableFields(String str, boolean z, List<OnlCgformField> list, List<String> list2);

    List<OnlCgformField> queryAvailableFields(String str, String str2, boolean z, List<OnlCgformField> list, List<String> list2);

    void executeInsertSQL(Map<String, Object> map);

    void executeUpdatetSQL(Map<String, Object> map);

    List<TreeModel> queryDataListByLinkDown(LinkDown linkDown);

    void updateTreeNodeNoChild(String str, String str2, String str3);

    String queryTreeChildIds(OnlCgformHead onlCgformHead, String str);

    String queryTreePids(OnlCgformHead onlCgformHead, String str);

    String queryForeignKey(String str, String str2);

    List<Map<String, Object>> queryListBySql(String str, String str2, String str3, String str4, String str5);

    void clearCacheOnlineConfig();

    OnlQueryModel getQueryInfo(OnlCgformHead onlCgformHead, Map<String, Object> map, List<String> list);

    void addOnlineInsertDataLog(String str, String str2);

    void addOnlineUpdateDataLog(String str, String str2, List<OnlCgformField> list, JSONObject jSONObject);

    List<Map<String, Object>> queryLinkTableDictList(String str, String str2, String str3);

    void handleLinkTableDictData(String str, List<Map<String, Object>> list);
}
