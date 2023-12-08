package cn.iocoder.yudao.module.sale.dal.mysql.productionmarsaleclass;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarsaleclass.ProductionMarsaleclassDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.sale.controller.admin.productionmarsaleclass.vo.*;

/**
 * 销售分类 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductionMarsaleclassMapper extends BaseMapperX<ProductionMarsaleclassDO> {

    default List<ProductionMarsaleclassDO> selectList(ProductionMarsaleclassListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProductionMarsaleclassDO>()
                .likeIfPresent(ProductionMarsaleclassDO::getName, reqVO.getName())
                .eqIfPresent(ProductionMarsaleclassDO::getParentId, reqVO.getParentId())
                .betweenIfPresent(ProductionMarsaleclassDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductionMarsaleclassDO::getId));
    }

	default ProductionMarsaleclassDO selectByParentIdAndName(Long parentId, String name) {
	    return selectOne(ProductionMarsaleclassDO::getParentId, parentId, ProductionMarsaleclassDO::getName, name);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(ProductionMarsaleclassDO::getParentId, parentId);
    }

}