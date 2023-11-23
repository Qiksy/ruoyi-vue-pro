package cn.iocoder.yudao.module.sale.dal.mysql.productioninfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioninfo.ProductionInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.sale.controller.admin.productioninfo.vo.*;

/**
 * 物料信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductionInfoMapper extends BaseMapperX<ProductionInfoDO> {

    default PageResult<ProductionInfoDO> selectPage(ProductionInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductionInfoDO>()
                .likeIfPresent(ProductionInfoDO::getName, reqVO.getName())
                .eqIfPresent(ProductionInfoDO::getMarsaleclassId, reqVO.getMarsaleclassId())
                .eqIfPresent(ProductionInfoDO::getMarbasclassId, reqVO.getMarbasclassId())
                .eqIfPresent(ProductionInfoDO::getProdlineId, reqVO.getProdlineId())
                .eqIfPresent(ProductionInfoDO::getSpec, reqVO.getSpec())
                .eqIfPresent(ProductionInfoDO::getPrice, reqVO.getPrice())
                .eqIfPresent(ProductionInfoDO::getProtein, reqVO.getProtein())
                .betweenIfPresent(ProductionInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductionInfoDO::getId));
    }

    default PageResult<ProductionInfoDO> selectPage(ProductionInfoPageReqVO reqVO, Collection<Long> marbasclassIds) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductionInfoDO>()
                .likeIfPresent(ProductionInfoDO::getName, reqVO.getName())
                .eqIfPresent(ProductionInfoDO::getMarsaleclassId, reqVO.getMarsaleclassId())
                .inIfPresent(ProductionInfoDO::getMarbasclassId, marbasclassIds)
                .eqIfPresent(ProductionInfoDO::getProdlineId, reqVO.getProdlineId())
                .eqIfPresent(ProductionInfoDO::getSpec, reqVO.getSpec())
                .eqIfPresent(ProductionInfoDO::getPrice, reqVO.getPrice())
                .eqIfPresent(ProductionInfoDO::getProtein, reqVO.getProtein())
                .betweenIfPresent(ProductionInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(ProductionInfoDO::getMarbasclassId,ProductionInfoDO::getId));
    }

}