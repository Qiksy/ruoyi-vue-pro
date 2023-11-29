package cn.iocoder.yudao.module.sale.service.productioncompeteinfo;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

        // 因为使用了逻辑删除，所以不再使用唯一索引，插入之前先判断是否存在
        LambdaQueryWrapper<ProductionCompeteInfoDO> queryWrapper = new LambdaQueryWrapper<ProductionCompeteInfoDO>()
                .eq(ProductionCompeteInfoDO::getProductionId, productionCompeteInfo.getProductionId())
                .eq(ProductionCompeteInfoDO::getDeptId, productionCompeteInfo.getDeptId())
                .eq(ProductionCompeteInfoDO::getDeleted, 0);

        Long count = productionCompeteInfoMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw exception(PRODUCTION_COMPETE_INFO_DUPLICATE);
        }

        productionCompeteInfoMapper.insert(productionCompeteInfo);

        // 返回
        return productionCompeteInfo.getId();
    }

    @Override
    public void updateProductionCompeteInfo(ProductionCompeteInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateProductionCompeteInfoExists(updateReqVO.getId());
        // 更新
        ProductionCompeteInfoDO updateObj = BeanUtils.toBean(updateReqVO, ProductionCompeteInfoDO.class);

        // 因为使用了逻辑删除，所以不再使用唯一索引，插入之前先判断是否存在
        LambdaQueryWrapper<ProductionCompeteInfoDO> queryWrapper = new LambdaQueryWrapper<ProductionCompeteInfoDO>()
                .eq(ProductionCompeteInfoDO::getProductionId, updateObj.getProductionId())
                .eq(ProductionCompeteInfoDO::getDeptId, updateObj.getDeptId())
                .eq(ProductionCompeteInfoDO::getDeleted, 0);

        Long count = productionCompeteInfoMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw exception(PRODUCTION_COMPETE_INFO_DUPLICATE);
        }

        productionCompeteInfoMapper.updateById(updateObj);

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

    @Override
    public List<ProductionCompeteInfoRespVO> getProductionCompeteInfoList(ProductionCompeteInfoListReqVO listReqVO) {
        return productionCompeteInfoMapper.selectListByCondition(listReqVO);
    }

    @Override
    public Map<Long, ProductionCompeteInfoDO> getProductionCompeteInfoMap(Set<Long> competeIds) {


        LambdaQueryWrapper<ProductionCompeteInfoDO> queryWrapper = new LambdaQueryWrapper<ProductionCompeteInfoDO>()
                .in(ProductionCompeteInfoDO::getId, competeIds)
                .eq(ProductionCompeteInfoDO::getDeleted, 0);
        List<ProductionCompeteInfoDO> productionCompeteInfoDOS = productionCompeteInfoMapper.selectList(queryWrapper);

        if (CollUtil.isEmpty(productionCompeteInfoDOS)){
            return Collections.emptyMap();
        }

        return productionCompeteInfoDOS.stream().collect(Collectors.toMap(ProductionCompeteInfoDO::getId, productionCompeteInfoDO -> productionCompeteInfoDO));

    }
}