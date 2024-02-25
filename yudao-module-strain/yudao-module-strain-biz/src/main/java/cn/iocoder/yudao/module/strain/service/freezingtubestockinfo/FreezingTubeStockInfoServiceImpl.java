package cn.iocoder.yudao.module.strain.service.freezingtubestockinfo;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.strain.controller.admin.freezingboxinfo.vo.FreezingBoxInfoDetailVO;
import cn.iocoder.yudao.module.strain.controller.admin.microbebasicinfo.vo.MicrobeBasicInfoRespVO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingboxinfo.FreezingBoxInfoDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockpreentry.FreezingTubeStockPreEntryDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.microbebasicinfo.MicrobeBasicInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingboxinfo.FreezingBoxInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdevicehierarchy.FreezingDeviceHierarchyMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockpreentry.FreezingTubeStockPreEntryMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.microbebasicinfo.MicrobeBasicInfoMapper;
import cn.iocoder.yudao.module.strain.enums.InventoryStatisEnum;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import static cn.iocoder.yudao.module.strain.enums.InventoryStatisEnum.IN_STOCK;

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

    @Resource
    private AdminUserApi adminUserApi; // 系统用户 API

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
     * @param tubeStockId  槽位id
     * @param perStockCode 预备入库的编号
     */
    @Override
    public void scannerUpdateFreezingTubeStockInfo(Long tubeStockId, String perStockCode) {
        // 获取预备入库的相关信息，存放到槽位当中。然后更新槽位信息

        //需要做并发控制，也就是加锁
        String tubeSockKey = tubeSockKeyPrefix + tubeStockId.toString();
        String tubePerSockKey = tubePreSockKeyPrefix + perStockCode;

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
                    FreezingTubeStockPreEntryDO entryDO = freezingTubeStockPreEntryMapper.selectOne(
                            new LambdaQueryWrapperX<FreezingTubeStockPreEntryDO>().
                                    eq(FreezingTubeStockPreEntryDO::getCode, perStockCode));
                    //判断是否已经入库了
                    if (StringUtils.equals(freezingTubeStockInfoDO.getStatus(), "0") && StringUtils.equals(entryDO.getStatus(), "0")) {

                        AdminUserRespDTO user = Optional.ofNullable(adminUserApi.getUser(entryDO.getSaveBy())).orElse(new AdminUserRespDTO());

                        //这里是没入库的
                        freezingTubeStockInfoDO.setMicrobeId(entryDO.getMicrobeId()); //设置菌种id
                        String creator = entryDO.getCreator();
                        freezingTubeStockInfoDO.setSaveBy(Long.valueOf(creator));//设置保存人
                        freezingTubeStockInfoDO.setSaveByName(user.getNickname());//设置保存人的姓名
                        freezingTubeStockInfoDO.setStockPreEntryId(entryDO.getId());//设置预备入库id
                        freezingTubeStockInfoDO.setThawFreezeCycleCount(entryDO.getThawFreezeCycleCount());//设置融冻次数
                        freezingTubeStockInfoDO.setTubeId(entryDO.getTubeId());//设置冷冻管类型id
                        freezingTubeStockInfoDO.setStatus("1");//在库状态

                        //设置保存时间和有效期
                        freezingTubeStockInfoDO.setExpirationDate(entryDO.getExpirationDate());
                        freezingTubeStockInfoDO.setSaveDate(entryDO.getSaveDate());

                        //设置预备录入编号
                        freezingTubeStockInfoDO.setStockPreEntryCode(entryDO.getCode());

                        //todo 冗余一些菌种信息在里面，例如菌种名称、编号、用途等等，如果以后要更新，就设置定时任务去执行同步更新
                        //更新冷冻管信息
                        freezingTubeStockInfoMapper.updateById(freezingTubeStockInfoDO);
                        //更新预备入库信息
                        entryDO.setStatus(InventoryStatisEnum.IN_STOCK.getValue()); //表示已经入库
                        freezingTubeStockPreEntryMapper.updateById(entryDO);


                        //菌种名称、编号、用途、来源备注
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


        //返回的最终的每个槽位的信息
        //除了槽位信息还包含着菌种信息
        List<FreezingTubeStockInfoRespVO> respVOList = BeanUtils.toBean(tubeStockInfoDOList, FreezingTubeStockInfoRespVO.class);


        // 槽位信息需要拼接各种菌种信息
        List<Long> microbeIds = tubeStockInfoDOList.stream().map(FreezingTubeStockInfoDO::getMicrobeId).filter(Objects::nonNull).distinct().toList();

        if (!microbeIds.isEmpty()) {
            List<MicrobeBasicInfoDO> microbeBasicInfoDOS = microbeBasicInfoMapper.selectBatchIds(microbeIds);
            List<MicrobeBasicInfoRespVO> microbeInfo = BeanUtils.toBean(microbeBasicInfoDOS, MicrobeBasicInfoRespVO.class);
            Map<Long, MicrobeBasicInfoRespVO> microbeMap = microbeInfo.stream().collect(Collectors.toMap(MicrobeBasicInfoRespVO::getId, o -> o));


            for (FreezingTubeStockInfoRespVO respVO : respVOList) {
                MicrobeBasicInfoRespVO microbeBasicInfoVo = microbeMap.get(respVO.getMicrobeId());
                if (microbeBasicInfoVo == null) {
                    continue;
                }
                respVO.setMicrobeInfo(microbeBasicInfoVo);
                respVO.setMicrobeType(microbeBasicInfoVo.getMicrobeType());// 菌种type
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

    /**
     * 将这个槽位设置为待回库
     *
     * @param tubeStockId 冻藏管槽位id
     */
    @Override
    public void tempDelivery(Long tubeStockId) {


        FreezingTubeStockInfoDO freezingTubeStockInfoDO = freezingTubeStockInfoMapper.selectById(tubeStockId);

        if (freezingTubeStockInfoDO.getStockPreEntryId() == null) {
            //表示没有预备入库的信息
            throw exception(TUBE_STOCK_NOT_PRE_ENTRY);
        }

        freezingTubeStockInfoDO.setStatus(InventoryStatisEnum.WAIT_STOCK.getValue());

        freezingTubeStockInfoMapper.updateById(freezingTubeStockInfoDO);


        //todo 可能需要关联出库单，把出库单的已经回库状态进行更新

    }


    /**
     * 完全出库，也就是不会设置待回库的
     *
     * @param tubeStockId 冻藏管槽位id
     */
    @Override
    public void delivery(Long tubeStockId) {
        //获取槽位id
        FreezingTubeStockInfoDO freezingTubeStockInfoDO = freezingTubeStockInfoMapper.selectById(tubeStockId);
        if (freezingTubeStockInfoDO == null) {
            throw exception(FREEZING_TUBE_STOCK_INFO_NOT_EXISTS);
        }

        Long stockPreEntryId = freezingTubeStockInfoDO.getStockPreEntryId(); // 获取冷冻管实例

        //冻藏管
        FreezingTubeStockPreEntryDO tubeInfo = freezingTubeStockPreEntryMapper.selectById(stockPreEntryId);
        if (tubeInfo != null) {
            //如果存在，就更新它的状态为未入库
            tubeInfo.setStatus(InventoryStatisEnum.NOT_IN_STOCK.getValue());
            freezingTubeStockPreEntryMapper.updateById(tubeInfo);
        }


        //重新设置状态
        freezingTubeStockInfoDO.setStatus(InventoryStatisEnum.NOT_IN_STOCK.getValue()); //重新设置为未入库
        freezingTubeStockInfoDO.setStockPreEntryId(null); //设置预备入库id为空
//        freezingTubeStockInfoMapper.updateById(freezingTubeStockInfoDO);

        //设置为null需要用到LambdaUpdateWrapper
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
        wrapperX.eq(FreezingTubeStockInfoDO::getId, tubeStockId);

        freezingTubeStockInfoMapper.update(freezingTubeStockInfoDO, wrapperX);


    }

    /**
     * 给冻藏管的融冻次数+1
     * 然后设置在库状态为在库
     *
     * @param perStockCode 冻藏管的编号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void scannerReStock(String perStockCode) {

        LambdaQueryWrapperX<FreezingTubeStockPreEntryDO> lambdaQueryWrapperX = new LambdaQueryWrapperX<FreezingTubeStockPreEntryDO>().eq(FreezingTubeStockPreEntryDO::getCode, perStockCode);
        FreezingTubeStockPreEntryDO perStock = freezingTubeStockPreEntryMapper.selectOne(lambdaQueryWrapperX);

        int thawFreezeCycleCount = Optional.ofNullable(perStock.getThawFreezeCycleCount()).orElse(1) + 1;
        perStock.setThawFreezeCycleCount(thawFreezeCycleCount);

        freezingTubeStockPreEntryMapper.updateById(perStock);


        LambdaQueryWrapperX<FreezingTubeStockInfoDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(FreezingTubeStockInfoDO::getStockPreEntryId, perStock.getId());

        List<FreezingTubeStockInfoDO> tubeStockInfoDOList = freezingTubeStockInfoMapper.selectList(wrapperX);

        if (tubeStockInfoDOList.isEmpty()) {
            //槽位不存在
            throw exception(TUBE_STOCK_NOT_EXISTS);
        } else if (tubeStockInfoDOList.size() > 1) {
            //槽位存在但是太多个，有冲突
            throw exception(TUBE_STOCK_TOO_MANY);
        } else {
            //更新
            FreezingTubeStockInfoDO freezingTubeStockInfoDO = tubeStockInfoDOList.get(0);
            freezingTubeStockInfoDO.setStatus(IN_STOCK.getValue());
            freezingTubeStockInfoDO.setThawFreezeCycleCount(thawFreezeCycleCount);
            freezingTubeStockInfoMapper.updateById(freezingTubeStockInfoDO);
        }
    }
}