package cn.iocoder.yudao.module.strain.enums;

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
     * 已删除
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
