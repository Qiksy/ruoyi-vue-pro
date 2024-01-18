package cn.iocoder.yudao.module.strain.service.freezingtubestockinfo;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo.FreezingTubeStockInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockinfo.FreezingTubeStockInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 冷冻盒槽位 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FreezingTubeStockInfoServiceImpl implements FreezingTubeStockInfoService {

    @Resource
    private FreezingTubeStockInfoMapper freezingTubeStockInfoMapper;

    @Override
    public Long createFreezingTubeStockInfo(FreezingTubeStockInfoSaveReqVO createReqVO) {
        // 插入
        FreezingTubeStockInfoDO freezingTubeStockInfo = BeanUtils.toBean(createReqVO, FreezingTubeStockInfoDO.class);
        freezingTubeStockInfoMapper.insert(freezingTubeStockInfo);
        // 返回
        return freezingTubeStockInfo.getId();
    }

    @Override
    public void updateFreezingTubeStockInfo(FreezingTubeStockInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateFreezingTubeStockInfoExists(updateReqVO.getId());
        // 更新
        FreezingTubeStockInfoDO updateObj = BeanUtils.toBean(updateReqVO, FreezingTubeStockInfoDO.class);
        freezingTubeStockInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteFreezingTubeStockInfo(Long id) {
        // 校验存在
        validateFreezingTubeStockInfoExists(id);
        // 删除
        freezingTubeStockInfoMapper.deleteById(id);
    }

    private void validateFreezingTubeStockInfoExists(Long id) {
        if (freezingTubeStockInfoMapper.selectById(id) == null) {
            throw exception(FREEZING_TUBE_STOCK_INFO_NOT_EXISTS);
        }
    }

    @Override
    public FreezingTubeStockInfoDO getFreezingTubeStockInfo(Long id) {
        return freezingTubeStockInfoMapper.selectById(id);
    }

    @Override
    public PageResult<FreezingTubeStockInfoDO> getFreezingTubeStockInfoPage(FreezingTubeStockInfoPageReqVO pageReqVO) {
        return freezingTubeStockInfoMapper.selectPage(pageReqVO);
    }

}