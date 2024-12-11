package cn.newbeedaly.springboot.elasticsearch.domain.repository;

import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author newbeedaly
 */
public interface UserRepository extends ElasticsearchRepository<User, String> {

    /**
     * 查询用户列表
     *
     * @param userName 用户名
     * @return 用户列表
     */
    List<User> findByUserName(String userName);

    /**
     * 模糊查询用户列表
     *
     * @param userName 用户名
     * @return 用户列表
     */
    List<User> findByUserNameLike(String userName);

    /**
     * 包含查询用户列表
     *
     * @param userName 用户名
     * @return 用户列表
     */
    List<User> findByUserNameContains(String userName);

    /**
     * 高亮查询用户列表
     *
     * @param mobile 手机号
     * @return 用户列表
     */
    @Highlight(fields = {
            @HighlightField(name = "mobile")
    }, parameters = @HighlightParameters(
            preTags = "<strong>",
            postTags = "</strong>"
    ))
    List<SearchHit<User>> findByMobile(String mobile);

    /**
     * 脚本查询用户列表
     *
     * @param userName 用户名
     * @return 用户列表
     */
    @Query("{\n" +
            "    \"match\": {\n" +
            "      \"userName\": {\n" +
            "        \"query\": \"?0\"\n" +
            "      }\n" +
            "    }\n" +
            "  }")
    List<User> queryByScript(String userName);

}
