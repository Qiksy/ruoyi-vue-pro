package cn.iocoder.yudao.module.sale.job;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.module.sale.controller.admin.productioninfo.vo.ProductionInfoRespVO;
import cn.iocoder.yudao.module.sale.dal.dataobject.prodlineinfo.ProdlineInfoDO;
import cn.iocoder.yudao.module.sale.dal.dataobject.productioninfo.ProductionInfoDO;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarbasclass.ProductionMarbasclassDO;
import cn.iocoder.yudao.module.sale.dal.dataobject.productionmarsaleclass.ProductionMarsaleclassDO;
import cn.iocoder.yudao.module.sale.dal.mysql.prodlineinfo.ProdlineInfoMapper;
import cn.iocoder.yudao.module.sale.dal.mysql.productioninfo.ProductionInfoMapper;
import cn.iocoder.yudao.module.sale.dal.mysql.productionmarbasclass.ProductionMarbasclassMapper;
import cn.iocoder.yudao.module.sale.dal.mysql.productionmarsaleclass.ProductionMarsaleclassMapper;
import cn.iocoder.yudao.module.system.api.openapi.BoenOpenApi;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static cn.iocoder.yudao.module.system.enums.openapi.BoenApiUrlEnums.*;

/**
 * 同步产品线、销售分类、物料分类、物料基本信息的定时任务
 *
 * @author linr
 * @since 2023/12/20 10:54
 */
@Component
@Slf4j
public class MaterialSyncJob implements JobHandler {


    @Resource
    private BoenOpenApi openApi;

    @Resource
    private ProdlineInfoMapper prodlineInfoMapper;

    @Resource
    private ProductionMarsaleclassMapper productionMarsaleclassMapper;

    @Resource
    private ProductionMarbasclassMapper productionMarbasclassMapper;

    @Resource
    private ProductionInfoMapper productionInfoMapper;

    /**
     * 执行任务
     * 1.同步产品线
     * 2.同步销售分类
     * 3.同步物料分类
     * 4.同步物料基本信息
     *
     * @param param 参数
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public String execute(String param) throws Exception {
        // 同步产品线
        syncProdline();
        // 同步销售分类
        syncSaleClass();
        //同步物料基本分类
        syncMaterialClass();
        //同步物料基本信息
        syncMaterialBaseInfo();

        return null;
    }

    /**
     * 同步物料基本信息
     */
    private void syncMaterialBaseInfo() {
        log.info("同步物料基本信息");
        ParameterizedTypeReference<List<ProductionInfoRespVO>> typeReference = new ParameterizedTypeReference<List<ProductionInfoRespVO>>() {
        };
        List<ProductionInfoRespVO> list = openApi.sendRequest(typeReference, MATERIAL_BASE_INFO_SYNC_URL.getUrl(), "GET",null);

        //todo 进行一些数据转换
        for (ProductionInfoRespVO item : list) {
            String specStr = item.getSpecStr();
            //判断specStr是否为纯数字或者纯数字+KG，是的话就把数字转为BigDecimal
            if (specStr!=null&&specStr.matches("^[0-9]+(KG|kg)?$")) {
                //利用正则取出数字
                String num = specStr.replaceAll("[^0-9]", "");
                item.setSpec(BigDecimal.valueOf(Long.parseLong(num)));
            }
        }


        List<ProductionInfoDO> productionInfoDOList = BeanUtils.toBean(list, ProductionInfoDO.class);

        productionInfoMapper.insertOrUpdateBatch(productionInfoDOList);
        log.info("同步物料基本信息完成");

    }

    /**
     * 同步物料分类
     */
    private void syncMaterialClass() {
        log.info("同步物料分类");
        ParameterizedTypeReference<List<ProductionMarbasclassDO>> typeReference = new ParameterizedTypeReference<List<ProductionMarbasclassDO>>() {
        };
        List<ProductionMarbasclassDO> list = openApi.sendRequest(typeReference, MATERIAL_CLASS_SYNC_URL.getUrl(), "GET",null);
        productionMarbasclassMapper.insertOrUpdateBatch(list);
        log.info("同步物料分类完成");
    }

    /**
     * 同步销售分类
     */
    private void syncSaleClass() {
        log.info("同步销售分类");
        ParameterizedTypeReference<List<ProductionMarsaleclassDO>> typeReference = new ParameterizedTypeReference<List<ProductionMarsaleclassDO>>() {
        };
        List<ProductionMarsaleclassDO> list = openApi.sendRequest(typeReference, SALE_CLASS_SYNC_URL.getUrl(), "GET",null);
        productionMarsaleclassMapper.insertOrUpdateBatch(list);
        log.info("同步销售分类完成");
    }

    /**
     * 同步产品线
     */
    private void syncProdline() {
        log.info("同步产品线");
        ParameterizedTypeReference<List<ProdlineInfoDO>> typeReference = new ParameterizedTypeReference<List<ProdlineInfoDO>>() {
        };
        List<ProdlineInfoDO> prodlineInfoDOList = openApi.sendRequest(typeReference, PRODLINE_SYNC_URL.getUrl(), "GET",null);
        prodlineInfoMapper.insertOrUpdateBatch(prodlineInfoDOList);
        log.info("同步产品线完成");
    }


}
