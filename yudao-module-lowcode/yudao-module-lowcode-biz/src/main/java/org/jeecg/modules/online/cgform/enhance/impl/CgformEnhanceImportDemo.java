package org.jeecg.modules.online.cgform.enhance.impl;

import com.alibaba.fastjson.JSONObject;

import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaImportInter;
import org.jeecg.modules.online.cgform.enums.EnhanceDataEnum;
import org.jeecg.modules.online.cgform.utils.OnlineImportValidator;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/* compiled from: CgformEnhanceImportDemo.java */
@Component("cgformEnhanceImportDemo")
/* renamed from: org.jeecg.modules.online.cgform.enhance.impl.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/enhance/impl/c.class */
public class CgformEnhanceImportDemo implements CgformEnhanceJavaImportInter {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(CgformEnhanceImportDemo.class);

    @Override // org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaImportInter
    public EnhanceDataEnum execute(String tableName, JSONObject json) throws BusinessException {
        if (ConvertUtils.isEmpty(json.get("name"))) {
            json.put("name", "默认值");
            return EnhanceDataEnum.INSERT;
        }
        if (OnlineImportValidator.ERROR.equals(json.getString("name"))) {
            json.put("name", "默认值");
            throw new BusinessException("测试抛出异常error");
        }
        if ("hello".equals(json.getString("name"))) {
            json.put("id", "testid123");
            json.put("name", "JAVA导入增强 测试修改");
            return EnhanceDataEnum.UPDATE;
        }
        if ("ok".equals(json.getString("name"))) {
            return EnhanceDataEnum.ABANDON;
        }
        return EnhanceDataEnum.INSERT;
    }
}
