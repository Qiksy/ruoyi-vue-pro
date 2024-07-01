package cn.iocoder.yudao.module.oa.service.signininfo;

import cn.iocoder.yudao.module.infra.api.file.FileApi;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.oa.controller.admin.signininfo.vo.*;
import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInInfoDO;
import cn.iocoder.yudao.module.oa.dal.dataobject.signinrecord.SignInRecordDO;
import cn.iocoder.yudao.module.oa.dal.dataobject.signintimerange.SignInTimeRangeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.oa.dal.mysql.signininfo.SignInInfoMapper;
import cn.iocoder.yudao.module.oa.dal.mysql.signinrecord.SignInRecordMapper;
import cn.iocoder.yudao.module.oa.dal.mysql.signintimerange.SignInTimeRangeMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.oa.enums.ErrorCodeConstants.SIGN_IN_INFO_NOT_EXISTS;

/**
 * 会议签到 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class SignInInfoServiceImpl implements SignInInfoService {

    @Resource
    private SignInInfoMapper signInInfoMapper;
    @Resource
    private SignInRecordMapper signInRecordMapper;
    @Resource
    private SignInTimeRangeMapper signInTimeRangeMapper;

    @Resource
    private FileApi fileApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSignInInfo(SignInInfoSaveReqVO createReqVO) {
        // 插入
        SignInInfoDO signInInfo = BeanUtils.toBean(createReqVO, SignInInfoDO.class);
        //coverPicId转为coverPicUrl
        String coverPicUrl = fileApi.getUrlById(createReqVO.getCoverPicId());
        signInInfo.setCoverPicUrl(coverPicUrl);
        signInInfoMapper.insert(signInInfo);
        // 插入子表
//        createSignInRecordList(signInInfo.getId(), createReqVO.getSignInRecords());
//        createSignInTimeRangeList(signInInfo.getId(), createReqVO.getSignInTimeRanges());
        // 返回
        return signInInfo.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSignInInfo(SignInInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateSignInInfoExists(updateReqVO.getId());
        // 更新
        SignInInfoDO updateObj = BeanUtils.toBean(updateReqVO, SignInInfoDO.class);
        signInInfoMapper.updateById(updateObj);

        // 更新子表
        updateSignInRecordList(updateReqVO.getId(), updateReqVO.getSignInRecords());
        updateSignInTimeRangeList(updateReqVO.getId(), updateReqVO.getSignInTimeRanges());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSignInInfo(Long id) {
        // 校验存在
        validateSignInInfoExists(id);
        // 删除
        signInInfoMapper.deleteById(id);

        // 删除子表
        deleteSignInRecordByParentId(id);
        deleteSignInTimeRangeByParentId(id);
    }

    private void validateSignInInfoExists(Long id) {
        if (signInInfoMapper.selectById(id) == null) {
            throw exception(SIGN_IN_INFO_NOT_EXISTS);
        }
    }

    @Override
    public SignInInfoDO getSignInInfo(Long id) {
        return signInInfoMapper.selectById(id);
    }

    @Override
    public PageResult<SignInInfoDO> getSignInInfoPage(SignInInfoPageReqVO pageReqVO) {
        return signInInfoMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（签到记录） ====================

    @Override
    public List<SignInRecordDO> getSignInRecordListByParentId(Long parentId) {
        return signInRecordMapper.selectListByParentId(parentId);
    }

    private void createSignInRecordList(Long parentId, List<SignInRecordDO> list) {
        if (list == null) {
            return;
        }
        list.forEach(o -> o.setParentId(parentId));
        signInRecordMapper.insertBatch(list);
    }

    private void updateSignInRecordList(Long parentId, List<SignInRecordDO> list) {
        deleteSignInRecordByParentId(parentId);
		list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null)); // 解决更新情况下：1）id 冲突；2）updateTime 不更新
        createSignInRecordList(parentId, list);
    }

    private void deleteSignInRecordByParentId(Long parentId) {
        signInRecordMapper.deleteByParentId(parentId);
    }

    // ==================== 子表（签到时间范围） ====================

    @Override
    public List<SignInTimeRangeDO> getSignInTimeRangeListByParentId(Long parentId) {
        return signInTimeRangeMapper.selectListByParentId(parentId);
    }

    private void createSignInTimeRangeList(Long parentId, List<SignInTimeRangeDO> list) {
        if (list == null) {
            return;
        }
        list.forEach(o -> o.setParentId(parentId));
        signInTimeRangeMapper.insertBatch(list);
    }

    private void updateSignInTimeRangeList(Long parentId, List<SignInTimeRangeDO> list) {
        deleteSignInTimeRangeByParentId(parentId);
		list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null)); // 解决更新情况下：1）id 冲突；2）updateTime 不更新
        createSignInTimeRangeList(parentId, list);
    }

    private void deleteSignInTimeRangeByParentId(Long parentId) {
        signInTimeRangeMapper.deleteByParentId(parentId);
    }

}