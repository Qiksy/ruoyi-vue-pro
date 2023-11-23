package cn.iocoder.yudao.module.sale.dal.mysql.productionmarbasclass;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarbasclass.ProductionMarbasclassDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.sale.controller.admin.productionmarbasclass.vo.*;

/**
 * 物料分类 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductionMarbasclassMapper extends BaseMapperX<ProductionMarbasclassDO> {

    default List<ProductionMarbasclassDO> selectList(ProductionMarbasclassListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProductionMarbasclassDO>()
                .likeIfPresent(ProductionMarbasclassDO::getName, reqVO.getName())
                .eqIfPresent(ProductionMarbasclassDO::getParentId, reqVO.getParentId())
                .betweenIfPresent(ProductionMarbasclassDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(ProductionMarbasclassDO::getId));
    }

	default ProductionMarbasclassDO selectByParentIdAndName(Long parentId, String name) {
	    return selectOne(ProductionMarbasclassDO::getParentId, parentId, ProductionMarbasclassDO::getName, name);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(ProductionMarbasclassDO::getParentId, parentId);
    }

    default List<ProductionMarbasclassDO> selectListByParentId(Collection<Long> parentIds){
        return selectList(ProductionMarbasclassDO::getParentId, parentIds);
    }
}