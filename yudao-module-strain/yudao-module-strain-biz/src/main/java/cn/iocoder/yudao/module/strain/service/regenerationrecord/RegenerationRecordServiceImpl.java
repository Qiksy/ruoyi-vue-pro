package cn.iocoder.yudao.module.strain.service.regenerationrecord;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.regenerationrecord.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.regenerationrecord.RegenerationRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.strain.dal.mysql.regenerationrecord.RegenerationRecordMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 样品复壮传代记录 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class RegenerationRecordServiceImpl implements RegenerationRecordService {

    @Resource
    private RegenerationRecordMapper regenerationRecordMapper;

    @Override
    public Long createRegenerationRecord(RegenerationRecordSaveReqVO createReqVO) {
        // 插入
        RegenerationRecordDO regenerationRecord = BeanUtils.toBean(createReqVO, RegenerationRecordDO.class);
        regenerationRecordMapper.insert(regenerationRecord);
        // 返回
        return regenerationRecord.getId();
    }

    @Override
    public void updateRegenerationRecord(RegenerationRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateRegenerationRecordExists(updateReqVO.getId());
        // 更新
        RegenerationRecordDO updateObj = BeanUtils.toBean(updateReqVO, RegenerationRecordDO.class);
        regenerationRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteRegenerationRecord(Long id) {
        // 校验存在
        validateRegenerationRecordExists(id);
        // 删除
        regenerationRecordMapper.deleteById(id);
    }

    private void validateRegenerationRecordExists(Long id) {
        if (regenerationRecordMapper.selectById(id) == null) {
            throw exception(REGENERATION_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public RegenerationRecordDO getRegenerationRecord(Long id) {
        return regenerationRecordMapper.selectById(id);
    }

    @Override
    public PageResult<RegenerationRecordDO> getRegenerationRecordPage(RegenerationRecordPageReqVO pageReqVO) {
        return regenerationRecordMapper.selectPage(pageReqVO);
    }

}