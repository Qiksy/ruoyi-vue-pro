package cn.iocoder.yudao.module.system.service.printtemplate;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.system.controller.admin.printtemplate.vo.*;
import cn.iocoder.yudao.module.system.dal.dataobject.printtemplate.PrintTemplateDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.system.dal.mysql.printtemplate.PrintTemplateMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 打印模板 Service 实现类
 *
 * @author 播恩超级管理员
 */
@Service
@Validated
public class PrintTemplateServiceImpl implements PrintTemplateService {

    @Resource
    private PrintTemplateMapper printTemplateMapper;

    @Override
    public Long createPrintTemplate(PrintTemplateSaveReqVO createReqVO) {
        // 插入
        PrintTemplateDO printTemplate = BeanUtils.toBean(createReqVO, PrintTemplateDO.class);
        printTemplateMapper.insert(printTemplate);
        // 返回
        return printTemplate.getId();
    }

    @Override
    public void updatePrintTemplate(PrintTemplateSaveReqVO updateReqVO) {
        // 校验存在
        validatePrintTemplateExists(updateReqVO.getId());
        // 更新
        PrintTemplateDO updateObj = BeanUtils.toBean(updateReqVO, PrintTemplateDO.class);
        printTemplateMapper.updateById(updateObj);
    }

    @Override
    public void deletePrintTemplate(Long id) {
        // 校验存在
        validatePrintTemplateExists(id);
        // 删除
        printTemplateMapper.deleteById(id);
    }

    private void validatePrintTemplateExists(Long id) {
        if (printTemplateMapper.selectById(id) == null) {
            throw exception(PRINT_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public PrintTemplateDO getPrintTemplate(Long id) {
        return printTemplateMapper.selectById(id);
    }

    @Override
    public PageResult<PrintTemplateDO> getPrintTemplatePage(PrintTemplatePageReqVO pageReqVO) {
        return printTemplateMapper.selectPage(pageReqVO);
    }


    @Override
    public PrintTemplateDO getPrintTemplateByCode(String code) {
        return printTemplateMapper.selectOne("code",code);
    }
}