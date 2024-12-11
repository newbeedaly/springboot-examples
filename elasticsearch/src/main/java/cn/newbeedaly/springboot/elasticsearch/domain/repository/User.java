package cn.newbeedaly.springboot.elasticsearch.domain.repository;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Document注解 indexName：对应索引库名称, type：对应在索引库中的类型, shards：分片数, replicas：副本数.
 *
 * @author newbeedaly
 */
@Data
@Builder
@ToString
@Document(indexName = "user-repository-index")
public class User {

    /**
     * Id
     */
    @Id
    private String id;
    /**
     * 用户名
     */
    @Field(type = FieldType.Text,/* searchAnalyzer = "ik_smart", analyzer = "ik_max_word",*/ fielddata = true)
    private String userName;
    /**
     * 手机号
     */
    private String mobile;

}
