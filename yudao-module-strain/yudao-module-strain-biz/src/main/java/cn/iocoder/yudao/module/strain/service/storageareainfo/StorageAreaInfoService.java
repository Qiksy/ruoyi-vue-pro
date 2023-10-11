package cn.iocoder.yudao.module.strain.service.storageareainfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.storageareainfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.storageareainfo.StorageAreaInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 存放区域信息 Service 接口
 *
 * @author 芋道源码
 */
public interface StorageAreaInfoService {

    /**
     * 创建存放区域信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStorageAreaInfo(@Valid StorageAreaInfoCreateReqVO createReqVO);

    /**
     * 更新存放区域信息
     *
     * @param updateReqVO 更新信息
     */
    void updateStorageAreaInfo(@Valid StorageAreaInfoUpdateReqVO updateReqVO);

    /**
     * 删除存放区域信息
     *
     * @param id 编号
     */
    void deleteStorageAreaInfo(Long id);

    /**
     * 获得存放区域信息
     *
     * @param id 编号
     * @return 存放区域信息
     */
    StorageAreaInfoDO getStorageAreaInfo(Long id);

    /**
     * 获得存放区域信息列表
     *
     * @param ids 编号
     * @return 存放区域信息列表
     */
    List<StorageAreaInfoDO> getStorageAreaInfoList(Collection<Long> ids);

    /**
     * 获得存放区域信息分页
     *
     * @param pageReqVO 分页查询
     * @return 存放区域信息分页
     */
    PageResult<StorageAreaInfoDO> getStorageAreaInfoPage(StorageAreaInfoPageReqVO pageReqVO);

    /**
     * 获得存放区域信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 存放区域信息列表
     */
    List<StorageAreaInfoDO> getStorageAreaInfoList(StorageAreaInfoExportReqVO exportReqVO);

}
