package cn.iocoder.yudao.module.strain.service.freezingboxinfo;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingboxinfo.FreezingBoxInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.strain.convert.freezingboxinfo.FreezingBoxInfoConvert;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingboxinfo.FreezingBoxInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 冷冻盒信息 Service 实现类
 *
 * @author qiksy
 */
@Service
@Validated
public class FreezingBoxInfoServiceImpl implements FreezingBoxInfoService {

    @Resource
    private FreezingBoxInfoMapper freezingBoxInfoMapper;

    @Override
    public Long createFreezingBoxInfo(FreezingBoxInfoCreateReqVO createReqVO) {
        // 插入
        FreezingBoxInfoDO freezingBoxInfo = FreezingBoxInfoConvert.INSTANCE.convert(createReqVO);
        freezingBoxInfoMapper.insert(freezingBoxInfo);
        // 返回
        return freezingBoxInfo.getId();
    }

    @Override
    public void updateFreezingBoxInfo(FreezingBoxInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateFreezingBoxInfoExists(updateReqVO.getId());
        // 更新
        FreezingBoxInfoDO updateObj = FreezingBoxInfoConvert.INSTANCE.convert(updateReqVO);
        freezingBoxInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteFreezingBoxInfo(Long id) {
        // 校验存在
        validateFreezingBoxInfoExists(id);
        // 删除
        freezingBoxInfoMapper.deleteById(id);
    }

    private void validateFreezingBoxInfoExists(Long id) {
        if (freezingBoxInfoMapper.selectById(id) == null) {
            throw exception(FREEZING_BOX_INFO_NOT_EXISTS);
        }
    }

    @Override
    public FreezingBoxInfoDO getFreezingBoxInfo(Long id) {
        return freezingBoxInfoMapper.selectById(id);
    }

    @Override
    public List<FreezingBoxInfoDO> getFreezingBoxInfoList(Collection<Long> ids) {
        return freezingBoxInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FreezingBoxInfoDO> getFreezingBoxInfoPage(FreezingBoxInfoPageReqVO pageReqVO) {
        return freezingBoxInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FreezingBoxInfoDO> getFreezingBoxInfoList(FreezingBoxInfoExportReqVO exportReqVO) {
        return freezingBoxInfoMapper.selectList(exportReqVO);
    }

}
