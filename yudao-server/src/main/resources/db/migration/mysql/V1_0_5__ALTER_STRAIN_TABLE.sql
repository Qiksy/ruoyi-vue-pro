ALTER TABLE `ruoyi-vue-pro`.`strain_freezing_device_hierarchy`
    ADD COLUMN `name` varchar(500) NULL COMMENT '名称' AFTER `parent_id`;

ALTER TABLE `ruoyi-vue-pro`.`strain_freezing_device_hierarchy`
    ADD COLUMN `level_code` varchar(255) NULL COMMENT '层级编码' AFTER `name`;

ALTER TABLE `ruoyi-vue-pro`.`strain_microbe_basic_info`
    MODIFY COLUMN `expiration_date` datetime NULL COMMENT '有效期至' AFTER `medium_id`;

ALTER TABLE `ruoyi-vue-pro`.`strain_freezing_tube_stock_pre_entry`
    MODIFY COLUMN `status` bit(1) NOT NULL COMMENT '是否入库' AFTER `save_date`;