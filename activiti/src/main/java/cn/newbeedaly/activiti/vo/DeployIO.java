package cn.newbeedaly.activiti.vo;

import lombok.Data;

@Data
public class DeployIO {
    /**
     * 流程名称(如：请假)
     */
    private String name;
    /**
     * 流程文件路径
     */
    private String filePath;
    /**
     * 流程文件xml内容
     * 此处的xml内容可以直接复制resource/processes下的xml内容
     */
    private String xml;
}
