package cn.iocoder.yudao.module.strain.dal.mysql.storageareainfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.storageareainfo.StorageAreaInfoDO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.storageareainfo.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 存放区域信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface StorageAreaInfoMapper extends BaseMapperX<StorageAreaInfoDO> {

    default PageResult<StorageAreaInfoDO> selectPage(StorageAreaInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StorageAreaInfoDO>()
                .eqIfPresent(StorageAreaInfoDO::getCode, reqVO.getCode())
                .likeIfPresent(StorageAreaInfoDO::getName, reqVO.getName())
                .eqIfPresent(StorageAreaInfoDO::getLocationInfo, reqVO.getLocationInfo())
                .betweenIfPresent(StorageAreaInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(StorageAreaInfoDO::getRemark, reqVO.getRemark())
                .orderByDesc(StorageAreaInfoDO::getId));
    }

    default List<StorageAreaInfoDO> selectList(StorageAreaInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<StorageAreaInfoDO>()
                .eqIfPresent(StorageAreaInfoDO::getCode, reqVO.getCode())
                .likeIfPresent(StorageAreaInfoDO::getName, reqVO.getName())
                .eqIfPresent(StorageAreaInfoDO::getLocationInfo, reqVO.getLocationInfo())
                .betweenIfPresent(StorageAreaInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(StorageAreaInfoDO::getRemark, reqVO.getRemark())
                .orderByDesc(StorageAreaInfoDO::getId));
    }

    /**
     * 获取整个库存的信息
     * @param list
     * @return
     */
    @MapKey("status")
    Map<String, Map<String,Object>> getSockStatus(@Param("list") List<Long> list);
}
