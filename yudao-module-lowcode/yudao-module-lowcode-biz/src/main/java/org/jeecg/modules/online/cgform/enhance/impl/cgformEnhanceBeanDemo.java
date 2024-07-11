package org.jeecg.modules.online.cgform.enhance.impl;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* compiled from: CgformEnhanceBeanDemo.java */
@Component("cgformEnhanceBeanDemo")
/* renamed from: org.jeecg.modules.online.cgform.enhance.impl.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/enhance/impl/a.class */
public class cgformEnhanceBeanDemo implements CgformEnhanceJavaInter {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(cgformEnhanceBeanDemo.class);

    @Override // org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter
    public void execute(String tableName, JSONObject json) throws BusinessException {
        if (json.containsKey("phone")) {
            json.put("phone", "18611100000");
        }
    }
}
