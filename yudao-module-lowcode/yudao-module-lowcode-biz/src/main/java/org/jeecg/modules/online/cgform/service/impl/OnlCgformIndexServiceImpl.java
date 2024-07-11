package org.jeecg.modules.online.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Iterator;
import java.util.List;

import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
import org.jeecg.modules.online.cgform.mapper.OnlCgformHeadMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformIndexMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* compiled from: OnlCgformIndexServiceImpl.java */
@Service("onlCgformIndexServiceImpl")
/* renamed from: org.jeecg.modules.online.cgform.service.a.e */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/a/e.class */
public class OnlCgformIndexServiceImpl extends ServiceImpl<OnlCgformIndexMapper, OnlCgformIndex> implements IOnlCgformIndexService {

    /* renamed from: a */
    private static final Logger logger = LoggerFactory.getLogger(OnlCgformIndexServiceImpl.class);

    @Autowired
    private OnlCgformHeadMapper onlCgformHeadMapper;

    /* renamed from: a */

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformIndexService
    public void createIndex(String code, String databaseType, String tbname) {
        LambdaQueryWrapper<OnlCgformIndex> var4 = new LambdaQueryWrapper<>();
        var4.eq(OnlCgformIndex::getCgformHeadId, code);
        List var5 = this.list(var4);
        if (var5 != null && !var5.isEmpty()) {

            for (Object o : var5) {
                OnlCgformIndex var7 = (OnlCgformIndex) o;
                if (!CommonConstant.DEL_FLAG_1.equals(var7.getDelFlag()) && "N".equals(var7.getIsDbSynch())) {
                    String var8 = "";
                    String var9 = var7.getIndexName();
                    String var10 = var7.getIndexField();
                    String var11 = "normal".equals(var7.getIndexType()) ? " index " : var7.getIndexType() + " index ";
                    switch (databaseType) {
                        case "MYSQL":
                            var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
                            break;
                        case "ORACLE":
                            var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
                            break;
                        case "SQLSERVER":
                            var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
                            break;
                        case "POSTGRESQL":
                            var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
                            break;
                        default:
                            var8 = "create " + var11 + var9 + " on " + tbname + "(" + var10 + ")";
                    }

                    this.onlCgformHeadMapper.executeDDL(var8);
                    var7.setIsDbSynch("Y");
                    this.updateById(var7);
                }
            }
        }

    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformIndexService
    public boolean isExistIndex(String countSql) {
        if (countSql == null) {
            return true;
        }
        int valueOf = this.baseMapper.queryIndexCount(countSql);
        return valueOf > 0;
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformIndexService
    public List<OnlCgformIndex> getCgformIndexsByCgformId(String cgformId) {
        return this.baseMapper.selectList( new LambdaQueryWrapper<OnlCgformIndex>().in(OnlCgformIndex::getCgformHeadId, cgformId));
    }
}
