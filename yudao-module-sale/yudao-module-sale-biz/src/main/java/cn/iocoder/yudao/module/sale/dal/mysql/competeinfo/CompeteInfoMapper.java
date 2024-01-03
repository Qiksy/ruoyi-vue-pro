package cn.iocoder.yudao.module.sale.dal.mysql.competeinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.sale.dal.dataobject.competeinfo.CompeteInfoDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.sale.controller.admin.competeinfo.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

/**
 * 竞品信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CompeteInfoMapper extends BaseMapperX<CompeteInfoDO> {

    default PageResult<CompeteInfoDO> selectPage(CompeteInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CompeteInfoDO>()
                .eqIfPresent(CompeteInfoDO::getBrand, reqVO.getBrand())
                .likeIfPresent(CompeteInfoDO::getProdName, reqVO.getProdName())
                .eqIfPresent(CompeteInfoDO::getSpec, reqVO.getSpec())
                .eqIfPresent(CompeteInfoDO::getCompeteId, reqVO.getCompeteId())
                .eqIfPresent(CompeteInfoDO::getPrice, reqVO.getPrice())
                .betweenIfPresent(CompeteInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CompeteInfoDO::getId));
    }


    IPage<CompeteInfoRespVO> selectPage2(IPage<CompeteInfoRespVO> page,@Param("req") CompeteInfoPageReqVO req);

    CompeteInfoRespVO selectInfoById(@Param("id") Long id);
}