package cn.iocoder.yudao.module.strain.service.storageareainfo;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.storageareainfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.storageareainfo.StorageAreaInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.strain.convert.storageareainfo.StorageAreaInfoConvert;
import cn.iocoder.yudao.module.strain.dal.mysql.storageareainfo.StorageAreaInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 存放区域信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class StorageAreaInfoServiceImpl implements StorageAreaInfoService {

    @Resource
    private StorageAreaInfoMapper storageAreaInfoMapper;

    @Override
    public Long createStorageAreaInfo(StorageAreaInfoCreateReqVO createReqVO) {
        // 插入
        StorageAreaInfoDO storageAreaInfo = StorageAreaInfoConvert.INSTANCE.convert(createReqVO);
        storageAreaInfoMapper.insert(storageAreaInfo);
        // 返回
        return storageAreaInfo.getId();
    }

    @Override
    public void updateStorageAreaInfo(StorageAreaInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateStorageAreaInfoExists(updateReqVO.getId());
        // 更新
        StorageAreaInfoDO updateObj = StorageAreaInfoConvert.INSTANCE.convert(updateReqVO);
        storageAreaInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteStorageAreaInfo(Long id) {
        // 校验存在
        validateStorageAreaInfoExists(id);
        // 删除
        storageAreaInfoMapper.deleteById(id);
    }

    private void validateStorageAreaInfoExists(Long id) {
        if (storageAreaInfoMapper.selectById(id) == null) {
            throw exception(STORAGE_AREA_INFO_NOT_EXISTS);
        }
    }

    @Override
    public StorageAreaInfoDO getStorageAreaInfo(Long id) {
        return storageAreaInfoMapper.selectById(id);
    }

    @Override
    public List<StorageAreaInfoDO> getStorageAreaInfoList(Collection<Long> ids) {
        return storageAreaInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<StorageAreaInfoDO> getStorageAreaInfoPage(StorageAreaInfoPageReqVO pageReqVO) {
        return storageAreaInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<StorageAreaInfoDO> getStorageAreaInfoList(StorageAreaInfoExportReqVO exportReqVO) {
        return storageAreaInfoMapper.selectList(exportReqVO);
    }


    /**
     * 获取全部的冷冻区域信息
     *
     * @return 冷冻区域信息
     */
    @Override
    public List<StorageAreaInfoDO> getAllStorageAreaInfoList() {

        return storageAreaInfoMapper.selectList();
    }
}
