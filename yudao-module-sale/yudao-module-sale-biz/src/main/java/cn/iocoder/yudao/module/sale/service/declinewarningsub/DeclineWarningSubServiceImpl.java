package cn.iocoder.yudao.module.sale.service.declinewarningsub;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.sale.controller.admin.declinewarningsub.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarningsub.DeclineWarningSubDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.declinewarningsub.DeclineWarningSubMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;

/**
 * 销量下降预警子表 Service 实现类
 *
 * @author 播恩超级管理员
 */
@Service
@Validated
public class DeclineWarningSubServiceImpl implements DeclineWarningSubService {

    @Resource
    private DeclineWarningSubMapper declineWarningSubMapper;

    @Override
    public Long createDeclineWarningSub(DeclineWarningSubSaveReqVO createReqVO) {
        // 插入
        DeclineWarningSubDO declineWarningSub = BeanUtils.toBean(createReqVO, DeclineWarningSubDO.class);
        declineWarningSubMapper.insert(declineWarningSub);
        // 返回
        return declineWarningSub.getId();
    }

    @Override
    public void updateDeclineWarningSub(DeclineWarningSubSaveReqVO updateReqVO) {
        // 校验存在
        validateDeclineWarningSubExists(updateReqVO.getId());
        // 更新
        DeclineWarningSubDO updateObj = BeanUtils.toBean(updateReqVO, DeclineWarningSubDO.class);
        declineWarningSubMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeclineWarningSub(Long id) {
        // 校验存在
        validateDeclineWarningSubExists(id);
        // 删除
        declineWarningSubMapper.deleteById(id);
    }

    private void validateDeclineWarningSubExists(Long id) {
        if (declineWarningSubMapper.selectById(id) == null) {
            throw exception(DECLINE_WARNING_SUB_NOT_EXISTS);
        }
    }

    @Override
    public DeclineWarningSubDO getDeclineWarningSub(Long id) {
        return declineWarningSubMapper.selectById(id);
    }

    @Override
    public PageResult<DeclineWarningSubDO> getDeclineWarningSubPage(DeclineWarningSubPageReqVO pageReqVO) {
        return declineWarningSubMapper.selectPage(pageReqVO);
    }

    @Override
    public Map<Long, List<DeclineWarningSubDO>> getDeclineWarningSubMap(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }


        LambdaQueryWrapper<DeclineWarningSubDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(DeclineWarningSubDO::getParentId, ids);
        List<DeclineWarningSubDO> subDOList = declineWarningSubMapper.selectList(queryWrapper);

        return subDOList.stream().collect(Collectors.groupingBy(DeclineWarningSubDO::getParentId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(List<DeclineWarningSubBatchSaveReqVO> updateReqVO) {

        for (DeclineWarningSubBatchSaveReqVO declineWarningSubDO : updateReqVO) {
            // 校验存在
            validateDeclineWarningSubExists(declineWarningSubDO.getId());
            // 更新
            DeclineWarningSubDO updateObj = BeanUtils.toBean(declineWarningSubDO, DeclineWarningSubDO.class);
            declineWarningSubMapper.updateById(updateObj);
        }
    }
}