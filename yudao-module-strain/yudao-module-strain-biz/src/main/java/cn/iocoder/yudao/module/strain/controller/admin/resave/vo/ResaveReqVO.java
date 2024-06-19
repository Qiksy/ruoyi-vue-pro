package cn.iocoder.yudao.module.strain.controller.admin.resave.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;


@Data
public class ResaveReqVO extends PageParam {

    /**
     * 申请单号
     */
    private String applyCode;
}
