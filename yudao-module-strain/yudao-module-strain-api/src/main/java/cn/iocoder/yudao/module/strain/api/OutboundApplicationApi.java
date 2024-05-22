package cn.iocoder.yudao.module.strain.api;

/**
 * 提供给其它模块调用的
 * @author linr
 * @since 2024/5/22 下午4:36
 */
public interface OutboundApplicationApi {

    /**
     * 更新审批结果
     * @param businessKey 表单主键
     * @param result 审批结果
     */
    void updateResult(Long businessKey, Integer result);
}
