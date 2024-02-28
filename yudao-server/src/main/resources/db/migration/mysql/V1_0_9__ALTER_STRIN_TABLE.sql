ALTER TABLE `ruoyi-vue-pro`.`strain_microbe_basic_info`
    ADD COLUMN `storage_mode` varchar(255) NULL COMMENT '保存方式' AFTER `is_visiable`,
    ADD COLUMN `microbe_images` varchar(2000) NULL COMMENT '图片列表id[12345,45641]' AFTER `storage_mode`,
    ADD COLUMN `microbe_attachment` varchar(2000) NULL COMMENT '附件列表' AFTER `microbe_images`;


ALTER TABLE `ruoyi-vue-pro`.`strain_microbe_basic_info`
    ADD COLUMN `microbial_morphology` varchar(255) NULL COMMENT '菌体形态' AFTER `colony_morphology`,
    MODIFY COLUMN `is_pathogenic` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否致病（0否1是）' AFTER `validity_period_days`,
    MODIFY COLUMN `is_visiable` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否公开浏览 （0否1是）' AFTER `is_pathogenic`;


ALTER TABLE `ruoyi-vue-pro`.`strain_outbound_sub_application`
    CHANGE COLUMN `tube_id` `specimen_id` bigint NOT NULL COMMENT '标本id' AFTER `parent_id`,
    ADD COLUMN `specimen_code` varchar(255) NULL COMMENT '标本保藏id' AFTER `specimen_id`,
    ADD COLUMN `chinese_name` varchar(255) NULL COMMENT '中文名称' AFTER `specimen_code`,
    ADD COLUMN `latin_name` varchar(255) NULL COMMENT '拉丁名称' AFTER `chinese_name`,
    ADD COLUMN `stock_id` bigint NULL COMMENT '槽位id' AFTER `latin_name`;