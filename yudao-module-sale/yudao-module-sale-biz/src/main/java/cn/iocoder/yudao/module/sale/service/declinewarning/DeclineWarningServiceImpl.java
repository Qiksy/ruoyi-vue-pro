package cn.iocoder.yudao.module.sale.service.declinewarning;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.operatelog.core.util.OperateLogUtils;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo.CustomerSalesDetailAnalysisRespVO;
import cn.iocoder.yudao.module.sale.controller.admin.customersalesdetail.vo.CustomerSalesDetailPageReqVO;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarningsub.DeclineWarningSubDO;
import cn.iocoder.yudao.module.sale.dal.mysql.declinewarningsub.DeclineWarningSubMapper;
import cn.iocoder.yudao.module.sale.service.customersalesdetail.CustomerSalesDetailService;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.sale.controller.admin.declinewarning.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarning.DeclineWarningDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.sale.dal.mysql.declinewarning.DeclineWarningMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.sale.enums.ErrorCodeConstants.*;

/**
 * 销量预警 Service 实现类
 *
 * @author 播恩超级管理员
 */
@Service
@Validated
@Slf4j
public class DeclineWarningServiceImpl implements DeclineWarningService {

    @Resource
    private DeclineWarningMapper declineWarningMapper;

    @Resource
    private DeclineWarningSubMapper declineWarningSubMapper;

    @Resource
    private CustomerSalesDetailService detailService;

    @Resource
    private DeptApi deptApi;

    @Resource
    private BpmProcessInstanceApi processInstanceApi;

    /**
     * 流程的key
     */
    public static final String PROCESS_KEY = "sale_decline";

    @Override
    public Long createDeclineWarning(DeclineWarningSaveReqVO createReqVO) {
        // 插入
        DeclineWarningDO declineWarning = BeanUtils.toBean(createReqVO, DeclineWarningDO.class);
        declineWarningMapper.insert(declineWarning);
        // 返回
        return declineWarning.getId();
    }

