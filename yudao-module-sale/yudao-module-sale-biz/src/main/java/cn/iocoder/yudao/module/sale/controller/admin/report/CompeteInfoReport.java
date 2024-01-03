package cn.iocoder.yudao.module.sale.controller.admin.report;


import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.sale.controller.admin.competeinfo.vo.CompeteInfoPageReqVO;
import cn.iocoder.yudao.module.sale.controller.admin.competeinfo.vo.CompeteInfoRespVO;
import cn.iocoder.yudao.module.sale.controller.admin.competeinfosub.vo.CompeteInfoSubRespVO;
import cn.iocoder.yudao.module.sale.service.competeinfo.CompeteInfoService;
import cn.iocoder.yudao.module.sale.service.competeinfosub.CompeteInfoSubService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Resource;
import org.jeecg.modules.jmreport.api.data.IDataSetFactory;
import org.jeecg.modules.jmreport.desreport.model.JmPage;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用来提供积木报表的数据
 * @author linr
 * @since 2024/1/2 16:18
 */
@Component(value = "competeInfoReport")
public class CompeteInfoReport implements IDataSetFactory {

    @Resource
    private CompeteInfoService competeInfoService;

    @Resource
    private CompeteInfoSubService competeInfoSubService;

    /**
     * 不分页时返回list
     * @param param 参数 包括浏览器地址栏 和 查询条件
     * @return
     */
    @Override
    public List<Map<String, Object>> createData(Map<String, Object> param) {
        CompeteInfoPageReqVO pageReqVO = new CompeteInfoPageReqVO();
        pageReqVO.setPageSize(-1); // 不分页

        //带出 物料名称、产品线、工厂、当前价格
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
                // 如果当前价格不为空，那么当前单价就是当前价格除以规格
                if (currentPrice != null && competeInfoRespVO.getSpec() != null){
                    BigDecimal currentUnitPrice = currentPrice.divide(competeInfoRespVO.getSpec(),2,BigDecimal.ROUND_HALF_UP);
                    competeInfoRespVO.setCurrentUnitPrice(currentUnitPrice);
                }
            }else {
                //如果子表为空，那么当前价格就是初始价格
                competeInfoRespVO.setCurrentPrice(competeInfoRespVO.getPrice());
                //如果子表为空，那么当前单价就是初始单价
                competeInfoRespVO.setCurrentUnitPrice(competeInfoRespVO.getUnitPrice());
            }
        }

//        competeInfoRespVOList;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        TypeReference<List<Map<String, Object>>> typeReference = new TypeReference<List<Map<String, Object>>>() {};

        return objectMapper.convertValue(competeInfoRespVOList, typeReference);
    }

    /**
     * 分页时返回 JmPage 并且参数param里会传入pageNo, pageSize
     * @param param 参数 包括浏览器地址栏 和 查询条件
     * @return
     */
    @Override
    public JmPage<List<Map<String,Object>>> createPageData(Map<String, Object> param) {
        return null;
    }
}
