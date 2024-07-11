package org.jeecg.modules.online.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.mapper.OnlAuthRelationMapper;
import org.jeecg.modules.online.auth.service.IOnlAuthRelationService;
import org.springframework.stereotype.Service;

/* compiled from: OnlAuthRelationServiceImpl.java */
@Service("onlAuthRelationServiceImpl")
/* renamed from: org.jeecg.modules.online.auth.service.a.c */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/service/a/c.class */
public class OnlAuthRelationServiceImpl extends ServiceImpl<OnlAuthRelationMapper, OnlAuthRelation> implements IOnlAuthRelationService {
    /* renamed from: a */

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthRelationService
    public void saveRoleAuth(String roleId, String cgformId, int type, String authMode, List<String> authIds) {
        this.baseMapper.delete(
                new LambdaQueryWrapper<OnlAuthRelation>()
                        .eq(OnlAuthRelation::getCgformId, cgformId)
                        .eq(OnlAuthRelation::getType, type).eq(OnlAuthRelation::getAuthMode, authMode)
                        .eq(OnlAuthRelation::getRoleId, roleId));
        ArrayList<OnlAuthRelation> arrayList = new ArrayList<>();
        for (String str : authIds) {
            OnlAuthRelation onlAuthRelation = new OnlAuthRelation();
            onlAuthRelation.setAuthId(str);
            onlAuthRelation.setCgformId(cgformId);
            onlAuthRelation.setRoleId(roleId);
            onlAuthRelation.setType(type);
            onlAuthRelation.setAuthMode(authMode);
            arrayList.add(onlAuthRelation);
        }
        // todo 可能导致事务异常
        saveBatch(arrayList);
    }
}
