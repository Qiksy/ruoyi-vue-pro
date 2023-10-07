package cn.iocoder.yudao.module.strain.dal.mysql.culturemediumdatainfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.culturemediumdatainfo.CultureMediumDataInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo.*;

/**
 * 培养基数据信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CultureMediumDataInfoMapper extends BaseMapperX<CultureMediumDataInfoDO> {

    default PageResult<CultureMediumDataInfoDO> selectPage(CultureMediumDataInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CultureMediumDataInfoDO>()
                .eqIfPresent(CultureMediumDataInfoDO::getCode, reqVO.getCode())
                .likeIfPresent(CultureMediumDataInfoDO::getName, reqVO.getName())
                .orderByDesc(CultureMediumDataInfoDO::getId));
    }

    default List<CultureMediumDataInfoDO> selectList(CultureMediumDataInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CultureMediumDataInfoDO>()
                .eqIfPresent(CultureMediumDataInfoDO::getCode, reqVO.getCode())
                .likeIfPresent(CultureMediumDataInfoDO::getName, reqVO.getName())
                .orderByDesc(CultureMediumDataInfoDO::getId));
    }

}
