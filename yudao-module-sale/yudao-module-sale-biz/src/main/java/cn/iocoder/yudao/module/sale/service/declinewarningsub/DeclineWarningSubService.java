package cn.iocoder.yudao.module.sale.service.declinewarningsub;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.sale.controller.admin.declinewarningsub.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarningsub.DeclineWarningSubDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 销量下降预警子表 Service 接口
 *
 * @author 播恩超级管理员
 */
public interface DeclineWarningSubService {

    /**
     * 创建销量下降预警子表
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDeclineWarningSub(@Valid DeclineWarningSubSaveReqVO createReqVO);

    /**
     * 更新销量下降预警子表
     *
     * @param updateReqVO 更新信息
     */
    void updateDeclineWarningSub(@Valid DeclineWarningSubSaveReqVO updateReqVO);

    /**
     * 删除销量下降预警子表
     *
     * @param id 编号
     */
    void deleteDeclineWarningSub(Long id);

    /**
     * 获得销量下降预警子表
     *
     * @param id 编号
     * @return 销量下降预警子表
     */
    DeclineWarningSubDO getDeclineWarningSub(Long id);

    /**
     * 获得销量下降预警子表分页
     *
     * @param pageReqVO 分页查询
     * @return 销量下降预警子表分页
     */
    PageResult<DeclineWarningSubDO> getDeclineWarningSubPage(DeclineWarningSubPageReqVO pageReqVO);

    Map<Long, List<DeclineWarningSubDO>> getDeclineWarningSubMap(Collection<Long> ids);

    void updateBatch(List<DeclineWarningSubBatchSaveReqVO> updateReqVO);
}