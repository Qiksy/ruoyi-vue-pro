package cn.iocoder.yudao.module.sale.service.prodlineinfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.sale.controller.admin.prodlineinfo.vo.*;
import cn.iocoder.yudao.module.sale.dal.dataobject.prodlineinfo.ProdlineInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 产品线 Service 接口
 *
 * @author 芋道源码
 */
public interface ProdlineInfoService {

    /**
     * 创建产品线
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProdlineInfo(@Valid ProdlineInfoSaveReqVO createReqVO);

    /**
     * 更新产品线
     *
     * @param updateReqVO 更新信息
     */
    void updateProdlineInfo(@Valid ProdlineInfoSaveReqVO updateReqVO);

    /**
     * 删除产品线
     *
     * @param id 编号
     */
    void deleteProdlineInfo(Long id);

    /**
     * 获得产品线
     *
     * @param id 编号
     * @return 产品线
     */
    ProdlineInfoDO getProdlineInfo(Long id);

    /**
     * 获得产品线列表
     *
     * @param listReqVO 查询条件
     * @return 产品线列表
     */
    List<ProdlineInfoDO> getProdlineInfoList(ProdlineInfoListReqVO listReqVO);

}