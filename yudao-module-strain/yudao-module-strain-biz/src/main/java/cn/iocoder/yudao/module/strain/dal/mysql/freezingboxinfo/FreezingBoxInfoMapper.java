package cn.iocoder.yudao.module.strain.dal.mysql.freezingboxinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingboxinfo.FreezingBoxInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo.*;

/**
 * 冷冻盒信息 Mapper
 *
 * @author qiksy
 */
@Mapper
public interface FreezingBoxInfoMapper extends BaseMapperX<FreezingBoxInfoDO> {

    default PageResult<FreezingBoxInfoDO> selectPage(FreezingBoxInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FreezingBoxInfoDO>()
                .eqIfPresent(FreezingBoxInfoDO::getCode, reqVO.getCode())
                .likeIfPresent(FreezingBoxInfoDO::getName, reqVO.getName())
                .eqIfPresent(FreezingBoxInfoDO::getRemark, reqVO.getRemark())
                .orderByDesc(FreezingBoxInfoDO::getId));
    }

    default List<FreezingBoxInfoDO> selectList(FreezingBoxInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FreezingBoxInfoDO>()
                .eqIfPresent(FreezingBoxInfoDO::getCode, reqVO.getCode())
                .likeIfPresent(FreezingBoxInfoDO::getName, reqVO.getName())
                .eqIfPresent(FreezingBoxInfoDO::getRemark, reqVO.getRemark())
                .orderByDesc(FreezingBoxInfoDO::getId));
    }

}
