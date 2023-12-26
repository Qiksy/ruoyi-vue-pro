ALTER TABLE `ruoyi-vue-pro`.`system_users`
    ADD COLUMN `code` varchar(255) NULL COMMENT '用户编码' AFTER `id`;