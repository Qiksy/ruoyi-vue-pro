package org.jeecg.modules.online.cgform.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import org.jeecg.modules.online.cgform.converter.ConvertUtil;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.enums.EnhanceDataEnum;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.utils.OnlineImportValidator;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.jeecg.modules.online.cgform.service.IOnlCgformSqlService;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* compiled from: OnlCgformSqlServiceImpl.java */
@Service("onlCgformSqlServiceImpl")
/* renamed from: org.jeecg.modules.online.cgform.service.a.f */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/service/a/f.class */
public class OnlCgformSqlServiceImpl implements IOnlCgformSqlService {

    /* renamed from: a */
    private static final Logger f423a = LoggerFactory.getLogger(OnlCgformSqlServiceImpl.class);

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformSqlService
    public void saveBatchOnlineTable(OnlCgformHead head, List<OnlCgformField> fieldList, List<Map<String, Object>> dataList) throws BusinessException {
        SqlSession sqlSession = null;
        try {
            try {
                ConvertUtil.m176a(2, dataList, fieldList);
                sqlSession = this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
                OnlCgformFieldMapper onlCgformFieldMapper = sqlSession.getMapper(OnlCgformFieldMapper.class);
                if (1000 >= dataList.size()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        m355a(JSON.toJSONString(dataList.get(i)), head, fieldList, onlCgformFieldMapper);
                    }
                } else {
                    for (int i2 = 0; i2 < dataList.size(); i2++) {
                        m355a(JSON.toJSONString(dataList.get(i2)), head, fieldList, onlCgformFieldMapper);
                        if (i2 % 1000 == 0) {
                            sqlSession.commit();
                            sqlSession.clearCache();
                        }
                    }
                }
                sqlSession.commit();
                sqlSession.close();
            } catch (Exception e) {
                sqlSession.rollback();
                throw new BusinessException(e.getMessage());
            }
        } catch (Throwable th) {
            sqlSession.close();
            throw th;
        }
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformSqlService
    public void saveOrUpdateSubData(String subDataJsonStr, OnlCgformHead head, List<OnlCgformField> subFiledList) throws BusinessException {
        m355a(subDataJsonStr, head, subFiledList, SpringUtil.getBean(OnlCgformFieldMapper.class));
    }

    @Override // org.jeecg.modules.online.cgform.service.IOnlCgformSqlService
    public Map<String, String> saveOnlineImportDataWithValidate(OnlCgformHead head, List<OnlCgformField> fieldList, List<Map<String, Object>> dataList) {
        StringBuffer stringBuffer = new StringBuffer();
        OnlineImportValidator onlineImportValidator = new OnlineImportValidator(fieldList);
        OnlCgformFieldMapper onlCgformFieldMapper = SpringUtil.getBean(OnlCgformFieldMapper.class);
        int i = 0;
        int i2 = 0;
        int size = dataList.size();
        for (int i3 = 0; i3 < size; i3++) {
            String jSONString = JSON.toJSONString(dataList.get(i3));
            i++;
            String m281a = onlineImportValidator.m281a(jSONString, i);
            if (m281a == null) {
                try {
                    m355a(jSONString, head, fieldList, onlCgformFieldMapper);
                } catch (Exception e) {
                    i2++;
                    stringBuffer.append(OnlineImportValidator.m282b(m356a(e.getCause().getMessage()), i));
                }
            } else {
                i2++;
                stringBuffer.append(m281a);
            }
        }
        HashMap<String,String> hashMap = new HashMap<>(5);
        hashMap.put(OnlineImportValidator.ERROR, stringBuffer.toString());
        hashMap.put(OnlineImportValidator.TIP, OnlineImportValidator.m283a(size, i2));
        return hashMap;
    }

    /* renamed from: a */
    private void m355a(String str, OnlCgformHead onlCgformHead, List<OnlCgformField> list, OnlCgformFieldMapper onlCgformFieldMapper) throws BusinessException {
        JSONObject parseObject = JSONObject.parseObject(str);
        EnhanceDataEnum executeEnhanceImport = this.onlCgformHeadService.executeEnhanceImport(onlCgformHead, parseObject);
        String tableName = onlCgformHead.getTableName();
        if (EnhanceDataEnum.INSERT == executeEnhanceImport) {
            Map<String, Object> m196a = CgformUtil.m196a(tableName, list, parseObject);
            f423a.info("executeInsertSQL params: " + JSON.toJSONString(m196a));
            onlCgformFieldMapper.executeInsertSQL(m196a);
        } else {
            if (EnhanceDataEnum.UPDATE == executeEnhanceImport) {
                Map<String, Object> m197b = CgformUtil.m197b(tableName, list, parseObject);
                f423a.info("executeUpdatetSQL params: " + JSON.toJSONString(m197b));
                onlCgformFieldMapper.executeUpdatetSQL(m197b);
                return;
            }
            if (EnhanceDataEnum.ABANDON == executeEnhanceImport) {
            }
        }
    }

    /* renamed from: a */
    private String m356a(String str) {
        Matcher matcher = Pattern.compile("^Duplicate entry \\'(.*)\\' for key .*$").matcher(str);
        if (matcher.find()) {
            return "重复数据" + matcher.group(1);
        }
        return str;
    }
}
