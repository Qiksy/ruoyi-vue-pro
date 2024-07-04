package cn.iocoder.yudao.module.system.api.tencent.dto;

import lombok.Data;

import java.nio.ByteBuffer;

@Data
public class WxacodeRespVO {
        private Integer errcode;
        private String errmsg;
        private ByteBuffer buffer;
    }