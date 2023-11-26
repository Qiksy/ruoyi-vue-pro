package cn.iocoder.yudao.module.sale.service.competeinfosub;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.sale.controller.admin.competeinfosub.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.competeinfosub.CompeteInfoSubDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.competeinfosub.CompeteInfoSubMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;

/**
 * 竞品信息子 Service 实现类
 *
 * @author 播恩超级管理员
 */
@Service
@Validated
public class CompeteInfoSubServiceImpl implements CompeteInfoSubService {

    @Resource
    private CompeteInfoSubMapper competeInfoSubMapper;

    @Override
    public Long createCompeteInfoSub(CompeteInfoSubSaveReqVO createReqVO) {
        // 插入
        CompeteInfoSubDO competeInfoSub = BeanUtils.toBean(createReqVO, CompeteInfoSubDO.class);
        competeInfoSubMapper.insert(competeInfoSub);
        // 返回
        return competeInfoSub.getId();
    }

    @Override
    public void updateCompeteInfoSub(CompeteInfoSubSaveReqVO updateReqVO) {
        // 校验存在
        validateCompeteInfoSubExists(updateReqVO.getId());
        // 更新
        CompeteInfoSubDO updateObj = BeanUtils.toBean(updateReqVO, CompeteInfoSubDO.class);
        competeInfoSubMapper.updateById(updateObj);
    }

    @Override
    public void deleteCompeteInfoSub(Long id) {
        // 校验存在
        validateCompeteInfoSubExists(id);
        // 删除
        competeInfoSubMapper.deleteById(id);
    }

    private void validateCompeteInfoSubExists(Long id) {
        if (competeInfoSubMapper.selectById(id) == null) {
            throw exception(COMPETE_INFO_SUB_NOT_EXISTS);
        }
    }

    @Override
    public CompeteInfoSubDO getCompeteInfoSub(Long id) {
        return competeInfoSubMapper.selectById(id);
    }

    @Override
    public PageResult<CompeteInfoSubDO> getCompeteInfoSubPage(CompeteInfoSubPageReqVO pageReqVO) {
        return competeInfoSubMapper.selectPage(pageReqVO);
    }

}