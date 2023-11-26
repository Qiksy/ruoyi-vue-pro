package cn.iocoder.yudao.module.sale.dal.mysql.competeinfosub;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.sale.dal.dataobject.competeinfosub.CompeteInfoSubDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.sale.controller.admin.competeinfosub.vo.*;

/**
 * 竞品信息子 Mapper
 *
 * @author 播恩超级管理员
 */
@Mapper
public interface CompeteInfoSubMapper extends BaseMapperX<CompeteInfoSubDO> {

    default PageResult<CompeteInfoSubDO> selectPage(CompeteInfoSubPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CompeteInfoSubDO>()
                .eqIfPresent(CompeteInfoSubDO::getParentId, reqVO.getParentId())
                .betweenIfPresent(CompeteInfoSubDO::getChangeDate, reqVO.getChangeDate())
                .eqIfPresent(CompeteInfoSubDO::getPriceChanges, reqVO.getPriceChanges())
                .eqIfPresent(CompeteInfoSubDO::getFileUrl, reqVO.getFileUrl())
                .betweenIfPresent(CompeteInfoSubDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CompeteInfoSubDO::getId));
    }

}