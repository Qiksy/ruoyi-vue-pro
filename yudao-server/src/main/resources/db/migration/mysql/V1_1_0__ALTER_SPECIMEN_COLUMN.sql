ALTER TABLE `ruoyi-vue-pro`.`strain_freezing_tube_stock_pre_entry`
    ADD COLUMN `box_code` varchar(255) NULL COMMENT '盒子编码，不关联真实库存位置' AFTER `status`;