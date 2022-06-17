package com.indi.electricity.mall.aop;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.indi.electricity.mall.annotation.ReturnTranslate;
import com.indi.electricity.mall.builder.TranslateAnnotationBuilder;
import com.indi.electricity.mall.utils.JsonUtil;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

@Aspect
@Component
@Log4j2
public class TranslateAnnotationAspect {

    @Autowired
    TranslateAnnotationBuilder builder;

    @AfterReturning(returning = "result", pointcut = "@annotation(com.indi.electricity.mall.annotation.ReturnTranslate)")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        long startTime = System.currentTimeMillis();
        log.info("data before ReturnTranslate:{}", JsonUtil.encode(result));
        if (result == null) {
            return;
        }
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        ReturnTranslate returnTranslate = method.getAnnotation(ReturnTranslate.class);
        if (result instanceof IPage) {
            result = ((IPage<?>) result).getRecords();
        }
        if (result instanceof List && !CollectionUtils.isEmpty((Collection<?>) result)) {
            builder.translateData((Collection<?>) result, returnTranslate.depth());
        }

        log.info("data after ReturnTranslate:{}", result);
        long endTime = System.currentTimeMillis();
        log.info("ReturnTranslate time :({})ms", endTime - startTime);
    }

}
