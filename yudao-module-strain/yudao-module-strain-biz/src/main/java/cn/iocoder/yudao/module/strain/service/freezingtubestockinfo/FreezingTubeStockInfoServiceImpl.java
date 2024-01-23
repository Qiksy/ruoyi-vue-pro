package cn.iocoder.yudao.module.strain.service.freezingtubestockinfo;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo.FreezingBoxInfoDetailVO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingboxinfo.FreezingBoxInfoDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.microbebasicinfo.MicrobeBasicInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingboxinfo.FreezingBoxInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdevicehierarchy.FreezingDeviceHierarchyMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry.FreezingTubeStockPreEntryMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.microbebasicinfo.MicrobeBasicInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.strain.controller.admin.freezingtubestockinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo.FreezingTubeStockInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockinfo.FreezingTubeStockInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.GET_LOCK_FAIL;
import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.GET_LOCK_INTERRUPT;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 冷冻盒槽位 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FreezingTubeStockInfoServiceImpl implements FreezingTubeStockInfoService {

    @Resource
    private FreezingTubeStockInfoMapper freezingTubeStockInfoMapper;

    @Resource
    private FreezingTubeStockPreEntryMapper freezingTubeStockPreEntryMapper;


    @Resource
    private FreezingBoxInfoMapper freezingBoxInfoMapper;


    @Resource
    private FreezingDeviceHierarchyMapper freezingDeviceHierarchyMapper;

    @Resource
    private RedissonClient redissonClient; // Redisson 客户端

    @Resource
    private MicrobeBasicInfoMapper microbeBasicInfoMapper; // 菌种基础信息 Mapper

    /**
     * 槽位id锁前缀
     */
    private final String tubeSockKeyPrefix = "TUBE_SOCK_ID:";
    /**
     * 预备录入锁前缀
     */
    private final String tubePreSockKeyPrefix = "TUBE_PRE_SOCK_ID:";


    @Override
    public Long createFreezingTubeStockInfo(FreezingTubeStockInfoSaveReqVO createReqVO) {
        // 插入
        FreezingTubeStockInfoDO freezingTubeStockInfo = BeanUtils.toBean(createReqVO, FreezingTubeStockInfoDO.class);
        freezingTubeStockInfoMapper.insert(freezingTubeStockInfo);
        // 返回
        return freezingTubeStockInfo.getId();
    }

    @Override
    public void updateFreezingTubeStockInfo(FreezingTubeStockInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateFreezingTubeStockInfoExists(updateReqVO.getId());
        // 更新
        FreezingTubeStockInfoDO updateObj = BeanUtils.toBean(updateReqVO, FreezingTubeStockInfoDO.class);
        freezingTubeStockInfoMapper.updateById(updateObj);
    }


    /**
     * @param tubeStockId 槽位id
     * @param perStockId  预备入库id
     */
    @Override
    public void scannerUpdateFreezingTubeStockInfo(Long tubeStockId, Long perStockId) {
        // 获取预备入库的相关信息，存放到槽位当中。然后更新槽位信息

        //需要做并发控制，也就是加锁
        String tubeSockKey = tubeSockKeyPrefix + tubeStockId.toString();
        String tubePerSockKey = tubePreSockKeyPrefix + perStockId.toString();

        // 获取槽位的锁
        RLock boxLock = redissonClient.getLock(tubeSockKey);
        // 获取样品的锁
        RLock sampleLock = redissonClient.getLock(tubePerSockKey);

        try {
            // 尝试获取锁，等待最多3秒，一旦获取锁，就会触发看门狗机制，每隔30秒自动续期
            if (boxLock.tryLock(3, TimeUnit.SECONDS) && sampleLock.tryLock(3, TimeUnit.SECONDS)) {
                //业务逻辑
                try {
                    FreezingTubeStockInfoDO freezingTubeStockInfoDO = freezingTubeStockInfoMapper.selectById(tubeStockId);
                    FreezingTubeStockPreEntryDO entryDO = freezingTubeStockPreEntryMapper.selectById(perStockId);
                    //判断是否已经入库了
                    if (StringUtils.equals(freezingTubeStockInfoDO.getStatus(), "0") && !entryDO.getStatus()) {
                        //这里是没入库的
                        freezingTubeStockInfoDO.setMicrobeId(entryDO.getMicrobeId()); //设置菌种id
                        freezingTubeStockInfoDO.setSaveBy(Long.valueOf(entryDO.getCreator()));//设置保存人
                        freezingTubeStockInfoDO.setStockPreEntryId(entryDO.getId());//设置预备入库id
                        freezingTubeStockInfoDO.setThawFreezeCycleCount(entryDO.getThawFreezeCycleCount());//设置融冻次数
                        freezingTubeStockInfoDO.setTubeId(entryDO.getTubeId());//设置冷冻管类型id
                        freezingTubeStockInfoDO.setStatus("1");//在库状态
                        //更新冷冻管信息
                        freezingTubeStockInfoMapper.updateById(freezingTubeStockInfoDO);
                        //更新预备入库信息
                        entryDO.setStatus(true); //表示已经入库
                        freezingTubeStockPreEntryMapper.updateById(entryDO);
                        //todo 冗余一些菌种信息在里面，例如菌种名称、编号、用途等等，如果以后要更新，就设置定时任务去执行同步更新
                    } else {
                        //触发1
                        throw exception(TUBE_STOCK_INFO_EXISTS);
                    }
                } catch (Exception e) {
                    //触发2
                    throw exception(TUBE_STOCK_INFO_EXISTS);
                } finally {
                    // 释放锁
                    boxLock.unlock();
                    sampleLock.unlock();
                }
            } else {
                throw exception(GET_LOCK_FAIL);
            }
        } catch (InterruptedException e) {
            // 获取锁被中断，可以进行相应的处理
            throw exception(GET_LOCK_INTERRUPT);
        }
    }

    @Override
    public void deleteFreezingTubeStockInfo(Long id) {
        // 校验存在
        validateFreezingTubeStockInfoExists(id);
        // 删除
        freezingTubeStockInfoMapper.deleteById(id);
    }

    private void validateFreezingTubeStockInfoExists(Long id) {
        if (freezingTubeStockInfoMapper.selectById(id) == null) {
            throw exception(FREEZING_TUBE_STOCK_INFO_NOT_EXISTS);
        }
    }

    @Override
    public FreezingTubeStockInfoDO getFreezingTubeStockInfo(Long id) {
        return freezingTubeStockInfoMapper.selectById(id);
    }

    @Override
    public PageResult<FreezingTubeStockInfoDO> getFreezingTubeStockInfoPage(FreezingTubeStockInfoPageReqVO pageReqVO) {
        return freezingTubeStockInfoMapper.selectPage(pageReqVO);
    }


    /**
     * 根据盒子id，返回一个盒子的所有槽位，
     * 包括盒子信息
     *
     * @param boxId 盒子实例id，也就是
     * @return 盒子的所有槽位
     */
    @Override
    public FreezingBoxInfoDetailVO getListByBoxId(Long boxId) {
        //首先，传输的是层级id
        FreezingDeviceHierarchyDO hierarchyDO = freezingDeviceHierarchyMapper.selectById(boxId);

        //获取盒子类型id
        Long boxTypeId = hierarchyDO.getFreezingBoxId();

        //获取具体的盒子信息
        FreezingBoxInfoDO freezingBoxInfoDO = freezingBoxInfoMapper.selectById(boxTypeId);
        FreezingBoxInfoDetailVO detailVO = BeanUtils.toBean(freezingBoxInfoDO, FreezingBoxInfoDetailVO.class);


        //获取具体的槽位信息
        LambdaQueryWrapperX<FreezingTubeStockInfoDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(FreezingTubeStockInfoDO::getBoxId, boxId);

        List<FreezingTubeStockInfoDO> tubeStockInfoDOList = freezingTubeStockInfoMapper.selectList(queryWrapperX);


        List<FreezingTubeStockInfoRespVO> respVOList = BeanUtils.toBean(tubeStockInfoDOList, FreezingTubeStockInfoRespVO.class);

        //todo 槽位信息需要拼接各种菌种信息
        List<Long> microbeIds = tubeStockInfoDOList.stream().map(FreezingTubeStockInfoDO::getMicrobeId).filter(Objects::nonNull).distinct().toList();

        if (!microbeIds.isEmpty()) {
            List<MicrobeBasicInfoDO> microbeBasicInfoDOS = microbeBasicInfoMapper.selectBatchIds(microbeIds);
            Map<Long, MicrobeBasicInfoDO> microbeMap = microbeBasicInfoDOS.stream().collect(Collectors.toMap(MicrobeBasicInfoDO::getId, o -> o));


            for (FreezingTubeStockInfoRespVO respVO : respVOList) {
                MicrobeBasicInfoDO microbeBasicInfoDO = microbeMap.get(respVO.getMicrobeId());
                if (microbeBasicInfoDO == null) {
                    continue;
                }
                respVO.setMicrobeName(microbeBasicInfoDO.getChineseName());
                respVO.setMicrobeCode(microbeBasicInfoDO.getCode());
                respVO.setMicrobeType(microbeBasicInfoDO.getMicrobeType());
            }
        }



        //开始将获取到的tubeInfo转为二维的
        int maxY = freezingBoxInfoDO.getAxisCapacityY();
        int maxX = freezingBoxInfoDO.getAxisCapacityX();

        List<List<FreezingTubeStockInfoRespVO>> matrix = new ArrayList<>(maxY + 1);
        for (int i = 0; i < maxY; i++) {
            List<FreezingTubeStockInfoRespVO> row = new ArrayList<>(maxX + 1);
            for (int j = 0; j < maxX; j++) {
                row.add(null);
            }
            matrix.add(row);
        }

//        for (FreezingTubeStockInfoDO freezingTubeStockInfoDO : tubeStockInfoDOList) {
//            FreezingTubeStockInfoRespVO respVO = BeanUtils.toBean(freezingTubeStockInfoDO, FreezingTubeStockInfoRespVO.class);
//            int x = Integer.parseInt(freezingTubeStockInfoDO.getTubePositionX());
//            int y = Integer.parseInt(freezingTubeStockInfoDO.getTubePositionY());
//            //设置进去
//            matrix.get(y).set(x, respVO);
//        }

        for (FreezingTubeStockInfoRespVO freezingTubeStockInfoRespVO : respVOList) {
            int x = Integer.parseInt(freezingTubeStockInfoRespVO.getTubePositionX());
            int y = Integer.parseInt(freezingTubeStockInfoRespVO.getTubePositionY());
            //设置进去
            matrix.get(y).set(x, freezingTubeStockInfoRespVO);
        }

        detailVO.setTubeStockInfoList(matrix);
        return detailVO;
    }
}