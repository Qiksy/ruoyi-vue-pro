package cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry;

import java.util.*;

import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningReqVO;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningRespVO;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningUpdateReqVO;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.RejuvenateReqVO;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.specimeninfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.specimen.SpecimenInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 冷冻管库存预录入 Service 接口
 *
 * @author 芋道源码
 */
public interface SpecimenInfoService {

    /**
     * 创建冷冻管库存预录入
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    List<Long> createSpecimenInfo(@Valid SpecimenInfoSaveReqVO createReqVO);

    /**
     * 更新冷冻管库存预录入
     *
     * @param updateReqVO 更新信息
     */
    void updateSpecimenInfo(@Valid SpecimenInfoUpdateReqVO updateReqVO);

    /**
     * 删除冷冻管库存预录入
     *
     * @param id 编号
     */
    void deleteSpecimenInfo(Long id);

    /**
     * 获得冷冻管库存预录入
     *
     * @param id 编号
     * @return 冷冻管库存预录入
     */
    SpecimenInfoDO getSpecimenInfo(Long id);

    /**
     * 获得冷冻管库存预录入分页
     *
     * @param pageReqVO 分页查询
     * @return 冷冻管库存预录入分页
     */
    PageResult<SpecimenInfoDO> getSpecimenInfo(SpecimenInfoPageReqVO pageReqVO);

    /**
     * 连表查询分页
     * @param pageReqVO 分页查询
     * @return 冷冻管库存预录入分页
     */
    PageResult<SpecimenInfoRespVO> getSpecimenInfoPage2(SpecimenInfoPageReqVO pageReqVO);

    /**
     * 连表查询分页，去掉正在处理的样品
     * @param pageReqVO 分页查询
     * @return 样品分页
     */
    PageResult<SpecimenInfoRespVO> getSpecimenInfoPage3(SpecimenInfoPageReqVO pageReqVO);


    /**
     * 传入槽位id，获取对应的槽位位置
     * @param stockIds 槽位id
     * @return map
     */
    Map<Long, String> getStockPositionStrMap(List<Long> stockIds);

    PageResult<ExpiredWarningRespVO> getExpiredWaringPage(ExpiredWarningReqVO queryVO);

    /**
     * 获取某个菌种的库存列表
     * @param id 菌种id
     * @return list
     */
    List<SpecimenInfoRespVO> getMicrobeBasicInfoStorageList(Long id);

    /**
     * 更新过期日期
     * @param reqVO 过期预警更新
     */
    void updateExpiredDate(ExpiredWarningUpdateReqVO reqVO);

    /**
     * 传代 / 复壮 并生成记录
     * @param reqVO vo
     */
    void rejuvenate(RejuvenateReqVO reqVO);
}