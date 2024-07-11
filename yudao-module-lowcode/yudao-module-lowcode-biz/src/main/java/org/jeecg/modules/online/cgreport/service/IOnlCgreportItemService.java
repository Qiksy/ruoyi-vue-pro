package org.jeecg.modules.online.cgreport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/service/IOnlCgreportItemService.class */
public interface IOnlCgreportItemService extends IService<OnlCgreportItem> {
    List<Map<String, String>> getAutoListQueryInfo(String str);
}
