package cn.iocoder.yudao.module.strain.service.freezingboxinfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingboxinfo.FreezingBoxInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 冷冻盒信息 Service 接口
 *
 * @author qiksy
 */
public interface FreezingBoxInfoService {

    /**
     * 创建冷冻盒信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFreezingBoxInfo(@Valid FreezingBoxInfoCreateReqVO createReqVO);

    /**
     * 更新冷冻盒信息
     *
     * @param updateReqVO 更新信息
     */
    void updateFreezingBoxInfo(@Valid FreezingBoxInfoUpdateReqVO updateReqVO);

    /**
     * 删除冷冻盒信息
     *
     * @param id 编号
     */
    void deleteFreezingBoxInfo(Long id);

    /**
     * 获得冷冻盒信息
     *
     * @param id 编号
     * @return 冷冻盒信息
     */
    FreezingBoxInfoDO getFreezingBoxInfo(Long id);

    /**
     * 获得冷冻盒信息列表
     *
     * @param ids 编号
     * @return 冷冻盒信息列表
     */
    List<FreezingBoxInfoDO> getFreezingBoxInfoList(Collection<Long> ids);

    /**
     * 获得冷冻盒信息分页
     *
     * @param pageReqVO 分页查询
     * @return 冷冻盒信息分页
     */
    PageResult<FreezingBoxInfoDO> getFreezingBoxInfoPage(FreezingBoxInfoPageReqVO pageReqVO);

    /**
     * 获得冷冻盒信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 冷冻盒信息列表
     */
    List<FreezingBoxInfoDO> getFreezingBoxInfoList(FreezingBoxInfoExportReqVO exportReqVO);

}
