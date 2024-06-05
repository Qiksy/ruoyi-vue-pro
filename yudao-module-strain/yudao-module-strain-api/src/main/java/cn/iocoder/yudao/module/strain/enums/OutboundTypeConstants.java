package cn.iocoder.yudao.module.strain.enums;

/**
 *
 * 出库类型 常量值
 * @author linr
 * @since 2024/6/2 下午7:06
 */
public interface OutboundTypeConstants {

    /**
     * 正常出库
     * 后续需要回库
     */
    String NORMAL = "1";

    /**
     * 销毁出库
     * 不需要回库
     */
    // 销毁出库
    String DESTROY = "2";


    /**
     * 消耗出库
     * 不需要回库
     */
    // 消耗出库
    String CONSUME = "3";


    /**
     * 传代/复壮出库
     * 需要回库，并且回库的时候，需要更新融冻次数、代数
     *
     */
    // 传代/复壮出库
    String REGENERATION = "4";

}
