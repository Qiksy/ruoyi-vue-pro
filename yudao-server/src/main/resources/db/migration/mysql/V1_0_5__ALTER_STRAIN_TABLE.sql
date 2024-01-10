ALTER TABLE `ruoyi-vue-pro`.`strain_freezing_device_hierarchy`
    ADD COLUMN `name` varchar(500) NULL COMMENT '名称' AFTER `parent_id`;

ALTER TABLE `ruoyi-vue-pro`.`strain_freezing_device_hierarchy`
    ADD COLUMN `level_code` varchar(255) NULL COMMENT '层级编码' AFTER `name`;