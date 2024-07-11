package org.jeecg.modules.online.cgform.enhance;

import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.config.exception.BusinessException;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/enhance/CgformEnhanceJavaListInter.class */
public interface CgformEnhanceJavaListInter {
    void execute(String str, List<Map<String, Object>> list) throws BusinessException;
}
