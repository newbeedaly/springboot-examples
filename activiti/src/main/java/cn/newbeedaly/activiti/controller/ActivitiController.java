package cn.newbeedaly.activiti.controller;

import cn.newbeedaly.activiti.vo.CompleteIO;
import cn.newbeedaly.activiti.vo.DeployIO;
import cn.newbeedaly.activiti.vo.RollbackIO;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/process")
public class ActivitiController {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;


    /**
     * 第一步
     * 部署流程定义(因为xml中含有特殊字符，前端需表单提交字段信息)
     *
     * @param io 请求对象
     * @return 成功
     */
    @PostMapping("/deploy")
    public String deployProcess(DeployIO io) {
        Deployment deploy = repositoryService.createDeployment()
                .name(io.getName())
                .category("分类名称")
                // .addClasspathResource(filePath)
                .addString(io.getName() + ".bpmn20.xml", io.getXml())
                .deploy();
        // 请假 流程定义完成部署, 部署id:"777bda3e-fa01-11ed-85f5-005056c00001", 部署name:"请假申请流程定义"
        log.info("{} 流程定义完成部署, 部署id:{}, 部署name:{}", io.getName(), deploy.getId(), deploy.getName());
        // 补充 deploy id 是流程定义ID，实际开发中，对应自定义的流程定义表中的流程定义ID，定义表与表单模板关联
        return HttpStatus.OK.getReasonPhrase();
    }


