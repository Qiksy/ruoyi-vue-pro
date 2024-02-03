package cn.iocoder.yudao.module.strain.service.freezingtubeinfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubeinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubeinfo.FreezingTubeInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 冷冻管基本信息 Service 接口
 *
 * @author 芋道源码
 */
public interface FreezingTubeInfoService {

    /**
     * 创建冷冻管基本信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFreezingTubeInfo(@Valid FreezingTubeInfoCreateReqVO createReqVO);

    /**
     * 更新冷冻管基本信息
     *
     * @param updateReqVO 更新信息
     */
    void updateFreezingTubeInfo(@Valid FreezingTubeInfoUpdateReqVO updateReqVO);

    /**
     * 删除冷冻管基本信息
     *
     * @param id 编号
     */
    void deleteFreezingTubeInfo(Long id);

    /**
     * 获得冷冻管基本信息
     *
     * @param id 编号
     * @return 冷冻管基本信息
     */
    FreezingTubeInfoDO getFreezingTubeInfo(Long id);

    /**
     * 获得冷冻管基本信息列表
     *
     * @param ids 编号
     * @return 冷冻管基本信息列表
     */
    List<FreezingTubeInfoDO> getFreezingTubeInfoList(Collection<Long> ids);

    /**
     * 获得冷冻管基本信息分页
     *
     * @param pageReqVO 分页查询
     * @return 冷冻管基本信息分页
     */
    PageResult<FreezingTubeInfoDO> getFreezingTubeInfoPage(FreezingTubeInfoPageReqVO pageReqVO);

    /**
     * 获得冷冻管基本信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 冷冻管基本信息列表
     */
    List<FreezingTubeInfoDO> getFreezingTubeInfoList(FreezingTubeInfoExportReqVO exportReqVO);

    /**
     * 只获取冷冻管基本信息的简单信息，包括编号、名称
     * @return 获得冷冻管基本信息列表
     */
    List<FreezingTubeInfoRespVO> getFreezingTubeInfoSimpleList();
}
