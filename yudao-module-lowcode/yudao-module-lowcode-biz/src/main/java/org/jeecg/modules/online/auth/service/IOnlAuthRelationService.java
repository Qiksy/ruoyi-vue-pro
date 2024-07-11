package org.jeecg.modules.online.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/service/IOnlAuthRelationService.class */
public interface IOnlAuthRelationService extends IService<OnlAuthRelation> {
    void saveRoleAuth(String str, String str2, int i, String str3, List<String> list);
}
