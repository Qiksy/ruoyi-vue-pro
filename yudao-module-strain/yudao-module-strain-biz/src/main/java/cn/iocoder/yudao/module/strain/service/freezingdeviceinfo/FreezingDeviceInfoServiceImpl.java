package cn.iocoder.yudao.module.strain.service.freezingdeviceinfo;

import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdevicehierarchy.FreezingDeviceHierarchyMapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdeviceinfo.FreezingDeviceInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.strain.convert.freezingdeviceinfo.FreezingDeviceInfoConvert;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdeviceinfo.FreezingDeviceInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.strain.enums.ErrorCodeConstants.*;

/**
 * 冷冻设备信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class FreezingDeviceInfoServiceImpl implements FreezingDeviceInfoService {

    @Resource
    private FreezingDeviceInfoMapper freezingDeviceInfoMapper;

    @Resource
    private FreezingDeviceHierarchyMapper freezingDeviceHierarchyMapper;

    @Override
    public Long createFreezingDeviceInfo(FreezingDeviceInfoCreateReqVO createReqVO) {
        // 插入
        FreezingDeviceInfoDO freezingDeviceInfo = FreezingDeviceInfoConvert.INSTANCE.convert(createReqVO);
        freezingDeviceInfoMapper.insert(freezingDeviceInfo);



        //todo 新增层级关系
        DeviceLayerVO[] layerList = createReqVO.getLayerList();

        List<List<FreezingDeviceHierarchyDO>> hierarchyList = new ArrayList<>(); // 最终需要插入的层级关系

        if (layerList!=null && layerList.length!=0){

            List<DeviceLayerVO> list = Arrays.stream(layerList).sorted(Comparator.comparing(DeviceLayerVO::getIndex)).toList();




            for (int i = 0; i < list.size(); i++) {
                List<FreezingDeviceHierarchyDO> tempList = new ArrayList<>(); // 临时层级关系
                DeviceLayerVO deviceLayerVO = list.get(i); // 获取对象

                for (int j = 0; j < deviceLayerVO.getNum(); j++) {
                    int xnum =1 ;  //表示上一层的父级数量


                    if (deviceLayerVO.getIndex()>0){
                        //如果不是第一层级，还要再加一套循环
                        xnum = hierarchyList.get(i-1).size();
                    }

                    for (int x = 0; x < xnum; x++) {
                        FreezingDeviceHierarchyDO tempDO = new FreezingDeviceHierarchyDO();
                        tempDO.setId(DefaultIdentifierGenerator.getInstance().nextId(null)); // 设置主键
                        tempDO.setFreezingDeviceId(freezingDeviceInfo.getId()); // 设置设备主键
                        tempDO.setIsFinalLevel(false); // 是否为末级
                        tempDO.setLayerType(deviceLayerVO.getType()); // 设置层级类型

                        if (deviceLayerVO.getIndex()>0){
                            tempDO.setParentId(hierarchyList.get(i-1).get(x).getId()); // 设置父级主键
                        }else {
                            tempDO.setParentId(null); // 设置父级主键
                        }
                        tempList.add(tempDO);//添加到临时层级关系
                    }
                }
                hierarchyList.add(tempList);//添加到最终层级关系
            }

            // 插入层级关系
            for (List<FreezingDeviceHierarchyDO> tempList : hierarchyList) {
                for (FreezingDeviceHierarchyDO freezingDeviceHierarchyDO : tempList) {
                    freezingDeviceHierarchyMapper.insert(freezingDeviceHierarchyDO);
                }
            }

            log.info("createFreezingDeviceInfo success, id={}", freezingDeviceInfo.getId());

        }



        if (!hierarchyList.isEmpty()){
            // 插入末级
            List<FreezingDeviceHierarchyDO> last = hierarchyList.getLast(); //获取最后一一个层级
            DeviceLayerVO endBox = createReqVO.getEndBox();

            if (endBox!=null){
                List<FreezingDeviceHierarchyDO> tempList = new ArrayList<>(); // 临时层级关系

                for (int i = 0; i < last.size(); i++) {
                    for (int integer = 0; integer < endBox.getNum(); integer++) {
                        FreezingDeviceHierarchyDO temp = new FreezingDeviceHierarchyDO();
                        temp.setParentId(last.get(i).getId()); //设置父级主键
                        temp.setFreezingDeviceId(freezingDeviceInfo.getId()); // 设置设备主键
                        temp.setIsFinalLevel(true); // 是否为末级
                        temp.setFreezingBoxId(Long.valueOf(endBox.getType())); // 设置末级类型
                        tempList.add(temp);
                    }
                }

                for (FreezingDeviceHierarchyDO freezingDeviceHierarchyDO : tempList) {
                    freezingDeviceHierarchyMapper.insert(freezingDeviceHierarchyDO);
                }

            }
        }else{
            //如果没有初始的层级
            DeviceLayerVO endBox = createReqVO.getEndBox();

            if (endBox!=null){
                List<FreezingDeviceHierarchyDO> tempList = new ArrayList<>(); // 临时层级关系
                for (int i = 0; i < endBox.getNum(); i++) {
                    FreezingDeviceHierarchyDO temp = new FreezingDeviceHierarchyDO();
                    temp.setParentId(null); //设置父级主键
                    temp.setFreezingDeviceId(freezingDeviceInfo.getId()); // 设置设备主键
                    temp.setIsFinalLevel(true); // 是否为末级
                    temp.setFreezingBoxId(Long.valueOf(endBox.getType())); // 设置末级类型
                    tempList.add(temp);
                }

                for (FreezingDeviceHierarchyDO freezingDeviceHierarchyDO : tempList) {
                    freezingDeviceHierarchyMapper.insert(freezingDeviceHierarchyDO);
                }
            }

        }







        // 返回
        return freezingDeviceInfo.getId();
    }

    @Override
    public void updateFreezingDeviceInfo(FreezingDeviceInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateFreezingDeviceInfoExists(updateReqVO.getId());
        // 更新
        FreezingDeviceInfoDO updateObj = FreezingDeviceInfoConvert.INSTANCE.convert(updateReqVO);
        freezingDeviceInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteFreezingDeviceInfo(Long id) {
        // 校验存在
        validateFreezingDeviceInfoExists(id);

        // 校验子层级是否存在
        validateFreezingDeviceHierarchyExistsByParentId(id);

        // 删除
        freezingDeviceInfoMapper.deleteById(id);
    }

    /**
     * 校验子层级是否存在
     * @param id
     */
    private void validateFreezingDeviceHierarchyExistsByParentId(Long id) {
        LambdaQueryWrapperX<FreezingDeviceHierarchyDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eqIfPresent(FreezingDeviceHierarchyDO::getFreezingDeviceId, id);
        if (freezingDeviceHierarchyMapper.selectCount(queryWrapper)>0) {
            //如果大于0，说明存在子层级
            throw exception(FREEZING_DEVICE_INFO_EXISTS_CHILDREN);
        }
    }

    private void validateFreezingDeviceInfoExists(Long id) {
        if (freezingDeviceInfoMapper.selectById(id) == null) {
            throw exception(FREEZING_DEVICE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public FreezingDeviceInfoDO getFreezingDeviceInfo(Long id) {
        return freezingDeviceInfoMapper.selectById(id);
    }

    @Override
    public List<FreezingDeviceInfoDO> getFreezingDeviceInfoList(Collection<Long> ids) {
        return freezingDeviceInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<FreezingDeviceInfoDO> getFreezingDeviceInfoPage(FreezingDeviceInfoPageReqVO pageReqVO) {
        return freezingDeviceInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<FreezingDeviceInfoDO> getFreezingDeviceInfoList(FreezingDeviceInfoExportReqVO exportReqVO) {
        return freezingDeviceInfoMapper.selectList(exportReqVO);
    }

}
