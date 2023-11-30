package cn.iocoder.yudao.module.sale.dal.mysql.declinewarning;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarning.DeclineWarningDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.sale.controller.admin.declinewarning.vo.*;

/**
 * 销量预警 Mapper
 *
 * @author 播恩超级管理员
 */
@Mapper
public interface DeclineWarningMapper extends BaseMapperX<DeclineWarningDO> {

    default PageResult<DeclineWarningDO> selectPage(DeclineWarningPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeclineWarningDO>()
                .eqIfPresent(DeclineWarningDO::getZoneCode, reqVO.getZoneCode())
                .likeIfPresent(DeclineWarningDO::getZoneName, reqVO.getZoneName())
                .likeIfPresent(DeclineWarningDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(DeclineWarningDO::getAreaCode, reqVO.getAreaCode())
                .betweenIfPresent(DeclineWarningDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DeclineWarningDO::getId));
    }

}