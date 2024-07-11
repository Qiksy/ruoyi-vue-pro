package org.jeecg.modules.online.cgform.rule;

import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.jeecg.common.handler.IFillRuleHandler;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/rule/OrderNumberRule.class */
public class OrderNumberRule implements IFillRuleHandler {
    public Object execute(JSONObject params, JSONObject formData) {
        Object obj;
        String str = "CN";
        if (params != null && (obj = params.get("prefix")) != null) {
            str = obj.toString();
        }
        String str2 = str + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + (RandomUtils.nextInt(90) + 10);
        String string = formData.getString("name");
        if (!StringUtils.isEmpty(string)) {
            str2 = str2 + string;
        }
        return str2;
    }
}
