package cn.newbeedaly.springboot.elasticsearch.domain.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author newbeedaly
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    private String id;
    /**
     * 用户
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
    /**
     * 年龄
     */
    private String sex;
}
