package cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.MPJLambdaWrapperX;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningReqVO;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningRespVO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDetailDO;
import cn.iocoder.yudao.module.system.dal.dataobject.user.AdminUserDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockpreentry.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 冷冻管库存预录入 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface FreezingTubeStockPreEntryMapper extends BaseMapperX<FreezingTubeStockPreEntryDO> {

    default PageResult<FreezingTubeStockPreEntryDO> selectPage(FreezingTubeStockPreEntryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FreezingTubeStockPreEntryDO>()
                .eqIfPresent(FreezingTubeStockPreEntryDO::getCode, reqVO.getCode())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getTubeId, reqVO.getTubeId())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getBoxId, reqVO.getBoxId())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getTubePosition, reqVO.getTubePosition())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getTubePositionX, reqVO.getTubePositionX())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getTubePositionY, reqVO.getTubePositionY())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getGenerationNumber, reqVO.getGenerationNumber())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getThawFreezeCycleCount, reqVO.getThawFreezeCycleCount())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getMicrobeId, reqVO.getMicrobeId())
                .betweenIfPresent(FreezingTubeStockPreEntryDO::getExpirationDate, reqVO.getExpirationDate())
                .betweenIfPresent(FreezingTubeStockPreEntryDO::getSaveDate, reqVO.getSaveDate())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getSaveBy, reqVO.getSaveBy())
                .betweenIfPresent(FreezingTubeStockPreEntryDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getStatus, reqVO.getStatus())
                .orderByDesc(FreezingTubeStockPreEntryDO::getId));
    }

    /*
    //这个是mpj多表连接的方式，太麻烦了。
        default PageResult<FreezingTubeStockPreEntryRespVO>  selectPage2(FreezingTubeStockPreEntryPageReqVO reqVO){

        MPJLambdaWrapperX<FreezingTubeStockPreEntryDO> wrapperX = new MPJLambdaWrapperX<>();
        wrapperX.selectAll(FreezingTubeStockPreEntryDO.class); // 查询所有字段
        wrapperX.selectAs(AdminUserDO::getNickname,FreezingTubeStockPreEntryDetailDO::getNickName); // 查询指定字段
        wrapperX.leftJoin(AdminUserDO.class,AdminUserDO::getId,FreezingTubeStockPreEntryDO::getSaveBy); // 连表
        //==================原本的逻辑=====================
        wrapperX.eqIfPresent(FreezingTubeStockPreEntryDO::getCode, reqVO.getCode())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getTubeId, reqVO.getTubeId())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getBoxId, reqVO.getBoxId())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getTubePosition, reqVO.getTubePosition())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getTubePositionX, reqVO.getTubePositionX())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getTubePositionY, reqVO.getTubePositionY())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getGenerationNumber, reqVO.getGenerationNumber())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getThawFreezeCycleCount, reqVO.getThawFreezeCycleCount())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getProjectId, reqVO.getProjectId())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getMicrobeId, reqVO.getMicrobeId())
                .betweenIfPresent(FreezingTubeStockPreEntryDO::getExpirationDate, reqVO.getExpirationDate())
                .betweenIfPresent(FreezingTubeStockPreEntryDO::getSaveDate, reqVO.getSaveDate())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getSaveBy, reqVO.getSaveBy())
                .betweenIfPresent(FreezingTubeStockPreEntryDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getRemark, reqVO.getRemark())
                .eqIfPresent(FreezingTubeStockPreEntryDO::getStatus, reqVO.getStatus())
                .orderByDesc(FreezingTubeStockPreEntryDO::getId);
        //================自己判断关联的表逻辑===============
        //自己判断是否为空
        if (StringUtils.isNotBlank(reqVO.getSaveByName())) {
            wrapperX.like(AdminUserDO::getNickname,reqVO.getSaveByName()); // 模糊查询
        }
        PageResult<FreezingTubeStockPreEntryDetailDO> result = selectJoinPage(reqVO,FreezingTubeStockPreEntryDetailDO.class,wrapperX);
        return BeanUtils.toBean(result, FreezingTubeStockPreEntryRespVO.class);
    }
     */
    IPage<FreezingTubeStockPreEntryRespVO> selectPage2(IPage<FreezingTubeStockPreEntryRespVO> page, @Param("req") FreezingTubeStockPreEntryPageReqVO reqVO);

    IPage<FreezingTubeStockPreEntryRespVO> selectPage3(IPage<FreezingTubeStockPreEntryRespVO> iPage, @Param("req") FreezingTubeStockPreEntryPageReqVO pageReqVO);

    IPage<ExpiredWarningRespVO> selectPage4(IPage<ExpiredWarningRespVO> iPage,@Param("req") ExpiredWarningReqVO pageReqVO);

    /**
     * 菌种id查询
     * @param microId 菌种id
     * @return
     */
    default List<FreezingTubeStockPreEntryDO> selectListByMicrobeId(Long microId){
        return selectList(new LambdaQueryWrapperX<FreezingTubeStockPreEntryDO>().eq(FreezingTubeStockPreEntryDO::getMicrobeId,microId));
    }
}