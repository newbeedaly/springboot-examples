package cn.newbeedaly.springboot.jpa.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "jpa_user", indexes = {@Index(name = "id", columnList = "id", unique = true), @Index(name = "name", columnList = "name", unique = true)})
public class User {

    @Id // @Id: 指明id列, 必须
    //@GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue： 表明是否自动生成, 必须, strategy也是必写, 指明主键生成策略, 默认是Oracle
    private Long id;

    @Column(name = "name", nullable = false) // @Column： 对应数据库列名,可选, nullable 是否可以为空, 默认true
    private String name;

    private String password;

    private String email;


}
