package cn.iocoder.yudao.module.sale.service.customersalesdetail;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.api.openapi.BoenOpenApi;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.customersalesdetail.CustomerSalesDetailDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.customersalesdetail.CustomerSalesDetailMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.system.enums.openapi.BoenApiUrlEnums.CUSTOMER_SALES_SYNC_URL;

/**
 * 客户销售明细 Service 实现类
 *
 * @author 播恩超级管理员
 */
@Service
@Validated
@Slf4j
public class CustomerSalesDetailServiceImpl implements CustomerSalesDetailService {

    @Autowired
    @Lazy
    private CustomerSalesDetailService detailService;

    @Resource
    private CustomerSalesDetailMapper customerSalesDetailMapper;
    @Resource
    private BoenOpenApi boenOpenApi;

    @Override
    public Long createCustomerSalesDetail(CustomerSalesDetailSaveReqVO createReqVO) {
        // 插入
        CustomerSalesDetailDO customerSalesDetail = BeanUtils.toBean(createReqVO, CustomerSalesDetailDO.class);
        customerSalesDetailMapper.insert(customerSalesDetail);
        // 返回
        return customerSalesDetail.getId();
    }

    @Override
    public void updateCustomerSalesDetail(CustomerSalesDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateCustomerSalesDetailExists(updateReqVO.getId());
        // 更新
        CustomerSalesDetailDO updateObj = BeanUtils.toBean(updateReqVO, CustomerSalesDetailDO.class);
        customerSalesDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteCustomerSalesDetail(Long id) {
        // 校验存在
        validateCustomerSalesDetailExists(id);
        // 删除
        customerSalesDetailMapper.deleteById(id);
    }

    private void validateCustomerSalesDetailExists(Long id) {
        if (customerSalesDetailMapper.selectById(id) == null) {
            throw exception(CUSTOMER_SALES_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public CustomerSalesDetailDO getCustomerSalesDetail(Long id) {
        return customerSalesDetailMapper.selectById(id);
    }

    @Override
    public PageResult<CustomerSalesDetailDO> getCustomerSalesDetailPage(CustomerSalesDetailPageReqVO pageReqVO) {
        return customerSalesDetailMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<CustomerSalesDetailAnalysisRespVO> getCustomerSalesDetailAnalysisPage(CustomerSalesDetailPageReqVO pageReqVO) {
        Integer pageNo = pageReqVO.getPageNo();
        Integer pageSize = pageReqVO.getPageSize();


        List<CustomerSalesDetailAnalysisRespVO> collect = detailService.getCustomerSalesDetailAnalysisList(pageReqVO);

        //截取分页数据
        List<CustomerSalesDetailAnalysisRespVO> pageList = collect.subList((pageNo - 1) * pageSize, Math.min(pageNo * pageSize, collect.size()));
        int total = collect.size();


        return new PageResult<>(pageList, (long) total);
    }

    @Override
    public List<CustomerSalesDetailAnalysisRespVO> getCustomerSalesDetailAnalysisList(CustomerSalesDetailPageReqVO pageReqVO) {
        //  分析验证
        LocalDate dateTime = LocalDate.now();
        if (StringUtils.hasText(pageReqVO.getSaleDate())) {
            dateTime = LocalDate.parse(pageReqVO.getSaleDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }


        String currStartDate = dateTime.toString().substring(0, 7) + "-01";  // 本月初 yyyy-MM-01格式的日期
        String currEndDate = dateTime.toString().substring(0, 10);  // 今天 yyyy-MM-dd格式的日期

        String lastStartDate = dateTime.minusMonths(1).toString().substring(0, 7) + "-01";  // 上个月初 yyyy-MM-01格式的日期
        String lastEndDate = dateTime.minusMonths(1).toString().substring(0, 10);  // 上个月初 yyyy-MM-DD格式的日期


        // 上个月的数据
        pageReqVO.setStartDate(lastStartDate);
        pageReqVO.setEndDate(lastEndDate);
        List<CustomerSalesDetailDO> lastMonthList = customerSalesDetailMapper.selectListByMaxSale(pageReqVO);

        // 本月的数据
        pageReqVO.setStartDate(currStartDate);
        pageReqVO.setEndDate(currEndDate);
        List<CustomerSalesDetailDO> currMonthList = customerSalesDetailMapper.selectListByMaxSale(pageReqVO);
        //转为Map
        Map<String, CustomerSalesDetailDO> currMonthMap = new HashMap<>();
        for (CustomerSalesDetailDO customerSalesDetailDO : currMonthList) {
            currMonthMap.put(customerSalesDetailDO.getCustomerCode(), customerSalesDetailDO);
        }
        List<CustomerSalesDetailAnalysisRespVO> result = new ArrayList<>();

        for (CustomerSalesDetailDO customerSalesDetailDO : lastMonthList) {
            //转换相同的数据
            CustomerSalesDetailAnalysisRespVO respVO = BeanUtils.toBean(customerSalesDetailDO, CustomerSalesDetailAnalysisRespVO.class);
            respVO.setPreMonthSales(customerSalesDetailDO.getMonthlyCumulativeSales()); // 上个月的销量


            //对比本月的销量数据
            CustomerSalesDetailDO currMonth = currMonthMap.get(customerSalesDetailDO.getCustomerCode());
            if (currMonth == null) {
                //也就是在本次的对比期间内没有销售数据，也就是上一个月有，这个月没有
                //这样的话，下降率就是100%
                respVO.setDeclineRatio(1D);
                respVO.setCurrMonthSales(0D);
                respVO.setDeclineNum(customerSalesDetailDO.getMonthlyCumulativeSales()); // 下降数量等于上个月的全部销量
            } else {
                // 本月的销量
                respVO.setCurrMonthSales(currMonth.getMonthlyCumulativeSales());
                // 下降数量
                respVO.setDeclineNum(customerSalesDetailDO.getMonthlyCumulativeSales() - currMonth.getMonthlyCumulativeSales());
                // 下降率
                respVO.setDeclineRatio((customerSalesDetailDO.getMonthlyCumulativeSales() - currMonth.getMonthlyCumulativeSales()) / customerSalesDetailDO.getMonthlyCumulativeSales());
            }
            result.add(respVO);
        }

        //结果序列进行排序，下降率从大到小，并且要大于0.3
        List<CustomerSalesDetailAnalysisRespVO> collect = result.stream().filter(item -> item.getDeclineRatio() > 0.3)
                .sorted(Comparator.comparing(CustomerSalesDetailAnalysisRespVO::getZoneName)
                        .thenComparing(CustomerSalesDetailAnalysisRespVO::getAreaName)
                        .thenComparing(CustomerSalesDetailAnalysisRespVO::getDeclineRatio, Comparator.reverseOrder()))
                .collect(Collectors.toList());


        return collect;
    }

    /**
     * 同步客户销售明细
     *
     * @param syncReqVO
     */
    @Override
    public void syncCustomerSalesDetail(CustomerSalesDetailSyncReqVO syncReqVO) {
        ParameterizedTypeReference<List<CustomerSalesDetailDO>> typeReference = new ParameterizedTypeReference<>() {
        };
        //
        List<CustomerSalesDetailDO> list = boenOpenApi.sendRequest(
                typeReference,
                CUSTOMER_SALES_SYNC_URL.toString(),
                HttpMethod.GET.toString(),
                (Object) syncReqVO.getTimeRange());

        log.info("同步客户销售明细数据大小：{}", list.size());
        // 插入或者更新
        for (CustomerSalesDetailDO detailDO : list) {
            LambdaQueryWrapper<CustomerSalesDetailDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CustomerSalesDetailDO::getCustomerCode, detailDO.getCustomerCode()) // 客户编码
                    .eq(CustomerSalesDetailDO::getZoneCode, detailDO.getZoneCode()) // 战区编码
                    .eq(CustomerSalesDetailDO::getAreaCode, detailDO.getAreaCode()) // 大区编码
                    .eq(CustomerSalesDetailDO::getSaleDate, detailDO.getSaleDate()); // 年月日
            //todo 等会验证这里是否会自动校验删除字段
            CustomerSalesDetailDO customerSalesDetailDO = customerSalesDetailMapper.selectOne(queryWrapper);
            if (customerSalesDetailDO == null) {
                // 插入
                customerSalesDetailMapper.insert(detailDO);
            } else {
                // 更新
                detailDO.setId(customerSalesDetailDO.getId());
                customerSalesDetailMapper.updateById(detailDO);
            }
        }

    }

    @Override
//    @DS("nc65")
    public List<CustomerSalesDetailDO> getDoFromNc(CustomerSalesDetailSyncReqVO syncReqVO) {
        // 从NC获取数据
        return customerSalesDetailMapper.getDoFromNc(syncReqVO);
    }
}