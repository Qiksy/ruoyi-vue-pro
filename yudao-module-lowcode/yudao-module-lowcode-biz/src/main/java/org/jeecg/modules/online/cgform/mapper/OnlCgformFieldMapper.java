package org.jeecg.modules.online.cgform.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/cgform/mapper/OnlCgformFieldMapper.class */
public interface OnlCgformFieldMapper extends BaseMapper<OnlCgformField> {
    public static final String SELECT_SQL = "SELECT ${ew.sqlSelect} FROM ${tableName} ${ew.customSqlSegment}";

    @Select({SELECT_SQL})
    JSONObject doSelect(@Param("tableName") String str, @Param("ew") QueryWrapper<?> queryWrapper);

    @Select({SELECT_SQL})
    List<JSONObject> doSelectList(@Param("tableName") String str, @Param("ew") QueryWrapper<?> queryWrapper);

    @Update({"UPDATE ${tableName} SET ${ew.sqlSet} ${ew.customSqlSegment}"})
    int doUpdate(@Param("tableName") String str, @Param("ew") UpdateWrapper<?> updateWrapper);

    @Delete({"DELETE FROM ${tableName} ${ew.customSqlSegment}"})
    int doDelete(@Param("tableName") String str, @Param("ew") QueryWrapper<?> queryWrapper);

    void executeInsertSQL(Map<String, Object> map);

    void executeUpdatetSQL(Map<String, Object> map);

    List<String> selectOnlineHideColumns(@Param("user_id") String str, @Param("online_tbname") String str2);

    List<String> selectOnlineDisabledColumns(@Param("user_id") String str, @Param("online_tbname") String str2);

    List<String> selectFlowAuthColumns(@Param("table_name") String str, @Param("task_id") String str2, @Param("rule_type") String str3);
}
