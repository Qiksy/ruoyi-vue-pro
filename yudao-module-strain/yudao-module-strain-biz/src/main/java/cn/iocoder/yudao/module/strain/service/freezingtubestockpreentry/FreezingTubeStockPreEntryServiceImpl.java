package cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockpreentry.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry.FreezingTubeStockPreEntryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 冷冻管库存预录入 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FreezingTubeStockPreEntryServiceImpl implements FreezingTubeStockPreEntryService {

    @Resource
    private FreezingTubeStockPreEntryMapper freezingTubeStockPreEntryMapper;

    @Override
    public Long createFreezingTubeStockPreEntry(FreezingTubeStockPreEntrySaveReqVO createReqVO) {
        // 插入
        FreezingTubeStockPreEntryDO freezingTubeStockPreEntry = BeanUtils.toBean(createReqVO, FreezingTubeStockPreEntryDO.class);
        freezingTubeStockPreEntryMapper.insert(freezingTubeStockPreEntry);
        // 返回
        return freezingTubeStockPreEntry.getId();
    }

    @Override
    public void updateFreezingTubeStockPreEntry(FreezingTubeStockPreEntrySaveReqVO updateReqVO) {
        // 校验存在
        validateFreezingTubeStockPreEntryExists(updateReqVO.getId());
        // 更新
        FreezingTubeStockPreEntryDO updateObj = BeanUtils.toBean(updateReqVO, FreezingTubeStockPreEntryDO.class);
        freezingTubeStockPreEntryMapper.updateById(updateObj);
    }

    @Override
    public void deleteFreezingTubeStockPreEntry(Long id) {
        // 校验存在
        validateFreezingTubeStockPreEntryExists(id);
        // 删除
        freezingTubeStockPreEntryMapper.deleteById(id);
    }

    private void validateFreezingTubeStockPreEntryExists(Long id) {
        if (freezingTubeStockPreEntryMapper.selectById(id) == null) {
            throw exception(FREEZING_TUBE_STOCK_PRE_ENTRY_NOT_EXISTS);
        }
    }

    @Override
    public FreezingTubeStockPreEntryDO getFreezingTubeStockPreEntry(Long id) {
        return freezingTubeStockPreEntryMapper.selectById(id);
    }

    @Override
    public PageResult<FreezingTubeStockPreEntryDO> getFreezingTubeStockPreEntryPage(FreezingTubeStockPreEntryPageReqVO pageReqVO) {
        return freezingTubeStockPreEntryMapper.selectPage(pageReqVO);
    }

    /**
     * 连表查询分页
     *
     * @param pageReqVO 分页查询
     * @return 冷冻管库存预录入分页
     */
    @Override
    public PageResult<FreezingTubeStockPreEntryRespVO> getFreezingTubeStockPreEntryPage2(FreezingTubeStockPreEntryPageReqVO pageReqVO) {
        return freezingTubeStockPreEntryMapper.selectPage2(pageReqVO);
    }
}