package cn.iocoder.yudao.module.strain.service.freezingdeviceinfo;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo.FreezingDeviceHierarchyRespVO;
import cn.iocoder.yudao.module.strain.convert.freezingdevicehierarchy.FreezingDeviceHierarchyConvert;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdevicehierarchy.FreezingDeviceHierarchyMapper;
import cn.iocoder.yudao.module.system.api.dict.DictDataApi;
import cn.iocoder.yudao.module.system.api.dict.dto.DictDataRespDTO;
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

    @Resource
    private DictDataApi dictDataApi;

    @Override
    public Long createFreezingDeviceInfo(FreezingDeviceInfoCreateReqVO createReqVO) {
        // 插入
        FreezingDeviceInfoDO freezingDeviceInfo = FreezingDeviceInfoConvert.INSTANCE.convert(createReqVO);
        freezingDeviceInfoMapper.insert(freezingDeviceInfo);

        //获取层级类型字典
        Map<String, DictDataRespDTO> strainDeviceLayerType = dictDataApi.getDictDataMap("strain_device_layer_type");


        //todo 新增层级关系  并且给每个层级进行命名，每个层级的命名不能重复
        DeviceLayerVO[] layerList = createReqVO.getLayerList();

        List<List<FreezingDeviceHierarchyDO>> hierarchyList = new ArrayList<>(); // 最终需要插入的层级关系

        if (layerList!=null && layerList.length!=0){
            //对层级信息进行排序
            List<DeviceLayerVO> list = Arrays.stream(layerList).sorted(Comparator.comparing(DeviceLayerVO::getIndex)).toList();




            for (int i = 0; i < list.size(); i++) {
                // i 代表第几层级

                List<FreezingDeviceHierarchyDO> tempList = new ArrayList<>(); // 临时层级关系
                DeviceLayerVO deviceLayerVO = list.get(i); // 获取对象

                for (int j = 0; j < deviceLayerVO.getNum(); j++) {
                    // j 代表层级中的第几个

                    int xnum =1 ;  //表示上一层的父级数量


                    if (deviceLayerVO.getIndex()>0){
                        //如果不是第一层级，还要再加一套循环
                        xnum = hierarchyList.get(i-1).size();
                    }

                    for (int x = 0; x < xnum; x++) {
                        //x就代表了整个同级中的第几个
                        FreezingDeviceHierarchyDO tempDO = new FreezingDeviceHierarchyDO();
                        tempDO.setId(DefaultIdentifierGenerator.getInstance().nextId(null)); // 设置主键
                        tempDO.setFreezingDeviceId(freezingDeviceInfo.getId()); // 设置设备主键
                        tempDO.setIsFinalLevel(false); // 是否为末级
                        tempDO.setLayerType(deviceLayerVO.getType()); // 设置层级类型

                        String typeName = strainDeviceLayerType.getOrDefault(deviceLayerVO.getType(), new DictDataRespDTO().setLabel("未知")).getLabel();
                        //
                        if (deviceLayerVO.getIndex()>0){
                            //层级编码
                            String currentLevelCode = String.format("%02d", j + 1);
                            String parentLevelCode = hierarchyList.get(i-1).get(x).getLevelCode();
                            tempDO.setLevelCode(parentLevelCode+"-"+currentLevelCode);

                            tempDO.setName(parentLevelCode+"-"+currentLevelCode+typeName);

                            tempDO.setParentId(hierarchyList.get(i-1).get(x).getId()); // 设置父级主键
                        }else {
                            //层级编码两位数，不足开头补零
                            String levelCode = String.format("%02d", j + 1);
                            tempDO.setLevelCode(levelCode);
                            //设置名称
                            tempDO.setName(String.format("%02d", j + 1)+typeName);
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

//                        int num = i*endBox.getNum()+integer+1;
                        String levelCode = String.format("%02d", integer + 1);
                        String parentLevelCode = last.get(i).getLevelCode();
                        temp.setName(parentLevelCode+"-"+levelCode+"冷冻盒");
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
    public FreezingDeviceInfoRespVO getFreezingDeviceInfo(Long id) {


        FreezingDeviceInfoDO freezingDeviceInfoDO = freezingDeviceInfoMapper.selectById(id);

        FreezingDeviceInfoRespVO respVO = BeanUtils.toBean(freezingDeviceInfoDO, FreezingDeviceInfoRespVO.class);
        //todo 带出层级关系
        LambdaQueryWrapperX<FreezingDeviceHierarchyDO> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(FreezingDeviceHierarchyDO::getFreezingDeviceId, id);
        //层级列表
        List<FreezingDeviceHierarchyDO> hierarchyDOS = freezingDeviceHierarchyMapper.selectList(queryWrapper);

        //转换成vo
        List<FreezingDeviceHierarchyRespVO> hierarchyRespVOS = BeanUtils.toBean(hierarchyDOS, FreezingDeviceHierarchyRespVO.class);
        respVO.setHierarchyList(hierarchyRespVOS);

        return respVO;
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
