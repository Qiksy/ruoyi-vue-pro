package cn.iocoder.yudao.module.strain.service.microbebasicinfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.microbebasicinfo.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.microbebasicinfo.MicrobeBasicInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 菌种信息 Service 接口
 *
 * @author 芋道源码
 */
public interface MicrobeBasicInfoService {

    /**
     * 创建菌种信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMicrobeBasicInfo(@Valid MicrobeBasicInfoSaveReqVO createReqVO);

    /**
     * 更新菌种信息
     *
     * @param updateReqVO 更新信息
     */
    void updateMicrobeBasicInfo(@Valid MicrobeBasicInfoSaveReqVO updateReqVO);

    /**
     * 删除菌种信息
     *
     * @param id 编号
     */
    void deleteMicrobeBasicInfo(Long id);

    /**
     * 获得菌种信息
     *
     * @param id 编号
     * @return 菌种信息
     */
    MicrobeBasicInfoDO getMicrobeBasicInfo(Long id);

    /**
     * 获得菌种信息分页
     *
     * @param pageReqVO 分页查询
     * @return 菌种信息分页
     */
    PageResult<MicrobeBasicInfoDO> getMicrobeBasicInfoPage(MicrobeBasicInfoPageReqVO pageReqVO);

}