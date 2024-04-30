package cn.iocoder.yudao.module.bpm.framework.flowable.core.listener;

import cn.hutool.core.collection.CollUtil;
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
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
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

    @Value("${yudao.bpm.is-send-message:false}")
    private Boolean isSendMessage;//默认关闭
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
        myListener.agentId = this.agentId;
        myListener.isSendMessage = this.isSendMessage;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        if (!myListener.isSendMessage){
            //如果不给开启发送消息，直接返回
            return;
        }
        String processInstanceId = delegateTask.getProcessInstanceId();
        ProcessInstance processInstance = myListener.processInstanceService.getProcessInstance(processInstanceId);
        Map<String, Object> variables = processInstance.getProcessVariables();

        //通知战区总
        Long zoneCode = (Long) variables.get("zoneCode");
        String zonePk =  (String)variables.get("zonePk");

        DeptRespDTO dept;
        if (!StringUtils.isBlank(zonePk)){
            dept = myListener.deptApi.getDeptByPk(zonePk);
        }else {
            dept = myListener.deptApi.getDept(zoneCode);
        }

        Long leaderUserId = dept.getLeaderUserId();

        if (leaderUserId==null){
            //找大区总
            DeptRespDTO areaDept = myListener.deptApi.getDeptByPk((String)variables.get("areaPk"));
            if (areaDept==null){
                areaDept = myListener.deptApi.getDept((Long)variables.get("areaCode"));
            }
            leaderUserId = areaDept.getLeaderUserId();
        }

        //大区总也没有的话
        if (leaderUserId==null){
            //找劳诗晓
            List<AdminUserRespDTO> temp = myListener.adminUserApi.getUserListByCodes(CollUtil.newArrayList("000130"));
            AdminUserRespDTO adminUserRespDTO = temp.getFirst();
            leaderUserId = adminUserRespDTO.getId();
        }

        ObjectMapper op = new ObjectMapper();

        if (Boolean.FALSE.equals(myListener.stringRedisTemplate.hasKey(ZONE_LEADER_CONTENT_SEND_RECORD_PREFIX + leaderUserId))) {
            AdminUserRespDTO leaderUser = myListener.adminUserApi.getUser(leaderUserId);

            ObjectNode leaderContent = JsonNodeFactory.instance.objectNode(); //大区总消息
            //设置消息类型
            leaderContent.put("msgtype", "text");
            //推送给谁
            leaderContent.put("touser", leaderUser.getWecomeId());
            //应用id
            leaderContent.put("agentid", myListener.agentId);

            ObjectNode textNode = JsonNodeFactory.instance.objectNode();
            textNode.put("content","您管辖的战区内有客户销量对比上月同期下降超过30%。\n 请及时与对应的大区总沟通，并进入<a href=\"https://saletool.bo-en.com/social-login-redirect\">微销售</a>进行处理");

                    //消息内容
            leaderContent.set("text", textNode);


            //发送
            WecomeMessageRespDTO wecomeMessageRespDTO2 =  new WecomeMessageRespDTO();
            try {
                log.info("jsonNode2:{}",op.writeValueAsString(leaderContent));
                wecomeMessageRespDTO2 = myListener.tencentApi.sendWelcomeMessage(op.writeValueAsString(leaderContent));


                //利用redisson，将这个人的这个类型的消息记录下来，5分钟内不再发送
                String key = ZONE_LEADER_CONTENT_SEND_RECORD_PREFIX + leaderUserId;
                //设置一个key
                myListener.stringRedisTemplate.opsForValue().set(key,"1", Duration.ofMinutes(5));


            } catch (JsonProcessingException e) {
                log.info("解析json失败");
                throw new RuntimeException(e);
            } catch (IOException e) {
                log.info("发送消息失败");
                throw new RuntimeException(e);
            }

            if (wecomeMessageRespDTO2.getErrcode() != 0) {
                log.info("发送消息失败{}", wecomeMessageRespDTO2);
                throw exception(wecomeMessageRespDTO2.getErrcode(),"消息发送失败：{}",wecomeMessageRespDTO2.getErrmsg());
            }
        }
    }
}
