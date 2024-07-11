package org.jeecg.modules.online.cgreport.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgreport/mapper/OnlCgreportHeadMapper.class */
public interface OnlCgreportHeadMapper extends BaseMapper<OnlCgreportHead> {
    @Select({"${selectSql}"})
    List<Map<String, Object>> executeSqlDict(@Param("selectSql") String str);

    List<DictModel> queryDictListBySql(@Param("dictCode") String str, @Param("ew") Wrapper<?> wrapper);

    IPage<Map<String, Object>> selectPageBySql(Page<Map<String, Object>> page, @Param("tableSql") String str, @Param("ew") Wrapper<?> wrapper);

    @Deprecated
    IPage<Map<String, Object>> executeParseSql(Page<Map<String, Object>> page, @Param("sqlStr") String str);

    Map<String, Object> queryCgReportMainConfig(@Param("reportId") String str);

    List<Map<String, Object>> queryCgReportItems(@Param("cgrheadId") String str);

    List<OnlCgreportParam> queryCgReportParams(@Param("cgrheadId") String str);

    List<Map<String, Object>> selectByCondition(@Param("sqlStr") String str, @Param("param") Map<String, Object> map);

    IPage<Map<String, Object>> selectPageByCondition(Page<Map<String, Object>> page, @Param("sqlStr") String str, @Param("param") Map<String, Object> map);
}
