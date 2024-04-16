package top.naccl.algorithm;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisLockCoreAlgorithm {

    private static final Integer timeOutMsecs = 10;
    private static final Long expireMsecs = 10L;
    private static final String lockKey = "testRedisKey";
    private Boolean locked = false;

    /**
     * Acquire lock.
     *
     * @param stringRedisTemplate redisTemplate
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException
     *             in case of thread interruption
     */
    public synchronized boolean acquire(StringRedisTemplate stringRedisTemplate) throws InterruptedException {
        int timeout = timeOutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires); //锁到期时间
            ValueOperations<String, String> valueOperations=stringRedisTemplate.opsForValue();
            if (valueOperations.setIfAbsent(lockKey, expiresStr)) {
                // lock acquired
                locked = true;
                return true;
            }

            //redis里的时间
            String currentValueStr = valueOperations.get(lockKey);
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                //判断是否为空，不为空的情况下，如果被其他线程设置了值，
                // 则第二个条件判断是过不去的
                // lock is expired
                // getAndSet()将给定 key 的值设为 value ，并返回 key 的旧值(old value)。

                // 当 key 存在但不是字符串类型时，返回一个错误。
                // 当key不存在时，返回nil
                String oldValueStr = valueOperations.getAndSet(lockKey, expiresStr);
                //获取上一个锁到期时间，并设置现在的锁到期时间，
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    // lock acquired
                    locked = true;
                    return true;
                }
            }
            timeout -= 100;
            Thread.sleep(100);
        }
        return false;
    }

}
