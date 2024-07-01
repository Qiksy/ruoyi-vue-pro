package cn.iocoder.yudao.module.infra.api.file;

import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileDO;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;

/**
 * 文件 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class FileApiImpl implements FileApi {

    @Resource
    private FileService fileService;

    @Override
    public String createFile(String name, String path, byte[] content,Long businessId) {
        return fileService.createFile(name, path, content, businessId);
    }


    /**
     * 通过文件主键，获取文件的访问路径
     *
     * @param id 主键
     * @return 文件路径
     */
    @Override
    public String getUrlById(Long id) {
        FileDO file = fileService.getFile(id);
        return (file==null || StringUtils.isBlank(file.getUrl())) ? "" : file.getUrl();
    }
}
