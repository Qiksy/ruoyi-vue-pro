package cn.iocoder.yudao.module.system.enums.openapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoenApiUrlEnums {


    //部门
    DEPT_DETAIL_URL("/server/data/deptDetail"),
    //客户销售明细
    CUSTOMER_SALES_SYNC_URL("/server/data/customerSalesDetail"),
    //同步用户基本信息
    NC_USER_SYNC_URL("/server/data/ncUserDetail"),

    MATERIAL_BASE_INFO_SYNC_URL("/server/data/materialDetail"),
    //同步物料分类
    MATERIAL_CLASS_SYNC_URL("/server/data/materialClassDetail"),
    //同步销售分类
    SALE_CLASS_SYNC_URL("/server/data/saleClassDetail"),
    //同步产品线
    PRODLINE_SYNC_URL("/server/data/prodlineDetail");

    private final String url;
}
