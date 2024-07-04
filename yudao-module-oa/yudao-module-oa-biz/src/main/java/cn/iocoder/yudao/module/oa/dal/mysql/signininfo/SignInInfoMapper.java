package cn.iocoder.yudao.module.oa.dal.mysql.signininfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.oa.dal.dataobject.signininfo.SignInInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.oa.controller.admin.signininfo.vo.*;

/**
 * 会议签到 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface SignInInfoMapper extends BaseMapperX<SignInInfoDO> {

    default PageResult<SignInInfoDO> selectPage(SignInInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SignInInfoDO>()
                .eqIfPresent(SignInInfoDO::getCoverPicId, reqVO.getCoverPicId())
                .eqIfPresent(SignInInfoDO::getCoverPicUrl, reqVO.getCoverPicUrl())
                .likeIfPresent(SignInInfoDO::getTitle, reqVO.getTitle())
                .eqIfPresent(SignInInfoDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(SignInInfoDO::getStartDate, reqVO.getStartDate())
                .betweenIfPresent(SignInInfoDO::getEndDate, reqVO.getEndDate())
                .eqIfPresent(SignInInfoDO::getSignInTimeType, reqVO.getSignInTimeType())
                .eqIfPresent(SignInInfoDO::getPersonInfoNeed, reqVO.getPersonInfoNeed())
                .eqIfPresent(SignInInfoDO::getPositionNeed, reqVO.getPositionNeed())
                .eqIfPresent(SignInInfoDO::getPositionInfo, reqVO.getPositionInfo())
                .eqIfPresent(SignInInfoDO::getScannerNeed, reqVO.getScannerNeed())
                .eqIfPresent(SignInInfoDO::getBannerId, reqVO.getBannerId())
                .eqIfPresent(SignInInfoDO::getBannerUrl, reqVO.getBannerUrl())
                .eqIfPresent(SignInInfoDO::getLogoId, reqVO.getLogoId())
                .eqIfPresent(SignInInfoDO::getLogoUrl, reqVO.getLogoUrl())
                .eqIfPresent(SignInInfoDO::getTitlePicUrl, reqVO.getTitlePicUrl())
                .eqIfPresent(SignInInfoDO::getTitlePicId, reqVO.getTitlePicId())
                .eqIfPresent(SignInInfoDO::getSignTaskCount, reqVO.getSignTaskCount())
                .betweenIfPresent(SignInInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SignInInfoDO::getStartDate,SignInInfoDO::getId));
    }

    default PageResult<SignInInfoDO> selectSelfPage(SignInInfoPageReqVO reqVO, Set<Long> meetingIds){
        return selectPage(reqVO, new LambdaQueryWrapperX<SignInInfoDO>()
                .eqIfPresent(SignInInfoDO::getCoverPicId, reqVO.getCoverPicId())
                .eqIfPresent(SignInInfoDO::getCoverPicUrl, reqVO.getCoverPicUrl())
                .likeIfPresent(SignInInfoDO::getTitle, reqVO.getTitle())
                .eqIfPresent(SignInInfoDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(SignInInfoDO::getStartDate, reqVO.getStartDate())
                .betweenIfPresent(SignInInfoDO::getEndDate, reqVO.getEndDate())
                .eqIfPresent(SignInInfoDO::getSignInTimeType, reqVO.getSignInTimeType())
                .eqIfPresent(SignInInfoDO::getPersonInfoNeed, reqVO.getPersonInfoNeed())
                .eqIfPresent(SignInInfoDO::getPositionNeed, reqVO.getPositionNeed())
                .eqIfPresent(SignInInfoDO::getPositionInfo, reqVO.getPositionInfo())
                .eqIfPresent(SignInInfoDO::getScannerNeed, reqVO.getScannerNeed())
                .eqIfPresent(SignInInfoDO::getBannerId, reqVO.getBannerId())
                .eqIfPresent(SignInInfoDO::getBannerUrl, reqVO.getBannerUrl())
                .eqIfPresent(SignInInfoDO::getLogoId, reqVO.getLogoId())
                .eqIfPresent(SignInInfoDO::getLogoUrl, reqVO.getLogoUrl())
                .eqIfPresent(SignInInfoDO::getTitlePicUrl, reqVO.getTitlePicUrl())
                .eqIfPresent(SignInInfoDO::getTitlePicId, reqVO.getTitlePicId())
                .eqIfPresent(SignInInfoDO::getSignTaskCount, reqVO.getSignTaskCount())
                .betweenIfPresent(SignInInfoDO::getCreateTime, reqVO.getCreateTime())
                .in(SignInInfoDO::getId, meetingIds)
                .orderByDesc(SignInInfoDO::getStartDate,SignInInfoDO::getId)); // 根据开始时间排序

    }
}