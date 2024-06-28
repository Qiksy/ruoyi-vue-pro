package cn.iocoder.yudao.module.oa.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;// TODO 待办：请将下面的错误码复制到 yudao-module-oa-api 模块的 ErrorCodeConstants 类中。注意，请给“TODO 补充编号”设置一个错误码编号！！！

public  interface ErrorCodeConstants {
    // ========== 会议签到 TODO 补充编号 ==========
    ErrorCode SIGN_IN_INFO_NOT_EXISTS = new ErrorCode(90_88_001, "会议签到不存在");
}
