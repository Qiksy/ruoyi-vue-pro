package cn.iocoder.yudao.module.infra.service.file;

import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileDO;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 * 文件预签名地址 Service 接口
 *
 * 如果一个S3存储器，访问是需要权限的，但是也需要给普通用户访问。这个时候就可以获取一个临时的地址。
 * 提供给用户访问，这个地址是有时间限制的，过了时间就不能访问了。
 * @author linr
 * @since 2024/5/8 15:25
 */
public interface FilePresignedUrlService {


    /**
     * 获取这个文件的预签名地址
     * @param fileDO
     * @throws ExecutionException
     */
    void wrapPresignedUrl(FileDO fileDO) throws ExecutionException;

    /**
     * 获取这个文件的预签名地址
     * @param fileDOList
     * @throws ExecutionException
     */
    void wrapPresignedUrl(List<FileDO> fileDOList) throws ExecutionException;
}
