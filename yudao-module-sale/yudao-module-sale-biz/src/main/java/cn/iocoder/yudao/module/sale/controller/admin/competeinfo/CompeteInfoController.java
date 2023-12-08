package cn.iocoder.yudao.module.sale.controller.admin.competeinfo;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.sale.controller.admin.competeinfosub.vo.CompeteInfoSubRespVO;
import cn.iocoder.yudao.module.sale.dal.dataobject.competeinfosub.CompeteInfoSubDO;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioncompeteinfo.ProductionCompeteInfoDO;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioninfo.ProductionInfoDO;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarsaleclass.ProductionMarsaleclassDO;
import cn.iocoder.yudao.module.sale.service.competeinfosub.CompeteInfoSubService;
import cn.iocoder.yudao.module.sale.service.productioncompeteinfo.ProductionCompeteInfoService;
import cn.iocoder.yudao.module.sale.service.productioninfo.ProductionInfoService;
import cn.iocoder.yudao.module.sale.service.productionmarbasclass.ProductionMarbasclassService;
import cn.iocoder.yudao.module.sale.service.productionmarsaleclass.ProductionMarsaleclassService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.sale.controller.admin.competeinfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.competeinfo.CompeteInfoDO;
import cn.iocoder.yudao.module.sale.service.competeinfo.CompeteInfoService;

@Tag(name = "管理后台 - 竞品信息")
@RestController
@RequestMapping("/sale/compete-info")
@Validated
public class CompeteInfoController {

    @Resource
    private CompeteInfoService competeInfoService;

    @Resource
    private CompeteInfoSubService competeInfoSubService;

    @PostMapping("/create")
    @Operation(summary = "创建竞品信息")
    @PreAuthorize("@ss.hasPermission('sale:compete-info:create')")
    public CommonResult<Long> createCompeteInfo(@Valid @RequestBody CompeteInfoSaveReqVO createReqVO) {
        return success(competeInfoService.createCompeteInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新竞品信息")
    @PreAuthorize("@ss.hasPermission('sale:compete-info:update')")
    public CommonResult<Boolean> updateCompeteInfo(@Valid @RequestBody CompeteInfoSaveReqVO updateReqVO) {
        competeInfoService.updateCompeteInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除竞品信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('sale:compete-info:delete')")
    public CommonResult<Boolean> deleteCompeteInfo(@RequestParam("id") Long id) {
        competeInfoService.deleteCompeteInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得竞品信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('sale:compete-info:query')")
    public CommonResult<CompeteInfoRespVO> getCompeteInfo(@RequestParam("id") Long id) {
        CompeteInfoRespVO competeInfoRespVO = competeInfoService.getCompeteInfo2(id);
        //带出物料名称等
        Map<Long,List<CompeteInfoSubRespVO>> dataMap =  competeInfoSubService.selectMapByCompeteInfoIdList(Collections.singletonList(id));

        if (CollUtil.isNotEmpty(dataMap)){
            List<CompeteInfoSubRespVO> subRows = dataMap.get(id);

            if (!CollUtil.isEmpty(subRows)){
                competeInfoRespVO.setSubRows(subRows);
                //如果子表不是为空的 subRows再按照changeDate排序，然后把priceChanges按照顺序加起来
                subRows = subRows.stream().filter(v-> v.getChangeDate().isBefore(LocalDateTime.now())).sorted(Comparator.comparing(CompeteInfoSubRespVO::getChangeDate)).collect(Collectors.toList());
                BigDecimal priceChanges = BigDecimal.ZERO;
                for (CompeteInfoSubRespVO subRow : subRows) {
                    priceChanges = priceChanges.add(subRow.getPriceChanges());
                }
                BigDecimal price = competeInfoRespVO.getPrice();
                BigDecimal currentPrice = price.add(priceChanges);
                competeInfoRespVO.setCurrentPrice(currentPrice);
            }else {
                //如果子表为空，那么当前价格就是初始价格
                competeInfoRespVO.setCurrentPrice(competeInfoRespVO.getPrice());
            }
        }

        return success(competeInfoRespVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得竞品信息分页")
    @PreAuthorize("@ss.hasPermission('sale:compete-info:query')")
    public CommonResult<PageResult<CompeteInfoRespVO>> getCompeteInfoPage(@Valid CompeteInfoPageReqVO pageReqVO) {
//        PageResult<CompeteInfoDO> pageResult = competeInfoService.getCompeteInfoPage(pageReqVO);


        //todo 带出 物料名称、产品线、工厂、当前价格
        PageResult<CompeteInfoRespVO> pageResult = competeInfoService.getCompeteInfoPage2(pageReqVO);

        //竞品信息子信息
        List<CompeteInfoRespVO> competeInfoRespVOList = pageResult.getList();
        List<Long> competeInfoIdList = competeInfoRespVOList.stream().map(CompeteInfoRespVO::getId).toList();

        Map<Long,List<CompeteInfoSubRespVO>> dataMap =  competeInfoSubService.selectMapByCompeteInfoIdList(competeInfoIdList);

        // 设置子表
        for (CompeteInfoRespVO competeInfoRespVO : competeInfoRespVOList) {
            List<CompeteInfoSubRespVO> subRows = Optional.ofNullable(dataMap.get(competeInfoRespVO.getId())).orElse(new ArrayList<>());

            if (!CollUtil.isEmpty(subRows)){
                competeInfoRespVO.setSubRows(subRows);

                //如果子表不是为空的 subRows再按照changeDate排序，然后把priceChanges按照顺序加起来
                subRows = subRows.stream().filter(v-> v.getChangeDate().isBefore(LocalDateTime.now())).sorted(Comparator.comparing(CompeteInfoSubRespVO::getChangeDate)).collect(Collectors.toList());
                BigDecimal priceChanges = BigDecimal.ZERO;
                for (CompeteInfoSubRespVO subRow : subRows) {
                    priceChanges = priceChanges.add(subRow.getPriceChanges());
                }
                BigDecimal price = competeInfoRespVO.getPrice();
                BigDecimal currentPrice = price.add(priceChanges);
                competeInfoRespVO.setCurrentPrice(currentPrice);
            }else {
                //如果子表为空，那么当前价格就是初始价格
                competeInfoRespVO.setCurrentPrice(competeInfoRespVO.getPrice());
            }


        }



        pageResult.setList(competeInfoRespVOList);

        return success(pageResult);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出竞品信息 Excel")
    @PreAuthorize("@ss.hasPermission('sale:compete-info:export')")
    @OperateLog(type = EXPORT)
    public void exportCompeteInfoExcel(@Valid CompeteInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CompeteInfoDO> list = competeInfoService.getCompeteInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "竞品信息.xls", "数据", CompeteInfoRespVO.class,
                        BeanUtils.toBean(list, CompeteInfoRespVO.class));
    }

}