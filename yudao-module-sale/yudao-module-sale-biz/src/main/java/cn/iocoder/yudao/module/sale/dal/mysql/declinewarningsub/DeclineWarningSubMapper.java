package cn.iocoder.yudao.module.sale.dal.mysql.declinewarningsub;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarningsub.DeclineWarningSubDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.sale.controller.admin.declinewarningsub.vo.*;

/**
 * 销量下降预警子表 Mapper
 *
 * @author 播恩超级管理员
 */
@Mapper
public interface DeclineWarningSubMapper extends BaseMapperX<DeclineWarningSubDO> {

    default PageResult<DeclineWarningSubDO> selectPage(DeclineWarningSubPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeclineWarningSubDO>()
                .likeIfPresent(DeclineWarningSubDO::getCustomerName, reqVO.getCustomerName())
                .eqIfPresent(DeclineWarningSubDO::getCustomerCode, reqVO.getCustomerCode())
                .likeIfPresent(DeclineWarningSubDO::getEmployeeName, reqVO.getEmployeeName())
                .likeIfPresent(DeclineWarningSubDO::getDeptName, reqVO.getDeptName())
                .eqIfPresent(DeclineWarningSubDO::getPreMonthSales, reqVO.getPreMonthSales())
                .eqIfPresent(DeclineWarningSubDO::getCurrMonthSales, reqVO.getCurrMonthSales())
                .eqIfPresent(DeclineWarningSubDO::getDeclineRatio, reqVO.getDeclineRatio())
                .eqIfPresent(DeclineWarningSubDO::getDeclineNum, reqVO.getDeclineNum())
                .eqIfPresent(DeclineWarningSubDO::getParentId, reqVO.getParentId())
                .betweenIfPresent(DeclineWarningSubDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DeclineWarningSubDO::getId));
    }

}