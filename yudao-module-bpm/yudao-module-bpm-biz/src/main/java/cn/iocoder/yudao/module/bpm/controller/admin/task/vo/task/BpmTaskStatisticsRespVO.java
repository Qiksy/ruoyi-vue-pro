package cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BpmTaskStatisticsRespVO {
    //我发起的数量、代办数量、已办数量
    private Long myStartCount;
    private Long todoCount;
    private Long doneCount;
}
