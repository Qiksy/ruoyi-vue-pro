package cn.iocoder.yudao.module.infra.api.config;

import cn.iocoder.yudao.module.infra.api.config.dto.ConfigRespDO;

public interface ConfigApi {

    /**
     * 根据参数键，获得参数配置
     *
     * @param key 配置键
     * @return 参数配置
     */
    ConfigRespDO getConfigByKey(String key);
}
