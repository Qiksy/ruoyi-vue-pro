package cn.iocoder.yudao.module.sale.service.productionmarsaleclass;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.sale.controller.admin.productionmarsaleclass.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarsaleclass.ProductionMarsaleclassDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.productionmarsaleclass.ProductionMarsaleclassMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;

/**
 * 销售分类 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ProductionMarsaleclassServiceImpl implements ProductionMarsaleclassService {

    @Resource
    private ProductionMarsaleclassMapper productionMarsaleclassMapper;

    @Override
    public Long createProductionMarsaleclass(ProductionMarsaleclassSaveReqVO createReqVO) {
        // 校验上级分类的有效性
        validateParentProductionMarsaleclass(null, createReqVO.getParentId());
        // 校验名称的唯一性
        validateProductionMarsaleclassNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入
        ProductionMarsaleclassDO productionMarsaleclass = BeanUtils.toBean(createReqVO, ProductionMarsaleclassDO.class);
        productionMarsaleclassMapper.insert(productionMarsaleclass);
        // 返回
        return productionMarsaleclass.getId();
    }

    @Override
    public void updateProductionMarsaleclass(ProductionMarsaleclassSaveReqVO updateReqVO) {
        // 校验存在
        validateProductionMarsaleclassExists(updateReqVO.getId());
        // 校验上级分类的有效性
        validateParentProductionMarsaleclass(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验名称的唯一性
        validateProductionMarsaleclassNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新
        ProductionMarsaleclassDO updateObj = BeanUtils.toBean(updateReqVO, ProductionMarsaleclassDO.class);
        productionMarsaleclassMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductionMarsaleclass(Long id) {
        // 校验存在
        validateProductionMarsaleclassExists(id);
        // 校验是否有子销售分类
        if (productionMarsaleclassMapper.selectCountByParentId(id) > 0) {
            throw exception(PRODUCTION_MARSALECLASS_EXITS_CHILDREN);
        }
        // 删除
        productionMarsaleclassMapper.deleteById(id);
    }

    private void validateProductionMarsaleclassExists(Long id) {
        if (productionMarsaleclassMapper.selectById(id) == null) {
            throw exception(PRODUCTION_MARSALECLASS_NOT_EXISTS);
        }
    }

    private void validateParentProductionMarsaleclass(Long id, Long parentId) {
        if (parentId == null || ProductionMarsaleclassDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父销售分类
        if (Objects.equals(id, parentId)) {
            throw exception(PRODUCTION_MARSALECLASS_PARENT_ERROR);
        }
        // 2. 父销售分类不存在
        ProductionMarsaleclassDO parentProductionMarsaleclass = productionMarsaleclassMapper.selectById(parentId);
        if (parentProductionMarsaleclass == null) {
            throw exception(PRODUCTION_MARSALECLASS_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父销售分类，如果父销售分类是自己的子销售分类，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentProductionMarsaleclass.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(PRODUCTION_MARSALECLASS_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父销售分类
            if (parentId == null || ProductionMarsaleclassDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentProductionMarsaleclass = productionMarsaleclassMapper.selectById(parentId);
            if (parentProductionMarsaleclass == null) {
                break;
            }
        }
    }

    private void validateProductionMarsaleclassNameUnique(Long id, Long parentId, String name) {
        ProductionMarsaleclassDO productionMarsaleclass = productionMarsaleclassMapper.selectByParentIdAndName(parentId, name);
        if (productionMarsaleclass == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的销售分类
        if (id == null) {
            throw exception(PRODUCTION_MARSALECLASS_NAME_DUPLICATE);
        }
        if (!Objects.equals(productionMarsaleclass.getId(), id)) {
            throw exception(PRODUCTION_MARSALECLASS_NAME_DUPLICATE);
        }
    }

    @Override
    public ProductionMarsaleclassDO getProductionMarsaleclass(Long id) {
        return productionMarsaleclassMapper.selectById(id);
    }

    @Override
    public List<ProductionMarsaleclassDO> getProductionMarsaleclassList(ProductionMarsaleclassListReqVO listReqVO) {
        return productionMarsaleclassMapper.selectList(listReqVO);
    }

    @Override
    public Map<Long, ProductionMarsaleclassDO> getMarsaleclassMap(Collection<Long> collect) {

        if (CollUtil.isEmpty(collect)) {
            return Collections.emptyMap();
        }
        LambdaQueryWrapper<ProductionMarsaleclassDO> queryWrapper = new LambdaQueryWrapper<ProductionMarsaleclassDO>()
                .select(ProductionMarsaleclassDO::getId, ProductionMarsaleclassDO::getName)
                .in(ProductionMarsaleclassDO::getId, collect);
        List<ProductionMarsaleclassDO> productionMarsaleclassDOS = productionMarsaleclassMapper.selectList(queryWrapper);



        return productionMarsaleclassDOS.stream().
                collect(Collectors.toMap(ProductionMarsaleclassDO::getId, ProductionMarsaleclassDO -> ProductionMarsaleclassDO));
    }
}