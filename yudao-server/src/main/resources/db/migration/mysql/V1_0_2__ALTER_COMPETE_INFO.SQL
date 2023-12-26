ALTER TABLE `ruoyi-vue-pro`.`sale_compete_info`
    ADD COLUMN `area_id` bigint NULL COMMENT '归属大区' AFTER `dept_id`;


ALTER TABLE `ruoyi-vue-pro`.`system_users`
    ADD COLUMN `code` varchar(255) NULL COMMENT '用户编码' AFTER `id`;