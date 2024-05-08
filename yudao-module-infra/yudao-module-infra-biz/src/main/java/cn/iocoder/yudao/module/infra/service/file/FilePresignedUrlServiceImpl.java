package cn.iocoder.yudao.module.infra.service.file;


import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileConfigDO;
import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileDO;
import cn.iocoder.yudao.module.infra.dal.mysql.file.FileConfigMapper;
import cn.iocoder.yudao.module.infra.dal.mysql.file.FileMapper;
import cn.iocoder.yudao.module.infra.framework.file.core.client.FileClient;
import cn.iocoder.yudao.module.infra.framework.file.core.client.FileClientConfig;
import cn.iocoder.yudao.module.infra.framework.file.core.client.s3.S3FileClientConfig;
import cn.iocoder.yudao.module.infra.framework.file.core.enums.FileStorageEnum;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static cn.iocoder.yudao.framework.common.util.cache.CacheUtils.buildAsyncReloadingCache;

@Service
public class FilePresignedUrlServiceImpl implements FilePresignedUrlService{

    @Resource
    private FileConfigMapper fileConfigMapper;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private FileConfigService fileConfigService;


    @Getter
    private final LoadingCache<Long, FileConfigDO> fileConfigCache = buildAsyncReloadingCache(Duration.ofMinutes(2L),
            new CacheLoader<Long, FileConfigDO>() {
                @Override
                public FileConfigDO load(Long configId) {
                    return fileConfigMapper.selectById(configId);
                }
            });



    @Getter
    private final LoadingCache<Pair<String, Long>,String> filePresignedUrlCache = buildAsyncReloadingCache(Duration.ofHours(3L),
            new CacheLoader<Pair<String, Long>, String>() {
                @Override
                public String load(Pair<String, Long> param) throws Exception {
                    String filePath = param.getLeft();
                    Long configId = param.getRight();
                    FileClient client = fileConfigService.getFileClient(configId);
                    return client.getPresignedObjectViewUrl(filePath);
                }
            });
    /**
     * 对传进来的FileDO获取预备签名，然后返回
     */

    @Override
    public void wrapPresignedUrl(FileDO fileDO) throws ExecutionException {
        Long configId = fileDO.getConfigId();
        FileConfigDO fileConfigDO = fileConfigCache.getUnchecked(configId);
        FileClientConfig config = fileConfigDO.getConfig();
        if (config instanceof S3FileClientConfig){
            S3FileClientConfig s3Config = (S3FileClientConfig) config;
            if (s3Config.isPrivateBucket()){
                //更新fileDO的url
                fileDO.setUrl(filePresignedUrlCache.getUnchecked(Pair.of(fileDO.getPath(),configId)));
            }
        }
    }

    /**
     * 对传进来的FileDOList获取预备签名，然后返回
     */
    @Override
    public void wrapPresignedUrl(List<FileDO> fileDOList) throws ExecutionException {
        for (FileDO fileDO : fileDOList) {
            wrapPresignedUrl(fileDO);
        }
    }
}
