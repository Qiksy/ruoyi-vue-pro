package org.jeecg.modules.online.cgreport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.mapper.OnlCgreportItemMapper;
import org.jeecg.modules.online.cgreport.utils.CgReportSqlUtil;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/* compiled from: OnlCgreportItemServiceImpl.java */
@Service("onlCgreportItemServiceImpl")
/* renamed from: org.jeecg.modules.online.cgreport.service.a.d */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/service/a/d.class */
public class OnlCgreportItemServiceImpl extends ServiceImpl<OnlCgreportItemMapper, OnlCgreportItem> implements IOnlCgreportItemService {
    /* renamed from: a */

    @Override // org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService
    @Cacheable(value = {"sys:cache:online:rp"}, key = "'search-v2-'+#cgrheadId")
    public List<Map<String, String>> getAutoListQueryInfo(String cgrheadId) {
        LambdaQueryWrapper<OnlCgreportItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OnlCgreportItem::getCgrheadId, cgrheadId);
        lambdaQueryWrapper.eq(OnlCgreportItem::getIsSearch, 1);
        List<OnlCgreportItem> list = list(lambdaQueryWrapper);
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        int i = 0;
        for (OnlCgreportItem onlCgreportItem : list) {
            HashMap<String,String> hashMap = new HashMap<>(5);
            hashMap.put("label", onlCgreportItem.getFieldTxt());
            String dictCode = onlCgreportItem.getDictCode();
            if (StrUtils.isNotEmpty(dictCode)) {
                if (CgReportSqlUtil.m431b(dictCode)) {
                    hashMap.put(CgformUtil.VIEW, "search");
                    hashMap.put("fieldId", onlCgreportItem.getId());
                } else {
                    hashMap.put(CgformUtil.VIEW, CgformUtil.LIST);
                }
            } else {
                hashMap.put(CgformUtil.VIEW, onlCgreportItem.getFieldType().toLowerCase());
            }
            hashMap.put("mode", StrUtils.isEmpty(onlCgreportItem.getSearchMode()) ? "single" : onlCgreportItem.getSearchMode());
            hashMap.put("field", onlCgreportItem.getFieldName());
            i++;
            if (i > 2) {
                hashMap.put("hidden", "1");
            }
            arrayList.add(hashMap);
        }
        return arrayList;
    }
}
