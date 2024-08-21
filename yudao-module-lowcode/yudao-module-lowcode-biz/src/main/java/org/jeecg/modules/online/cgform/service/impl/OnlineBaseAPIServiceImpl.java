package org.jeecg.modules.online.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.Iterator;
import java.util.List;

import org.jeecg.common.util.online.ConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.service.IOnlineBaseAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* compiled from: OnlineBaseAPIServiceImpl.java */
@Service("onlineBaseAPI")
/* renamed from: org.jeecg.modules.online.cgform.service.a.g */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/a/g.class */
public class OnlineBaseAPIServiceImpl implements IOnlineBaseAPI {

    @Autowired
    private OnlCgformHeadMapper onlCgformHeadMapper;

    @Autowired
    private OnlCgformFieldMapper onlCgformFieldMapper;

    /* renamed from: a */
    public OnlineBaseAPIServiceImpl() {
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlineBaseAPI
    public String getOnlineErpCode(String code, String tableType) {
        if ("3".equals(tableType)) {
            String substring = code.substring(1);
            OnlCgformHead onlCgformHead = (OnlCgformHead) this.onlCgformHeadMapper.selectById(substring);
            if (onlCgformHead != null && onlCgformHead.getTableType() == 3) {
                List<OnlCgformField> selectList = this.onlCgformFieldMapper.selectList(new LambdaQueryWrapper<OnlCgformField>().eq(OnlCgformField::getCgformHeadId, substring));
                if (selectList != null && !selectList.isEmpty()) {
                    String str = null;
                    Iterator<OnlCgformField> it = selectList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        OnlCgformField onlCgformField = (OnlCgformField) it.next();
                        if (ConvertUtils.isNotEmpty(onlCgformField.getMainTable())) {
                            str = onlCgformField.getMainTable();
                            break;
                        }
                    }
                    OnlCgformHead onlCgformHead2 = this.onlCgformHeadMapper.selectOne(new LambdaQueryWrapper<OnlCgformHead>().eq(OnlCgformHead::getTableName, str));
                    if (onlCgformHead2 != null) {
                        code = "/" + onlCgformHead2.getId();
                    }
                }
            }
        }
        return code;
    }
}
