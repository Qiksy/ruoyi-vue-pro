package cn.iocoder.yudao.module.sale.service.productioncompeteinfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.sale.controller.admin.productioncompeteinfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioncompeteinfo.ProductionCompeteInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 工厂竞品管理 Service 接口
 *
 * @author 芋道源码
 */
public interface ProductionCompeteInfoService {

    /**
     * 创建工厂竞品管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductionCompeteInfo(@Valid ProductionCompeteInfoSaveReqVO createReqVO);

    /**
     * 更新工厂竞品管理
     *
     * @param updateReqVO 更新信息
     */
    void updateProductionCompeteInfo(@Valid ProductionCompeteInfoSaveReqVO updateReqVO);

    /**
     * 删除工厂竞品管理
     *
     * @param id 编号
     */
    void deleteProductionCompeteInfo(Long id);

    /**
     * 获得工厂竞品管理
     *
     * @param id 编号
     * @return 工厂竞品管理
     */
    ProductionCompeteInfoDO getProductionCompeteInfo(Long id);

    /**
     * 获得工厂竞品管理分页
     *
     * @param pageReqVO 分页查询
     * @return 工厂竞品管理分页
     */
    PageResult<ProductionCompeteInfoDO> getProductionCompeteInfoPage(ProductionCompeteInfoPageReqVO pageReqVO);

}