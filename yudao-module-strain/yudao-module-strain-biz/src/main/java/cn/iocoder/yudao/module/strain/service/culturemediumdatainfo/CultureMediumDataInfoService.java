package cn.iocoder.yudao.module.strain.service.culturemediumdatainfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.culturemediumdatainfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.culturemediumdatainfo.CultureMediumDataInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 培养基数据信息 Service 接口
 *
 * @author 芋道源码
 */
public interface CultureMediumDataInfoService {

    /**
     * 创建培养基数据信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCultureMediumDataInfo(@Valid CultureMediumDataInfoCreateReqVO createReqVO);

    /**
     * 更新培养基数据信息
     *
     * @param updateReqVO 更新信息
     */
    void updateCultureMediumDataInfo(@Valid CultureMediumDataInfoUpdateReqVO updateReqVO);

    /**
     * 删除培养基数据信息
     *
     * @param id 编号
     */
    void deleteCultureMediumDataInfo(Long id);

    /**
     * 获得培养基数据信息
     *
     * @param id 编号
     * @return 培养基数据信息
     */
    CultureMediumDataInfoDO getCultureMediumDataInfo(Long id);

    /**
     * 获得培养基数据信息列表
     *
     * @param ids 编号
     * @return 培养基数据信息列表
     */
    List<CultureMediumDataInfoDO> getCultureMediumDataInfoList(Collection<Long> ids);

    /**
     * 获得培养基数据信息分页
     *
     * @param pageReqVO 分页查询
     * @return 培养基数据信息分页
     */
    PageResult<CultureMediumDataInfoDO> getCultureMediumDataInfoPage(CultureMediumDataInfoPageReqVO pageReqVO);

    /**
     * 获得培养基数据信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 培养基数据信息列表
     */
    List<CultureMediumDataInfoDO> getCultureMediumDataInfoList(CultureMediumDataInfoExportReqVO exportReqVO);

    /**
     * 获取简单的name和id列表
     * @return 培养基列表
     */
    List<CultureMediumDataInfoDO> getSimpleCultureMediumDataInfoList();
}
