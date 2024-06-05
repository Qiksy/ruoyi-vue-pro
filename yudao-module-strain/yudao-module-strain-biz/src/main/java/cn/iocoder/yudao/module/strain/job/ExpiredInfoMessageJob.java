package cn.iocoder.yudao.module.strain.job;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.infra.api.config.ConfigApi;
import cn.iocoder.yudao.module.infra.api.config.dto.ConfigRespDO;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningReqVO;
import cn.iocoder.yudao.module.strain.controller.admin.expiredWarning.vo.ExpiredWarningRespVO;
import cn.iocoder.yudao.module.strain.service.freezingtubestockpreentry.FreezingTubeStockPreEntryService;
import cn.iocoder.yudao.module.system.api.mail.MailSendApi;
import cn.iocoder.yudao.module.system.api.mail.dto.MailSendSingleToUserReqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 过期信息消息任务
 *
 * @author linr 林荣
 * @since 2024/5/10 15:08
 */
@Component
@Slf4j
public class ExpiredInfoMessageJob implements JobHandler {


    @Resource
    private FreezingTubeStockPreEntryService freezingTubeStockPreEntryService;

    @Resource
    private MailSendApi mailSendApi;


    @Resource
    private ConfigApi configApi;


    /**
     * 执行任务
     * 获取菌种过期的信息，然后发送消息给用户
     *
     * @param param 参数
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public String execute(String param) throws Exception {


        ConfigRespDO configRespDO = configApi.getConfigByKey("strain.expired.message.acceptor");

        String value = configRespDO.getValue();

        List<String> emails = Arrays.stream(value.split(",")).map(str -> str.trim()).distinct().toList();


        sendEmail(emails);

        return "执行成功";
    }

    private void sendEmail(List<String> emails) {
        ExpiredWarningReqVO expiredWarningReqVO = new ExpiredWarningReqVO();
        expiredWarningReqVO.setPageNo(1);
        expiredWarningReqVO.setPageNo(20);

        PageResult<ExpiredWarningRespVO> pageResult = freezingTubeStockPreEntryService.getExpiredWaringPage(expiredWarningReqVO);

        Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("total", pageResult.getTotal());

        StringBuilder sb = new StringBuilder();

        // 遍历过期信息
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<ExpiredWarningRespVO> list = pageResult.getList();
        // 拼接表格
        for (ExpiredWarningRespVO item : list) {
            String formattedSaveDate = item.getSaveDate() != null ? item.getSaveDate().format(formatter) : "N/A";
            String formattedExpirationDate = item.getExpirationDate() != null ? item.getExpirationDate().format(formatter) : "N/A";

            sb.append("<tr>");
            sb.append("<td>").append(item.getSpecimenCode() != null ? item.getSpecimenCode() : "N/A").append("</td>");
            sb.append("<td>").append(item.getChineseName() != null ? item.getChineseName() : "N/A").append("</td>");
            sb.append("<td>").append(item.getLatinName() != null ? item.getLatinName() : "N/A").append("</td>");
            sb.append("<td>").append(formattedSaveDate).append("</td>");
            sb.append("<td>").append(formattedExpirationDate).append("</td>");
            sb.append("<td>").append(item.getSource() != null ? item.getSource() : "N/A").append("</td>");
            sb.append("<td>").append(item.getUseage() != null ? item.getUseage() : "N/A").append("</td>");
            sb.append("</tr>");
        }
        templateParams.put("list", sb.toString());
        //批量发送邮件
        for (String email : emails) {
            MailSendSingleToUserReqDTO reqDTO = new MailSendSingleToUserReqDTO();
            reqDTO.setMail(email);
            reqDTO.setUserId(SecurityFrameworkUtils.getLoginUserId());
            reqDTO.setTemplateCode("strain_expired_message");
            reqDTO.setTemplateParams(templateParams);
            mailSendApi.sendSingleMailToAdmin(reqDTO);
        }
    }
}
