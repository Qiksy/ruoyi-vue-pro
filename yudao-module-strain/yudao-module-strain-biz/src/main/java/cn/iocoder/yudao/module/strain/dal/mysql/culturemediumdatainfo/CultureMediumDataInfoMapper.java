package cn.iocoder.yudao.module.strain.dal.mysql.culturemediumdatainfo;

import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * 获得培养基名称
     * @param mediumIds ids
     * @return 返回培养基名称
     */
    default Map<Long, String> selectMediumNameByIds(Set<Long> mediumIds){
        LambdaQueryWrapperX<CultureMediumDataInfoDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.select(CultureMediumDataInfoDO::getId, CultureMediumDataInfoDO::getName)
                .in(CultureMediumDataInfoDO::getId, mediumIds);

        List<CultureMediumDataInfoDO> cultureMediumDataInfoDOS = selectList(queryWrapperX);

        return cultureMediumDataInfoDOS.stream().collect(Collectors.toMap(CultureMediumDataInfoDO::getId, CultureMediumDataInfoDO::getName, (k1, k2) -> k1));
    }
}
