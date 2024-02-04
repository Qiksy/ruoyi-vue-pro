package cn.iocoder.yudao.module.bpm.framework.flowable.core.listener;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceService;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

@Component
@Slf4j
public class DeclineKPYTaskListener implements TaskListener {

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

    private static DeclineKPYTaskListener myListener;

    private final static String ZONE_LEADER_CONTENT_SEND_RECORD_PREFIX = "kpy_content_send_record:";


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
        textNode.put("content", "有一条掉量预警需要你反馈，请你点击<a href=\"https://saletool.bo-en.com/social-login-redirect\">微销售</a>进行处理");


        //消息内容
        kpyContent.set("text",textNode );
        ObjectMapper op = new ObjectMapper();


        WecomeMessageRespDTO wecomeMessageRespDTO = new WecomeMessageRespDTO();
        try {
            log.info("jsonNode:{}",op.writeValueAsString(kpyContent));
            wecomeMessageRespDTO = myListener.tencentApi.sendWelcomeMessage(op.writeValueAsString(kpyContent));



        } catch (JsonProcessingException e) {
            log.info("解析json失败");
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.info("发送消息失败");
            throw new RuntimeException(e);
        }

        if (wecomeMessageRespDTO.getErrcode() != 0) {
            log.info("发送消息失败{}",wecomeMessageRespDTO);
            throw exception(wecomeMessageRespDTO.getErrcode(),"消息发送失败：{}",wecomeMessageRespDTO.getErrmsg());
        }


    }
}
