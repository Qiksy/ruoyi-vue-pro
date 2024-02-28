package cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry;

import java.util.*;

import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningReqVO;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningRespVO;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockpreentry.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 冷冻管库存预录入 Service 接口
 *
 * @author 芋道源码
 */
public interface FreezingTubeStockPreEntryService {

    /**
     * 创建冷冻管库存预录入
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    List<Long> createFreezingTubeStockPreEntry(@Valid FreezingTubeStockPreEntrySaveReqVO createReqVO);

    /**
     * 更新冷冻管库存预录入
     *
     * @param updateReqVO 更新信息
     */
    void updateFreezingTubeStockPreEntry(@Valid FreezingTubeStockPreEntrySaveReqVO updateReqVO);

    /**
     * 删除冷冻管库存预录入
     *
     * @param id 编号
     */
    void deleteFreezingTubeStockPreEntry(Long id);

    /**
     * 获得冷冻管库存预录入
     *
     * @param id 编号
     * @return 冷冻管库存预录入
     */
    FreezingTubeStockPreEntryDO getFreezingTubeStockPreEntry(Long id);

    /**
     * 获得冷冻管库存预录入分页
     *
     * @param pageReqVO 分页查询
     * @return 冷冻管库存预录入分页
     */
    PageResult<FreezingTubeStockPreEntryDO> getFreezingTubeStockPreEntryPage(FreezingTubeStockPreEntryPageReqVO pageReqVO);

    /**
     * 连表查询分页
     * @param pageReqVO 分页查询
     * @return 冷冻管库存预录入分页
     */
    PageResult<FreezingTubeStockPreEntryRespVO> getFreezingTubeStockPreEntryPage2(FreezingTubeStockPreEntryPageReqVO pageReqVO);

    /**
     * 连表查询分页，去掉正在处理的样品
     * @param pageReqVO 分页查询
     * @return 样品分页
     */
    PageResult<FreezingTubeStockPreEntryRespVO> getFreezingTubeStockPreEntryPage3(FreezingTubeStockPreEntryPageReqVO pageReqVO);


    /**
     * 传入槽位id，获取对应的槽位位置
     * @param stockIds 槽位id
     * @return map
     */
    Map<Long, String> getStockPositionStrMap(List<Long> stockIds);

    PageResult<ExpiredWarningRespVO> getExpiredWaringPage(ExpiredWarningReqVO queryVO);
}