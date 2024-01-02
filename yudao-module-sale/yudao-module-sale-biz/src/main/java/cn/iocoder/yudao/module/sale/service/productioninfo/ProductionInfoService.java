package cn.iocoder.yudao.module.sale.service.productioninfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.sale.controller.admin.productioninfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioninfo.ProductionInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 物料信息 Service 接口
 *
 * @author 芋道源码
 */
public interface ProductionInfoService {

    /**
     * 创建物料信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductionInfo(@Valid ProductionInfoSaveReqVO createReqVO);

    /**
     * 更新物料信息
     *
     * @param updateReqVO 更新信息
     */
    void updateProductionInfo(@Valid ProductionInfoSaveReqVO updateReqVO);

    /**
     * 删除物料信息
     *
     * @param id 编号
     */
    void deleteProductionInfo(Long id);

    /**
     * 获得物料信息
     *
     * @param id 编号
     * @return 物料信息
     */
    ProductionInfoDO getProductionInfo(Long id);

    /**
     * 获得物料信息分页
     *
     * @param pageReqVO 分页查询
     * @return 物料信息分页
     */
    PageResult<ProductionInfoDO> getProductionInfoPage(ProductionInfoPageReqVO pageReqVO);

    Map<Long, ProductionInfoDO> getProductionMap(Collection<Long> productionIds);

    /**
     * 获取产成品物料以供选择
     * @param reqVO
     * @return
     */
    List<ProductionInfoDO> getProductionInfoList(ProductionInfoPageReqVO reqVO);
}