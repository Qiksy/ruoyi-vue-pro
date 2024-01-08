package cn.iocoder.yudao.module.strain.controller.admin.freezingdeviceinfo.vo;

import lombok.Data;

/**
 * 用来接受请求中的层级信息
 * @author linr
 * @since 2024/1/4 0:39
 */
@Data
public class DeviceLayerVO {
    Integer index; //层数
    Integer num;//数量
    String Type; // 类型
}
