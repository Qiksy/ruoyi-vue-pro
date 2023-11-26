package cn.iocoder.yudao.module.sale.service.competeinfosub;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.sale.controller.admin.competeinfosub.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.competeinfosub.CompeteInfoSubDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 竞品信息子 Service 接口
 *
 * @author 播恩超级管理员
 */
public interface CompeteInfoSubService {

    /**
     * 创建竞品信息子
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCompeteInfoSub(@Valid CompeteInfoSubSaveReqVO createReqVO);

    /**
     * 更新竞品信息子
     *
     * @param updateReqVO 更新信息
     */
    void updateCompeteInfoSub(@Valid CompeteInfoSubSaveReqVO updateReqVO);

    /**
     * 删除竞品信息子
     *
     * @param id 编号
     */
    void deleteCompeteInfoSub(Long id);

    /**
     * 获得竞品信息子
     *
     * @param id 编号
     * @return 竞品信息子
     */
    CompeteInfoSubDO getCompeteInfoSub(Long id);

    /**
     * 获得竞品信息子分页
     *
     * @param pageReqVO 分页查询
     * @return 竞品信息子分页
     */
    PageResult<CompeteInfoSubDO> getCompeteInfoSubPage(CompeteInfoSubPageReqVO pageReqVO);

}