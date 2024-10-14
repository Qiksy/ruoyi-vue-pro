package org.jeecg.modules.online.cgform.service;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.enums.EnhanceDataEnum;
import org.jeecg.modules.online.cgform.model.OnlCgformModel;
import org.jeecg.modules.online.cgform.model.OnlGenerateModel;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.online.config.exception.DBException;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/IOnlCgformHeadService.class */
public interface IOnlCgformHeadService extends IService<OnlCgformHead> {
    CommonResult<?> addAll(OnlCgformModel onlCgformModel);

    CommonResult<?> editAll(OnlCgformModel onlCgformModel);

    void doDbSynch(String code, String syncMethod) throws  IOException, TemplateException, SQLException, DBException;

    void deleteRecordAndTable(String str) throws DBException, SQLException;

    void deleteRecord(String str) throws DBException, SQLException;

    List<Map<String, Object>> queryListData(String str);

    OnlCgformEnhanceJs queryEnhance(String code, String str2);

    void saveEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs);

    void editEnhance(OnlCgformEnhanceJs onlCgformEnhanceJs);

    OnlCgformEnhanceSql queryEnhanceSql(String str, String str2);

    OnlCgformEnhanceJava queryEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava);

    List<OnlCgformButton> queryButtonList(String str, boolean z);

    List<OnlCgformButton> queryButtonList(String str);

    List<String> queryOnlinetables();

    void saveDbTable2Online(String str);

    JSONObject queryFormItem(OnlCgformHead onlCgformHead, String str);

    String saveManyFormData(String str, JSONObject jSONObject, String token) throws DBException, BusinessException;

    Map<String, Object> queryManyFormData(String str, String str2) throws DBException;

    List<Map<String, Object>> queryManySubFormData(String str, String str2) throws DBException;

    Map<String, Object> querySubFormData(String str, String str2) throws DBException;

    String editManyFormData(String str, JSONObject jSONObject) throws DBException, BusinessException;

    void executeEnhanceJava(String str, String str2, OnlCgformHead onlCgformHead, JSONObject jSONObject) throws BusinessException;

    void executeEnhanceExport(OnlCgformHead onlCgformHead, List<Map<String, Object>> list) throws BusinessException;

    EnhanceDataEnum executeEnhanceImport(OnlCgformHead onlCgformHead, JSONObject jSONObject) throws BusinessException;

    /**
     * 执行增强列表
     * @param onlCgformHead 表定义
     * @param buttonCode 什么时候增强
     * @param dataList 数据列表
     * @throws BusinessException
     */
    void executeEnhanceList(OnlCgformHead onlCgformHead, String buttonCode, List<Map<String, Object>> dataList) throws BusinessException;

    void executeEnhanceSql(String str, String str2, JSONObject jSONObject);

    void executeCustomerButton(String str, String str2, String str3) throws BusinessException;

    List<OnlCgformButton> queryValidButtonList(String str);

    OnlCgformEnhanceJs queryEnhanceJs(String str, String str2);

    void deleteOneTableInfo(String str, String str2) throws BusinessException;

    List<String> generateCode(OnlGenerateModel onlGenerateModel) throws Exception;

    List<String> generateOneToMany(OnlGenerateModel onlGenerateModel) throws Exception;

    void addCrazyFormData(String str, JSONObject jSONObject) throws DBException, UnsupportedEncodingException;

    void editCrazyFormData(String str, JSONObject jSONObject) throws DBException, UnsupportedEncodingException;

    Integer getMaxCopyVersion(String str);

    void copyOnlineTableConfig(OnlCgformHead onlCgformHead) throws Exception;

    void initCopyState(List<OnlCgformHead> list);

    void deleteBatch(String str, String str2);

    void updateParentNode(OnlCgformHead onlCgformHead, String str);

    String deleteDataByCode(String str, String str2);

    JSONObject queryAllDataByTableNameForDesform(String str, String str2) throws DBException;

    OnlCgformHead copyOnlineTable(String str, String str2);

    OnlCgformHead getTable(String str) throws DBException;
}
