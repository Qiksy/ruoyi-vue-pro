package cn.iocoder.yudao.module.oa.service.signininfo;

import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.infra.api.file.FileApi;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import cn.iocoder.yudao.module.system.service.permission.PermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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
    private PermissionService permissionService;

    @Resource
    private AdminUserApi adminUserApi;

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
    public SignInInfoRespVO getSignInInfo(Long id) {
        SignInInfoDO signInInfoDO = signInInfoMapper.selectById(id);

        SignInInfoRespVO respVO = BeanUtils.toBean(signInInfoDO, SignInInfoRespVO.class);
        fillCreator(Collections.singletonList(respVO));

        fillMeetingStatus(Collections.singletonList(respVO));

        return respVO;
    }

    @Override
    public PageResult<SignInInfoRespVO> getSignInInfoPage(SignInInfoPageReqVO pageReqVO) {

        PageResult<SignInInfoDO> signInInfoDOPageResult;
        //如果是管理员角色，直接返回全部
        if (permissionService.hasAnyRoles(SecurityFrameworkUtils.getLoginUserId(), "super_admin")) {
            signInInfoDOPageResult = signInInfoMapper.selectPage(pageReqVO);
        } else {
            //只能查询自己创建或者签到的会议

            // 1. 查询自己签到的
            List<SignInRecordDO> signInRecordDOS = signInRecordMapper.selectList(new LambdaQueryWrapper<SignInRecordDO>().select(SignInRecordDO::getParentId).eq(SignInRecordDO::getUserId, SecurityFrameworkUtils.getLoginUserId()));
            Set<Long> meetingIds = signInRecordDOS.stream().map(SignInRecordDO::getParentId).collect(Collectors.toSet());

            // 2. 查询自己创建的
            List<SignInInfoDO> signInInfoDOS = signInInfoMapper.selectList(new LambdaQueryWrapper<SignInInfoDO>().select(SignInInfoDO::getId).eq(SignInInfoDO::getCreator, SecurityFrameworkUtils.getLoginUserId()));
            signInInfoDOS.stream().map(SignInInfoDO::getId).forEach(meetingIds::add);


            signInInfoDOPageResult =  signInInfoMapper.selectSelfPage(pageReqVO, meetingIds);
        }

        // 处理会议状态
        PageResult<SignInInfoRespVO> bean = BeanUtils.toBean(signInInfoDOPageResult, SignInInfoRespVO.class);
        fillMeetingStatus(bean.getList());

        // 填充创建人消息
        fillCreator(bean.getList());

        return bean;
    }

    /**
     * 填充创建人的名称
     * @param list
     */
    private void fillCreator(List<SignInInfoRespVO> list) {

        Set<Long> userIds = list.stream().map(SignInInfoRespVO::getCreator).map(Long::valueOf).collect(Collectors.toSet());

        List<AdminUserRespDTO> userList = adminUserApi.getUserList(userIds);
        Map<Long, String> userMap = userList.stream().collect(Collectors.toMap(AdminUserRespDTO::getId, AdminUserRespDTO::getNickname));

        for (SignInInfoRespVO vo : list) {
            vo.setCreatorName(userMap.get(Long.valueOf(vo.getCreator())));
        }
    }

    /**
     * 处理会议记录，如果时间为当天，并且在签到时间范围内，则设置为进行中。
     * 如果还没有到时间，就显示未开始
     * 如果已经结束，就显示已结束
     * @param list 会议记录
     */
    private void fillMeetingStatus(List<SignInInfoRespVO> list) {
        LocalDateTime now = LocalDateTime.now();

        for (SignInInfoRespVO vo : list) {
            LocalDateTime endDate = vo.getEndDate();
            LocalDateTime startDate = vo.getStartDate();


            // 只比较日期部分，不比较时间部分
            LocalDate nowDate = now.toLocalDate();
            LocalDate startDateDate = startDate.toLocalDate();
            LocalDate endDateDate = endDate.toLocalDate();

            if (nowDate.isBefore(startDateDate)) {
                //未开始
                vo.setStatus(0);
            } else if (nowDate.isAfter(endDateDate)) {
                //已结束
                vo.setStatus(1);
            } else {
                //进行中
                vo.setStatus(2);
            }

        }
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