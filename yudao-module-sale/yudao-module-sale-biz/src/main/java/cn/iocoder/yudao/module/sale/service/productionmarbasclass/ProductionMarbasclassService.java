package cn.iocoder.yudao.module.sale.service.productionmarbasclass;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.sale.controller.admin.productionmarbasclass.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarbasclass.ProductionMarbasclassDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 物料分类 Service 接口
 *
 * @author 芋道源码
 */
public interface ProductionMarbasclassService {

    /**
     * 创建物料分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductionMarbasclass(@Valid ProductionMarbasclassSaveReqVO createReqVO);

    /**
     * 更新物料分类
     *
     * @param updateReqVO 更新信息
     */
    void updateProductionMarbasclass(@Valid ProductionMarbasclassSaveReqVO updateReqVO);

    /**
     * 删除物料分类
     *
     * @param id 编号
     */
    void deleteProductionMarbasclass(Long id);

    /**
     * 获得物料分类
     *
     * @param id 编号
     * @return 物料分类
     */
    ProductionMarbasclassDO getProductionMarbasclass(Long id);

    /**
     * 获得物料分类列表
     *
     * @param listReqVO 查询条件
     * @return 物料分类列表
     */
    List<ProductionMarbasclassDO> getProductionMarbasclassList(ProductionMarbasclassListReqVO listReqVO);

    /**
     * 获取子物料分类
     * @param marbasclassId 物料分类编号
     * @return
     */
    List<ProductionMarbasclassDO> getChildMarbasclassIdList(Long marbasclassId);
}