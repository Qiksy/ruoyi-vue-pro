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


    // ========== 存放区域信息 98_004_001 ==========
    ErrorCode STORAGE_AREA_INFO_NOT_EXISTS = new ErrorCode(98_004_001, "存放区域信息不存在");

    // ========== 冷冻设备信息 98_005_001 ==========
    ErrorCode FREEZING_DEVICE_INFO_NOT_EXISTS = new ErrorCode(98_005_001, "冷冻设备信息不存在");
    ErrorCode FREEZING_DEVICE_INFO_EXISTS_CHILDREN = new ErrorCode(98_005_002, "冷冻设备信息存在子层级");

    // ========== 冷冻设备层级 98_006_001 ==========
    ErrorCode FREEZING_DEVICE_HIERARCHY_NOT_EXISTS = new ErrorCode(98_006_001, "冷冻设备层级不存在");
    ErrorCode FREEZING_DEVICE_HIERARCHY_EXISTS_CHILDREN = new ErrorCode(98_006_002, "冷冻设备层级存在子层级");
    ErrorCode FREEZING_DEVICE_HIERARCHY_IS_FINAL_LEVEL = new ErrorCode(98_006_002, "冷冻设备层级已经是末级，不能再添加子层级");

    // ========== 菌种信息 98_007_001 ==========
    ErrorCode MICROBE_BASIC_INFO_NOT_EXISTS = new ErrorCode(98_007_001, "菌种信息不存在");


    // ========== 冷冻管库存预录入 98_008_001 ==========
    ErrorCode FREEZING_TUBE_STOCK_PRE_ENTRY_NOT_EXISTS = new ErrorCode(98_008_001, "冷冻管库存预录入不存在");

}
