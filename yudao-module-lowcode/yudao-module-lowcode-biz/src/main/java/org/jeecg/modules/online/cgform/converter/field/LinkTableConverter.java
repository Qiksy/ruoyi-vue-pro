package org.jeecg.modules.online.cgform.converter.field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.online.cgform.converter.common.ForeseeConvert;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.utils.CgformUtil;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* compiled from: LinkTableConverter.java */
/* renamed from: org.jeecg.modules.online.cgform.converter.b.f */
/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/converter/b/f.class */
public class LinkTableConverter extends ForeseeConvert {

    /* renamed from: d */
    private static final Logger log = LoggerFactory.getLogger(LinkTableConverter.class);

    /* renamed from: c */
    protected IOnlCgformFieldService onlCgformFieldService;

    public LinkTableConverter(OnlCgformField onlCgformField) {
        String dictTable = onlCgformField.getDictTable();
        String dictText = onlCgformField.getDictText();
        String dictField = onlCgformField.getDictField();
        List<DictModel> arrayList = new ArrayList<>();
        try {
            String str = dictText.split(CgformUtil.COMMA_SEPARATOR)[0];
            this.onlCgformFieldService = SpringContextUtils.getBean(IOnlCgformFieldService.class);
            List<Map<String, Object>> queryLinkTableDictList = this.onlCgformFieldService.queryLinkTableDictList(dictTable, dictText, dictField);
            if (queryLinkTableDictList != null && !queryLinkTableDictList.isEmpty()) {
                for (Map<String, Object> map : queryLinkTableDictList) {
                    arrayList.add(new DictModel(CgformUtil.m253a(map, dictField), CgformUtil.m253a(map, str)));
                }
            }
        } catch (Exception e) {
            log.error("关联记录组件 导入导出数据翻译失败", e.getMessage());
        }
        this.dictlList = arrayList;
    }
}
