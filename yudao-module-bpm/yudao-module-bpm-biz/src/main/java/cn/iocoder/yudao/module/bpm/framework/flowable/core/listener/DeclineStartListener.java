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
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 测试用的执行监听器
 * 可能会出现无法注入的问题
 * https://www.jianshu.com/p/aced758ad935
 * https://blog.csdn.net/qq_38374397/article/details/120286414
 * @author linr
 * @since 2023/12/26 12:12
 */
@Component
@Slf4j
public class DeclineStartListener implements TaskListener {

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

    private static DeclineStartListener myListener;

    //redis key前缀，同一个人，同一个类型，5分钟内只能发一次
    private final static String AREA_LEADER_CONTENT_SEND_RECORD_PREFIX = "area_leader_content_send_record:";
    // 科普的前缀key
    private final static String KPY_CONTENT_SEND_RECORD_PREFIX = "kpy_content_send_record:";

    /**
     * 监听器中无法获取spring bean配置
     */
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
        // 这里准备通知各个战区总、大区总、科普员
        String kpyIds = (String) variables.get("kpyIds");

        String[] idArr = kpyIds.split(",");//其实是用户code

        //先通知对应的科普员，客户需要跟进，请及时与大区总沟通
        List<AdminUserRespDTO> userListByCodes = myListener.adminUserApi.getUserListByCodes(CollUtil.newArrayList(idArr));
        String touserStr = userListByCodes.stream().map(AdminUserRespDTO::getWecomeId).distinct().collect(Collectors.joining("|"));

        ObjectNode kpyContent = JsonNodeFactory.instance.objectNode(); //科普员消息
        //设置消息类型
        kpyContent.put("msgtype", "text");
        //推送给谁
        kpyContent.put("touser", touserStr);
        //应用id
        kpyContent.put("agentid", myListener.agentId);

        ObjectNode textNode = JsonNodeFactory.instance.objectNode();
        textNode.put("content","你有客户销量对比上月同期下降超过30%，请及时与大区总沟通");

        //消息内容
        kpyContent.set("text",textNode );
        ObjectMapper op = new ObjectMapper();


        WecomeMessageRespDTO wecomeMessageRespDTO = new WecomeMessageRespDTO();
        try {
            log.info("jsonNode:{}",op.writeValueAsString(kpyContent));
            wecomeMessageRespDTO = myListener.tencentApi.sendWelcomeMessage(op.writeValueAsString(kpyContent));
            log.info("wecomeMessageRespDTO:{}",wecomeMessageRespDTO);

        } catch (JsonProcessingException e) {
            log.info("解析json失败");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.info("发送消息失败");
            throw new RuntimeException(e);
        }
        if (wecomeMessageRespDTO.getErrcode() != 0) {
            log.info("发送消息失败{}",wecomeMessageRespDTO);
            throw exception(512,wecomeMessageRespDTO);
        }


        //其次通知对应的大区总，您有待处理的销售掉量预警，请及时处理
        Long areaCode = (Long) variables.get("areaCode");
        DeptRespDTO dept = myListener.deptApi.getDept(areaCode);
        Long leaderUserId = dept.getLeaderUserId();

        //判断redis key是否存在
        if (Boolean.FALSE.equals(myListener.stringRedisTemplate.hasKey(AREA_LEADER_CONTENT_SEND_RECORD_PREFIX + leaderUserId))) {
            AdminUserRespDTO leaderUser = myListener.adminUserApi.getUser(leaderUserId);

            ObjectNode leaderContent = JsonNodeFactory.instance.objectNode(); //大区总消息
            //设置消息类型
            leaderContent.put("msgtype", "text");
            //推送给谁
            leaderContent.put("touser", leaderUser.getWecomeId());
            //应用id
            leaderContent.put("agentid", myListener.agentId);

            ObjectNode textNode2 = JsonNodeFactory.instance.objectNode();
            textNode2.put("content","您管辖的大区内有客户销量对比上月同期下降超过30%。\n 请及时与对应的科普员沟通，并进入<a href=\"https://saletool.bo-en.com/social-login-redirect\">微销售</a>进行处理");

                    //消息内容
            leaderContent.set("content", textNode2);

            //发送
            WecomeMessageRespDTO wecomeMessageRespDTO2 =  new WecomeMessageRespDTO();
            try {
                log.info("jsonNode2:{}",op.writeValueAsString(leaderContent));
                wecomeMessageRespDTO2 = myListener.tencentApi.sendWelcomeMessage(op.writeValueAsString(leaderContent));
                log.info("wecomeMessageRespDTO2:{}",wecomeMessageRespDTO2);
                if (wecomeMessageRespDTO2.getErrcode() != 0) {
                    log.info("发送消息失败{}", wecomeMessageRespDTO2);
                    throw exception(wecomeMessageRespDTO2.getErrcode(),"消息发送失败：{}",wecomeMessageRespDTO2.getErrmsg());
                }
                //利用redisson，将这个人的这个类型的消息记录下来，5分钟内不再发送
                String key = AREA_LEADER_CONTENT_SEND_RECORD_PREFIX + leaderUserId;
                //设置一个key
                myListener.stringRedisTemplate.opsForValue().set(key,"1", Duration.ofMinutes(5));

            } catch (JsonProcessingException e) {
                log.info("解析json失败");
                throw new RuntimeException(e);
            } catch (IOException e) {
                log.info("发送消息失败");
                throw new RuntimeException(e);
            }


        }






        // 插入抄送表

    }

    // 审批界面的地址 https://saletool.bo-en.com/sale/decline-warning
}
