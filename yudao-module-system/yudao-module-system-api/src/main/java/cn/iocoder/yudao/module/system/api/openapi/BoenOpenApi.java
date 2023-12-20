package cn.iocoder.yudao.module.system.api.openapi;

import org.springframework.core.ParameterizedTypeReference;

/**
 * 用来统一请求openapi平台
 * @author linr
 * @since 2023/12/20 11:01
 */
public interface BoenOpenApi {

    public <T> T sendRequest(ParameterizedTypeReference<T> typeRef, String url, String method, Object... params);
}
