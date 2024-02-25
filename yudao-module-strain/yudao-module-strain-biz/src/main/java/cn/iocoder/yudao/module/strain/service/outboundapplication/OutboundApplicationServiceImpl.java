package cn.iocoder.yudao.module.strain.service.outboundapplication;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundapplication.OutboundApplicationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.strain.dal.mysql.outboundapplication.OutboundApplicationMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 出库申请 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OutboundApplicationServiceImpl implements OutboundApplicationService {

    @Resource
    private OutboundApplicationMapper outboundApplicationMapper;

    @Override
    public Long createOutboundApplication(OutboundApplicationSaveReqVO createReqVO) {
        // 插入
        OutboundApplicationDO outboundApplication = BeanUtils.toBean(createReqVO, OutboundApplicationDO.class);
        outboundApplicationMapper.insert(outboundApplication);
        // 返回
        return outboundApplication.getId();
    }

    @Override
    public void updateOutboundApplication(OutboundApplicationSaveReqVO updateReqVO) {
        // 校验存在
        validateOutboundApplicationExists(updateReqVO.getId());
        // 更新
        OutboundApplicationDO updateObj = BeanUtils.toBean(updateReqVO, OutboundApplicationDO.class);
        outboundApplicationMapper.updateById(updateObj);
    }

    @Override
    public void deleteOutboundApplication(Long id) {
        // 校验存在
        validateOutboundApplicationExists(id);
        // 删除
        outboundApplicationMapper.deleteById(id);
    }

    private void validateOutboundApplicationExists(Long id) {
        if (outboundApplicationMapper.selectById(id) == null) {
            throw exception(OUTBOUND_APPLICATION_NOT_EXISTS);
        }
    }

    @Override
    public OutboundApplicationDO getOutboundApplication(Long id) {
        return outboundApplicationMapper.selectById(id);
    }

    @Override
    public PageResult<OutboundApplicationDO> getOutboundApplicationPage(OutboundApplicationPageReqVO pageReqVO) {
        return outboundApplicationMapper.selectPage(pageReqVO);
    }

}