package cn.iocoder.yudao.module.sale.dal.mysql.productioncompeteinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioncompeteinfo.ProductionCompeteInfoDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.sale.controller.admin.productioncompeteinfo.vo.*;

/**
 * 工厂竞品管理 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductionCompeteInfoMapper extends BaseMapperX<ProductionCompeteInfoDO> {

    default PageResult<ProductionCompeteInfoDO> selectPage(ProductionCompeteInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductionCompeteInfoDO>()
                .eqIfPresent(ProductionCompeteInfoDO::getProductionId, reqVO.getProductionId())
                .eqIfPresent(ProductionCompeteInfoDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(ProductionCompeteInfoDO::getPrice, reqVO.getPrice())
                .betweenIfPresent(ProductionCompeteInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductionCompeteInfoDO::getId));
    }

    /*
      这里不能叫做 selectList，因为会和 MyBatis Plus 冲突
     */
    List<ProductionCompeteInfoRespVO> selectListByCondition(ProductionCompeteInfoListReqVO listReqVO);
}