    /**
     * 第二步
     * 查询流程
     *
     * @param key 流程key，即：xml中process的id:leaveApplication
     * @return 流程名称列表
     */
    @GetMapping(value = {"/list/{key}", "/list"})
    public List<String> getProcessList(@PathVariable(name = "key", required = false) String key) {
        List<ProcessDefinition> definitionList = StringUtils.isNotBlank(key) ?
                repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).list()
                : repositoryService.createProcessDefinitionQuery().list();
        List<String> processList = new ArrayList<>();
        for (ProcessDefinition processDefinition : definitionList) {
            // 查询流程, 流程Key:leaveApplication, 流程Name:请假申请流程定义, 流程部署id:777bda3e-fa01-11ed-85f5-005056c00001
            log.info("查询流程, 流程Key:{}, 流程Name:{}, 流程部署id:{}", processDefinition.getKey(), processDefinition.getName(), processDefinition.getDeploymentId());
            processList.add(processDefinition.getName());
        }
        return processList;
    }

    /**
     * 第三步
     * 启动流程定义（由流程定义-->流程实例）
     *
     * @param key 流程key，即：xml中process的id:leaveApplication
     * @return processDefinitionName 流程定义名称
     */
    @PostMapping("/start/{key}")
    public String startProcess(@PathVariable(name = "key") String key) {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(4);
        map.put("assignee0", "Jack");
        map.put("assignee1", "Marry");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, map);
        // id: ab91de8f-fa04-11ed-bd0c-005056c00001 流程实例ID，对应自定义的流程实例表ID，并于表单实例关联
        // processDefinitionKey: leaveApplication:1:77d062e0-fa01-11ed-85f5-005056c00001
        // processInstanceId: leaveApplication
        // variableInstances: size = 2 [jack, Marry]
        log.info("流程实例内容, 流程实例id: {}, 流程定义Key: {}, 流程实例Id: {}, 流程参数实例: {}", processInstance.getId(),
                processInstance.getProcessDefinitionKey(), processInstance.getProcessInstanceId(), processInstance.getProcessVariables());
        // 补充 流程实例ID，对应自定义的流程实例表是流程实例ID，并于表单实例关联，启动这一步非常重要：确定各个审批人，自动完成第一个节点（申请），修改流程相关表的状态。
        return processInstance.getProcessDefinitionName();
    }


    /**
     * 第四步
     * 查看任务列表
     *
     * @return 任务名称列表
     */
    @GetMapping("/task/list")
    public List<String> getMyTaskList() {
        // 获取当前登录的用户名
        String username = "Jack";
        // 查询用户的任务
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(username).list();
        // 任务id : aba0f9c7-fa04-11ed-bd0c-005056c00001
        return tasks.stream().map(Task::getName).collect(Collectors.toList());
    }

    /**
     * 第五步(实际情况下，第三步和第五步提交申请时一起使用)
     * 完成任务
     *
     * @param io 完成任务对象
     * @return 成功
     */
    @PostMapping("/complete")
    public String doTask(@RequestBody CompleteIO io) {
        List<Task> tasks = taskService.createTaskQuery().processDefinitionKey(io.getKey())
                .taskAssignee(io.getAssignee()).list();
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                // 任务取消认领
                taskService.unclaim(task.getId());
                // 把任务由其他人认领
                taskService.claim(task.getId(), io.getAssignee());
                // 完成任务
                taskService.complete(task.getId());
                log.info("{}，任务已完成", task.getName());
            }
        }
        return HttpStatus.OK.getReasonPhrase();
    }

    /**
     * 第六步
     * 回退任务
     *
     * @param io 回退任务对象
     * @return 成功
     */
    @PostMapping("/rollback")
    public String doRollbackTask(@RequestBody RollbackIO io) {
        // 回退流程思路图 http://img.newbeedaly.cn/2023/activiti-rollback.png
        Task task = taskService.createTaskQuery().taskId(io.getTaskId()).singleResult();
        // 1. 获取回滚节点
        HistoricTaskInstance rollbackTask = getRollbackTask(task.getProcessInstanceId(), io.getRollbackNode());
        // 2. 查询bpmn信息
        BpmnModel bpmnModel = repositoryService.getBpmnModel(rollbackTask.getProcessDefinitionId());
        // 3. 查询回滚节点活动
        List<HistoricActivityInstance> haiFinishedList = historyService.createHistoricActivityInstanceQuery()
                .executionId(rollbackTask.getExecutionId()).finished().list();
        String rollbackActivityId = haiFinishedList.stream().filter(o -> StringUtils.equals(o.getTaskId(), rollbackTask.getId())).findFirst().orElse(new HistoricActivityInstanceEntityImpl()).getActivityId();
        FlowNode rollbackNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(rollbackActivityId);
        // 4. 查询当前节点的信息
        Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(execution.getActivityId());
        // 5. 记录当前节点的原活动方向
        List<SequenceFlow> oriSequenceFlows = new ArrayList<>(flowNode.getOutgoingFlows());
        // 6. 清理活动方向
        flowNode.getOutgoingFlows().clear();
        // 7. 建立新方向
        List<SequenceFlow> newSequenceFlowList = new ArrayList<>();
        SequenceFlow newSequenceFlow = new SequenceFlow();
        newSequenceFlow.setSourceFlowElement(flowNode);
        newSequenceFlow.setTargetFlowElement(rollbackNode);
        newSequenceFlowList.add(newSequenceFlow);
        flowNode.setOutgoingFlows(newSequenceFlowList);
        // 8. 完成任务
        taskService.complete(task.getId());
        // 9. 恢复原方向
        flowNode.setOutgoingFlows(oriSequenceFlows);
        Task nextTask = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        // 10. 设置执行人
        if (nextTask != null) {
            taskService.setAssignee(nextTask.getId(), rollbackTask.getAssignee());
        }
        return HttpStatus.OK.getReasonPhrase();
    }

    /**
     * 第七步
     * 删除部署
     *
     * @param deploymentId 部署Id
     * @return 成功
     */
    @PostMapping("/delete/{id}")
    public String deleteDeployment(@PathVariable(name = "id") String deploymentId) {
        // deleteDeployment() 方法的第二个参数 cascade 设置为 true,表示需要进行级联删除，从而可以删除掉未完成的任务
        repositoryService.deleteDeployment(deploymentId, true);
        return HttpStatus.OK.getReasonPhrase();
    }


    /**
     * 查询回滚节点任务
     *
     * @param processInstanceId 流程实例Id
     * @param rollbackNode      回滚节点Key
     * @return 回滚节点任务实例
     */
    private HistoricTaskInstance getRollbackTask(String processInstanceId, String rollbackNode) {
        // 取得所有历史任务按时间降序排序
        List<HistoricTaskInstance> hitList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByTaskCreateTime()
                .desc()
                .list();
        if (CollectionUtils.isEmpty(hitList) || hitList.size() < 2) {
            throw new RuntimeException("节点无法回滚");
        }
        return hitList.stream().filter(o -> StringUtils.equals(o.getTaskDefinitionKey(), rollbackNode)).findFirst().orElse(null);
    }

}