    @Override
    public void updateDeclineWarning(DeclineWarningSaveReqVO updateReqVO) {
        // 校验存在
        validateDeclineWarningExists(updateReqVO.getId());
        // 更新
        DeclineWarningDO updateObj = BeanUtils.toBean(updateReqVO, DeclineWarningDO.class);
        declineWarningMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeclineWarning(Long id) {
        // 校验存在
        validateDeclineWarningExists(id);
        // 删除
        declineWarningMapper.deleteById(id);
    }

    private void validateDeclineWarningExists(Long id) {
        if (declineWarningMapper.selectById(id) == null) {
            throw exception(DECLINE_WARNING_NOT_EXISTS);
        }
    }

    @Override
    public DeclineWarningDO getDeclineWarning(Long id) {
        return declineWarningMapper.selectById(id);
    }

    @Override
    public PageResult<DeclineWarningDO> getDeclineWarningPage(DeclineWarningPageReqVO pageReqVO) {
        return declineWarningMapper.selectPage(pageReqVO);
    }

    /**
     * 根据销售明细生成预警信息
     *
     * @param generateReqVO
     * @return 返回生成是否成功的信息
     */
    @Override
    public String generateDeclineWarning(DeclineWarningGenerateReqVO generateReqVO) {
        //todo 获取对应的时间，然后分析 生成主表，然后生成子表信息，插入数据库
        String saleDate = getSaleDate(generateReqVO);

        //获取完数据之后
        CustomerSalesDetailPageReqVO reqVO = new CustomerSalesDetailPageReqVO();
        reqVO.setSaleDate(saleDate);
        List<CustomerSalesDetailAnalysisRespVO> warningSourceList = detailService.getCustomerSalesDetailAnalysisList(reqVO);

        //判断 大区、战区是否拥有对应的负责人，如果没有，则抛出异常信息
//        checkDeptLeader(warningSourceList);

        //主表信息
        List<DeclineWarningDO> declineWarningList = new ArrayList<>();
        // 子表信息
        Map<String, List<DeclineWarningSubDO>> declineWarningSubMap = new HashMap<>();

        //填充进主子表信息
        generateDO(warningSourceList, saleDate, declineWarningList, declineWarningSubMap);

        //判断之子表是否有数据，有则不生成，无则生成

        int total = declineWarningList.size();
        int successCount = 0;

        for (DeclineWarningDO warningDO : declineWarningList) {
            LambdaQueryWrapperX<DeclineWarningDO> queryWrapperX = new LambdaQueryWrapperX<>();
            queryWrapperX.eq(DeclineWarningDO::getCompeteTime, warningDO.getCompeteTime())
                    .eq(DeclineWarningDO::getAreaCode, warningDO.getAreaCode());
            boolean exists = declineWarningMapper.exists(queryWrapperX);
            if (exists) {
                log.info("当前时间：{}，大区：{}，已经生成过预警信息，无需再次生成", warningDO.getCompeteTime(), warningDO.getAreaName());
                continue;
            }

            //插入主表信息
            declineWarningMapper.insert(warningDO);

            //插入子表
            List<DeclineWarningSubDO> declineWarningSubDOS = declineWarningSubMap.get(warningDO.getAreaCode());
            declineWarningSubDOS.forEach(subDO -> {
                subDO.setParentId(warningDO.getId());
                declineWarningSubMapper.insert(subDO);
            });
            successCount++;
        }
        //ps 这个时候可以选择创建审批流程，只是暂时不用
        StringBuffer sb = new StringBuffer();
        sb.append("共有").append(total).append("个大区，成功生成").append(successCount).append("个大区的预警信息");
        return sb.toString();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean submitApproved(DeclineWarningSubmitApprovedReqVO submitApprovedReqVO) {
        // 获取ids，然后批量创建流程，然后更新流程id到主表中
        List<Long> ids = submitApprovedReqVO.getIds();

        for (Long id : ids) {
            DeclineWarningDO declineWarningDO = declineWarningMapper.selectById(id);

            // 发起 BPM 流程
            Map<String, Object> processInstanceVariables = new HashMap<>();
            processInstanceVariables.put("zoneCode", Long.valueOf(declineWarningDO.getZoneCode()));
            processInstanceVariables.put("areaCode", Long.valueOf(declineWarningDO.getAreaCode()));

            String processInstanceId = processInstanceApi.createProcessInstance(submitApprovedReqVO.getLoginUserId(),
                    new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                            .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(declineWarningDO.getId())));

            //会写流程id到主表中
            declineWarningDO.setProcessInstanceId(processInstanceId);
            declineWarningMapper.updateById(declineWarningDO);

            log.info("插入流程实例成功，流程实例id为：{}，业务id为：{}", processInstanceId,id);
        }
        return true;
    }

    /**
     * 判断大区、战区是否拥有对应的负责人，如果没有，则抛出异常信息
     *
     * @param warningSourceList
     */
    private void checkDeptLeader(List<CustomerSalesDetailAnalysisRespVO> warningSourceList) {
        //战区编码
        List<Long> zoneCodeList = warningSourceList.stream().map(CustomerSalesDetailAnalysisRespVO::getZoneCode).map(Long::valueOf).distinct().toList();
        //大区编码
        List<Long> areaCodeList = warningSourceList.stream().map(CustomerSalesDetailAnalysisRespVO::getAreaCode).map(Long::valueOf).distinct().toList();

        // 合并两个list
        List<Long> deptIdList = new ArrayList<>();
        deptIdList.addAll(zoneCodeList);
        deptIdList.addAll(areaCodeList);

        List<DeptRespDTO> notExistsLeaderDepts = deptApi.getNotExistsLeaderDepts(deptIdList);
        if (!notExistsLeaderDepts.isEmpty()) {
            String errorMessage = "以下部门没有负责人，请先设置负责人：" + notExistsLeaderDepts.stream().map(DeptRespDTO::getName).collect(Collectors.joining(","));
            throw exception(new ErrorCode(88088, errorMessage));
        }
    }

    /**
     * @param warningSourceList    销量下降分析的源数据
     * @param saleDate             对比的时间
     * @param declineWarningList   主表信息
     * @param declineWarningSubMap 子表信息
     */
    private void generateDO(List<CustomerSalesDetailAnalysisRespVO> warningSourceList, String saleDate, List<DeclineWarningDO> declineWarningList, Map<String, List<DeclineWarningSubDO>> declineWarningSubMap) {
        // warningSourceList按照大区进行分组
        Map<String, List<CustomerSalesDetailAnalysisRespVO>> areaMap = warningSourceList.stream().collect(Collectors.groupingBy(CustomerSalesDetailAnalysisRespVO::getAreaCode));

        for (Map.Entry<String, List<CustomerSalesDetailAnalysisRespVO>> stringListEntry : areaMap.entrySet()) {
            // 每个大区一条预警信息，也就是主表
            String areaCode = stringListEntry.getKey();
            List<CustomerSalesDetailAnalysisRespVO> subSourceList = stringListEntry.getValue();
            CustomerSalesDetailAnalysisRespVO tempBaseVO = subSourceList.get(0); // 用于获取基础信息

            // 生成主表信息
            DeclineWarningDO mainDO = new DeclineWarningDO();
            mainDO.setZoneCode(tempBaseVO.getZoneCode());
            mainDO.setZoneName(tempBaseVO.getZoneName());
            mainDO.setAreaCode(areaCode);
            mainDO.setAreaName(tempBaseVO.getAreaName());
            mainDO.setCompeteTime(saleDate);

            int custCount = subSourceList.size(); // 上个月销量下降超过30%的客户数量cust_count
            double totalDeclineNum = subSourceList.stream().mapToDouble(CustomerSalesDetailAnalysisRespVO::getDeclineNum).sum() / 1000;  //下降的数量  total_decline_num
            double totalPreMonthSales = subSourceList.stream().mapToDouble(CustomerSalesDetailAnalysisRespVO::getPreMonthSales).sum() / 1000; //上个月的销量 total_pre_month_sales
            double totalCurrMonthSales = subSourceList.stream().mapToDouble(CustomerSalesDetailAnalysisRespVO::getCurrMonthSales).sum() / 1000; //本月的销量 total_curr_month_sales
            double totalDeclineRatio = totalDeclineNum / totalPreMonthSales; //下降的比例 total_decline_ratio
            mainDO.setCustCount(custCount);
            mainDO.setTotalDeclineNum(totalDeclineNum);
            mainDO.setTotalPreMonthSales(totalPreMonthSales);
            mainDO.setTotalCurrMonthSales(totalCurrMonthSales);
            mainDO.setTotalDeclineRatio(totalDeclineRatio);


            // 统计信息，生成总结
            mainDO.setSummarize(generateSummarize(tempBaseVO.getAreaName(), saleDate, custCount, totalDeclineNum, totalPreMonthSales, totalCurrMonthSales, totalDeclineRatio));

            declineWarningList.add(mainDO);

            // 生成子表信息
            List<DeclineWarningSubDO> subDOList = new ArrayList<>();
            for (CustomerSalesDetailAnalysisRespVO subSourceDO : subSourceList) {
                DeclineWarningSubDO subDO = BeanUtils.toBean(subSourceDO, DeclineWarningSubDO.class);
                subDOList.add(subDO);
            }
            declineWarningSubMap.put(areaCode, subDOList);
        }
    }

    /**
     * 获取销售时间
     *
     * @param generateReqVO
     * @return
     */
    @NotNull
    private static String getSaleDate(DeclineWarningGenerateReqVO generateReqVO) {
        String saleDate;
        int type = generateReqVO.getType();
        LocalDate now = LocalDate.now();
        if (type == 0) {
            saleDate = now.getYear() + "-" + now.getMonthValue() + "-10";
            if (now.getDayOfMonth() < 10) {
                throw exception(DECLINE_WARNING_GENERATE_TIME_ERROR);
            }
        } else if (type == 1) {
            saleDate = now.getYear() + "-" + now.getMonthValue() + "-20";
            if (now.getDayOfMonth() < 20) {
                throw exception(DECLINE_WARNING_GENERATE_TIME_ERROR);
            }
        } else if (type == 2) {
            saleDate = now.getYear() + "-" + now.getMonthValue() + "-" + now.lengthOfMonth();
            if (now.getDayOfMonth() < now.lengthOfMonth()) {
                throw exception(DECLINE_WARNING_GENERATE_TIME_ERROR);
            }
        } else {
            saleDate = generateReqVO.getDate();

            LocalDate date = LocalDate.parse(saleDate);
            // 如果now还没达到date，那么抛出异常
            if (now.isBefore(date)) {
                throw exception(DECLINE_WARNING_GENERATE_TIME_ERROR);
            }

            int dayOfMonth = date.getDayOfMonth();
            if (dayOfMonth != 10 && dayOfMonth != 20 && dayOfMonth != date.lengthOfMonth()) {
                throw exception(DECLINE_WARNING_GENERATE_TIME_ERROR2);
            }
        }
        //时间还是没有的话，就说明参数错误，抛出异常
        if (!StringUtils.hasText(saleDate)) {
            throw exception(DECLINE_WARNING_GENERATE_TIME_PARAMS_ERROR);
        }
        return saleDate;
    }

    /**
     * 统计信息，生成总结
     *
     * @param areaName            大区名称
     * @param saleDate            销售时间
     * @param custCount           客户数量
     * @param totalDeclineNum     下降的数量
     * @param totalPreMonthSales  上个月的销量
     * @param totalCurrMonthSales 本月的销量
     * @param totalDeclineRatio   下降的比例
     * @return
     */
    private String generateSummarize(String areaName, String saleDate, int custCount, Double totalDeclineNum, Double totalPreMonthSales, Double totalCurrMonthSales, Double totalDeclineRatio) {

        //下降比例转为百分比
        totalDeclineRatio = totalDeclineRatio * 100;
        String totalDeclineRatioStr = String.format("%.2f", totalDeclineRatio);

        StringBuffer sb = new StringBuffer();
        sb.append("截至至").append(saleDate).append("，");
        sb.append(areaName).append("共有").append(custCount).append("个客户销量下降超过30%。这些用户");
        sb.append("上个月总销量为").append(totalPreMonthSales).append("吨，");
        sb.append("本月总销量为").append(totalCurrMonthSales).append("吨，");
        sb.append("总下降量为").append(totalDeclineNum).append("吨，");
        sb.append("总下降比例为").append(totalDeclineRatioStr).append("%。");


        return sb.toString();
    }
}