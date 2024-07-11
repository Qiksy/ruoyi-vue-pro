package org.jeecg.modules.online.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.vo.AuthPageVO;

/* loaded from: hibernate-re-3.6.1-beta.jar:org/jeecg/modules/online/auth/mapper/OnlAuthPageMapper.class */
public interface OnlAuthPageMapper extends BaseMapper<OnlAuthPage> {
    List<AuthPageVO> queryRoleAuthByFormId(@Param("roleId") String str, @Param("cgformId") String str2, @Param("type") int i);

    List<AuthPageVO> queryAuthColumnByFormId(@Param("cgformId") String str);

    List<AuthPageVO> queryAuthButtonByFormId(@Param("cgformId") String str);

    List<AuthPageVO> queryRoleDataAuth(@Param("roleId") String str, @Param("cgformId") String str2);

    List<String> queryRoleNoAuthCode(@Param("userId") String str, @Param("cgformId") String str2, @Param("control") Integer num, @Param("page") Integer num2, @Param("type") Integer num3);
}
