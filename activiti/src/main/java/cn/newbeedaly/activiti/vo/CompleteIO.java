package cn.newbeedaly.activiti.vo;

import lombok.Data;

@Data
public class CompleteIO {
    /**
     * 流程key，即：xml中process的id:leaveApplication
     */
    private String key;
    /**
     * assignee 用户，如 Jack, Marry
     */
    private String assignee;
}
