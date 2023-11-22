package cn.iocoder.yudao.module.sale.service.productioninfo;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.sale.controller.admin.productioninfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioninfo.ProductionInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.productioninfo.ProductionInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;

/**
 * 物料信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ProductionInfoServiceImpl implements ProductionInfoService {

    @Resource
    private ProductionInfoMapper productionInfoMapper;

    @Override
    public Long createProductionInfo(ProductionInfoSaveReqVO createReqVO) {
        // 插入
        ProductionInfoDO productionInfo = BeanUtils.toBean(createReqVO, ProductionInfoDO.class);
        productionInfoMapper.insert(productionInfo);
        // 返回
        return productionInfo.getId();
    }

    @Override
    public void updateProductionInfo(ProductionInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateProductionInfoExists(updateReqVO.getId());
        // 更新
        ProductionInfoDO updateObj = BeanUtils.toBean(updateReqVO, ProductionInfoDO.class);
        productionInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductionInfo(Long id) {
        // 校验存在
        validateProductionInfoExists(id);
        // 删除
        productionInfoMapper.deleteById(id);
    }

    private void validateProductionInfoExists(Long id) {
        if (productionInfoMapper.selectById(id) == null) {
            throw exception(PRODUCTION_INFO_NOT_EXISTS);
        }
    }

    @Override
    public ProductionInfoDO getProductionInfo(Long id) {
        return productionInfoMapper.selectById(id);
    }

    @Override
    public PageResult<ProductionInfoDO> getProductionInfoPage(ProductionInfoPageReqVO pageReqVO) {
        return productionInfoMapper.selectPage(pageReqVO);
    }

}