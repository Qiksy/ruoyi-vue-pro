package cn.iocoder.yudao.module.oa.service.signininfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.oa.controller.admin.signininfo.vo.*;
import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInInfoDO;
import cn.iocoder.yudao.module.oa.dal.dataobject.signinrecord.SignInRecordDO;
import cn.iocoder.yudao.module.oa.dal.dataobject.signintimerange.SignInTimeRangeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 会议签到 Service 接口
 *
 * @author 超级管理员
 */
public interface SignInInfoService {

    /**
     * 创建会议签到
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSignInInfo(@Valid SignInInfoSaveReqVO createReqVO);

    /**
     * 更新会议签到
     *
     * @param updateReqVO 更新信息
     */
    void updateSignInInfo(@Valid SignInInfoSaveReqVO updateReqVO);

    /**
     * 删除会议签到
     *
     * @param id 编号
     */
    void deleteSignInInfo(Long id);

    /**
     * 获得会议签到
     *
     * @param id 编号
     * @return 会议签到
     */
    SignInInfoDO getSignInInfo(Long id);

    /**
     * 获得会议签到分页
     *
     * @param pageReqVO 分页查询
     * @return 会议签到分页
     */
    PageResult<SignInInfoDO> getSignInInfoPage(SignInInfoPageReqVO pageReqVO);

    // ==================== 子表（签到记录） ====================

    /**
     * 获得签到记录列表
     *
     * @param parentId 主表id
     * @return 签到记录列表
     */
    List<SignInRecordDO> getSignInRecordListByParentId(Long parentId);

    // ==================== 子表（签到时间范围） ====================

    /**
     * 获得签到时间范围列表
     *
     * @param parentId 主表id
     * @return 签到时间范围列表
     */
    List<SignInTimeRangeDO> getSignInTimeRangeListByParentId(Long parentId);

}