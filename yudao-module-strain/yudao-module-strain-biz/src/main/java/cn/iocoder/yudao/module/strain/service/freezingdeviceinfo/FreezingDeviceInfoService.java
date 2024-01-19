package cn.iocoder.yudao.module.strain.service.freezingdeviceinfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdeviceinfo.FreezingDeviceInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 冷冻设备信息 Service 接口
 *
 * @author 芋道源码
 */
public interface FreezingDeviceInfoService {

    /**
     * 创建冷冻设备信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFreezingDeviceInfo(@Valid FreezingDeviceInfoCreateReqVO createReqVO);

    /**
     * 更新冷冻设备信息
     *
     * @param updateReqVO 更新信息
     */
    void updateFreezingDeviceInfo(@Valid FreezingDeviceInfoUpdateReqVO updateReqVO);

    /**
     * 删除冷冻设备信息
     *
     * @param id 编号
     */
    void deleteFreezingDeviceInfo(Long id);

    /**
     * 获得冷冻设备信息
     *
     * @param id 编号
     * @return 冷冻设备信息
     */
    FreezingDeviceInfoRespVO getFreezingDeviceInfo(Long id);

    /**
     * 获得冷冻设备信息列表
     *
     * @param ids 编号
     * @return 冷冻设备信息列表
     */
    List<FreezingDeviceInfoDO> getFreezingDeviceInfoList(Collection<Long> ids);

    /**
     * 获得冷冻设备信息分页
     *
     * @param pageReqVO 分页查询
     * @return 冷冻设备信息分页
     */
    PageResult<FreezingDeviceInfoDO> getFreezingDeviceInfoPage(FreezingDeviceInfoPageReqVO pageReqVO);

    /**
     * 获得冷冻设备信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 冷冻设备信息列表
     */
    List<FreezingDeviceInfoDO> getFreezingDeviceInfoList(FreezingDeviceInfoExportReqVO exportReqVO);

    /**
     * 查询某个冷冻设备的层级信息，包括它自身，成为一个树节点
     * @param id
     * @return
     */
    FreezingDeviceInfoLevelRespVO getFreezingDeviceInfoLevel(Long id);

    /**
     * @param code 存放区域编码1
     * @return 返回该区域所有的冷冻设备包括子层级列表，用于前端展示
     */
    List<FreezingDeviceInfoLevelRespVO>  getFreezingDeviceInfoListByAreaCode(String code);
}
