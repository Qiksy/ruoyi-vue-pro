package cn.iocoder.yudao.module.sale.service.competeinfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.sale.controller.admin.competeinfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.competeinfo.CompeteInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 竞品信息 Service 接口
 *
 * @author 芋道源码
 */
public interface CompeteInfoService {

    /**
     * 创建竞品信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCompeteInfo(@Valid CompeteInfoSaveReqVO createReqVO);

    /**
     * 更新竞品信息
     *
     * @param updateReqVO 更新信息
     */
    void updateCompeteInfo(@Valid CompeteInfoSaveReqVO updateReqVO);

    /**
     * 删除竞品信息
     *
     * @param id 编号
     */
    void deleteCompeteInfo(Long id);

    /**
     * 获得竞品信息
     *
     * @param id 编号
     * @return 竞品信息
     */
    CompeteInfoDO getCompeteInfo(Long id);

    /**
     * 获得竞品信息分页
     *
     * @param pageReqVO 分页查询
     * @return 竞品信息分页
     */
    PageResult<CompeteInfoDO> getCompeteInfoPage(CompeteInfoPageReqVO pageReqVO);

    /**
     * 使用mybatis xml实现查询和分页
     * @param pageReqVO
     * @return
     */
    PageResult<CompeteInfoRespVO> getCompeteInfoPage2(CompeteInfoPageReqVO pageReqVO);

    CompeteInfoRespVO getCompeteInfo2(Long id);
}