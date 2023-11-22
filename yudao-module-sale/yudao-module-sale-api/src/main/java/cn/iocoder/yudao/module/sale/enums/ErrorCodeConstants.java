package cn.iocoder.yudao.module.sale.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {

    // TODO 待办：请将下面的错误码复制到 yudao-module-sale-api 模块的 ErrorCodeConstants
// ========== 产品线 60_001 ==========
    ErrorCode PRODLINE_INFO_NOT_EXISTS = new ErrorCode(60_001_01, "产品线不存在");
    ErrorCode PRODLINE_INFO_EXITS_CHILDREN = new ErrorCode(60_001_02, "存在存在子产品线，无法删除");
    ErrorCode PRODLINE_INFO_PARENT_NOT_EXITS = new ErrorCode(60_001_03,"父级产品线不存在");
    ErrorCode PRODLINE_INFO_PARENT_ERROR = new ErrorCode(60_001_04, "不能设置自己为父产品线");
    ErrorCode PRODLINE_INFO_NAME_DUPLICATE = new ErrorCode(60_001_05, "已经存在该名称的产品线");
    ErrorCode PRODLINE_INFO_PARENT_IS_CHILD = new ErrorCode(60_001_06, "不能设置自己的子ProdlineInfo为父ProdlineInfo");


    // TODO 待办：请将下面的错误码复制到 yudao-module-sale-api 模块的 ErrorCodeConstants
// ========== 销售分类 70_001 ==========
    ErrorCode PRODUCTION_MARSALECLASS_NOT_EXISTS = new ErrorCode(70_001_01, "销售分类不存在");
    ErrorCode PRODUCTION_MARSALECLASS_EXITS_CHILDREN = new ErrorCode(70_001_02, "存在存在子销售分类，无法删除");
    ErrorCode PRODUCTION_MARSALECLASS_PARENT_NOT_EXITS = new ErrorCode(70_001_03,"父级销售分类不存在");
    ErrorCode PRODUCTION_MARSALECLASS_PARENT_ERROR = new ErrorCode(70_001_04, "不能设置自己为父销售分类");
    ErrorCode PRODUCTION_MARSALECLASS_NAME_DUPLICATE = new ErrorCode(70_001_05, "已经存在该名称的销售分类");
    ErrorCode PRODUCTION_MARSALECLASS_PARENT_IS_CHILD = new ErrorCode(70_001_06, "不能设置自己的子ProductionMarsaleclass为父ProductionMarsaleclass");

    }
