package cn.iocoder.yudao.module.infra.api.config;


import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.infra.api.config.dto.ConfigRespDO;
import cn.iocoder.yudao.module.infra.dal.dataobject.config.ConfigDO;
import cn.iocoder.yudao.module.infra.service.config.ConfigService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ConfigApiImpl implements ConfigApi{

    @Resource
    ConfigService configService;

    /**
     * 根据参数键，获得参数配置
     *
     * @param key 配置键
     * @return 参数配置
     */
    @Override
    public ConfigRespDO getConfigByKey(String key) {
        ConfigDO configByKey = configService.getConfigByKey(key);
        if (configByKey == null) {
            return null;
        }
        return BeanUtils.toBean(configByKey, ConfigRespDO.class);
    }
}
