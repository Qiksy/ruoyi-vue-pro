package cn.iocoder.yudao.module.strain.dal.mysql.microbebasicinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.microbebasicinfo.MicrobeBasicInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.microbebasicinfo.vo.*;

/**
 * 菌种信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MicrobeBasicInfoMapper extends BaseMapperX<MicrobeBasicInfoDO> {

    default PageResult<MicrobeBasicInfoDO> selectPage(MicrobeBasicInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MicrobeBasicInfoDO>()
                .eqIfPresent(MicrobeBasicInfoDO::getOriginalCode, reqVO.getOriginalCode())
                .eqIfPresent(MicrobeBasicInfoDO::getCode, reqVO.getCode())
                .likeIfPresent(MicrobeBasicInfoDO::getChineseName, reqVO.getChineseName())
                .likeIfPresent(MicrobeBasicInfoDO::getLatinName, reqVO.getLatinName())
                .eqIfPresent(MicrobeBasicInfoDO::getGeneAccessionNumber, reqVO.getGeneAccessionNumber())
                .eqIfPresent(MicrobeBasicInfoDO::getIsPathogenic, reqVO.getIsPathogenic())
                .eqIfPresent(MicrobeBasicInfoDO::getIsVisiable, reqVO.getIsVisiable())
                .eqIfPresent(MicrobeBasicInfoDO::getMicrobeType, reqVO.getMicrobeType())
                .orderByDesc(MicrobeBasicInfoDO::getId));
    }

}