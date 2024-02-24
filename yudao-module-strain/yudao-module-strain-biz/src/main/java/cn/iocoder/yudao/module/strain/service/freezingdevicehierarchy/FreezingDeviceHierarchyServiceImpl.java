package cn.iocoder.yudao.module.strain.service.freezingdevicehierarchy;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingboxinfo.FreezingBoxInfoDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdeviceinfo.FreezingDeviceInfoDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo.FreezingTubeStockInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingboxinfo.FreezingBoxInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdeviceinfo.FreezingDeviceInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockinfo.FreezingTubeStockInfoMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.strain.convert.freezingdevicehierarchy.FreezingDeviceHierarchyConvert;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdevicehierarchy.FreezingDeviceHierarchyMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 冷冻设备层级 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FreezingDeviceHierarchyServiceImpl implements FreezingDeviceHierarchyService {

    @Resource
    private FreezingDeviceHierarchyMapper freezingDeviceHierarchyMapper;

    @Resource
    private FreezingDeviceInfoMapper freezingDeviceMapper;


    @Resource
    private FreezingBoxInfoMapper freezingBoxInfoMapper;


    @Resource
    private FreezingTubeStockInfoMapper freezingTubeStockInfoMapper;

    @Override
    public Long createFreezingDeviceHierarchy(FreezingDeviceHierarchyCreateReqVO createReqVO) {
        // 插入
        FreezingDeviceHierarchyDO freezingDeviceHierarchy = FreezingDeviceHierarchyConvert.INSTANCE.convert(createReqVO);
        freezingDeviceHierarchyMapper.insert(freezingDeviceHierarchy);
        // 返回
        return freezingDeviceHierarchy.getId();
    }

    @Override
    public void updateFreezingDeviceHierarchy(FreezingDeviceHierarchyUpdateReqVO updateReqVO) {
        // 校验存在
        validateFreezingDeviceHierarchyExists(updateReqVO.getId());
        // 更新
        FreezingDeviceHierarchyDO updateObj = BeanUtils.toBean(updateReqVO, FreezingDeviceHierarchyDO.class);
        freezingDeviceHierarchyMapper.updateById(updateObj);
    }

    @Override
    public void deleteFreezingDeviceHierarchy(Long id) {
        // 校验存在
        validateFreezingDeviceHierarchyExists(id);

        // 校验子层级是否存在，不存在才能删除
        validateChildrenFreezingDeviceHierarchyNotExists(id);

        // 删除 层级本身
        freezingDeviceHierarchyMapper.deleteById(id);

        //删除层级下面的冻藏盒槽位
        freezingTubeStockInfoMapper.delete(FreezingTubeStockInfoDO::getBoxId,id);
    }

    private void validateChildrenFreezingDeviceHierarchyNotExists(Long id) {
        // 校验是否存在子层级
        Long l = freezingDeviceHierarchyMapper.selectCountByPid(id);
        if (l > 0) {
            throw exception(FREEZING_DEVICE_HIERARCHY_EXISTS_CHILDREN);
        }

        // 校验是否存在在库，或者等待回库的冻藏盒槽位 1在库，2等待回库
        LambdaQueryWrapperX<FreezingTubeStockInfoDO> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(FreezingTubeStockInfoDO::getBoxId,id)
                        .in(FreezingTubeStockInfoDO::getStatus, Arrays.asList("1","2"));
        Long count = freezingTubeStockInfoMapper.selectCount(queryWrapperX);
        if (count > 0){
            throw exception(FREEZING_DEVICE_HIERARCHY_EXISTS_CHILDREN);
        }
    }

    private void validateFreezingDeviceHierarchyExists(Long id) {
        if (freezingDeviceHierarchyMapper.selectById(id) == null) {
            throw exception(FREEZING_DEVICE_HIERARCHY_NOT_EXISTS);
        }
    }

    @Override
    public FreezingDeviceHierarchyDO getFreezingDeviceHierarchy(Long id) {
        return freezingDeviceHierarchyMapper.selectById(id);
    }

    @Override
    public List<FreezingDeviceHierarchyDO> getFreezingDeviceHierarchyList(Collection<Long> ids) {
        return freezingDeviceHierarchyMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FreezingDeviceHierarchyDO> getFreezingDeviceHierarchyPage(FreezingDeviceHierarchyPageReqVO pageReqVO) {
        return freezingDeviceHierarchyMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FreezingDeviceHierarchyDO> getFreezingDeviceHierarchyList(FreezingDeviceHierarchyExportReqVO exportReqVO) {
        return freezingDeviceHierarchyMapper.selectList(exportReqVO);
    }

    /**
     * 给某个层级下面添加一个新的层级
     *
     * @param updateReqVO
     */
    @Override
    public void addNewFreezingDeviceHierarchy(FreezingDeviceHierarchyUpdateReqVO updateReqVO) {
        
        List<FreezingTubeStockInfoDO> tubeStockInfoDOList = new ArrayList<>();
        
        Long id = updateReqVO.getParentId();
        //父级的信息
        FreezingDeviceHierarchyDO parentInfo = freezingDeviceHierarchyMapper.selectById(id);


        //判断是上级是否是冷冻设备
        boolean flag = false;

        if (parentInfo == null){
            //可能是第一层级，也就是父级就是冷冻设备
            FreezingDeviceInfoDO freezingDeviceInfoDO = freezingDeviceMapper.selectById(id);
            if (freezingDeviceInfoDO != null){
                flag = true;
            }
        }

        if (!flag && parentInfo.getIsFinalLevel()){
            // 末级冻藏盒不能再添加子集
            throw exception(FREEZING_DEVICE_HIERARCHY_IS_FINAL_LEVEL);
        }

        //判断是否为添加末级，通过freezingBoxId是否为空来判断
        boolean isFinalLevel = updateReqVO.getFreezingBoxId() != null;



        FreezingDeviceHierarchyDO freezingDeviceHierarchyDO = new FreezingDeviceHierarchyDO();
        // 不用再添加levelCode了
        freezingDeviceHierarchyDO.setParentId(id);
        freezingDeviceHierarchyDO.setFreezingDeviceId(updateReqVO.getFreezingDeviceId());
        freezingDeviceHierarchyDO.setName(updateReqVO.getName());
        freezingDeviceHierarchyDO.setLayerType(updateReqVO.getLayerType());
        freezingDeviceHierarchyDO.setIsFinalLevel(false);
        //如果是冻藏盒
        if(isFinalLevel){
            freezingDeviceHierarchyDO.setFreezingBoxId(updateReqVO.getFreezingBoxId()); // 设置冻藏盒id
            freezingDeviceHierarchyDO.setIsFinalLevel(true); //设置为末级
        }


        freezingDeviceHierarchyMapper.insert(freezingDeviceHierarchyDO);


        if (isFinalLevel){
            //如果是冻藏盒类型，初始化它的子级

            //获取冻藏盒信息，然后初始化
            FreezingBoxInfoDO boxInfoDO = freezingBoxInfoMapper.selectById(updateReqVO.getFreezingBoxId());

            int xNum = boxInfoDO.getAxisCapacityX();
            int yNum = boxInfoDO.getAxisCapacityY();

            for (int i = 0; i < xNum; i++) {
                for (int j = 0; j < yNum; j++) {
                    FreezingTubeStockInfoDO tubeStockInfoDO = new FreezingTubeStockInfoDO();
                    Long box = freezingDeviceHierarchyDO.getId();
                    tubeStockInfoDO.setBoxId(box);  //对应的最末的层级
                    tubeStockInfoDO.setCode(String.format("%s:%s-%s",box,(i+1),(j+1))); // 可阅读的编号
                    tubeStockInfoDO.setTubePosition(String.format("%s-%s",i,j)); //相对位置  x-y编号
                    tubeStockInfoDO.setTubePositionX(String.valueOf(i)); //x轴编号
                    tubeStockInfoDO.setTubePositionY(String.valueOf(j)); //y轴编号
                    tubeStockInfoDO.setStatus("0"); //0 表示这里是空槽位
                    tubeStockInfoDOList.add(tubeStockInfoDO);
                }
            }

            freezingTubeStockInfoMapper.insertBatch(tubeStockInfoDOList);
        }

    }
}
