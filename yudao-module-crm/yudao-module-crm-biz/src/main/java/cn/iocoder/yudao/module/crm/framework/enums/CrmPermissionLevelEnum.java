package cn.iocoder.yudao.module.crm.framework.enums;

import cn.hutool.core.util.ObjUtil;
import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Crm 数据权限级别枚举
 *
 * @author HUIHUI
 */
@Getter
@AllArgsConstructor
public enum CrmPermissionLevelEnum implements IntArrayValuable {

    OWNER(1, "负责人"),
    READ(2, "读"),
    WRITE(3, "写");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CrmPermissionLevelEnum::getLevel).toArray();

    /**
     * 级别
     */
    private final Integer level;
    /**
     * 级别名称
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static boolean isOwner(Integer level) {
        return ObjUtil.equal(OWNER.level, level);
    }

    public static boolean isRead(Integer level) {
        return ObjUtil.equal(READ.level, level);
    }

    public static boolean isWrite(Integer level) {
        return ObjUtil.equal(WRITE.level, level);
    }

    public static String getNameByLevel(Integer level) {
        // TODO @puhui999：可以 findone，更简洁；另外，不存在返回 null 即可啦；
        for (CrmPermissionLevelEnum levelEnum : CrmPermissionLevelEnum.values()) {
            if (ObjUtil.equal(levelEnum.level, level)) {
                return levelEnum.name;
            }
        }
        return "";
    }

}