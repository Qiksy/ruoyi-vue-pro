package cn.iocoder.yudao.module.strain.service.outboundapplication;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.bpm.api.task.BpmProcessInstanceApi;
import cn.iocoder.yudao.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.iocoder.yudao.module.bpm.enums.task.BpmTaskStatusEnum;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningRespVO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo.FreezingTubeStockInfoDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.specimen.SpecimenInfoDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundsubapplication.OutboundSubApplicationDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockinfo.FreezingTubeStockInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry.SpecimenInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.outboundsubapplication.OutboundSubApplicationMapper;
import cn.iocoder.yudao.module.strain.enums.InventoryStatisEnum;
import cn.iocoder.yudao.module.strain.enums.OutboundTypeConstants;
import cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry.SpecimenInfoService;
import cn.iocoder.yudao.module.system.api.permission.PermissionApi;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import cn.iocoder.yudao.module.system.enums.permission.RoleCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.strain.controller.admin.outboundapplication.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundapplication.OutboundApplicationDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.strain.dal.mysql.outboundapplication.OutboundApplicationMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 出库申请 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OutboundApplicationServiceImpl implements OutboundApplicationService {

    @Resource
    private OutboundApplicationMapper outboundApplicationMapper;

    @Resource
    private OutboundSubApplicationMapper subApplicationMapper;

    @Resource
    private SpecimenInfoService specimenInfoService;


    @Resource
    private SpecimenInfoMapper specimenInfoMapper;


    @Resource
    private FreezingTubeStockInfoMapper stockInfoMapper;

    @Resource
    private AdminUserApi adminUserApi;


    @Resource
    private PermissionApi permissionApi;


    @Resource
    private BpmProcessInstanceApi processInstanceApi;


    //流程模型的key
    public static final String OUTBOUND_PROCESS_KEY = "strain-outbound";


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOutboundApplication(OutboundApplicationCreateReqVO createReqVO) {
        return getSelf().saveApplication(createReqVO, "save");
    }

    @Override
    public void updateOutboundApplication(OutboundApplicationCreateReqVO updateReqVO) {
        // 校验存在
        validateOutboundApplicationExists(updateReqVO.getId());
        // 更新
        OutboundApplicationDO updateObj = BeanUtils.toBean(updateReqVO, OutboundApplicationDO.class);
        outboundApplicationMapper.updateById(updateObj);

        //删除子表
        subApplicationMapper.delete(OutboundSubApplicationDO::getParentId, updateReqVO.getId());

        //重新插入子表
        List<OutboundSubApplicationDO> subApplicationDOS = BeanUtils.toBean(updateReqVO.getSubList(), OutboundSubApplicationDO.class);
        //设置子表的信息
        for (OutboundSubApplicationDO item : subApplicationDOS) {
            item.setParentId(updateReqVO.getId());

            //判断主表的类型，然后设置回库状态
            if (updateObj.getType().equals(OutboundTypeConstants.NORMAL)
                    || updateReqVO.getType().equals(OutboundTypeConstants.REGENERATION)) {
                // 正常出库 传代/复壮都要正常回库
                item.setResave(false); //需要回库
            } else if (updateReqVO.getType().equals(OutboundTypeConstants.CONSUME)
                    || updateReqVO.getType().equals(OutboundTypeConstants.DESTROY)) {
                //消耗出库
                item.setResave(true);
            }

        }


        subApplicationMapper.insertBatch(subApplicationDOS);
    }


    /**
     * 更新并且提交
     * 1. 先删除子表，再重新插入
     * 2. 创建流程实例
     * 3. 更新流程实例和状态到主表
     *
     * @param updateReqVO 更新信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAndSubmitOutboundApplication(OutboundApplicationCreateReqVO updateReqVO) {


        // 1. 先删除子表，再重新插入


        // 校验存在
        validateOutboundApplicationExists(updateReqVO.getId());

        //删除子表
        subApplicationMapper.delete(OutboundSubApplicationDO::getParentId, updateReqVO.getId());
        //重新插入子表
        List<OutboundSubApplicationDO> subApplicationDOS = BeanUtils.toBean(updateReqVO.getSubList(), OutboundSubApplicationDO.class);
        //设置子表的parent_id
        for (OutboundSubApplicationDO item : subApplicationDOS) {
            item.setParentId(updateReqVO.getId());

            //判断主表的类型，然后设置是否需要回库
            if (updateReqVO.getType().equals(OutboundTypeConstants.NORMAL)
                    || updateReqVO.getType().equals(OutboundTypeConstants.REGENERATION)) {
                // 正常出库 传代/复壮都要正常回库
                item.setResave(false);
            } else if (updateReqVO.getType().equals(OutboundTypeConstants.CONSUME)
                    || updateReqVO.getType().equals(OutboundTypeConstants.DESTROY)) {
                //消耗出库
                item.setResave(true);
            }

        }

        // 校验是否存在相同的明细正在处理中
        validateSubIsExistsProcess(subApplicationDOS);
        // 重新插入这个子表
        subApplicationMapper.insertBatch(subApplicationDOS);


        //2.创建流程实例

        // 流程变量
        Map<String, Object> processInstanceVariables = new HashMap<>();
        String processInstanceId = processInstanceApi.createProcessInstance(SecurityFrameworkUtils.getLoginUserId(),
                new BpmProcessInstanceCreateReqDTO()
                        .setProcessDefinitionKey(OUTBOUND_PROCESS_KEY)
                        .setBusinessKey(updateReqVO.getId().toString())
                        .setVariables(processInstanceVariables)
        );


        // 主表处理

        OutboundApplicationDO updateObj = BeanUtils.toBean(updateReqVO, OutboundApplicationDO.class);

        //更新状态
        updateObj.setApproResult(BpmTaskStatusEnum.RUNNING.getStatus().toString());
        // 更新流程实例id
        updateObj.setProcessInstanceId(processInstanceId);
        outboundApplicationMapper.updateById(updateObj);
    }


    @Override
    public void deleteOutboundApplication(Long id) {
        // 校验存在和是否自由态
        validateOutboundApplicationStatus(id);

        // 删除 主表
        outboundApplicationMapper.deleteById(id);
        //删除子表
        subApplicationMapper.delete(OutboundSubApplicationDO::getParentId, id);
    }

    private void validateOutboundApplicationStatus(Long id) {
        OutboundApplicationDO outboundApplicationDO = outboundApplicationMapper.selectById(id);
        if (outboundApplicationDO == null) {
            throw exception(OUTBOUND_APPLICATION_NOT_EXISTS);
        }
        if (!BpmTaskStatusEnum.WAIT.getStatus().toString().equals(outboundApplicationDO.getApproResult())) {
            throw exception(OUTBOUND_APPLICATION_STATUS_NOT_UN_START);
        }
    }

    private void validateOutboundApplicationExists(Long id) {
        if (outboundApplicationMapper.selectById(id) == null) {
            throw exception(OUTBOUND_APPLICATION_NOT_EXISTS);
        }
    }

    @Override
    public OutboundApplicationDO getOutboundApplication(Long id) {
        return outboundApplicationMapper.selectById(id);
    }

    /**
     * 获取主子表的所有的数据
     *
     * @param id 主键
     * @return 单独的表的数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OutboundApplicationRespVO getOutboundApplicationVO(Long id) {
        //获取主表数据
        OutboundApplicationDO outboundApplicationDO = outboundApplicationMapper.selectById(id);
        OutboundApplicationRespVO respVO = BeanUtils.toBean(outboundApplicationDO, OutboundApplicationRespVO.class);

        List<OutboundSubApplicationDO> subApplicationDOS = subApplicationMapper.selectList("parent_id", id);

        //获取子表数据
        List<OutboundApplicationSubRespVO> subRespVOS = BeanUtils.toBean(subApplicationDOS, OutboundApplicationSubRespVO.class);


        //获取槽位id
        List<Long> stockIds = subRespVOS.stream().map(OutboundApplicationSubRespVO::getStockId).filter(Objects::nonNull).toList();

        //根据槽位id获取位置信息并设置到
        Map<Long, String> stockPositionStrMap = specimenInfoService.getStockPositionStrMap(stockIds);
        if (!stockPositionStrMap.isEmpty()) {
            for (OutboundApplicationSubRespVO subRespVO : subRespVOS) {
                subRespVO.setPositionStr(stockPositionStrMap.get(subRespVO.getStockId()));
            }
        }
        //还需要设置保存人

        List<Long> specimenIds = subRespVOS.stream().map(OutboundApplicationSubRespVO::getSpecimenId).filter(Objects::nonNull).toList();
        //直接获取样品的saveBy
        List<SpecimenInfoDO> specimen = specimenInfoMapper.selectList("id", specimenIds);
        Set<Long> collect = specimen.stream().map(SpecimenInfoDO::getSaveBy).filter(Objects::nonNull).collect(Collectors.toSet());
        //用户信息
        List<AdminUserRespDTO> userList = adminUserApi.getUserList(collect);

        Map<Long, String> userMap = userList.stream().collect(Collectors.toMap(AdminUserRespDTO::getId, AdminUserRespDTO::getNickname));
        Map<Long, Long> specimenMap = specimen.stream().collect(Collectors.toMap(SpecimenInfoDO::getId, SpecimenInfoDO::getSaveBy));

        for (OutboundApplicationSubRespVO subRespVO : subRespVOS) {
            Long specimenId = subRespVO.getSpecimenId();
            Long userId = specimenMap.get(specimenId);
            String userName = userMap.get(userId);
            subRespVO.setSaveByName(userName);
        }

        respVO.setSubList(subRespVOS);
        return respVO;
    }

    @Override
    public PageResult<OutboundApplicationDO> getOutboundApplicationPage(OutboundApplicationPageReqVO pageReqVO) {
        return outboundApplicationMapper.selectPage(pageReqVO);
    }

    /**
     * 获取自己申请的出库申请
     *
     * @param pageReqVO 分页查询
     * @return 出库申请分页
     */
    @Override
    public PageResult<OutboundApplicationRespVO> getOutboundApplicationPageSelf(OutboundApplicationPageReqVO pageReqVO) {
        //权限管理

        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();

        PageResult<OutboundApplicationDO> pageResult;

        if (permissionApi.hasAnyRoles(loginUserId, RoleCodeEnum.SUPER_ADMIN.getCode())) {
            // 超级管理员有所有的权限
            pageResult = outboundApplicationMapper.selectPage(pageReqVO);
        } else {
            pageResult = outboundApplicationMapper.selectPage2(pageReqVO, loginUserId);
        }


        PageResult<OutboundApplicationRespVO> finalPageResult = BeanUtils.toBean(pageResult, OutboundApplicationRespVO.class);

        for (OutboundApplicationRespVO parentVo : finalPageResult.getList()) {
            List<OutboundSubApplicationDO> subApplicationDOS = subApplicationMapper.selectList("parent_id", parentVo.getId());
            //获取子表数据
            List<OutboundApplicationSubRespVO> subRespVOS = BeanUtils.toBean(subApplicationDOS, OutboundApplicationSubRespVO.class);


            //获取槽位id
            List<Long> stockIds = subRespVOS.stream().map(OutboundApplicationSubRespVO::getStockId).filter(Objects::nonNull).toList();

            //根据槽位id获取位置信息并设置到
            Map<Long, String> stockPositionStrMap = specimenInfoService.getStockPositionStrMap(stockIds);
            if (!stockPositionStrMap.isEmpty()) {
                for (OutboundApplicationSubRespVO subRespVO : subRespVOS) {
                    subRespVO.setPositionStr(stockPositionStrMap.get(subRespVO.getStockId()));
                }
            }
            //还需要设置保存人

            List<Long> specimenIds = subRespVOS.stream().map(OutboundApplicationSubRespVO::getSpecimenId).filter(Objects::nonNull).toList();
            //直接获取样品的saveBy
            List<SpecimenInfoDO> specimen = specimenInfoMapper.selectList("id", specimenIds);
            Set<Long> collect = specimen.stream().map(SpecimenInfoDO::getSaveBy).filter(Objects::nonNull).collect(Collectors.toSet());
            //用户信息
            List<AdminUserRespDTO> userList = adminUserApi.getUserList(collect);

            Map<Long, String> userMap = userList.stream().collect(Collectors.toMap(AdminUserRespDTO::getId, AdminUserRespDTO::getNickname));
            Map<Long, Long> specimenMap = specimen.stream().collect(Collectors.toMap(SpecimenInfoDO::getId, SpecimenInfoDO::getSaveBy));

            for (OutboundApplicationSubRespVO subRespVO : subRespVOS) {
                Long specimenId = subRespVO.getSpecimenId();
                Long userId = specimenMap.get(specimenId);
                String userName = userMap.get(userId);
                subRespVO.setSaveByName(userName);
            }

            parentVo.setSubList(subRespVOS);


            //判断parenVo 是否全部重新入库了
            parentVo.setAllResave(subRespVOS.stream().anyMatch(vo -> !vo.isResave()));
        }


        return finalPageResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAndApplyOutboundApplication(OutboundApplicationCreateReqVO createReqVO) {
        return getSelf().saveApplication(createReqVO, "apply");
    }

    @Transactional(rollbackFor = Exception.class)
    public Long saveApplication(OutboundApplicationCreateReqVO createReqVO, String type) {
        //保存主子表数据
        OutboundApplicationDO outboundApplicationDO = BeanUtils.toBean(createReqVO, OutboundApplicationDO.class);

        //设置保存人姓名
        Long userId = Objects.requireNonNull(SecurityFrameworkUtils.getLoginUser()).getId();
        String nickname = adminUserApi.getUser(userId).getNickname();
        outboundApplicationDO.setApplicant(nickname);


        //设置编码
        long id = DefaultIdentifierGenerator.getInstance().nextId(outboundApplicationDO);

        //如果编码为空或者id为空的情况下才需要设置编码
        if (StringUtils.isBlank(outboundApplicationDO.getCode()) || outboundApplicationDO.getId() == null) {
            String codePrefix = "CK";
            //获取当前时间
            String now = DateUtil.format(new Date(), "yyyyMMdd");
            //查询数据库已经有的流水号
            LambdaQueryWrapper<OutboundApplicationDO> queryWrapper = new LambdaQueryWrapper<OutboundApplicationDO>()
                    .likeRight(OutboundApplicationDO::getCode, codePrefix + now)
                    .orderByDesc(OutboundApplicationDO::getCode)
                    .eq(OutboundApplicationDO::getDeleted, 0)
                    .last("LIMIT 1");
            OutboundApplicationDO tempDO = outboundApplicationMapper.selectOne(queryWrapper);
            //如果没有查询到数据，说明是当天第一条数据
            if (tempDO == null) {
                outboundApplicationDO.setCode(codePrefix + now + "001");
            } else {
                //如果查询到数据，说明不是当天第一条数据
                String code = tempDO.getCode();
                String suffix = code.substring(code.length() - 3);
                int num = Integer.parseInt(suffix) + 1;
                String newSuffix = String.format("%03d", num);
                outboundApplicationDO.setCode(codePrefix + now + newSuffix);
            }

            if (outboundApplicationDO.getId() == null) {
                outboundApplicationDO.setId(id);
            } else {
                id = outboundApplicationDO.getId();
            }

        }

        //设置审批状态
        //设置主表状态为处理中
        if (type.equals("apply")) {
            //需要设置审批状态为处理中
            outboundApplicationDO.setApproResult(BpmTaskStatusEnum.RUNNING.getStatus().toString());
            //创建流程实例
            HashMap<String, Object> processVariables = new HashMap<>();
            String processInstanceId = processInstanceApi.createProcessInstance(SecurityFrameworkUtils.getLoginUserId(),
                    new BpmProcessInstanceCreateReqDTO()
                            .setBusinessKey(String.valueOf(id))
                            .setProcessDefinitionKey(OUTBOUND_PROCESS_KEY)
                            .setVariables(processVariables)
            );
            // 设置application
            outboundApplicationDO.setProcessInstanceId(processInstanceId);
        } else if (type.equals("save")) {
            //需要设置审批状态为未开始
            outboundApplicationDO.setApproResult(BpmTaskStatusEnum.WAIT.getStatus().toString());
        }

        //插入数据
        outboundApplicationMapper.insert(outboundApplicationDO);


        List<OutboundSubApplicationDO> subApplicationDOS = BeanUtils.toBean(createReqVO.getSubList(), OutboundSubApplicationDO.class);
        if (subApplicationDOS == null || subApplicationDOS.isEmpty()) {
            // 如果是空的或者为null，则直接返回就好了
            return outboundApplicationDO.getId();
        }


        //设置子表的父id
        for (OutboundSubApplicationDO item : subApplicationDOS) {
            item.setParentId(outboundApplicationDO.getId());


            //判断主表的类型，然后设置回库状态
            if (outboundApplicationDO.getType().equals(OutboundTypeConstants.NORMAL)
                    || outboundApplicationDO.getType().equals(OutboundTypeConstants.REGENERATION)) {
                // 正常出库 传代/复壮都要正常回库
                item.setResave(false); //需要回库
            } else if (outboundApplicationDO.getType().equals(OutboundTypeConstants.CONSUME)
                    || outboundApplicationDO.getType().equals(OutboundTypeConstants.DESTROY)) {
                //消耗出库
                item.setResave(true);
            }
        }


        // 如果是审批的逻辑，需要检查是否已经存在相同的明细正在处理中

        validateSubIsExistsProcess(subApplicationDOS);


        //后续需要出库的时候，需要扫码核验，否则不可以出库
        subApplicationMapper.insertBatch(subApplicationDOS);
        return outboundApplicationDO.getId();
    }


    /**
     * 将某个单据进行提交审批
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean submit(Long id) {


        //创建流程实例
        HashMap<String, Object> processVariables = new HashMap<>();
        String processInstanceId = processInstanceApi.createProcessInstance(SecurityFrameworkUtils.getLoginUserId(),
                new BpmProcessInstanceCreateReqDTO()
                        .setBusinessKey(String.valueOf(id))
                        .setProcessDefinitionKey(OUTBOUND_PROCESS_KEY)
                        .setVariables(processVariables)
        );

        OutboundApplicationDO applicationDO = outboundApplicationMapper.selectById(id);
        applicationDO.setProcessInstanceId(processInstanceId);

        //需要设置审批状态为处理中
        applicationDO.setApproResult(BpmTaskStatusEnum.RUNNING.getStatus().toString());

        //进行更新-
        outboundApplicationMapper.updateById(applicationDO);

        return true;
    }

    /**
     * 审批通过或者不通过
     * 这里是旧版的逻辑，新版的逻辑是在流程中处理
     *
     * @param updateReqVO 单据数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveOutboundApplication(OutboundApplicationCreateReqVO updateReqVO) {
        String approveResult = updateReqVO.getApproResult();

        Set<Long> specimenIds = updateReqVO.getSubList().stream().map(OutboundApplicationSubCreateReqVO::getSpecimenId).collect(Collectors.toSet());

        //如果是审批通过，则更新单据、更新样品信息和槽位信息
        if (BpmTaskStatusEnum.APPROVE.getStatus().toString().equals(approveResult)) {
            OutboundApplicationDO mainDO = BeanUtils.toBean(updateReqVO, OutboundApplicationDO.class);
            outboundApplicationMapper.updateById(mainDO);

            String status;

            if (updateReqVO.getType().equals(OutboundTypeConstants.NORMAL) || updateReqVO.getType().equals(OutboundTypeConstants.REGENERATION)) {
                // 正常出库 传代/复壮都要正常回库
                List<SpecimenInfoDO> specimenList = specimenInfoMapper.selectList("id", specimenIds);
                for (SpecimenInfoDO entryDO : specimenList) {
                    entryDO.setStatus(InventoryStatisEnum.WAIT_STOCK.getValue());
                }
                //更新样品数据
                specimenInfoMapper.updateBatch(specimenList);

                //获取槽位数据
                List<FreezingTubeStockInfoDO> tubeStockInfoDOList = stockInfoMapper.selectList(FreezingTubeStockInfoDO::getStockPreEntryId, specimenIds);
                for (FreezingTubeStockInfoDO freezingTubeStockInfoDO : tubeStockInfoDOList) {
                    freezingTubeStockInfoDO.setStatus(InventoryStatisEnum.WAIT_STOCK.getValue());
                }

                stockInfoMapper.updateBatch(tubeStockInfoDOList);
            } else if (!updateReqVO.getType().equals(OutboundTypeConstants.CONSUME)) {
                if (updateReqVO.getType().equals(OutboundTypeConstants.DESTROY)) {
                    //销毁出库
                    status = InventoryStatisEnum.DESTROY_STOCK.getValue();
                    deliverSpecimen(specimenIds, status);
                }
            } else {
                //消耗出库
                status = InventoryStatisEnum.DELETE_STOCK.getValue();


                deliverSpecimen(specimenIds, status);

            }
        } else if (updateReqVO.getApproResult().equals(BpmTaskStatusEnum.REJECT.getStatus().toString())) {
            //如果审批不通过，则设置主表为
            OutboundApplicationDO mainDO = BeanUtils.toBean(updateReqVO, OutboundApplicationDO.class);
            outboundApplicationMapper.updateById(mainDO);
        }


    }

    /**
     * @param specimenIds 样品id
     * @param status      状态
     */
    private void deliverSpecimen(Set<Long> specimenIds, String status) {
        List<SpecimenInfoDO> specimenList = specimenInfoMapper.selectList("id", specimenIds);
        for (SpecimenInfoDO entryDO : specimenList) {
            entryDO.setStatus(status);
        }
        //更新样品数据
        specimenInfoMapper.updateBatch(specimenList);

//        Set<Long> collect = specimenList.stream().map(FreezingTubeStockPreEntryDO::getId).collect(Collectors.toSet());
        //获取槽位数据，清空菌种
        List<FreezingTubeStockInfoDO> tubeStockInfoDOList = stockInfoMapper.selectList(FreezingTubeStockInfoDO::getStockPreEntryId, specimenIds);

        LambdaUpdateWrapper<FreezingTubeStockInfoDO> wrapperX = new LambdaUpdateWrapper<>();
        wrapperX.set(FreezingTubeStockInfoDO::getStockPreEntryId, null)
                .set(FreezingTubeStockInfoDO::getMicrobeId, null)
                .set(FreezingTubeStockInfoDO::getExpirationDate, null)
                .set(FreezingTubeStockInfoDO::getSaveDate, null)
                .set(FreezingTubeStockInfoDO::getStatus, InventoryStatisEnum.NOT_IN_STOCK.getValue())
                .set(FreezingTubeStockInfoDO::getSaveBy, null)
                .set(FreezingTubeStockInfoDO::getSaveByName, null)
                .set(FreezingTubeStockInfoDO::getStockPreEntryCode, null)
                .set(FreezingTubeStockInfoDO::getCreator, null)
                .set(FreezingTubeStockInfoDO::getCreateTime, null)
                .set(FreezingTubeStockInfoDO::getUpdater, null)
                .set(FreezingTubeStockInfoDO::getUpdateTime, null);
        wrapperX.in(FreezingTubeStockInfoDO::getId, tubeStockInfoDOList.stream().map(FreezingTubeStockInfoDO::getId).collect(Collectors.toSet()));
        stockInfoMapper.update(wrapperX);
    }

    /**
     * 判断是否已经存在正在处理的明细了
     *
     * @param subApplicationDOS 子表数据
     */
    private void validateSubIsExistsProcess(List<OutboundSubApplicationDO> subApplicationDOS) {
        List<Long> specimenIds = subApplicationDOS.stream().map(OutboundSubApplicationDO::getSpecimenId).distinct().toList();

        List<String> specimenCode = subApplicationMapper.selectProcessorBySpecimenIds(specimenIds);

        if (!specimenCode.isEmpty()) {
            throw exception(new ErrorCode(999, String.join(",", specimenCode) + "已经存在于出库单中，正在处理中"));
        }

        // todo 删除的菌种不可以重新入库
    }


    @Override
    public boolean addSubList(OutboundApplicationSubInfoUpdateReqVO updateReqVO) {
        Long parentId = updateReqVO.getId(); // 主表id

        // 判断是否存在
        OutboundApplicationDO parent = outboundApplicationMapper.selectOne(new LambdaQueryWrapperX<OutboundApplicationDO>().eq(OutboundApplicationDO::getId, parentId));
        if (parent==null) {
            // 主表不存在，不能新增
            throw exception(OUTBOUND_APPLICATION_NOT_EXISTS);
        }


        // 查询关联的子表数据，然后与传进来的数据进行对比，重复的去掉
        List<OutboundSubApplicationDO> subApplicationDOS = subApplicationMapper.selectList("parent_id", parentId);
        Set<Long> existsSpecimenIds = subApplicationDOS.stream().map(OutboundSubApplicationDO::getSpecimenId).collect(Collectors.toSet());
//
        //排除已经存在的
        List<ExpiredWarningRespVO> list = updateReqVO.getSpecimenInfo().stream().filter(item -> !existsSpecimenIds.contains(item.getId())).toList();


        List<OutboundSubApplicationDO> newList = new ArrayList<>();
        for (ExpiredWarningRespVO item : list) {
            OutboundSubApplicationDO tempDo = new OutboundSubApplicationDO();
            tempDo.setParentId(updateReqVO.getId()); // 主表id
            tempDo.setSpecimenId(item.getId());
            tempDo.setStockId(item.getStockId());
            tempDo.setSpecimenCode(item.getSpecimenCode());
            tempDo.setChineseName(item.getChineseName());
            tempDo.setLatinName(item.getLatinName());


            //判断主表的类型，然后设置回库状态
            if (parent.getType().equals(OutboundTypeConstants.NORMAL)
                    || parent.getType().equals(OutboundTypeConstants.REGENERATION)) {
                // 正常出库 传代/复壮都要正常回库
                tempDo.setResave(false); //需要回库
            } else if (parent.getType().equals(OutboundTypeConstants.CONSUME)
                    || parent.getType().equals(OutboundTypeConstants.DESTROY)) {
                //消耗出库
                tempDo.setResave(true);
            }

            newList.add(tempDo);
        }

        if (!newList.isEmpty()) {
            subApplicationMapper.insertBatch(newList);
        }
        return true;
    }

    /**
     * 获取自身的代理对象，解决AOP生效的问题
     *
     * @return
     */
    private OutboundApplicationServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }
}