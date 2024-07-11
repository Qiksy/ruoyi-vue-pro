package org.jeecg.modules.online.cgform.enhance;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.cgform.enums.EnhanceDataEnum;
import org.jeecg.modules.online.config.exception.BusinessException;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/enhance/CgformEnhanceJavaImportInter.class */
public interface CgformEnhanceJavaImportInter {
    EnhanceDataEnum execute(String str, JSONObject jSONObject) throws BusinessException;
}
