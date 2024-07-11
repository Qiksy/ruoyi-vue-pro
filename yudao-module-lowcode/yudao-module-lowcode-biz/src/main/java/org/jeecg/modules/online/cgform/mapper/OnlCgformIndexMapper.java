package org.jeecg.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/mapper/OnlCgformIndexMapper.class */
public interface OnlCgformIndexMapper extends BaseMapper<OnlCgformIndex> {
    int queryIndexCount(@Param("sqlStr") String str);
}
