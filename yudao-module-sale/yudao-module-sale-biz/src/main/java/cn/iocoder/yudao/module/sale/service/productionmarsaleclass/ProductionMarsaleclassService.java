package cn.iocoder.yudao.module.sale.service.productionmarsaleclass;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.sale.controller.admin.productionmarsaleclass.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarsaleclass.ProductionMarsaleclassDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 销售分类 Service 接口
 *
 * @author 芋道源码
 */
public interface ProductionMarsaleclassService {

    /**
     * 创建销售分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductionMarsaleclass(@Valid ProductionMarsaleclassSaveReqVO createReqVO);

    /**
     * 更新销售分类
     *
     * @param updateReqVO 更新信息
     */
    void updateProductionMarsaleclass(@Valid ProductionMarsaleclassSaveReqVO updateReqVO);

    /**
     * 删除销售分类
     *
     * @param id 编号
     */
    void deleteProductionMarsaleclass(Long id);

    /**
     * 获得销售分类
     *
     * @param id 编号
     * @return 销售分类
     */
    ProductionMarsaleclassDO getProductionMarsaleclass(Long id);

    /**
     * 获得销售分类列表
     *
     * @param listReqVO 查询条件
     * @return 销售分类列表
     */
    List<ProductionMarsaleclassDO> getProductionMarsaleclassList(ProductionMarsaleclassListReqVO listReqVO);

}