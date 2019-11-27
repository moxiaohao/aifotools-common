package io.aifo.aifotoolslib.aop;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.List;

import io.aifo.aifotoolslib.utils.MemoryCacheManager;

@Aspect
public class MemoryCacheAspect {

    @Pointcut("execution(@com.app.annotation.aspect.MemoryCache * *(..))")//方法切入点
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        MemoryCacheManager mMemoryCacheManager = MemoryCacheManager.getInstance();
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(methodName);
        for (Object obj : joinPoint.getArgs()) {
            if (obj instanceof String) keyBuilder.append((String) obj);
            else if (obj instanceof Class) keyBuilder.append(((Class) obj).getSimpleName());
        }
        String key = keyBuilder.toString();
        Object result = mMemoryCacheManager.get(key);//key规则 ： 方法名＋参数1+参数2+...
        if (result != null) return result;//缓存已有，直接返回
        result = joinPoint.proceed();//执行原方法
        if (result instanceof List && result != null && ((List) result).size() > 0 //列表不为空
                || result instanceof String && !TextUtils.isEmpty((String) result)//字符不为空
                || result instanceof Object && result != null)//对象不为空
            mMemoryCacheManager.add(key, result);//存入缓存
        return result;
    }
}
