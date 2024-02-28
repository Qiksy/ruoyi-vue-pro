package cn.iocoder.yudao.module.strain.enums;

/**
 * 冷冻管库存状态枚举
 * @author linr
 * @since 2024/2/21 16:10
 */
public enum InventoryStatisEnum {

    /**
     * 未入库
     */
    NOT_IN_STOCK("0"),
    /**
     * 已入库
     */
    IN_STOCK("1"),
    /**
     * 等待回库
     */
    WAIT_STOCK("2"),
    /**
     * 已销毁
     */
    DESTROY_STOCK("3"),
    /**
     * 已删除 /已消耗
     */
    DELETE_STOCK("4");

    private final String value;

    InventoryStatisEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
