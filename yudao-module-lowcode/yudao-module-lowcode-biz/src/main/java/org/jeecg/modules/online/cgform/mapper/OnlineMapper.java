package org.jeecg.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/mapper/OnlineMapper.class */
public interface OnlineMapper {
    List<Map<String, Object>> selectByCondition(@Param("sqlStr") String str, @Param("param") Map<String, Object> map);

    IPage<Map<String, Object>> selectPageByCondition(Page<Map<String, Object>> page, @Param("sqlStr") String str, @Param("param") Map<String, Object> map);
}
