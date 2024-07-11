package org.jeecg.modules.online.cgform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/IOnlCgformIndexService.class */
public interface IOnlCgformIndexService extends IService<OnlCgformIndex> {
    void createIndex(String str, String str2, String str3);

    boolean isExistIndex(String str);

    List<OnlCgformIndex> getCgformIndexsByCgformId(String str);
}
