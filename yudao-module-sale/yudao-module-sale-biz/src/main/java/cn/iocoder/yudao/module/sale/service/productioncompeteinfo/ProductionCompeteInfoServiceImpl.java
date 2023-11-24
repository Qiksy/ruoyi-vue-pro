package cn.iocoder.yudao.module.sale.service.productioncompeteinfo;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.sale.controller.admin.productioncompeteinfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioncompeteinfo.ProductionCompeteInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.productioncompeteinfo.ProductionCompeteInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;

/**
 * 工厂竞品管理 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ProductionCompeteInfoServiceImpl implements ProductionCompeteInfoService {

    @Resource
    private ProductionCompeteInfoMapper productionCompeteInfoMapper;

    @Override
    public Long createProductionCompeteInfo(ProductionCompeteInfoSaveReqVO createReqVO) {
        // 插入
        ProductionCompeteInfoDO productionCompeteInfo = BeanUtils.toBean(createReqVO, ProductionCompeteInfoDO.class);
        try {
            productionCompeteInfoMapper.insert(productionCompeteInfo);
        } catch (Exception e) {
            // 判断是否违反唯一约束
            if (e.getMessage().contains("SQLIntegrityConstraintViolationException")) {
                throw exception(PRODUCTION_COMPETE_INFO_DUPLICATE);
            }
            throw new RuntimeException(e);
        }
        // 返回
        return productionCompeteInfo.getId();
    }

    @Override
    public void updateProductionCompeteInfo(ProductionCompeteInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateProductionCompeteInfoExists(updateReqVO.getId());
        // 更新
        ProductionCompeteInfoDO updateObj = BeanUtils.toBean(updateReqVO, ProductionCompeteInfoDO.class);
        try {
            productionCompeteInfoMapper.updateById(updateObj);
        } catch (Exception e) {
            // 判断是否违反唯一约束
            if (e.getMessage().contains("SQLIntegrityConstraintViolationException")) {
                throw exception(PRODUCTION_COMPETE_INFO_DUPLICATE);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteProductionCompeteInfo(Long id) {
        // 校验存在
        validateProductionCompeteInfoExists(id);
        // 删除
        productionCompeteInfoMapper.deleteById(id);
    }

    private void validateProductionCompeteInfoExists(Long id) {
        if (productionCompeteInfoMapper.selectById(id) == null) {
            throw exception(PRODUCTION_COMPETE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public ProductionCompeteInfoDO getProductionCompeteInfo(Long id) {
        return productionCompeteInfoMapper.selectById(id);
    }

    @Override
    public PageResult<ProductionCompeteInfoDO> getProductionCompeteInfoPage(ProductionCompeteInfoPageReqVO pageReqVO) {
        return productionCompeteInfoMapper.selectPage(pageReqVO);
    }

}