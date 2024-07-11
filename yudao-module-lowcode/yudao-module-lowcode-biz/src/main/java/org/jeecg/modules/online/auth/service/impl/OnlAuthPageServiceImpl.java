package org.jeecg.modules.online.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.mapper.OnlAuthPageMapper;
import org.jeecg.modules.online.auth.mapper.OnlAuthRelationMapper;
import org.jeecg.modules.online.auth.vo.AuthColumnVO;
import org.jeecg.modules.online.auth.vo.AuthPageVO;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* compiled from: OnlAuthPageServiceImpl.java */
@Service("onlAuthPageServiceImpl")
/* renamed from: org.jeecg.modules.online.auth.service.a.b */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/service/a/b.class */
public class OnlAuthPageServiceImpl extends ServiceImpl<OnlAuthPageMapper, OnlAuthPage> implements IOnlAuthPageService {

    @Autowired
    private OnlAuthRelationMapper onlAuthRelationMapper;

    /* renamed from: a */

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public void disableAuthColumn(AuthColumnVO authColumnVO) {
        update(new UpdateWrapper<OnlAuthPage>().lambda()
                .eq(OnlAuthPage::getCgformId, authColumnVO.getCgformId())
                .eq(OnlAuthPage::getCode, authColumnVO.getCode()).eq(OnlAuthPage::getType, 1)
                .set(OnlAuthPage::getStatus, 0));
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    @Transactional
    public void enableAuthColumn(AuthColumnVO authColumnVO) {
        String cgformId = authColumnVO.getCgformId();
        String code = authColumnVO.getCode();
        List<OnlAuthPage> list = list(new LambdaQueryWrapper<OnlAuthPage>().eq(OnlAuthPage::getCgformId, cgformId).eq(OnlAuthPage::getCode, code).eq(OnlAuthPage::getType, 1));
        if (list != null && !list.isEmpty()) {
            update(new UpdateWrapper<OnlAuthPage>().lambda().eq(OnlAuthPage::getCgformId, cgformId).eq(OnlAuthPage::getCode, code).eq(OnlAuthPage::getType, 1).set(OnlAuthPage::getStatus, 1));
            return;
        }
        ArrayList<OnlAuthPage> arrayList = new ArrayList<>();
        arrayList.add(new OnlAuthPage(cgformId, code, 3, 5));
        arrayList.add(new OnlAuthPage(cgformId, code, 5, 5));
        arrayList.add(new OnlAuthPage(cgformId, code, 5, 3));
        saveBatch(arrayList);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public void switchAuthColumn(AuthColumnVO authColumnVO) {
        String cgformId = authColumnVO.getCgformId();
        String code = authColumnVO.getCode();
        int switchFlag = authColumnVO.getSwitchFlag();
        //todo 可能导致事务异常
        if (switchFlag == 1) {
            switchListShow(cgformId, code, authColumnVO.isListShow());
        } else if (switchFlag == 2) {
            switchFormShow(cgformId, code, authColumnVO.isFormShow());
        } else if (switchFlag == 3) {
            switchFormEditable(cgformId, code, authColumnVO.isFormEditable());
        }
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    @Transactional
    public void switchFormShow(String cgformId, String code, boolean flag) {
        m73a(cgformId, code, 5, 5, flag);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    @Transactional
    public void switchFormEditable(String cgformId, String code, boolean flag) {
        m73a(cgformId, code, 3, 5, flag);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    @Transactional
    public void switchListShow(String cgformId, String code, boolean flag) {
        m73a(cgformId, code, 5, 3, flag);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public List<AuthPageVO> queryRoleAuthByFormId(String roleId, String cgformId, int type) {
        return ((OnlAuthPageMapper) this.baseMapper).queryRoleAuthByFormId(roleId, cgformId, type);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public List<AuthPageVO> queryRoleDataAuth(String roleId, String cgformId) {
        return this.baseMapper.queryRoleDataAuth(roleId, cgformId);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public List<AuthPageVO> queryAuthByFormId(String cgformId, int type) {
        if (type == 1) {
            return this.baseMapper.queryAuthColumnByFormId(cgformId);
        }
        return this.baseMapper.queryAuthButtonByFormId(cgformId);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public List<String> queryRoleNoAuthCode(String cgformId, Integer control, Integer page) {
        return this.baseMapper.queryRoleNoAuthCode(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId(), cgformId, control, page, null);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public List<String> queryFormDisabledCode(String cgformId) {
        return queryRoleNoAuthCode(cgformId, 3, 5);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public List<String> queryHideCode(String userId, String cgformId, boolean isList) {
        return this.baseMapper.queryRoleNoAuthCode(userId, cgformId, 5, Integer.valueOf(isList ? 3 : 5), null);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public List<String> queryListHideColumn(String userId, String cgformId) {
        return this.baseMapper.queryRoleNoAuthCode(userId, cgformId, 5, 3, 1);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public List<String> queryFormHideColumn(String userId, String cgformId) {
        return this.baseMapper.queryRoleNoAuthCode(userId, cgformId, 5, 5, 1);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public List<String> queryFormHideButton(String userId, String cgformId) {
        return this.baseMapper.queryRoleNoAuthCode(userId, cgformId, 5, 5, 2);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public List<String> queryHideCode(String cgformId, boolean isList) {
        return this.baseMapper.queryRoleNoAuthCode(((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId(), cgformId, 5, isList ? 3 : 5, null);
    }

    @Override // org.jeecg.modules.online.auth.service.IOnlAuthPageService
    public List<String> queryListHideButton(String userId, String cgformId) {
        if (userId == null) {
            userId = ((LoginUser) SecurityUtils.getSubject().getPrincipal()).getId();
        }
        return this.baseMapper.queryRoleNoAuthCode(userId, cgformId, 5, 3, 2);
    }

    /* renamed from: a */
    private void m73a(String str, String str2, int i, int i2, boolean z) {
        OnlAuthPage onlAuthPage = this.baseMapper.selectOne(
                new LambdaQueryWrapper<OnlAuthPage>()
                        .eq(OnlAuthPage::getCgformId, str)
                        .eq(OnlAuthPage::getCode, str2)
                        .eq(OnlAuthPage::getControl, i)
                        .eq(OnlAuthPage::getPage, i2)
                        .eq(OnlAuthPage::getType, 1));
        if (z) {
            if (onlAuthPage == null) {
                OnlAuthPage onlAuthPage2 = new OnlAuthPage();
                onlAuthPage2.setCgformId(str);
                onlAuthPage2.setCode(str2);
                onlAuthPage2.setControl(i);
                onlAuthPage2.setPage(i2);
                onlAuthPage2.setType(1);
                onlAuthPage2.setStatus(1);
                this.baseMapper.insert(onlAuthPage2);
                return;
            }
            if (onlAuthPage.getStatus() == 0) {
                onlAuthPage.setStatus(1);
                this.baseMapper.updateById(onlAuthPage);
                return;
            }
            return;
        }
        if (!z && onlAuthPage != null) {
            String id = onlAuthPage.getId();
            this.baseMapper.deleteById(id);
            this.onlAuthRelationMapper.delete((LambdaQueryWrapper<OnlAuthRelation>) new LambdaQueryWrapper<OnlAuthRelation>().eq(OnlAuthRelation::getAuthId, id));
        }
    }
}
