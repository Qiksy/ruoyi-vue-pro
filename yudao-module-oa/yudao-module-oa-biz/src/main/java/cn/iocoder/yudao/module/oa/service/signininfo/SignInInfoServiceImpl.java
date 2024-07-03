package cn.iocoder.yudao.module.oa.service.signininfo;

import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.infra.api.file.FileApi;
import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInRecordDO;
import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInTimeRangeDO;
import cn.iocoder.yudao.module.oa.dal.mysql.signininfo.SignInRecordMapper;
import cn.iocoder.yudao.module.oa.dal.mysql.signininfo.SignInTimeRangeMapper;
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
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.oa.controller.admin.signininfo.vo.*;
import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.oa.dal.mysql.signininfo.SignInInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.oa.enums.ErrorCodeConstants.*;

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

        // 2. 插入子表
        // 判断签到时间的类型，如果是全体，则插入00:00-23:59
        if (signInInfo.getSignInTimeType().equals("0")) {
            SignInTimeRangeDO signInTimeRangeDO = new SignInTimeRangeDO();
            signInTimeRangeDO.setParentId(signInInfo.getId());
            signInTimeRangeDO.setStartTime(LocalTime.MIN);
            signInTimeRangeDO.setEndTime(LocalTime.of(23,59,59));
            signInTimeRangeMapper.insert(signInTimeRangeDO);
        } else {
            createSignInTimeRangeList(signInInfo.getId(), createReqVO.getSignInTimeRanges());
        }

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

