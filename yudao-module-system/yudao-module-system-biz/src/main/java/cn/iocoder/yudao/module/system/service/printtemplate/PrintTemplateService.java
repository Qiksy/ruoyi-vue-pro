package cn.iocoder.yudao.module.system.service.printtemplate;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.system.controller.admin.printtemplate.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.printtemplate.PrintTemplateDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 打印模板 Service 接口
 *
 * @author 播恩超级管理员
 */
public interface PrintTemplateService {

    /**
     * 创建打印模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPrintTemplate(@Valid PrintTemplateSaveReqVO createReqVO);

    /**
     * 更新打印模板
     *
     * @param updateReqVO 更新信息
     */
    void updatePrintTemplate(@Valid PrintTemplateSaveReqVO updateReqVO);

    /**
     * 删除打印模板
     *
     * @param id 编号
     */
    void deletePrintTemplate(Long id);

    /**
     * 获得打印模板
     *
     * @param id 编号
     * @return 打印模板
     */
    PrintTemplateDO getPrintTemplate(Long id);

    /**
     * 获得打印模板分页
     *
     * @param pageReqVO 分页查询
     * @return 打印模板分页
     */
    PageResult<PrintTemplateDO> getPrintTemplatePage(PrintTemplatePageReqVO pageReqVO);

    PrintTemplateDO getPrintTemplateByCode(String code);
}