package cc.maids.library.aop;


import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Around;

import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

  @Before("within(cc.maids.library..*ServiceImpl)")
  public void logMethodCall(JoinPoint joinPoint) {
    log.info("Entering method: {} with arguments: {}", joinPoint.getSignature().toShortString(),
        joinPoint.getArgs());
  }

  @After("within(cc.maids.library..*ServiceImpl)")
  public void logMethodExit(JoinPoint joinPoint) {
    log.info("Exiting method: {}", joinPoint.getSignature().toShortString());
  }

  @AfterThrowing(pointcut = "within(cc.maids.library..*ServiceImpl)", throwing = "exception")
  public void logMethodException(JoinPoint joinPoint, Throwable exception) {
    log.error("Exception in method: {} with message: {}", joinPoint.getSignature().toShortString(),
        exception.getMessage());
  }

  @Around("within(cc.maids.library..*ServiceImpl)")
  public Object logMethodPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object result = joinPoint.proceed();
    long elapsedTime = System.currentTimeMillis() - start;
    log.info("Method: {} executed in {} ms", joinPoint.getSignature().toShortString(), elapsedTime);
    return result;
  }
}


