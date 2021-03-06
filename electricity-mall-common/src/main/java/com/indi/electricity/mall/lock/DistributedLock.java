//package com.indi.electricity.mall.lock;
//
//import com.indi.electricity.mall.utils.SpringUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class DistributedLock {
//
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private static final int expireMsecs = 100;
//
//    private String lockKey;
//    private String requestId;
//    private int expireTime;
//
//    private RedisDistributedLock redisDistributedLock;
//
//    public DistributedLock(String lockKey, int expireTime) {
//        this.lockKey = lockKey;
//        this.expireTime = expireTime;
//        this.redisDistributedLock = SpringUtil.getBean(RedisDistributedLock.class);
//    }
//
//    public DistributedLock() {
//    }
//
//
//    /**
//     * 执行操作
//     *
//     * @return
//     */
//    public boolean run() {
//        this.requestId = this.redisDistributedLock.lock(lockKey, expireMsecs, expireTime);
//        if (StringUtils.isNoneBlank(requestId)) {
//            try {
//                // 定时任务处理逻辑
//                return this.execute();
//            } catch (Exception e) {
//                logger.error("{} lockRunError{}", lockKey, e.getMessage());
//            } finally {
//                // 释放锁
//                this.redisDistributedLock.release(lockKey, requestId);
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 执行动作 可覆盖
//     *
//     * @return
//     */
//    public boolean execute() {
//        return true;
//    }
//
//
//}
