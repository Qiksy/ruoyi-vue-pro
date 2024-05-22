package cn.iocoder.yudao.module.strain.service.regenerationrecord;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.strain.controller.admin.regenerationrecord.vo.*;
import cn.iocoder.yudao.module.strain.dal.dataobject.regenerationrecord.RegenerationRecordDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 样品复壮传代记录 Service 接口
 *
 * @author 超级管理员
 */
public interface RegenerationRecordService {

    /**
     * 创建样品复壮传代记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRegenerationRecord(@Valid RegenerationRecordSaveReqVO createReqVO);

    /**
     * 更新样品复壮传代记录
     *
     * @param updateReqVO 更新信息
     */
    void updateRegenerationRecord(@Valid RegenerationRecordSaveReqVO updateReqVO);

    /**
     * 删除样品复壮传代记录
     *
     * @param id 编号
     */
    void deleteRegenerationRecord(Long id);

    /**
     * 获得样品复壮传代记录
     *
     * @param id 编号
     * @return 样品复壮传代记录
     */
    RegenerationRecordDO getRegenerationRecord(Long id);

    /**
     * 获得样品复壮传代记录分页
     *
     * @param pageReqVO 分页查询
     * @return 样品复壮传代记录分页
     */
    PageResult<RegenerationRecordDO> getRegenerationRecordPage(RegenerationRecordPageReqVO pageReqVO);

}