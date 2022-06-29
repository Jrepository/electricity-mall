//package com.indi.electricity.mall.lock;
//
//import com.indi.electricity.mall.utils.RedisUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
///**
// * redis 实现的分布式锁
// */
//@Component
//public class RedisDistributedLock {
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    /**
//     * 加锁
//     * @return 返回字符串
//     */
//    public String lock(String key, Integer ackTime, Integer expireTime) {
//        try {
//            String requestId = UUID.randomUUID().toString();
//            if (!redisUtil.hasKey(key) && redisUtil.setNx(key, requestId, expireTime)) {
//                logger.info("redis lock:{} success requestId:{}", key, requestId);
//                return requestId;
//            }
//        } catch (Exception e) {
//            logger.error("lock key : {} error msg: {}", key, e.getMessage());
//        }
//        return null;
//    }
//
//    /**
//     * 解锁key
//     * @param requestId
//     * @return
//     */
//    public   boolean release(String key, String requestId){
//        if(requestId == null){
//            return false;
//        }
//        try {
//            String strRequestId = redisUtil.get(key).toString();
//            if (strRequestId.equals(requestId)) {
//                redisUtil.del(key);
//                return  true;
//            } else {
//                logger.error("release key {} client error {}", key , requestId);
//                return  false;
//            }
//        } catch (Exception e){
//            logger.error("release key {} error {} ", key, e.getMessage());
//        }
//        return  false;
//    }
//}
