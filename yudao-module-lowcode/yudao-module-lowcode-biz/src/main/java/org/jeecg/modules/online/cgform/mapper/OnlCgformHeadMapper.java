package org.jeecg.modules.online.cgform.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/mapper/OnlCgformHeadMapper.class */
public interface OnlCgformHeadMapper extends BaseMapper<OnlCgformHead> {
    @InterceptorIgnore(tenantLine = "true")
    void executeDDL(@Param("sqlStr") String str);

    List<Map<String, Object>> queryList(@Param("sqlStr") String str);

    List<String> queryOnlinetables();

    Map<String, Object> queryOneByTableNameAndId(@Param("tbname") String str, @Param("dataId") String str2);

    void deleteOne(@Param("tbname") String str, @Param("dataId") String str2);

    @Select({"select max(copy_version) from onl_cgform_head where physic_id = #{physicId}"})
    Integer getMaxCopyVersion(@Param("physicId") String str);

    @Select({"select table_name from onl_cgform_head where physic_id = #{physicId}"})
    List<String> queryAllCopyTableName(@Param("physicId") String str);

    @Select({"select physic_id from onl_cgform_head GROUP BY physic_id"})
    List<String> queryCopyPhysicId();

    String queryCategoryIdByCode(@Param("code") String str);

    @Select({"select count(*) from ${tableName} where ${pidField} = #{pidValue}"})
    Integer queryChildNode(@Param("tableName") String str, @Param("pidField") String str2, @Param("pidValue") String str3);
}
