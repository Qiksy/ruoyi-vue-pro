package cn.iocoder.yudao.module.sale.service.declinewarning;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.sale.controller.admin.declinewarning.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarning.DeclineWarningDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 销量预警 Service 接口
 *
 * @author 播恩超级管理员
 */
public interface DeclineWarningService {

    /**
     * 创建销量预警
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDeclineWarning(@Valid DeclineWarningSaveReqVO createReqVO);

    /**
     * 更新销量预警
     *
     * @param updateReqVO 更新信息
     */
    void updateDeclineWarning(@Valid DeclineWarningSaveReqVO updateReqVO);

    /**
     * 删除销量预警
     *
     * @param id 编号
     */
    void deleteDeclineWarning(Long id);

    /**
     * 获得销量预警
     *
     * @param id 编号
     * @return 销量预警
     */
    DeclineWarningDO getDeclineWarning(Long id);

    /**
     * 获得销量预警分页
     *
     * @param pageReqVO 分页查询
     * @return 销量预警分页
     */
    PageResult<DeclineWarningDO> getDeclineWarningPage(DeclineWarningPageReqVO pageReqVO);

}