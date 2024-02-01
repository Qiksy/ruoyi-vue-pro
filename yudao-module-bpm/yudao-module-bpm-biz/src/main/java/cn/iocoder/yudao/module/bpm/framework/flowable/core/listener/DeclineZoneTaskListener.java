package cn.iocoder.yudao.module.bpm.framework.flowable.core.listener;

import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceService;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.api.tenant.dto.WecomeMessageRespDTO;
import cn.iocoder.yudao.module.system.api.tencent.TencentApi;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 审批流程到战区总的时候，进行消息推送
 * @author linr
 * @since 2024/2/1 11:31
 */
@Component
@Slf4j
public class DeclineZoneTaskListener implements TaskListener {

    @Value("${tencent.work.agent-id}")
    private String agentId;

    @Resource
    private BpmProcessInstanceService processInstanceService;


    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private DeptApi deptApi;

    @Resource
    private TencentApi tencentApi;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static DeclineZoneTaskListener myListener;

    private final static String ZONE_LEADER_CONTENT_SEND_RECORD_PREFIX = "zone_leader_content_send_record:";

    @PostConstruct
    public void init() {
        myListener = this;
        myListener.processInstanceService = this.processInstanceService;
        myListener.adminUserApi = this.adminUserApi;
        myListener.deptApi = this.deptApi;
        myListener.tencentApi = this.tencentApi;
        myListener.stringRedisTemplate = this.stringRedisTemplate;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        ProcessInstance processInstance = myListener.processInstanceService.getProcessInstance(processInstanceId);
        Map<String, Object> variables = processInstance.getProcessVariables();

        //通知战区总
        Long zoneCode = (Long) variables.get("zoneCode");
        DeptRespDTO dept = myListener.deptApi.getDept(zoneCode);
        Long leaderUserId = dept.getLeaderUserId();
        ObjectMapper op = new ObjectMapper();

        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(ZONE_LEADER_CONTENT_SEND_RECORD_PREFIX + leaderUserId))) {
            AdminUserRespDTO leaderUser = myListener.adminUserApi.getUser(leaderUserId);

            ObjectNode leaderContent = JsonNodeFactory.instance.objectNode(); //大区总消息
            //设置消息类型
            leaderContent.put("msgtype", "text");
            //推送给谁
            leaderContent.put("touser", leaderUser.getWecomeId());
            //应用id
            leaderContent.put("agentid", agentId);
            //消息内容
            leaderContent.put("content", "您管辖的战区内有客户销量对比上月同期下降超过30%。\n 请及时与对应的大区总沟通，并进入<a href=\"https://saletool.bo-en.com/social-login-redirect\">微销售</a>进行处理");


            //发送
            WecomeMessageRespDTO wecomeMessageRespDTO2 =  new WecomeMessageRespDTO();
            try {
                log.info("jsonNode2:{}",op.writeValueAsString(leaderContent));
                wecomeMessageRespDTO2 = myListener.tencentApi.sendWelcomeMessage(op.writeValueAsString(leaderContent));

                //利用redisson，将这个人的这个类型的消息记录下来，5分钟内不再发送
                String key = ZONE_LEADER_CONTENT_SEND_RECORD_PREFIX + leaderUserId;
                //设置一个key
                stringRedisTemplate.opsForValue().set(key,"1", Duration.ofMinutes(5));

            } catch (JsonProcessingException e) {
                log.info("解析json失败");
                throw new RuntimeException(e);
            } catch (IOException e) {
                log.info("发送消息失败");

                if (wecomeMessageRespDTO2.getErrcode() != 0) {
                    log.info("发送消息失败{}",wecomeMessageRespDTO2);
                    throw exception(wecomeMessageRespDTO2.getErrcode(),"消息发送失败：{}",wecomeMessageRespDTO2.getErrmsg());
                }else {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
