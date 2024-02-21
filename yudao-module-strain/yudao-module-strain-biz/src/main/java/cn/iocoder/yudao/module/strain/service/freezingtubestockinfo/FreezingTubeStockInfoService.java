package cn.iocoder.yudao.module.strain.service.freezingtubestockinfo;

import java.util.*;

import cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo.FreezingBoxInfoDetailVO;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo.FreezingTubeStockInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import jakarta.validation.constraints.NotNull;

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

    void scannerUpdateFreezingTubeStockInfo(@NotNull Long tubeStockId, @NotNull String perStockCode);

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

    /**
     * 根据盒子id，返回一个盒子的所有槽位，
     * 包括盒子信息
     * @param boxId 盒子id
     * @return 盒子的所有槽位
     */
    FreezingBoxInfoDetailVO getListByBoxId(Long boxId);

    /**
     * 将这个槽位设置为待出库
     * @param tubeStockId 冻藏管槽位id
     */
    void tempDelivery(Long tubeStockId);

    /**
     * 给冻藏管的融冻次数+1
     * 然后设置在库状态为在库
     * @param perStockCode 冻藏管的编号
     */
    void scannerReStock(String perStockCode);

    /**
     * 完全出库，也就是不会设置待回库的
     * @param tubeStockId 冻藏管槽位id
     */
    void delivery(Long tubeStockId);
}