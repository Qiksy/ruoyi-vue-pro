package cn.iocoder.yudao.module.oa.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;// TODO 待办：请将下面的错误码复制到 yudao-module-oa-api 模块的 ErrorCodeConstants 类中。注意，请给“TODO 补充编号”设置一个错误码编号！！！

public  interface ErrorCodeConstants {
    // ========== 会议签到  ==========
    ErrorCode SIGN_IN_INFO_NOT_EXISTS = new ErrorCode(90_88_001, "会议签到不存在");

//  签到已经结束了
    ErrorCode SIGN_IN_INFO_ENDED = new ErrorCode(90_88_002, "签到已经结束了");
    // 还没有开始
    ErrorCode SIGN_IN_INFO_NOT_BEGIN = new ErrorCode(90_88_003, "签到还没有开始");

    //不在签到时间范围内
    ErrorCode SIGN_IN_INFO_NOT_IN_TIME_RANGE = new ErrorCode(90_88_004, "不在签到时间范围内");

}
