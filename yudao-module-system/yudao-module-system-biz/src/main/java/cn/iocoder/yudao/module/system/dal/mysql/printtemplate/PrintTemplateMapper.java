package cn.iocoder.yudao.module.system.dal.mysql.printtemplate;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.system.dal.dataobject.printtemplate.PrintTemplateDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.system.controller.admin.printtemplate.vo.*;

/**
 * 打印模板 Mapper
 *
 * @author 播恩超级管理员
 */
@Mapper
public interface PrintTemplateMapper extends BaseMapperX<PrintTemplateDO> {

    default PageResult<PrintTemplateDO> selectPage(PrintTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PrintTemplateDO>()
                .likeIfPresent(PrintTemplateDO::getCode, reqVO.getCode())
                .likeIfPresent(PrintTemplateDO::getName, reqVO.getName())
                .eqIfPresent(PrintTemplateDO::getTemplateContent, reqVO.getTemplateContent())
                .eqIfPresent(PrintTemplateDO::getIsSystemDefault, reqVO.getIsSystemDefault())
                .betweenIfPresent(PrintTemplateDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PrintTemplateDO::getRemark, reqVO.getRemark())
                .orderByDesc(PrintTemplateDO::getId));
    }

}