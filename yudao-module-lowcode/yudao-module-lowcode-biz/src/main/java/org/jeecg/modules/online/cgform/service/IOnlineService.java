package org.jeecg.modules.online.cgform.service;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJs;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.model.OnlComplexModel;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/IOnlineService.class */
public interface IOnlineService {
    OnlComplexModel queryOnlineConfig(OnlCgformHead onlCgformHead, String str);

    JSONObject queryOnlineFormObj(OnlCgformHead onlCgformHead, OnlCgformEnhanceJs onlCgformEnhanceJs);

    JSONObject queryOnlineFormObj(OnlCgformHead onlCgformHead, String str);

    List<OnlCgformButton> queryFormValidButton(String str);

    JSONObject queryOnlineFormItem(OnlCgformHead onlCgformHead, String str);

    JSONObject queryFlowOnlineFormItem(OnlCgformHead onlCgformHead, String str, String str2);

    String queryEnahcneJsString(String str, String str2);

    JSONObject getOnlineVue3QueryInfo(String str);

    List<DictModel> getOnlineTableDictData(String str, String str2, String str3);
}
