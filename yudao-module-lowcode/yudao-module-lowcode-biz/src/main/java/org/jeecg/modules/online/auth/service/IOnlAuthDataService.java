package org.jeecg.modules.online.auth.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.modules.online.auth.entity.OnlAuthData;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/service/IOnlAuthDataService.class */
public interface IOnlAuthDataService extends IService<OnlAuthData> {
    void deleteOne(String str);

    List<SysPermissionDataRuleModel> queryUserOnlineAuthData(String str, String str2);

    void createAiTestAuthData(JSONObject jSONObject);
}
