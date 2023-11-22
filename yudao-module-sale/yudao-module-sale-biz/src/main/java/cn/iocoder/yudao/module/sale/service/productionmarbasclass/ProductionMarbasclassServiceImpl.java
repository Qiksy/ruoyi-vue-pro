package cn.iocoder.yudao.module.sale.service.productionmarbasclass;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.sale.controller.admin.productionmarbasclass.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarbasclass.ProductionMarbasclassDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.productionmarbasclass.ProductionMarbasclassMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;

/**
 * 物料分类 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ProductionMarbasclassServiceImpl implements ProductionMarbasclassService {

    @Resource
    private ProductionMarbasclassMapper productionMarbasclassMapper;

    @Override
    public Long createProductionMarbasclass(ProductionMarbasclassSaveReqVO createReqVO) {
        // 校验父级的有效性
        validateParentProductionMarbasclass(null, createReqVO.getParentId());
        // 校验名称的唯一性
        validateProductionMarbasclassNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入
        ProductionMarbasclassDO productionMarbasclass = BeanUtils.toBean(createReqVO, ProductionMarbasclassDO.class);
        productionMarbasclassMapper.insert(productionMarbasclass);
        // 返回
        return productionMarbasclass.getId();
    }

    @Override
    public void updateProductionMarbasclass(ProductionMarbasclassSaveReqVO updateReqVO) {
        // 校验存在
        validateProductionMarbasclassExists(updateReqVO.getId());
        // 校验父级的有效性
        validateParentProductionMarbasclass(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验名称的唯一性
        validateProductionMarbasclassNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新
        ProductionMarbasclassDO updateObj = BeanUtils.toBean(updateReqVO, ProductionMarbasclassDO.class);
        productionMarbasclassMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductionMarbasclass(Long id) {
        // 校验存在
        validateProductionMarbasclassExists(id);
        // 校验是否有子物料分类
        if (productionMarbasclassMapper.selectCountByParentId(id) > 0) {
            throw exception(PRODUCTION_MARBASCLASS_EXITS_CHILDREN);
        }
        // 删除
        productionMarbasclassMapper.deleteById(id);
    }

    private void validateProductionMarbasclassExists(Long id) {
        if (productionMarbasclassMapper.selectById(id) == null) {
            throw exception(PRODUCTION_MARBASCLASS_NOT_EXISTS);
        }
    }

    private void validateParentProductionMarbasclass(Long id, Long parentId) {
        if (parentId == null || ProductionMarbasclassDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父物料分类
        if (Objects.equals(id, parentId)) {
            throw exception(PRODUCTION_MARBASCLASS_PARENT_ERROR);
        }
        // 2. 父物料分类不存在
        ProductionMarbasclassDO parentProductionMarbasclass = productionMarbasclassMapper.selectById(parentId);
        if (parentProductionMarbasclass == null) {
            throw exception(PRODUCTION_MARBASCLASS_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父物料分类，如果父物料分类是自己的子物料分类，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentProductionMarbasclass.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(PRODUCTION_MARBASCLASS_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父物料分类
            if (parentId == null || ProductionMarbasclassDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentProductionMarbasclass = productionMarbasclassMapper.selectById(parentId);
            if (parentProductionMarbasclass == null) {
                break;
            }
        }
    }

    private void validateProductionMarbasclassNameUnique(Long id, Long parentId, String name) {
        ProductionMarbasclassDO productionMarbasclass = productionMarbasclassMapper.selectByParentIdAndName(parentId, name);
        if (productionMarbasclass == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的物料分类
        if (id == null) {
            throw exception(PRODUCTION_MARBASCLASS_NAME_DUPLICATE);
        }
        if (!Objects.equals(productionMarbasclass.getId(), id)) {
            throw exception(PRODUCTION_MARBASCLASS_NAME_DUPLICATE);
        }
    }

    @Override
    public ProductionMarbasclassDO getProductionMarbasclass(Long id) {
        return productionMarbasclassMapper.selectById(id);
    }

    @Override
    public List<ProductionMarbasclassDO> getProductionMarbasclassList(ProductionMarbasclassListReqVO listReqVO) {
        return productionMarbasclassMapper.selectList(listReqVO);
    }

}