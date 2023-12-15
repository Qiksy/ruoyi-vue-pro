```java
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

RuntimeService runtimeService = processEngine.getRuntimeService();
RepositoryService repositoryService = processEngine.getRepositoryService();
TaskService taskService = processEngine.getTaskService();
ManagementService managementService = processEngine.getManagementService();
IdentityService identityService = processEngine.getIdentityService();
HistoryService historyService = processEngine.getHistoryService();
FormService formService = processEngine.getFormService();
DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();
```

[官网说明](!(https://www.flowable.com/open-source/docs/bpmn/ch04-API)[https://www.flowable.com/open-source/docs/bpmn/ch04-API]
)

需要由系统的人类用户执行的任务是 BPM 引擎（如 Flowable）的核心。围绕任务的所有内容都分组在 TaskService 中，例如：

查询分配给用户或组的任务

创建新的独立任务。这些任务与流程实例无关。

操作将任务分配给哪个用户或哪些用户以某种方式参与该任务。

认领并完成任务。声明意味着某人决定成为任务的受托人，这意味着该用户将完成任务。完成意味着“完成任务的工作”。通常，这是以某种形式填写


实现事件监听

```java
public class MyEventListener implements FlowableEventListener {

  @Override
  public void onEvent(FlowableEvent event) {

    if(event.getType() == FlowableEngineEventType.JOB_EXECUTION_SUCCESS) {
      System.out.println("A job well done!");
    } else if (event.getType() == FlowableEngineEventType.JOB_EXECUTION_FAILURE) {
      System.out.println("A job has failed...");
    } else {
      System.out.println("Event received: " + event.getType());
    }
  }

  @Override
  public boolean isFailOnException() {
    // The logic in the onEvent method of this listener is not critical, exceptions
    // can be ignored if logging fails...
    return false;
  }

  @Override
  public boolean isFireOnTransactionLifecycleEvent() {
    return false;
  }

  @Override
  public String getOnTransaction() {
    return null;
  }
}
```