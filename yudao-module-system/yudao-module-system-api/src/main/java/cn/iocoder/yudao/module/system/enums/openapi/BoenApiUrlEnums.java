package cn.iocoder.yudao.module.system.enums.openapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoenApiUrlEnums {

    DEPT_DETAIL_URL("/server/data/deptDetail"),

    CUSTOMER_SALES_SYNC_URL("/server/data/customerSalesDetail"),

    NC_USER_SYNC_URL("/server/data/ncUserDetail");

    private final String url;
}
