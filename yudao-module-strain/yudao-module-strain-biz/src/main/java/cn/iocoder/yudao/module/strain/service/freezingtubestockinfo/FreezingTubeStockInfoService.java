package cn.iocoder.yudao.module.strain.service.freezingtubestockinfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo.FreezingTubeStockInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 冷冻盒槽位 Service 接口
 *
 * @author 芋道源码
 */
public interface FreezingTubeStockInfoService {

    /**
     * 创建冷冻盒槽位
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFreezingTubeStockInfo(@Valid FreezingTubeStockInfoSaveReqVO createReqVO);

    /**
     * 更新冷冻盒槽位
     *
     * @param updateReqVO 更新信息
     */
    void updateFreezingTubeStockInfo(@Valid FreezingTubeStockInfoSaveReqVO updateReqVO);

    /**
     * 删除冷冻盒槽位
     *
     * @param id 编号
     */
    void deleteFreezingTubeStockInfo(Long id);

    /**
     * 获得冷冻盒槽位
     *
     * @param id 编号
     * @return 冷冻盒槽位
     */
    FreezingTubeStockInfoDO getFreezingTubeStockInfo(Long id);

    /**
     * 获得冷冻盒槽位分页
     *
     * @param pageReqVO 分页查询
     * @return 冷冻盒槽位分页
     */
    PageResult<FreezingTubeStockInfoDO> getFreezingTubeStockInfoPage(FreezingTubeStockInfoPageReqVO pageReqVO);

}