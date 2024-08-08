package org.jeecg.modules.online.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;

import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;
import org.jeecg.modules.online.cgform.mapper.OnlCgformEnhanceJavaMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformEnhanceSqlMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* compiled from: OnlCgformEnhanceServiceImpl.java */
@Service("onlCgformEnhanceService")
/* renamed from: org.jeecg.modules.online.cgform.service.a.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/a/b.class */
public class OnlCgformEnhanceServiceImpl implements IOnlCgformEnhanceService {

    @Autowired
    private OnlCgformEnhanceJavaMapper onlCgformEnhanceJavaMapper;

    @Autowired
    private OnlCgformEnhanceSqlMapper onlCgformEnhanceSqlMapper;

    /* renamed from: a */
    public OnlCgformEnhanceServiceImpl() {
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public List<OnlCgformEnhanceJava> queryEnhanceJavaList(String cgformId) {
        return this.onlCgformEnhanceJavaMapper.selectList(new LambdaQueryWrapper<OnlCgformEnhanceJava>().eq(OnlCgformEnhanceJava::getCgformHeadId, cgformId));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void saveEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        this.onlCgformEnhanceJavaMapper.insert(onlCgformEnhanceJava);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void updateEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        this.onlCgformEnhanceJavaMapper.updateById(onlCgformEnhanceJava);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void deleteEnhanceJava(String id) {
        this.onlCgformEnhanceJavaMapper.deleteById(id);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void deleteBatchEnhanceJava(List<String> idList) {
        this.onlCgformEnhanceJavaMapper.deleteBatchIds(idList);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public boolean checkOnlyEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        LambdaQueryWrapper<OnlCgformEnhanceJava> lambdaQueryWrapper = new LambdaQueryWrapper<OnlCgformEnhanceJava>();
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getButtonCode, onlCgformEnhanceJava.getButtonCode());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getCgformHeadId, onlCgformEnhanceJava.getCgformHeadId());
        lambdaQueryWrapper.eq(OnlCgformEnhanceJava::getEvent, onlCgformEnhanceJava.getEvent());
        Long selectCount = this.onlCgformEnhanceJavaMapper.selectCount(lambdaQueryWrapper);
        if (selectCount == null) {
            return true;
        }
        if ((selectCount == 1 && StrUtils.isEmpty(onlCgformEnhanceJava.getId())) || selectCount == 2) {
            return false;
        }
        return true;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public boolean checkOnlyEnhance(OnlCgformEnhanceSql onlCgformEnhanceSql) {
        LambdaQueryWrapper<OnlCgformEnhanceSql> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgformEnhanceSql::getButtonCode, onlCgformEnhanceSql.getButtonCode());
        lambdaQueryWrapper.eq(OnlCgformEnhanceSql::getCgformHeadId, onlCgformEnhanceSql.getCgformHeadId());
        Long selectCount = this.onlCgformEnhanceSqlMapper.selectCount(lambdaQueryWrapper);
        if (selectCount == null) {
            return true;
        }
        if ((selectCount == 1 && StrUtils.isEmpty(onlCgformEnhanceSql.getId())) || selectCount > 1) {
            return false;
        }
        return true;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public List<OnlCgformEnhanceSql> queryEnhanceSqlList(String cgformId) {
        return this.onlCgformEnhanceSqlMapper.selectList(new LambdaQueryWrapper<OnlCgformEnhanceSql>().eq(OnlCgformEnhanceSql::getCgformHeadId, cgformId));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void saveEnhanceSql(OnlCgformEnhanceSql onlCgformEnhanceSql) {
        this.onlCgformEnhanceSqlMapper.insert(onlCgformEnhanceSql);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void updateEnhanceSql(OnlCgformEnhanceSql onlCgformEnhanceSql) {
        this.onlCgformEnhanceSqlMapper.updateById(onlCgformEnhanceSql);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void deleteEnhanceSql(String id) {
        this.onlCgformEnhanceSqlMapper.deleteById(id);
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService
    public void deleteBatchEnhanceSql(List<String> idList) {
        this.onlCgformEnhanceSqlMapper.deleteBatchIds(idList);
    }
}
