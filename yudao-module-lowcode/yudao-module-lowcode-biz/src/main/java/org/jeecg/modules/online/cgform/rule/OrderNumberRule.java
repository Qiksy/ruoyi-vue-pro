package org.jeecg.modules.online.cgform.rule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomUtils;
import org.jeecg.common.handler.IFillRuleHandler;
import org.jeecg.common.util.online.ConvertUtils;
import org.json.JSONObject;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/rule/OrderNumberRule.class */
public class OrderNumberRule implements IFillRuleHandler {
    public Object execute(JSONObject params, JSONObject formData) {
        Object obj;
        String str = "CN";
        if (params != null && (obj = params.get("prefix")) != null) {
            str = obj.toString();
        }
        String str2 = str + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + (RandomUtils.nextInt(0,90) + 10);
        String string = formData.getString("name");
        if (!ConvertUtils.isEmpty(string)) {
            str2 = str2 + string;
        }
        return str2;
    }
}
