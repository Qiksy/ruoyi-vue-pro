package cn.iocoder.yudao.module.strain.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 *
 * strain 系统，使用 98_000_000 段
 */
public interface ErrorCodeConstants {

    // ========== 培养基数据信息 8196891 ==========
    ErrorCode CULTURE_MEDIUM_DATA_INFO_NOT_EXISTS = new ErrorCode(98_001_001, "培养基数据信息不存在");

    // ========== 冷冻盒信息 98_002_001 ==========
    ErrorCode FREEZING_BOX_INFO_NOT_EXISTS = new ErrorCode(98_002_001, "冷冻盒信息不存在");

    // ========== 冷冻管 98_003_001 ==========
    ErrorCode FREEZING_TUBE_INFO_NOT_EXISTS = new ErrorCode(98_003_001, "冷冻管基本信息不存在");

}
