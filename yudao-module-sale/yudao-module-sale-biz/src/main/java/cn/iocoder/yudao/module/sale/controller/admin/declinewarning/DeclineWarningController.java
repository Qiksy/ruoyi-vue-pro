package cn.iocoder.yudao.module.sale.controller.admin.declinewarning;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileDO;
import cn.iocoder.yudao.module.infra.dal.mysql.file.FileMapper;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarningsub.DeclineWarningSubDO;
import cn.iocoder.yudao.module.sale.service.declinewarningsub.DeclineWarningSubService;
import cn.iocoder.yudao.module.system.api.tenant.dto.WecomeMessageRespDTO;
import cn.iocoder.yudao.module.system.api.tencent.TencentMiniProgramAuthApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;



import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

import cn.iocoder.yudao.module.sale.controller.admin.declinewarning.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarning.DeclineWarningDO;
import cn.iocoder.yudao.module.sale.service.declinewarning.DeclineWarningService;

@Tag(name = "管理后台 - 销量预警")
@RestController
@RequestMapping("/sale/decline-warning")
@Validated
@Slf4j
public class DeclineWarningController {

    @Resource
    private DeclineWarningService declineWarningService;

    @Resource
    private DeclineWarningSubService declineWarningSubService;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private TencentMiniProgramAuthApi tencentMiniProgramAuthApi;

    @PostMapping("/create")
    @Operation(summary = "创建销量预警")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:create')")
    public CommonResult<Long> createDeclineWarning(@Valid @RequestBody DeclineWarningSaveReqVO createReqVO) {
        return success(declineWarningService.createDeclineWarning(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新销量预警")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:update')")
    public CommonResult<Boolean> updateDeclineWarning(@Valid @RequestBody DeclineWarningSaveReqVO updateReqVO) {
        declineWarningService.updateDeclineWarning(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除销量预警")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:delete')")
    public CommonResult<Boolean> deleteDeclineWarning(@RequestParam("id") Long id) {
        declineWarningService.deleteDeclineWarning(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得销量预警")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:query')")
    public CommonResult<DeclineWarningRespVO> getDeclineWarning(@RequestParam("id") Long id) {
        DeclineWarningDO declineWarning = declineWarningService.getDeclineWarning(id);
        DeclineWarningRespVO bean = BeanUtils.toBean(declineWarning, DeclineWarningRespVO.class);
        //查询子表
        Map<Long,List<DeclineWarningSubDO>> dataMap = declineWarningSubService.getDeclineWarningSubMap(CollUtil.newArrayList(id));

        List<DeclineWarningSubDO> subDOList = dataMap.get(bean.getId());
        if (subDOList!=null&&!subDOList.isEmpty()) {
            bean.setSubRows(subDOList);
        }

        // 查询出附件
        FileDO fileDO = fileMapper.selectOne("id", bean.getFileId());
        bean.setFileInfo(fileDO);

        return success(bean);
    }

    @GetMapping("/page")
    @Operation(summary = "获得销量预警分页")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:query')")
    public CommonResult<PageResult<DeclineWarningRespVO>> getDeclineWarningPage(@Valid DeclineWarningPageReqVO pageReqVO) {
        PageResult<DeclineWarningDO> pageResult = declineWarningService.getDeclineWarningPage(pageReqVO);
        //查询出来子表放进去
        List<DeclineWarningDO> list = pageResult.getList();
        List<Long> ids = list.stream().map(DeclineWarningDO::getId).toList();
        Map<Long,List<DeclineWarningSubDO>> dataMap = declineWarningSubService.getDeclineWarningSubMap(ids);

        //将来这里转换成respVO
        List<DeclineWarningRespVO> result = BeanUtils.toBean(list, DeclineWarningRespVO.class);
        for (DeclineWarningRespVO item : result) {
            List<DeclineWarningSubDO> subDOList = dataMap.get(item.getId());
            if (subDOList!=null&&!subDOList.isEmpty()) {
                item.setSubRows(subDOList);
            }
        }

        return success(new PageResult<>(result, pageResult.getTotal()));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出销量预警 Excel")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:export')")
    
    public void exportDeclineWarningExcel(@Valid DeclineWarningPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DeclineWarningDO> list = declineWarningService.getDeclineWarningPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "销量预警.xls", "数据", DeclineWarningRespVO.class,
                        BeanUtils.toBean(list, DeclineWarningRespVO.class));
    }

    @PostMapping("/generate")
    @Operation(summary = "生成预警")
    @PreAuthorize("@ss.hasPermission('sale:decline-warning:create')")
    public CommonResult<String> createDeclineWarning(@Valid @RequestBody DeclineWarningGenerateReqVO generateReqVO) {
        return success(declineWarningService.generateDeclineWarning(generateReqVO));
    }

    @PostMapping("/submit-approved")
    @Operation(summary = "提交审核")
//    @PreAuthorize("@ss.hasPermission('sale:decline-warning:submit-approved')")
    @PermitAll
    public CommonResult<Boolean> submitApproved(@Valid @RequestBody DeclineWarningSubmitApprovedReqVO submitApprovedReqVO) {
        submitApprovedReqVO.setLoginUserId(getLoginUserId());
        declineWarningService.submitApproved(submitApprovedReqVO);
        return success(true);
    }


    @GetMapping("/test")
    @PreAuthorize("@ss.hasRole('super_admin')")
    public CommonResult<Boolean> test() throws IOException {
        ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();

        //设置消息类型
        jsonNode.put("msgtype", "text");
        jsonNode.put("touser", "0001E11000000007ND8O");
        jsonNode.put("agentid", 1000037);

        ObjectNode textNode = JsonNodeFactory.instance.objectNode();
        textNode.put("content", "测试消息");

        jsonNode.set("text", textNode);
        ObjectMapper op = new ObjectMapper();


        log.info("jsonNode:{}",op.writeValueAsString(jsonNode));

        WecomeMessageRespDTO wecomeMessageRespDTO = tencentMiniProgramAuthApi.sendWelcomeMessage(op.writeValueAsString(jsonNode));
        //转为json
        op.writeValueAsString(wecomeMessageRespDTO);
        System.out.println(op.writeValueAsString(wecomeMessageRespDTO));

        return success(true);
    }
}