package cn.iocoder.yudao.module.strain.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 *
 * system 系统，使用 1-002-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== AUTH 模块 1-002-000-000 ==========
    // ========== 培养基数据信息 8196891 ==========
    ErrorCode CULTURE_MEDIUM_DATA_INFO_NOT_EXISTS = new ErrorCode(2_00_021, "培养基数据信息不存在");

}
