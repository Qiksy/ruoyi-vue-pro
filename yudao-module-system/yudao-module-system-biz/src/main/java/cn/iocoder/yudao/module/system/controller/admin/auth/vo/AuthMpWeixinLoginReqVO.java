package cn.iocoder.yudao.module.system.controller.admin.auth.vo;

import cn.iocoder.yudao.framework.common.validation.InEnum;
import cn.iocoder.yudao.module.system.enums.social.SocialTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author linr
 * @since 2024/6/26 下午2:37
 */
@Schema(description = "管理后台 - 微信登录 Request VO")
@Data
public class AuthMpWeixinLoginReqVO {


    @Schema(description = "小程序类型 判断是企业微信还是普通的微信", requiredMode = Schema.RequiredMode.REQUIRED, example = "wxwork")
    @NotNull
    private String mpType;


    @Schema(description = "授权码",requiredMode = Schema.RequiredMode.REQUIRED, example = "asajlshfjikasdhf")
    @NotEmpty(message = "授权码不能为空")
    private String code;


//    @Schema(description = "state 自定义字段，一般都是随机的，避免攻击", requiredMode = Schema.RequiredMode.REQUIRED, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
//    @NotEmpty(message = "state 不能为空")
//    private String state;

}
