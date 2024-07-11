package org.jeecg.modules.online.auth.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.modules.online.auth.entity.OnlAuthData;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.mapper.OnlAuthDataMapper;
import org.jeecg.modules.online.auth.mapper.OnlAuthRelationMapper;
import org.jeecg.modules.online.auth.service.IOnlAuthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* compiled from: OnlAuthDataServiceImpl.java */
@Service("onlAuthDataServiceImpl")
/* renamed from: org.jeecg.modules.online.auth.service.a.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/service/a/a.class */
public class OnlAuthDataServiceImpl extends ServiceImpl<OnlAuthDataMapper, OnlAuthData> implements IOnlAuthDataService {

    @Autowired
    private OnlAuthRelationMapper onlAuthRelationMapper;

    /* renamed from: a */
    public OnlAuthDataServiceImpl() {
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthDataService
    public void deleteOne(String id) {
        removeById(id);
        this.onlAuthRelationMapper.delete(new LambdaQueryWrapper<OnlAuthRelation>().eq(OnlAuthRelation::getAuthId, id));
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthDataService
    public List<SysPermissionDataRuleModel> queryUserOnlineAuthData(String userId, String cgformId) {
        List<SysPermissionDataRuleModel> queryRoleAuthData = this.baseMapper.queryRoleAuthData(userId, cgformId);
        List<SysPermissionDataRuleModel> queryDepartAuthData = this.baseMapper.queryDepartAuthData(userId, cgformId);
        List<SysPermissionDataRuleModel> queryUserAuthData = this.baseMapper.queryUserAuthData(userId, cgformId);
        HashMap<String, SysPermissionDataRuleModel> hashMap = new HashMap<>(5);
        for (SysPermissionDataRuleModel sysPermissionDataRuleModel : queryRoleAuthData) {
            String id = sysPermissionDataRuleModel.getId();
            hashMap.putIfAbsent(id, sysPermissionDataRuleModel);
        }
        for (SysPermissionDataRuleModel sysPermissionDataRuleModel2 : queryDepartAuthData) {
            String id2 = sysPermissionDataRuleModel2.getId();
            hashMap.putIfAbsent(id2, sysPermissionDataRuleModel2);
        }
        for (SysPermissionDataRuleModel sysPermissionDataRuleModel3 : queryUserAuthData) {
            String id3 = sysPermissionDataRuleModel3.getId();
            hashMap.putIfAbsent(id3, sysPermissionDataRuleModel3);
        }
        Collection<SysPermissionDataRuleModel> values = hashMap.values();
        if (values.isEmpty()) {
            return null;
        }
        return new ArrayList<SysPermissionDataRuleModel>(values);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthDataService
    public void createAiTestAuthData(JSONObject json) {
        ArrayList<OnlAuthData> arrayList = new ArrayList<>();
        JSONArray jSONArray = json.getJSONArray("data");
        if (jSONArray != null && !jSONArray.isEmpty()) {
            for (int i = 0; i < jSONArray.size(); i++) {
                arrayList.add(JSONObject.toJavaObject(jSONArray.getJSONObject(i), OnlAuthData.class));
            }
        }
        // todo 可能导致事务错误
        saveBatch(arrayList);
    }
}
