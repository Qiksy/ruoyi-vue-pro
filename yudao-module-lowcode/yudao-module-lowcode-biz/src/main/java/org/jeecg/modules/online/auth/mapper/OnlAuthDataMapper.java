package org.jeecg.modules.online.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.modules.online.auth.entity.OnlAuthData;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/mapper/OnlAuthDataMapper.class */
public interface OnlAuthDataMapper extends BaseMapper<OnlAuthData> {
    List<SysPermissionDataRuleModel> queryRoleAuthData(@Param("userId") String str, @Param("cgformId") String str2);

    List<SysPermissionDataRuleModel> queryDepartAuthData(@Param("userId") String str, @Param("cgformId") String str2);

    List<SysPermissionDataRuleModel> queryUserAuthData(@Param("userId") String str, @Param("cgformId") String str2);
}
