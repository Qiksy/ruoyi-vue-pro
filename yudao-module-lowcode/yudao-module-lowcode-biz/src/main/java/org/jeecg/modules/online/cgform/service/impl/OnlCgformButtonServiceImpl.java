package org.jeecg.modules.online.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.mapper.OnlCgformButtonMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformButtonService;
import org.springframework.stereotype.Service;

/* compiled from: OnlCgformButtonServiceImpl.java */
@Service("onlCgformButtonServiceImpl")
/* renamed from: org.jeecg.modules.online.cgform.service.a.a */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/a/a.class */
public class OnlCgformButtonServiceImpl extends ServiceImpl<OnlCgformButtonMapper, OnlCgformButton> implements IOnlCgformButtonService {
    /* renamed from: a */
    public OnlCgformButtonServiceImpl(){

    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformButtonService
    public void saveButton(OnlCgformButton onlCgformButton) {
        Long selectCount = this.baseMapper.selectCount(new LambdaQueryWrapper<OnlCgformButton>().eq(OnlCgformButton::getButtonCode, onlCgformButton.getButtonCode()).eq(OnlCgformButton::getCgformHeadId, onlCgformButton.getCgformHeadId()));
        if (selectCount == null || selectCount == 0) {
            save(onlCgformButton);
        }
    }
}
