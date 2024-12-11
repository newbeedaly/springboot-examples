package cn.newbeedaly.activiti.vo;

import lombok.Data;

@Data
public class RollbackIO {
    /**
     * 任务Id，当前执行到的节点任务Id，如：aba0f9c7-fa04-11ed-bd0c-005056c00001
     */
    private String taskId;
    /**
     * 回退节点，如：sid-813f6940-24ec-43d1-9252-b42e30f00e77
     */
    private String rollbackNode;
}
