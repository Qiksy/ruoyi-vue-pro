package cn.iocoder.yudao.module.sale.dal.mysql.prodlineinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.sale.dal.dataobject.prodlineinfo.ProdlineInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.sale.controller.admin.prodlineinfo.vo.*;

/**
 * 产品线 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ProdlineInfoMapper extends BaseMapperX<ProdlineInfoDO> {

    default List<ProdlineInfoDO> selectList(ProdlineInfoListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProdlineInfoDO>()
                .likeIfPresent(ProdlineInfoDO::getName, reqVO.getName())
                .eqIfPresent(ProdlineInfoDO::getParentId, reqVO.getParentId())
                .betweenIfPresent(ProdlineInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProdlineInfoDO::getId));
    }

	default ProdlineInfoDO selectByParentIdAndName(Long parentId, String name) {
	    return selectOne(ProdlineInfoDO::getParentId, parentId, ProdlineInfoDO::getName, name);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(ProdlineInfoDO::getParentId, parentId);
    }

}