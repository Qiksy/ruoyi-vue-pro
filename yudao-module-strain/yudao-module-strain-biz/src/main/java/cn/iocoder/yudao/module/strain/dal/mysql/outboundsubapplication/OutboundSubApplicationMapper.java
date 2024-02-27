package cn.iocoder.yudao.module.strain.dal.mysql.outboundsubapplication;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundsubapplication.OutboundSubApplicationDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.strain.controller.admin.outboundsubapplication.vo.*;
import org.apache.ibatis.annotations.Select;

/**
 * 出库申请子 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OutboundSubApplicationMapper extends BaseMapperX<OutboundSubApplicationDO> {

    default PageResult<OutboundSubApplicationDO> selectPage(OutboundSubApplicationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OutboundSubApplicationDO>()
                .eqIfPresent(OutboundSubApplicationDO::getParentId, reqVO.getParentId())
                .eqIfPresent(OutboundSubApplicationDO::getSpecimenId, reqVO.getTubeId())
                .betweenIfPresent(OutboundSubApplicationDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(OutboundSubApplicationDO::getRemark, reqVO.getRemark())
                .orderByDesc(OutboundSubApplicationDO::getId));
    }

    /**
     * 查询是否存在某个标本已经正在处理当中
     * @param specimenIds 标本编号
     * @return  编号
     */
    @Select("""
            <script>
                select DISTINCT specimen_code from strain_outbound_sub_application
                where exists (
                select 1 from strain_outbound_application 
                where strain_outbound_application.deleted = 0
                and strain_outbound_application.id = strain_outbound_sub_application.parent_id
                and strain_outbound_application.appro_result = '1'
                )
                and deleted = 0
                and specimen_id in 
                <foreach item='item' index='index' collection='specimenIds' open='(' separator=',' close=')'>
                    #{item}
                </foreach>
                </script>
                """)
    List<String> selectProcessorBySpecimenIds(List<Long> specimenIds);
}