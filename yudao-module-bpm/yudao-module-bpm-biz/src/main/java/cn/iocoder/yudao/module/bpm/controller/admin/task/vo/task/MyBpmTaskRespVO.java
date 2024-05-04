package cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task;


import cn.iocoder.yudao.module.sale.dal.dataobject.declinewarning.DeclineWarningDO;
import lombok.Data;


@Data
public class MyBpmTaskRespVO extends BpmTaskRespVO{

    private DeclineWarningDO declineWarningInfo;
}
