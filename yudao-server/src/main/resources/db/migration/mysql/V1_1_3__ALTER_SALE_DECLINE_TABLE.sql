ALTER TABLE `ruoyi-vue-pro`.`sale_decline_warning`
    ADD COLUMN `zone_pk` varchar(255) NULL COMMENT '战区主键' AFTER `zone_name`,
    ADD COLUMN `area_pk` varchar(255) NULL COMMENT '大区主键' AFTER `area_code`;