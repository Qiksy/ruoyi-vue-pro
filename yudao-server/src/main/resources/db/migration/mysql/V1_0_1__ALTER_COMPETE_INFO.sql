ALTER TABLE `ruoyi-vue-pro`.`sale_compete_info`
MODIFY COLUMN `compete_id` bigint NULL DEFAULT NULL COMMENT '对标产品（废弃）' AFTER `spec`,
MODIFY COLUMN `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '初始价格（吨价）' AFTER `compete_id`,
ADD COLUMN `unit_price` decimal(10, 2) NULL COMMENT '初始价格单价' AFTER `price`,
ADD COLUMN `production_id` bigint NULL COMMENT '我方产品id' AFTER `unit_price`,
ADD COLUMN `our_price` decimal(10, 2) NULL COMMENT '我方产品价格（吨价）' AFTER `production_id`,
ADD COLUMN `our_unit_price` decimal(10, 2) NULL COMMENT '我方产品单价' AFTER `our_price`,
ADD COLUMN `dept_id` bigint NULL COMMENT '归属工厂' AFTER `our_unit_price`;