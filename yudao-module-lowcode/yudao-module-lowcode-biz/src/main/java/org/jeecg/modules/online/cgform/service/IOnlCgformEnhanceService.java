package org.jeecg.modules.online.cgform.service;

import java.util.List;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/IOnlCgformEnhanceService.class */
public interface IOnlCgformEnhanceService {
    List<OnlCgformEnhanceJava> queryEnhanceJavaList(String str);

    void saveEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava);

    void updateEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava);

    void deleteEnhanceJava(String str);

    void deleteBatchEnhanceJava(List<String> list);

    boolean checkOnlyEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava);

    boolean checkOnlyEnhance(OnlCgformEnhanceSql onlCgformEnhanceSql);

    List<OnlCgformEnhanceSql> queryEnhanceSqlList(String str);

    void saveEnhanceSql(OnlCgformEnhanceSql onlCgformEnhanceSql);

    void updateEnhanceSql(OnlCgformEnhanceSql onlCgformEnhanceSql);

    void deleteEnhanceSql(String str);

    void deleteBatchEnhanceSql(List<String> list);
}
