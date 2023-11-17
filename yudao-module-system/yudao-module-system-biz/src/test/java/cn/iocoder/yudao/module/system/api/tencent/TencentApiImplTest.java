package cn.iocoder.yudao.module.system.api.tencent;


import cn.iocoder.yudao.framework.test.core.ut.BaseRedisUnitTest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.io.IOException;



public class TencentApiImplTest extends BaseRedisUnitTest {

    @InjectMocks
    private TencentApiImpl tencentApi;

    @Test
    public void getAccessToken() {
        String tencentApiAccessToken;
        try {
            tencentApiAccessToken = tencentApi.getAccessToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(tencentApiAccessToken);
        assert (1==1);
    }
}