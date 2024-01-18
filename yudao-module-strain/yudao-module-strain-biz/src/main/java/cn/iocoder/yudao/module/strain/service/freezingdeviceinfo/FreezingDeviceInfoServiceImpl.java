package cn.iocoder.yudao.module.strain.service.freezingdeviceinfo;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.strain.controller.admin.freezingdevicehierarchy.vo.FreezingDeviceHierarchyRespVO;
import cn.iocoder.yudao.module.strain.convert.freezingdevicehierarchy.FreezingDeviceHierarchyConvert;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingboxinfo.FreezingBoxInfoDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingdevicehierarchy.FreezingDeviceHierarchyDO;
import cn.iocoder.yudao.module.strain.dal.dataobject.freezingtubestockinfo.FreezingTubeStockInfoDO;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingboxinfo.FreezingBoxInfoMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingdevicehierarchy.FreezingDeviceHierarchyMapper;
import cn.iocoder.yudao.module.strain.dal.mysql.freezingtubestockinfo.FreezingTubeStockInfoMapper;
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
    private FreezingBoxInfoMapper freezingBoxInfoMapper;


    @Resource
    private FreezingTubeStockInfoMapper freezingTubeStockInfoMapper;

    @Resource
    private DictDataApi dictDataApi;

    /**
     * 主要分三部
     * 1.新增层级
     * 2.新增末级
     * 3.初始化冷冻盒的每个槽位
     * @param createReqVO 创建信息
     * @return 主键
     */
    @Override
    public Long createFreezingDeviceInfo(FreezingDeviceInfoCreateReqVO createReqVO) {
        // 插入
        FreezingDeviceInfoDO freezingDeviceInfo = FreezingDeviceInfoConvert.INSTANCE.convert(createReqVO);
        freezingDeviceInfoMapper.insert(freezingDeviceInfo);

        //获取层级类型字典
        Map<String, DictDataRespDTO> strainDeviceLayerType = dictDataApi.getDictDataMap("strain_device_layer_type");


        //===================================================
        // ===========1.新增层级关系  并且给每个层级进行命名=========
        //===================================================
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
                            tempDO.setParentId(freezingDeviceInfo.getId()); // 设置父级主键
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

        // ======================================================
        // ===============2.插入最末级，也就是冷冻盒====================
        //=======================================================

        List<FreezingDeviceHierarchyDO> boxHierarchyList = new ArrayList<>(); // 临时层级关系

        if (!hierarchyList.isEmpty()){
            // 插入末级
            List<FreezingDeviceHierarchyDO> last = hierarchyList.getLast(); //获取最后一一个层级
            DeviceLayerVO endBox = createReqVO.getEndBox();

            if (endBox!=null){

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
                        boxHierarchyList.add(temp);
                    }
                }

                for (FreezingDeviceHierarchyDO freezingDeviceHierarchyDO : boxHierarchyList) {
                    freezingDeviceHierarchyMapper.insert(freezingDeviceHierarchyDO);
                }

            }
        }else{
            //如果没有初始的层级
            DeviceLayerVO endBox = createReqVO.getEndBox();

            if (endBox!=null){
                for (int i = 0; i < endBox.getNum(); i++) {
                    FreezingDeviceHierarchyDO temp = new FreezingDeviceHierarchyDO();
                    temp.setParentId(null); //设置父级主键
                    temp.setFreezingDeviceId(freezingDeviceInfo.getId()); // 设置设备主键
                    temp.setIsFinalLevel(true); // 是否为末级
                    temp.setFreezingBoxId(Long.valueOf(endBox.getType())); // 设置末级类型
                    boxHierarchyList.add(temp);
                }

                for (FreezingDeviceHierarchyDO freezingDeviceHierarchyDO : boxHierarchyList) {
                    freezingDeviceHierarchyMapper.insert(freezingDeviceHierarchyDO);
                }
            }

        }



        //=============================================
        //=================3初始化冷冻盒的槽位=============
        //=============================================


        //3.1 获取冷冻盒的具体信息

        LambdaQueryWrapperX<FreezingBoxInfoDO> queryWrapper = new LambdaQueryWrapperX<>();
        //这里需要将String转换为Long类型。因为提高通用性，这个type有的时候传输的是字典类型，有的时候是主键long
        Long boxId = Long.valueOf(createReqVO.getEndBox().getType());
        queryWrapper.eq(FreezingBoxInfoDO::getId,boxId);
        FreezingBoxInfoDO boxInfoDO = freezingBoxInfoMapper.selectOne(queryWrapper);

        Integer xNum = boxInfoDO.getAxisCapacityX();//x容量
        Integer yNum = boxInfoDO.getAxisCapacityY();//y容量
        Integer xCodeType = Integer.valueOf(boxInfoDO.getAxisCodeTypeX());//x轴编号类型
        Integer yCodeType = Integer.valueOf(boxInfoDO.getAxisCodeTypeY());//y轴编号类型


        List<FreezingTubeStockInfoDO> tubeStockInfoDOS = new ArrayList<>(); // 槽位表先初始化

        for (FreezingDeviceHierarchyDO freezingDeviceHierarchyDO : boxHierarchyList) {
            for (int i = 0; i < xNum; i++) {
                for (int j = 0; j < yNum; j++) {
                    //循环插入
                    FreezingTubeStockInfoDO tubeStockInfoDO = new FreezingTubeStockInfoDO();
                    Long box = freezingDeviceHierarchyDO.getId();
                    tubeStockInfoDO.setBoxId(box);  //对应的最末的层级
                    tubeStockInfoDO.setCode(String.format("%s:%s-%s",box,(i+1),(j+1))); // 可阅读的编号
                    tubeStockInfoDO.setTubePosition(String.format("%1s-%2s",i,j)); //相对位置  x-y编号
                    tubeStockInfoDO.setTubePositionX(String.valueOf(i)); //x轴编号
                    tubeStockInfoDO.setTubePositionY(String.valueOf(j)); //y轴编号
                    tubeStockInfoDO.setStatus("0"); //0 表示这里是空槽位
                    tubeStockInfoDOS.add(tubeStockInfoDO);
                }
            }
        }

        //插入数据库
        freezingTubeStockInfoMapper.insertBatch(tubeStockInfoDOS);

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
//        不再带出层级关系，转为单独请求
//        LambdaQueryWrapperX<FreezingDeviceHierarchyDO> queryWrapper = new LambdaQueryWrapperX<>();
//        queryWrapper.eq(FreezingDeviceHierarchyDO::getFreezingDeviceId, id);
//        //层级列表
//        List<FreezingDeviceHierarchyDO> hierarchyDOS = freezingDeviceHierarchyMapper.selectList(queryWrapper);
//
//        //转换成vo
//        List<FreezingDeviceHierarchyRespVO> hierarchyRespVOS = BeanUtils.toBean(hierarchyDOS, FreezingDeviceHierarchyRespVO.class);
//        respVO.setHierarchyList(hierarchyRespVOS);

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


    /**
     * 查询某个冷冻设备的层级信息，包括它自身，成为一个树节点
     *
     * @param id
     * @return
     */
    @Override
    public FreezingDeviceInfoLevelRespVO getFreezingDeviceInfoLevel(Long id) {

        FreezingDeviceInfoDO freezingDeviceInfoDO = freezingDeviceInfoMapper.selectById(id);

        if (freezingDeviceInfoDO==null){
            //不存在则报错
            throw exception(FREEZING_DEVICE_INFO_NOT_EXISTS);
        }

        //构建父节点
        FreezingDeviceInfoLevelRespVO parent = new FreezingDeviceInfoLevelRespVO(); // 父级id
        parent.setId(freezingDeviceInfoDO.getId());
        parent.setIsFinalLevel(false);
        parent.setName(freezingDeviceInfoDO.getName());
        parent.setFreezingDeviceId(freezingDeviceInfoDO.getId());
        parent.setLayerType(null);

        // 获取层级信息
        parent.setChildren(getChildren(parent.getId()));
        return parent;
    }

    /**
     * 遍历获取获取子节点
     * @param id
     * @return
     */
    private List<FreezingDeviceInfoLevelRespVO> getChildren(Long id) {
        List<FreezingDeviceHierarchyDO> hierarchyDOS = freezingDeviceHierarchyMapper.selectList(new LambdaQueryWrapperX<FreezingDeviceHierarchyDO>().eq(FreezingDeviceHierarchyDO::getParentId, id));
        if (hierarchyDOS.isEmpty()){
            //返回一个空的list
            return new ArrayList<>();
        }
        List<FreezingDeviceInfoLevelRespVO> result = BeanUtils.toBean(hierarchyDOS, FreezingDeviceInfoLevelRespVO.class);

        for (FreezingDeviceInfoLevelRespVO freezingDeviceInfoLevelRespVO : result) {
            freezingDeviceInfoLevelRespVO.setChildren(getChildren(freezingDeviceInfoLevelRespVO.getId()));
        }
        return result;
    }


}