//        // 更新子表
//        updateSignInRecordList(updateReqVO.getId(), updateReqVO.getSignInRecords());
//        updateSignInTimeRangeList(updateReqVO.getId(), updateReqVO.getSignInTimeRanges());
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

        List<SignInTimeRangeDO> rangeDOS = getSignInTimeRangeListByParentId(id);
        respVO.setSignInTimeRangeList(rangeDOS);


        setMeetingStatus( respVO);
        return respVO;
    }

    /**
     * 必须拥有signintimerange属性
     * @param respVO
     */
    private void setMeetingStatus( SignInInfoRespVO respVO) {
        Long id = respVO.getId();
        List<SignInTimeRangeDO> rangeDOS = respVO.getSignInTimeRangeList();
    /*
        允许可以签到的情况
        1. 首先日期需要在会议日期内
        2. 在时间范围内没有签到记录

        1. 如果在签到日期前，显示尚未开始
        2. 如果在签到日期后，显示已结束
        3. 如果在签到日期内，且在时间范围内，没有签过到。则显示可以签到
        4. 如果在签到日期内，且在时间范围内，已经签过到。则显示已经签到
     */

        // 判断会议状态: 先获取签到的时间范围中，endTime 最大的时间
        //rangeDOS经过排序后，获取endTime最大的那个对象
        SignInTimeRangeDO max = rangeDOS.stream().max(Comparator.comparing(SignInTimeRangeDO::getEndTime)).get();
        // 获取最小的那个对象
        SignInTimeRangeDO min = rangeDOS.stream().min(Comparator.comparing(SignInTimeRangeDO::getStartTime)).get();

        LocalDateTime now = LocalDateTime.now(); //获取当前时间

        //会议最早的开始时间是startDate+min.startTime
        LocalDateTime minDateTime = LocalDateTime.of(respVO.getStartDate().toLocalDate(), min.getStartTime());
        //会议最晚的结束时间是endDate+max.endTime
        LocalDateTime maxDateTime = LocalDateTime.of(respVO.getEndDate().toLocalDate(), max.getEndTime());

        // 设置会议状态，0未开始，1进行中，2已结束
        if (now.isBefore(minDateTime)) {
            respVO.setStatus(0);
        } else if (now.isAfter(maxDateTime)) {
            respVO.setStatus(2);
        } else {
            // 正在进行中
            respVO.setStatus(1);

            //紧接着，判断签到记录，如果有记录，则显示已签到，如果没有记录，则显示可以签到
            LocalTime nowTime = now.toLocalTime();
            List<Long> rangeIds = rangeDOS.stream().filter(o -> o.getStartTime().isBefore(nowTime) && o.getEndTime().isAfter(nowTime)).map(SignInTimeRangeDO::getId).toList();
            //如果是空，则说明不在今天签到的任何一个范围内，显示可以签到，但是后面真正进行签到的时候，进行校验提示，说目前不在签到的时间范围内。
            if (rangeIds.isEmpty()) {
                respVO.setCanSignIn(true);
            }else {
                //如果不是空，则查询出来是否有签到记录
                List<SignInRecordDO> recordDOS = signInRecordMapper.selectList(
                        new LambdaQueryWrapperX<SignInRecordDO>().in(SignInRecordDO::getRangeId, rangeIds)
                                .eq(SignInRecordDO::getParentId, id) // 是当前会议
                                .eq(SignInRecordDO::getUserId, SecurityFrameworkUtils.getLoginUserId()) // 是当前用户
                                .eq(SignInRecordDO::getSignDate, LocalDate.now())  // 是今天
                );
                //如果有签到记录，并且数量等于recordDOS.size()，则说明已经签到完成了，不能再签到了
                // 否则还可以继续签到
                respVO.setCanSignIn(!(recordDOS.size() == rangeIds.size()));
            }
        }
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

            // 如果meetingIds为空，直接返回空
            if (meetingIds.isEmpty()) {
                PageResult<SignInInfoRespVO> result = new PageResult<>();
                result.setList(Collections.emptyList());
                result.setTotal(0L);
                return result;
            }

            signInInfoDOPageResult = signInInfoMapper.selectSelfPage(pageReqVO, meetingIds);
        }

        // 处理会议状态
        PageResult<SignInInfoRespVO> bean = BeanUtils.toBean(signInInfoDOPageResult, SignInInfoRespVO.class);

        for (SignInInfoRespVO respVO : bean.getList()) {
            //todo 后面再进行优化，不应该把查询放在for循环内
            respVO.setSignInTimeRangeList(getSignInTimeRangeListByParentId(respVO.getId()));
        }

        // 批量设置会议状态
        setMeetingStatusBatch(bean.getList());

        // 填充创建人消息
        fillCreator(bean.getList());

        return bean;
    }

    private void setMeetingStatusBatch(List<SignInInfoRespVO> list) {
        for (SignInInfoRespVO respVO : list) {
            setMeetingStatus(respVO);
        }
    }

    /**
     * 填充创建人的名称
     *
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
     *
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
                vo.setStatus(2);
            } else {
                //进行中
                vo.setStatus(1);
            }

        }
    }

    // ==================== 子表（签到记录） ====================

    @Override
    public List<SignInRecordDO> getSignInRecordListByParentId(Long parentId) {
        return signInRecordMapper.selectListByParentId(parentId);
    }

    //
//    private void createSignInRecordList(Long parentId, List<SignInRecordDO> list) {
//        if (list == null) {
//            return;
//        }
//        list.forEach(o -> o.setParentId(parentId));
//        signInRecordMapper.insertBatch(list);
//    }
//
//    private void updateSignInRecordList(Long parentId, List<SignInRecordDO> list) {
//        deleteSignInRecordByParentId(parentId);
//		list.forEach(o -> o.setId(null).setUpdater(null).setUpdateTime(null)); // 解决更新情况下：1）id 冲突；2）updateTime 不更新
//        createSignInRecordList(parentId, list);
//    }
//
    private void deleteSignInRecordByParentId(Long parentId) {
        signInRecordMapper.deleteByParentId(parentId);
    }

    /**
     * 用户进行签到
     *
     * @param id 会议id
     */
    @Override
    public void signIn(Long id) {
        // 1. 先获取这个会议信息
        SignInInfoRespVO signInInfo = getSignInInfo(id);

        // 2. 校验日期
        validateSignDate(signInInfo);

        // 3. 如果不是全天都可以签到，则要获取时间范围，进行遍历、判断
        List<SignInTimeRangeDO> rangeDOS = getSignInTimeRangeListByParentId(id);
        LocalTime nowTime = LocalTime.now();
        // 筛选符合时间范围内的签到记录
        List<SignInTimeRangeDO> list = rangeDOS.stream().filter(o -> nowTime.isAfter(o.getStartTime()) && nowTime.isBefore(o.getEndTime())).toList();
        if (list.isEmpty()) {
            //不在签到时间范围内
            throw exception(SIGN_IN_INFO_NOT_IN_TIME_RANGE);
        }

        List<SignInRecordDO> recordDOS = new ArrayList<>();// 需要插入的签到记录
        for (SignInTimeRangeDO rangeDO : list) {
            SignInRecordDO record = new SignInRecordDO();
            record.setParentId(id); // 主表id
            record.setRangeId(rangeDO.getId());  //所属的时间范围
            record.setSignDate(LocalDate.now()); // 签到日期
            record.setUserId(SecurityFrameworkUtils.getLoginUserId());
            recordDOS.add(record);
        }
        // 插入
        signInRecordMapper.insertBatch(recordDOS);
    }

    /**
     * 校验时间是否满足
     *
     * @param signInInfo
     */
    private void validateSignDate(SignInInfoRespVO signInInfo) {
        LocalDate now = LocalDate.now();

        LocalDate startDate = signInInfo.getStartDate().toLocalDate();
        LocalDate endDate = signInInfo.getEndDate().toLocalDate();

        if (now.isAfter(endDate)) {
            // 签到已经结束了
            throw exception(SIGN_IN_INFO_ENDED);
        }
        if (now.isBefore(startDate)) {
            // 还没有开始
            throw exception(SIGN_IN_INFO_NOT_BEGIN);
        }

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


    /**
     * 获得自身的代理对象，解决 AOP 生效问题
     *
     * @return 自己
     */
    private SignInInfoServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}