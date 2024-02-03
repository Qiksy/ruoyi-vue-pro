package cn.iocoder.yudao.module.strain.service.storageareainfo;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdeviceinfo.FreezingDeviceInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdeviceinfo.FreezingDeviceInfoMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private FreezingDeviceInfoMapper freezingDeviceInfoMapper;

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
    public List<StorageAreaInfoRespVO> getAllStorageAreaInfoList() {
        List<StorageAreaInfoDO> doList = storageAreaInfoMapper.selectList();
        //转换成对应的vo
        List<StorageAreaInfoRespVO> resuleVOList = BeanUtils.toBean(doList, StorageAreaInfoRespVO.class);

        //分析区域内所拥有的设备数量、总容量
        List<FreezingDeviceInfoDO> deviceInfoDOS = freezingDeviceInfoMapper.selectList();
        Map<Long, List<FreezingDeviceInfoDO>> deviceMap = deviceInfoDOS.stream().collect(Collectors.groupingBy(FreezingDeviceInfoDO::getStorageAreaId));

        for (StorageAreaInfoRespVO vo : resuleVOList) {
            List<FreezingDeviceInfoDO> deviceList = Optional.ofNullable(deviceMap.get(vo.getId())).orElse(Collections.emptyList());
            //设置设备数量
            vo.setDeviceNum(
                    deviceList.size()
            );

            //根据设备，获取所有的库存状态
            List<Long> list = deviceList.stream().map(FreezingDeviceInfoDO::getId).toList();
            if (list.isEmpty()) {
                continue;
            }
            Map<String, Map<String,Object>> map = storageAreaInfoMapper.getSockStatus(list);

            long totalNum = 0; //总数量
            long inStockNum = 0; //在库数量
            long waitInStockNum = 0; //待回库数量
            long freeNum = 0; //空闲数量
            for (Map.Entry<String, Map<String, Object>> stringObjectEntry : map.entrySet()) {
                String key = stringObjectEntry.getKey();
                Map<String, Object> value = stringObjectEntry.getValue();
                if("0".equals(key)) {
                    freeNum += (long)value.get("num");
                }else if ("1".equals(key)) {
                    inStockNum += (long)value.get("num");
                }else if ("2".equals(key)) {
                    waitInStockNum += (long)value.get("num");
                }else {
                    freeNum += (long)value.get("num");
                }
                totalNum += (long)value.get("num");
            }

            vo.setTotalNum(totalNum);
            vo.setInStockNum(inStockNum);
            vo.setWaitInStockNum(waitInStockNum);
            vo.setFreeNum(freeNum);
        }





        return resuleVOList;
    }
}
