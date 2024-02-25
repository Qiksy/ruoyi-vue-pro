package cn.iocoder.yudao.module.strain.service.outboundapplication;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundapplication.OutboundApplicationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 出库申请 Service 接口
 *
 * @author 芋道源码
 */
public interface OutboundApplicationService {

    /**
     * 创建出库申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOutboundApplication(@Valid OutboundApplicationSaveReqVO createReqVO);

    /**
     * 更新出库申请
     *
     * @param updateReqVO 更新信息
     */
    void updateOutboundApplication(@Valid OutboundApplicationSaveReqVO updateReqVO);

    /**
     * 删除出库申请
     *
     * @param id 编号
     */
    void deleteOutboundApplication(Long id);

    /**
     * 获得出库申请
     *
     * @param id 编号
     * @return 出库申请
     */
    OutboundApplicationDO getOutboundApplication(Long id);

    /**
     * 获得出库申请分页
     *
     * @param pageReqVO 分页查询
     * @return 出库申请分页
     */
    PageResult<OutboundApplicationDO> getOutboundApplicationPage(OutboundApplicationPageReqVO pageReqVO);

}