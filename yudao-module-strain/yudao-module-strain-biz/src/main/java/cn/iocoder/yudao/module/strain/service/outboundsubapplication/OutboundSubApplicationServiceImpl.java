package cn.iocoder.yudao.module.strain.service.outboundsubapplication;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.outboundsubapplication.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundsubapplication.OutboundSubApplicationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.strain.dal.mysql.outboundsubapplication.OutboundSubApplicationMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 出库申请子 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OutboundSubApplicationServiceImpl implements OutboundSubApplicationService {

    @Resource
    private OutboundSubApplicationMapper outboundSubApplicationMapper;

    @Override
    public Long createOutboundSubApplication(OutboundSubApplicationSaveReqVO createReqVO) {
        // 插入
        OutboundSubApplicationDO outboundSubApplication = BeanUtils.toBean(createReqVO, OutboundSubApplicationDO.class);
        outboundSubApplicationMapper.insert(outboundSubApplication);
        // 返回
        return outboundSubApplication.getId();
    }

    @Override
    public void updateOutboundSubApplication(OutboundSubApplicationSaveReqVO updateReqVO) {
        // 校验存在
        validateOutboundSubApplicationExists(updateReqVO.getId());
        // 更新
        OutboundSubApplicationDO updateObj = BeanUtils.toBean(updateReqVO, OutboundSubApplicationDO.class);
        outboundSubApplicationMapper.updateById(updateObj);
    }

    @Override
    public void deleteOutboundSubApplication(Long id) {
        // 校验存在
        validateOutboundSubApplicationExists(id);
        // 删除
        outboundSubApplicationMapper.deleteById(id);
    }

    private void validateOutboundSubApplicationExists(Long id) {
        if (outboundSubApplicationMapper.selectById(id) == null) {
            throw exception(OUTBOUND_SUB_APPLICATION_NOT_EXISTS);
        }
    }

    @Override
    public OutboundSubApplicationDO getOutboundSubApplication(Long id) {
        return outboundSubApplicationMapper.selectById(id);
    }

    @Override
    public PageResult<OutboundSubApplicationDO> getOutboundSubApplicationPage(OutboundSubApplicationPageReqVO pageReqVO) {
        return outboundSubApplicationMapper.selectPage(pageReqVO);
    }

}