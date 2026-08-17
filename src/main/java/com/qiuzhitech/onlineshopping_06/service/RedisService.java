package com.qiuzhitech.onlineshopping_06.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;

@Service
public class RedisService {

    @Resource
    JedisPool jedisPool;

    public void setValue(String key, String value) {
        Jedis jedisClient = jedisPool.getResource();
        jedisClient.set(key, value);
        jedisClient.close();
    }

    public String getValue(String key) {
        Jedis jedisClient = jedisPool.getResource();
        String value = jedisClient.get(key);
        jedisClient.close();
        return value;
    }

    // 方法二：distributed Lock

    // Why requestId?
    // 防止在redis传往数据库的过程中，出现延迟超过expiretime，而导致自动解锁，下一个request会沿用同样的key
    // 防止A商品的锁，解了B商品的锁
    public boolean tryToGetDistributionLock(String lockKey, String requestId, int expireTime) {
        Jedis jedis = jedisPool.getResource();

        // Ensures that the key is only set if it does not already exist.
        // NX: if the key already exits, I do nothing
        // PY: expire time
        String result = jedis.set(lockKey, requestId, "NX", "PX", expireTime);
        jedis.close();
        return "OK".equals(result);
    }



    // distribution lock
    public boolean releaseDistributedLock(String lockKey, String requestId) {
        Jedis jedisClient = jedisPool.getResource();
        String script = "if redis.call('get', KEYS[1]) == ARGV[1]" +
                " then return redis.call('del', KEYS[1])" +
                " else return 0 end";
        Long result = (Long) jedisClient.eval(script,
                Collections.singletonList(lockKey),
                Collections.singletonList(requestId));
        if (result == 1L) {
            return true;
        }
        return false;
    }



    // 方法三：Redis + Lua scripts
    public long stockDeduct(String redisKey) {
        try (Jedis jedisClient = jedisPool.getResource()) {
            // check key existence -> get stock -> deduct
            // Why use script, not getter and setter?
            // 保证所有脚本的原子性 （都执行完毕）。 用set和get，不能保证两条指令之间有无其他操作插入
            String script =
                    "if redis.call('exists', KEYS[1]) == 1 then\n" +
                    " local stock = tonumber(redis.call('get', KEYS[1]))\n" +
                    " if (stock<=0) then\n" +
                            " return -1\n" +
                            " end\n" +
                            "\n" +
                            " redis.call('decr', KEYS[1]);\n" +
                            " return stock - 1;\n" +
                            "end\n" +
                            "\n" +
                            "return -1;";

            // return stock, if no stack, it will return -1;
            Long stock = (Long) jedisClient.eval(script, Collections.singletonList(redisKey), Collections.emptyList());

            if (stock < 0) {
                System.out.println("There is no stock available");
                return -1;
            } else {
                System.out.println("Validate and decreased redis stock, current available stock：" + stock);
                return stock;
            }
        } catch (Throwable throwable) {
            System.out.println("Exception on stockDeductValidation：" + throwable.toString());
            return -1;
        }
    }

    public long revertStock(String redisKey) {
        Jedis jedis = jedisPool.getResource();
        Long incr = jedis.incr(redisKey);
        jedis.close();
        return incr;
    }
}
