ALTER TABLE `ruoyi-vue-pro`.`strain_freezing_device_hierarchy`
    ADD COLUMN `name` varchar(500) NULL COMMENT '名称' AFTER `parent_id`;

ALTER TABLE `ruoyi-vue-pro`.`strain_freezing_device_hierarchy`
    ADD COLUMN `level_code` varchar(255) NULL COMMENT '层级编码' AFTER `name`;

ALTER TABLE `ruoyi-vue-pro`.`strain_microbe_basic_info`
    MODIFY COLUMN `expiration_date` datetime NULL COMMENT '有效期至' AFTER `medium_id`;

ALTER TABLE `ruoyi-vue-pro`.`strain_freezing_tube_stock_pre_entry`
    MODIFY COLUMN `status` bit(1) NOT NULL COMMENT '是否入库' AFTER `save_date`;


ALTER TABLE `ruoyi-vue-pro`.`strain_freezing_tube_stock_info`
    MODIFY COLUMN `tube_id` bigint NULL COMMENT '冷冻管类型id' AFTER `code`,
    MODIFY COLUMN `expiration_date` datetime NULL COMMENT '有效期至' AFTER `microbe_id`,
    MODIFY COLUMN `save_date` datetime NULL COMMENT '保存日期' AFTER `expiration_date`,
    MODIFY COLUMN `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '状态（销毁、出库待回库、已用完、正常存储）' AFTER `save_date`,
    MODIFY COLUMN `save_by` bigint NULL COMMENT '保存人id' AFTER `status`;