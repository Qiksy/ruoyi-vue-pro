ALTER TABLE `ruoyi-vue-pro`.`strain_freezing_tube_stock_info`
    ADD COLUMN `save_by_name` varchar(255) NULL COMMENT '保存人名称' AFTER `save_by`;

ALTER TABLE `ruoyi-vue-pro`.`strain_freezing_tube_stock_info`
    ADD COLUMN `stock_pre_entry_code` varchar(255) NULL COMMENT '预录入编号' AFTER `stock_pre_entry_id`