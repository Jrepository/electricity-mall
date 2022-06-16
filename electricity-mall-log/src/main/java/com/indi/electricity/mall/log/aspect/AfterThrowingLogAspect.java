package com.indi.electricity.mall.log.aspect;

import com.indi.electricity.mall.log.annotation.AfterThrowingLog;
import com.indi.electricity.mall.utils.SystemUtil;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Log4j2
@Component
public class AfterThrowingLogAspect {

    @Pointcut("@annotation(com.indi.electricity.mall.log.annotation.AfterThrowingLog)")
    public void pointCut() {
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        AfterThrowingLog afterThrowingLog = method.getAnnotation(AfterThrowingLog.class);
//        if (afterThrowingLog == null
//                || !Arrays.asList(afterThrowingLog.evn()).contains(SystemUtil.getProperties())) {
//            return;
//        }
        String className = joinPoint.getTarget().getClass().getName();
        log.info("the param of method ({}) in class ({}) is ({})", className, method.getName(), joinPoint.getArgs());

    }
}
