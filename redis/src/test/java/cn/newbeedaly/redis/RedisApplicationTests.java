package cn.newbeedaly.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisApplicationTests {

    /**
     * 方法
     * opsForValue ：对应 String（字符串）
     * opsForZSet：对应 ZSet（有序集合）
     * opsForHash：对应 Hash（哈希）
     * opsForList：对应 List（列表）
     * opsForSet：对应 Set（集合）
     * opsForGeo：对应 GEO（地理位置）
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println((String) name);
        redisTemplate.opsForValue().set("name", "newbeedaly");
        name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

}
