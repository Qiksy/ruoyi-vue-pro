package cn.iocoder.yudao.module.strain.service.outboundapplication;

import cn.iocoder.yudao.module.strain.api.OutboundApplicationApi;
import cn.iocoder.yudao.module.strain.dal.dataobject.outboundapplication.OutboundApplicationDO;
import cn.iocoder.yudao.module.strain.dal.mysql.outboundapplication.OutboundApplicationMapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OutboundApplicationApiImpl implements OutboundApplicationApi {

    @Resource
    private OutboundApplicationMapper outboundApplicationMapper;



    @Override
    public void updateResult(Long businessKey, Integer result) {
        if (businessKey==null){
            throw new IllegalArgumentException("businessKey is null");
        }

        LambdaUpdateWrapper<OutboundApplicationDO> updateWrapper = new LambdaUpdateWrapper<OutboundApplicationDO>()
                .set(OutboundApplicationDO::getApproResult, result)
                .eq(OutboundApplicationDO::getId, businessKey);
        outboundApplicationMapper.update(updateWrapper);
    }
}
