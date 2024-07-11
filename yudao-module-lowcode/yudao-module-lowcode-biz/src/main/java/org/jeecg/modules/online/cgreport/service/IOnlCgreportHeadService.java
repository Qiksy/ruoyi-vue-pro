package org.jeecg.modules.online.cgreport.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import net.sf.jsqlparser.JSQLParserException;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.model.OnlCgreportModel;
import org.jeecg.modules.online.config.exception.DBException;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/service/IOnlCgreportHeadService.class */
public interface IOnlCgreportHeadService extends IService<OnlCgreportHead> {
    Result<?> editAll(OnlCgreportModel onlCgreportModel);

    Result<?> delete(String str);

    Result<?> bathDelete(String[] strArr);

    Map<String, Object> executeSelectSql(String str, String str2, Map<String, Object> map) throws SQLException;

    Map<String, Object> executeSelectSqlDynamic(String str, String str2, Map<String, Object> map, String str3);

    List<String> getSqlFields(String str, String str2) throws SQLException, DBException, JSQLParserException;

    List<String> getSqlParams(String str);

    Map<String, Object> queryCgReportConfig(String str);

    List<DictModel> queryDictSelectData(String str, String str2);

    Map<String, Object> queryColumnInfo(String str, boolean z);

    List<DictModel> queryColumnDict(String str, JSONArray jSONArray, String str2);

    List<DictModel> queryColumnDictList(String str, List<Map<String, Object>> list, String str2);
}
