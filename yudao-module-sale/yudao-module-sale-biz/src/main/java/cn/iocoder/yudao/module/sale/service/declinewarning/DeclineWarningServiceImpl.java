package cn.iocoder.yudao.module.sale.service.declinewarning;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.sale.controller.admin.declinewarning.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarning.DeclineWarningDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.declinewarning.DeclineWarningMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;

/**
 * 销量预警 Service 实现类
 *
 * @author 播恩超级管理员
 */
@Service
@Validated
public class DeclineWarningServiceImpl implements DeclineWarningService {

    @Resource
    private DeclineWarningMapper declineWarningMapper;

    @Override
    public Long createDeclineWarning(DeclineWarningSaveReqVO createReqVO) {
        // 插入
        DeclineWarningDO declineWarning = BeanUtils.toBean(createReqVO, DeclineWarningDO.class);
        declineWarningMapper.insert(declineWarning);
        // 返回
        return declineWarning.getId();
    }

    @Override
    public void updateDeclineWarning(DeclineWarningSaveReqVO updateReqVO) {
        // 校验存在
        validateDeclineWarningExists(updateReqVO.getId());
        // 更新
        DeclineWarningDO updateObj = BeanUtils.toBean(updateReqVO, DeclineWarningDO.class);
        declineWarningMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeclineWarning(Long id) {
        // 校验存在
        validateDeclineWarningExists(id);
        // 删除
        declineWarningMapper.deleteById(id);
    }

    private void validateDeclineWarningExists(Long id) {
        if (declineWarningMapper.selectById(id) == null) {
            throw exception(DECLINE_WARNING_NOT_EXISTS);
        }
    }

    @Override
    public DeclineWarningDO getDeclineWarning(Long id) {
        return declineWarningMapper.selectById(id);
    }

    @Override
    public PageResult<DeclineWarningDO> getDeclineWarningPage(DeclineWarningPageReqVO pageReqVO) {
        return declineWarningMapper.selectPage(pageReqVO);
    }

}