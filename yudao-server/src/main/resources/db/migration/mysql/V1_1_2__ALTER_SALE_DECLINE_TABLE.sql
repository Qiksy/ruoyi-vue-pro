ALTER TABLE `ruoyi-vue-pro`.`sale_customer_sales_detail`
    ADD COLUMN `zone_pk` varchar(255) NULL COMMENT '战区主键' AFTER `zone_name`,
    ADD COLUMN `area_pk` varchar(255) NULL COMMENT '战区主键' AFTER `area_code`,
    ADD COLUMN `dept_pk` varchar(255) NULL COMMENT '营盘主键' AFTER `dept_name`;