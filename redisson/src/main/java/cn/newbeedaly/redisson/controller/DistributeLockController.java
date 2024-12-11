package cn.newbeedaly.redisson.controller;

import cn.newbeedaly.redisson.lock.RedissonDistributedLocker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class DistributeLockController {

    @Autowired
    private RedissonDistributedLocker redissonLocker;

    @GetMapping("lock")
    public void lock() {
        int count = 10;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> {
                try {
                    String lockKey = "16601108330";
                    redissonLocker.tryLock(lockKey, TimeUnit.SECONDS, 100, 8);
                    log.info("加锁:" + Thread.currentThread().getName());

                    log.info("业务操作:{}", Thread.currentThread().getName());
                    Thread.sleep(1000);

                    log.info("释放锁:{}" + Thread.currentThread().getName());
                    redissonLocker.unlock(lockKey);

                    log.info("latch count:{}", latch.getCount());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
            thread.start();
        }
    }
}
