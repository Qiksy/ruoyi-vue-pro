package cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningReqVO;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningRespVO;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningUpdateReqVO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdeviceinfo.FreezingDeviceInfoDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo.FreezingTubeStockInfoDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.microbebasicinfo.MicrobeBasicInfoDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.storageareainfo.StorageAreaInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdevicehierarchy.FreezingDeviceHierarchyMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdeviceinfo.FreezingDeviceInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockinfo.FreezingTubeStockInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.microbebasicinfo.MicrobeBasicInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.storageareainfo.StorageAreaInfoMapper;
import cn.iocoder.yudao.module.strain.enums.InventoryStatisEnum;
import cn.iocoder.yudao.module.strain.service.microbebasicinfo.MicrobeBasicInfoService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockpreentry.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry.FreezingTubeStockPreEntryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 冷冻管库存预录入 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FreezingTubeStockPreEntryServiceImpl implements FreezingTubeStockPreEntryService {

    @Resource
    private FreezingTubeStockPreEntryMapper freezingTubeStockPreEntryMapper;

    @Resource
    private MicrobeBasicInfoMapper microbeBasicInfoMapper;  //菌种信息


    @Resource
    @Lazy
    private MicrobeBasicInfoService microbeBasicInfoService; //菌种信息


    /**
     * 槽位信息
     */
    @Resource
    private FreezingTubeStockInfoMapper tubeStockInfoMapper; //槽位信息


    @Resource
    private FreezingTubeStockInfoMapper freezingTubeStockInfoMapper; //冷冻管库存信息


    @Resource
    private FreezingDeviceHierarchyMapper deviceHierarchyMapper; //冻存设备层级信息


    @Resource
    private FreezingDeviceInfoMapper deviceInfoMapper; // 设备信息


    @Resource
    private StorageAreaInfoMapper storageAreaInfoMapper; //区域信息


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> createFreezingTubeStockPreEntry(FreezingTubeStockPreEntrySaveReqVO createReqVO) {


        Long microbeId = createReqVO.getMicrobeId();
        MicrobeBasicInfoDO microbeBasicInfoDO = microbeBasicInfoMapper.selectById(microbeId);
        String microbeType = microbeBasicInfoDO.getMicrobeType();
        String microbeCode = microbeBasicInfoDO.getCode();

        // 获取数量，然后构造多个对象，并且批量插入
        List<FreezingTubeStockPreEntryDO> doList = new ArrayList<>();

        for (int i = 0; i < createReqVO.getNum(); i++) {
            FreezingTubeStockPreEntryDO entryDO = new FreezingTubeStockPreEntryDO();
            entryDO.setTubeId(createReqVO.getTubeId()); // 冻藏管
            entryDO.setRemark(createReqVO.getRemark()); // 备注
            entryDO.setMicrobeId(createReqVO.getMicrobeId()); // 菌种
            entryDO.setSaveBy(SecurityFrameworkUtils.getLoginUserId()); // 保存人
            entryDO.setSaveDate(createReqVO.getSaveDate()); // 保存日期
            entryDO.setCode(generateCode(microbeCode, i));

            //设置融冻次数
            entryDO.setThawFreezeCycleCount(1);

            //过期时间=保存时间+有效期天数
            entryDO.setExpirationDate(createReqVO.getSaveDate().plusDays(microbeBasicInfoDO.getValidityPeriodDays()));

            // 默认没有入库
            entryDO.setStatus(InventoryStatisEnum.NOT_IN_STOCK.getValue());

            doList.add(entryDO);
        }

        freezingTubeStockPreEntryMapper.insertBatch(doList);

        return doList.stream().map(FreezingTubeStockPreEntryDO::getId).toList();//返回id
    }

    /**
     * 生成冷冻管编号
     * 菌种编号-加上流水号
     *
     * @param microbeCode 菌种编号
     * @param i           第几个
     * @return 冷冻管编号
     */
    private String generateCode(String microbeCode, int i) {
        //菌种类型
//        microbeCode = String.format("%02d", Integer.parseInt(microbeCode));

        FreezingTubeStockPreEntryDO entryDO = freezingTubeStockPreEntryMapper.selectOne(new LambdaQueryWrapperX<FreezingTubeStockPreEntryDO>()
                .select(FreezingTubeStockPreEntryDO::getCode)
                .likeRight(FreezingTubeStockPreEntryDO::getCode, (microbeCode + "-"))
                .last("limit 1")
                .orderByDesc(FreezingTubeStockPreEntryDO::getCode));


        int serialNumber;
        if (entryDO != null) {
            String oldCode = entryDO.getCode();
            serialNumber = Integer.parseInt(oldCode.substring((microbeCode + "-").length()));
        } else {
            serialNumber = 0;
        }

        serialNumber = serialNumber + i + 1;

        int length = Math.max(2, String.valueOf(serialNumber).length());

        return (microbeCode + "-") + String.format("%0" + length + "d", serialNumber);
    }

    @Override
    public void updateFreezingTubeStockPreEntry(FreezingTubeStockPreEntryUpdateReqVO updateReqVO) {
        // 校验存在
        validateFreezingTubeStockPreEntryExists(updateReqVO.getId());
        // 更新
        FreezingTubeStockPreEntryDO updateObj = BeanUtils.toBean(updateReqVO, FreezingTubeStockPreEntryDO.class);
        freezingTubeStockPreEntryMapper.updateById(updateObj);
    }

    @Override
    public void deleteFreezingTubeStockPreEntry(Long id) {
        // 校验存在
        validateFreezingTubeStockPreEntryExists(id);
        // 校验是否已经入库了
        if (!Objects.equals(freezingTubeStockPreEntryMapper.selectById(id).getStatus(), InventoryStatisEnum.NOT_IN_STOCK.getValue())) {
            throw exception(FREEZING_TUBE_STOCK_PRE_ENTRY_IN_STOCK);
        }
        // 删除
        freezingTubeStockPreEntryMapper.deleteById(id);
    }

    private void validateFreezingTubeStockPreEntryExists(Long id) {
        if (freezingTubeStockPreEntryMapper.selectById(id) == null) {
            throw exception(FREEZING_TUBE_STOCK_PRE_ENTRY_NOT_EXISTS);
        }
    }

    @Override
    public FreezingTubeStockPreEntryDO getFreezingTubeStockPreEntry(Long id) {
        return freezingTubeStockPreEntryMapper.selectById(id);
    }

    @Override
    public PageResult<FreezingTubeStockPreEntryDO> getFreezingTubeStockPreEntryPage(FreezingTubeStockPreEntryPageReqVO pageReqVO) {
        return freezingTubeStockPreEntryMapper.selectPage(pageReqVO);
    }

    /**
     * 连表查询分页
     *
     * @param pageReqVO 分页查询
     * @return 冷冻管库存预录入分页
     */
    @Override
    public PageResult<FreezingTubeStockPreEntryRespVO> getFreezingTubeStockPreEntryPage2(FreezingTubeStockPreEntryPageReqVO pageReqVO) {
        IPage<FreezingTubeStockPreEntryRespVO> iPage = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        freezingTubeStockPreEntryMapper.selectPage2(iPage, pageReqVO);

        // 查询这些样品的位置信息
        List<Long> list = iPage.getRecords().stream().map(FreezingTubeStockPreEntryRespVO::getStockId).filter(
                Objects::nonNull
        ).toList();

        Map<Long, String> positionMap = getStockPositionStrMap(list);

        if (!positionMap.isEmpty()) {
            iPage.getRecords().forEach(e -> e.setPositionStr(positionMap.get(e.getStockId())));
        }
        return new PageResult<>(iPage.getRecords(), iPage.getTotal());
    }


    @Override
    public PageResult<ExpiredWarningRespVO> getExpiredWaringPage(ExpiredWarningReqVO pageReqVO) {
        IPage<ExpiredWarningRespVO> iPage = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        freezingTubeStockPreEntryMapper.selectPage4(iPage, pageReqVO);
// 查询这些样品的位置信息
        List<Long> list = iPage.getRecords().stream().map(ExpiredWarningRespVO::getStockId).filter(
                Objects::nonNull
        ).toList();

        Map<Long, String> positionMap = getStockPositionStrMap(list);

        if (!positionMap.isEmpty()) {
            iPage.getRecords().forEach(e -> e.setPositionStr(positionMap.get(e.getStockId())));
        }

        return new PageResult<>(iPage.getRecords(), iPage.getTotal());
    }

    /**
     * 连表查询分页，去掉正在处理的样品
     *
     * @param pageReqVO 分页查询
     * @return 样品分页
     */
    @Override
    public PageResult<FreezingTubeStockPreEntryRespVO> getFreezingTubeStockPreEntryPage3(FreezingTubeStockPreEntryPageReqVO pageReqVO) {
        IPage<FreezingTubeStockPreEntryRespVO> iPage = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        freezingTubeStockPreEntryMapper.selectPage3(iPage, pageReqVO);

        // 查询这些样品的位置信息
        List<Long> list = iPage.getRecords().stream().map(FreezingTubeStockPreEntryRespVO::getStockId).filter(
                Objects::nonNull
        ).toList();

        Map<Long, String> positionMap = getStockPositionStrMap(list);

        if (!positionMap.isEmpty()) {
            iPage.getRecords().forEach(e -> e.setPositionStr(positionMap.get(e.getStockId())));
        }
        return new PageResult<>(iPage.getRecords(), iPage.getTotal());
    }

    /**
     * 从这里获取样品的位置信息
     *
     * @param stockIds 槽位id
     * @return 返回一个槽位id和对应的位置信息
     */
    public Map<Long, String> getStockPositionStrMap(List<Long> stockIds) {
        if (stockIds.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, String> map = new HashMap<>();

        try {
            for (Long stockId : stockIds) {
                //查询这个位置的具体信息
                FreezingTubeStockInfoDO tubeStockInfoDO = tubeStockInfoMapper.selectOne("id", stockId);
                if (tubeStockInfoDO == null) {
                    continue;
                }
                String boxPositionStr = getBoxPositionStr(tubeStockInfoDO);

                //1. 递归查询层级名称 和设备名称
                LevelTempInfo levelTempInfo = deviceHierarchyMapper.selectLevelNameById(tubeStockInfoDO.getBoxId());
                String levelName = levelTempInfo.getName();

                //2. 查询设备名称
                FreezingDeviceInfoDO deviceInfoDO = deviceInfoMapper.selectById(levelTempInfo.getParentId());// 层级的最上级是设备
                String deviceName = deviceInfoDO.getName();

                //3. 查询区域名称
                StorageAreaInfoDO areaInfoDO = storageAreaInfoMapper.selectById(deviceInfoDO.getStorageAreaId());

                String areaName = areaInfoDO.getName();
                //4. 添加到对应的map中

                map.put(stockId, areaName + "/" + deviceName + "/" + levelName + "/" + boxPositionStr);
            }
        } catch (Exception e) {
            throw exception(MICROBE_POSITION_INFO_NOT_EXISTS);
        }


        return map;
    }

    /**
     * 获取xy的相对位置，因为实际上数据存储的是0~9，所以需要转换
     *
     * @param tubeStockInfoDO 冷冻管库存信息
     * @return 返回位置信息
     */
    private String getBoxPositionStr(FreezingTubeStockInfoDO tubeStockInfoDO) {
        String tubePositionX = tubeStockInfoDO.getTubePositionX();
        String tubePositionY = tubeStockInfoDO.getTubePositionY();

        return (Integer.parseInt(tubePositionX) + 1) + "-" + (Integer.parseInt(tubePositionY) + 1);
    }


    @Override
    public List<FreezingTubeStockPreEntryRespVO> getMicrobeBasicInfoStorageList(Long id) {
        List<FreezingTubeStockPreEntryDO> list = freezingTubeStockPreEntryMapper.selectListByMicrobeId(id);


        List<FreezingTubeStockPreEntryRespVO> result = BeanUtils.toBean(list, FreezingTubeStockPreEntryRespVO.class);

        //获取槽位id
        Set<Long> collect = list.stream().map(FreezingTubeStockPreEntryDO::getId).collect(Collectors.toSet());
        if (collect.isEmpty()) {
            return result;
        }

        LambdaQueryWrapperX<FreezingTubeStockInfoDO> wrapperX = new LambdaQueryWrapperX<FreezingTubeStockInfoDO>()
                .in(FreezingTubeStockInfoDO::getStockPreEntryId, collect);
        List<FreezingTubeStockInfoDO> tubeStockInfoDOList = freezingTubeStockInfoMapper.selectList(wrapperX);

        Map<Long, FreezingTubeStockInfoDO> map = tubeStockInfoDOList.stream().collect(Collectors.toMap(FreezingTubeStockInfoDO::getStockPreEntryId, v -> v));

        for (FreezingTubeStockPreEntryRespVO temp : result) {
            temp.setStockId(map.getOrDefault(temp.getId(), new FreezingTubeStockInfoDO()).getId());
        }
        //根据槽位id获取位置信息
        Map<Long, String> stockPositionStrMap = getStockPositionStrMap(tubeStockInfoDOList.stream().map(FreezingTubeStockInfoDO::getId).collect(Collectors.toList()));


        for (FreezingTubeStockPreEntryRespVO temp : result) {
            temp.setPositionStr(stockPositionStrMap.get(temp.getStockId()));
        }

        return result;
    }

    /**
     * 更新过期日期
     *
     * @param reqVO 过期预警更新
     */
    @Override
    public void updateExpiredDate(ExpiredWarningUpdateReqVO reqVO) {
        if (reqVO == null) {
            throw new IllegalArgumentException("reqVO cannot be null");
        }

        String type = reqVO.getType();
        if (type == null) {
            throw new IllegalArgumentException("reqVO type cannot be null");
        }

        Long[] ids = reqVO.getIds();
        if (ids == null || ids.length==0) {
            throw new IllegalArgumentException("reqVO ids cannot be null");
        }


        if (type.equals("VALUE")) {
            // 把所有的数据的过期事件，更新到指定值
            freezingTubeStockPreEntryMapper.update(
                    new LambdaUpdateWrapper<FreezingTubeStockPreEntryDO>()
                            .in(FreezingTubeStockPreEntryDO::getId,Arrays.asList(reqVO.getIds()))
                            .set(FreezingTubeStockPreEntryDO::getExpirationDate, reqVO.getExpiredDate()));
        } else if (type.equals("EXPIRATION")) {
            // Your code here
            List<FreezingTubeStockPreEntryDO> entryDOS = freezingTubeStockPreEntryMapper.selectList(new LambdaQueryWrapperX<FreezingTubeStockPreEntryDO>()
                    .in(FreezingTubeStockPreEntryDO::getId, Arrays.asList(reqVO.getIds())));

            // 批量更新
            for (FreezingTubeStockPreEntryDO entryDO : entryDOS) {



                MicrobeBasicInfoDO microbeBasicInfo = microbeBasicInfoService.getMicrobeBasicInfo(entryDO.getMicrobeId());
                Integer validityPeriodDays = null;
                if (microbeBasicInfo != null) {
                    validityPeriodDays = microbeBasicInfo.getValidityPeriodDays();
                }
                LocalDateTime currentDate = LocalDateTime.now();

                //加上有效期天数
                currentDate = currentDate.plusDays(validityPeriodDays);

                entryDO.setExpirationDate(currentDate);

                // 更新就删缓存
                freezingTubeStockPreEntryMapper.updateById(entryDO);
            }

        } else {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}