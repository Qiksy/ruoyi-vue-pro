package org.jeecg.modules.online.cgform.enhance;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import org.jeecg.modules.online.config.exception.BusinessException;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/enhance/CgformEnhanceJavaInter.class */
public interface CgformEnhanceJavaInter {
    void execute(String str, JSONObject jSONObject) throws BusinessException;

    @Deprecated
    default void execute(String tableName, Map<String, Object> map) throws BusinessException {
    }
}
