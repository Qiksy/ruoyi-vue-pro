package cn.iocoder.yudao.module.strain.service.outboundsubapplication;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.outboundsubapplication.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundsubapplication.OutboundSubApplicationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 出库申请子 Service 接口
 *
 * @author 芋道源码
 */
public interface OutboundSubApplicationService {

    /**
     * 创建出库申请子
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOutboundSubApplication(@Valid OutboundSubApplicationSaveReqVO createReqVO);

    /**
     * 更新出库申请子
     *
     * @param updateReqVO 更新信息
     */
    void updateOutboundSubApplication(@Valid OutboundSubApplicationSaveReqVO updateReqVO);

    /**
     * 删除出库申请子
     *
     * @param id 编号
     */
    void deleteOutboundSubApplication(Long id);

    /**
     * 获得出库申请子
     *
     * @param id 编号
     * @return 出库申请子
     */
    OutboundSubApplicationDO getOutboundSubApplication(Long id);

    /**
     * 获得出库申请子分页
     *
     * @param pageReqVO 分页查询
     * @return 出库申请子分页
     */
    PageResult<OutboundSubApplicationDO> getOutboundSubApplicationPage(OutboundSubApplicationPageReqVO pageReqVO);

}