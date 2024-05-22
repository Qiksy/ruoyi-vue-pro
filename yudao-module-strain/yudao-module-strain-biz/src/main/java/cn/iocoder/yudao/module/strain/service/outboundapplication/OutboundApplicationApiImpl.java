package cn.iocoder.yudao.module.strain.service.outboundapplication;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.strain.api.OutboundApplicationApi;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo.FreezingTubeStockInfoDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundapplication.OutboundApplicationDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundsubapplication.OutboundSubApplicationDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockinfo.FreezingTubeStockInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry.FreezingTubeStockPreEntryMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.outboundapplication.OutboundApplicationMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.outboundsubapplication.OutboundSubApplicationMapper;
import cn.iocoder.yudao.module.strain.enums.InventoryStatisEnum;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OutboundApplicationApiImpl implements OutboundApplicationApi {

    @Resource
    private OutboundApplicationMapper outboundApplicationMapper;

    @Resource
    private OutboundSubApplicationMapper subApplicationMapper;

    @Resource
    private FreezingTubeStockInfoMapper stockInfoMapper;

    @Resource
    private FreezingTubeStockPreEntryMapper freezingTubeStockPreEntryMapper;


    @Override
    public void updateResult(Long businessKey, Integer result) {
        if (businessKey==null){
            throw new IllegalArgumentException("businessKey is null");
        }

        LambdaUpdateWrapper<OutboundApplicationDO> updateWrapper = new LambdaUpdateWrapper<OutboundApplicationDO>()
                .set(OutboundApplicationDO::getApproResult, result)
                .eq(OutboundApplicationDO::getId, businessKey);
        outboundApplicationMapper.update(updateWrapper);
    }


    /**
     * 审批通过后，更新相关的逻辑
     *
     * @param businessKey 表单主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStrainStock(Long businessKey) {

        OutboundApplicationDO applicationDO = outboundApplicationMapper.selectById(businessKey);

        List<OutboundSubApplicationDO> subApplicationDOS = subApplicationMapper.selectList(
                new LambdaQueryWrapperX<OutboundSubApplicationDO>()
                        .eq(OutboundSubApplicationDO::getParentId, businessKey));


        Set<Long> specimenIds = subApplicationDOS.stream().map(OutboundSubApplicationDO::getSpecimenId).collect(Collectors.toSet());


        String status;

        if (applicationDO.getIsRestocked() && applicationDO.getType().equals("1")){
            status = null;
            //回库并且是正常出库的，设置菌种为待回库
            List<FreezingTubeStockPreEntryDO> specimenList = freezingTubeStockPreEntryMapper.selectList("id", specimenIds);
            for (FreezingTubeStockPreEntryDO entryDO : specimenList) {
                entryDO.setStatus(InventoryStatisEnum.WAIT_STOCK.getValue());
            }
            //更新样品数据
            freezingTubeStockPreEntryMapper.updateBatch(specimenList);

            //获取槽位数据
            List<FreezingTubeStockInfoDO> tubeStockInfoDOList = stockInfoMapper.selectList(FreezingTubeStockInfoDO::getStockPreEntryId,specimenIds );
            for (FreezingTubeStockInfoDO freezingTubeStockInfoDO : tubeStockInfoDOList) {
                freezingTubeStockInfoDO.setStatus(InventoryStatisEnum.WAIT_STOCK.getValue());
            }

            stockInfoMapper.updateBatch(tubeStockInfoDOList);
        }else if (!applicationDO.getIsRestocked() && applicationDO.getType().equals("1")){
            //如果是正常出库并且不回库 设置为消耗态
            status = InventoryStatisEnum.DELETE_STOCK.getValue();


            deliverSpecimen(specimenIds, status);

        } else if (applicationDO.getType().equals("2")){
            //销毁出库
            status = InventoryStatisEnum.DESTROY_STOCK.getValue();
            deliverSpecimen(specimenIds, status);
        }
    }


    /**
     * @param specimenIds 样品id
     * @param status 状态
     */
    private void deliverSpecimen(Set<Long> specimenIds, String status) {
        List<FreezingTubeStockPreEntryDO> specimenList = freezingTubeStockPreEntryMapper.selectList("id", specimenIds);
        for (FreezingTubeStockPreEntryDO entryDO : specimenList) {
            entryDO.setStatus(status);
        }
        //更新样品数据
        freezingTubeStockPreEntryMapper.updateBatch(specimenList);

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
        wrapperX.in(FreezingTubeStockInfoDO::getId,tubeStockInfoDOList.stream().map(FreezingTubeStockInfoDO::getId).collect(Collectors.toSet()) );
        stockInfoMapper.update(wrapperX);
    }
}
