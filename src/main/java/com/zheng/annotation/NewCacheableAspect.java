package com.zheng.annotation;


import com.zheng.cache.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * redis缓存处理 不适用与内部方法调用(this.)或者private
 */
@Component
@Aspect
@Slf4j
public class NewCacheableAspect {

    @Autowired
    private RedisService redisService;

    @Pointcut("@annotation(com.zheng.annotation.NewCacheable)")
    public void annotationPointcut() {
    }

    @Around("annotationPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获得当前访问的class
        Class<?> className = joinPoint.getTarget().getClass();
        // 获得访问的方法名
        String methodName = joinPoint.getSignature().getName();
        // 得到方法的参数的类型
        Class<?>[] argClass = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Object[] args = joinPoint.getArgs();
        String key = "";
        int expireTime = 3600;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            method.setAccessible(true);
            // 判断是否存在@ExtCacheable注解
            if (method.isAnnotationPresent(NewCacheable.class)) {
                NewCacheable annotation = method.getAnnotation(NewCacheable.class);
                key = getRedisKey(args, annotation);
                expireTime = getExpireTime(annotation);
            }
        } catch (Exception e) {
            throw new RuntimeException("redis缓存注解参数异常", e);
        }
        log.info(key);
        boolean hasKey = redisService.hasKey(key);
        if (hasKey) {
            return redisService.get(key);
        } else {
            Object res = joinPoint.proceed();
            redisService.set(key, res);
            redisService.expire(key, expireTime);
            return res;
        }
    }

    private int getExpireTime(NewCacheable annotation) {
        return annotation.expireTime();
    }

    private String getRedisKey(Object[] args, NewCacheable annotation) throws Exception{
        String primalKey = annotation.key();
        // 获取#p0...集合
        List<String> keyList = getKeyParsList(primalKey);
        for (String keyName : keyList) {
            int keyIndex = Integer.parseInt(keyName.toLowerCase().replace("#p", ""));
            Object parValue = getParValue(annotation, keyIndex, args);
            primalKey = primalKey.replace(keyName, String.valueOf(parValue));
        }
        return primalKey.replace("+", "").replace("'", "");
    }

    private Object getParValue(NewCacheable annotation, int keyIndex, Object[] args) throws Exception{
        int[] objectIndexArray = annotation.objectIndexArray();
        String[] objectFieldArray = annotation.objectFieldArray();
        if (existsObject(keyIndex, objectIndexArray)) {
            return getParValueByObject(args, keyIndex, objectFieldArray);
        } else {
            return args[keyIndex];
        }
    }

    private Object getParValueByObject(Object[] args, int keyIndex, String[] objectFieldArray) throws Exception {
        Class cls = args[keyIndex].getClass();
        Method method;
        if(objectFieldArray!=null&&objectFieldArray.length>=keyIndex){
            method = cls.getMethod("get" + StringUtil.firstCharToUpperCase(objectFieldArray[keyIndex]));
        }else{
            method = cls.getMethod("get" + StringUtil.firstCharToUpperCase(cls.getFields()[0].getName()));
        }
        method.setAccessible(true);
        log.info(method.getName());
        return method.invoke(args[keyIndex]);
    }

    private boolean existsObject(int keyIndex, int[] objectIndexArray) {
        if (objectIndexArray == null || objectIndexArray.length <= 0) {
            return false;
        }
        for (int i = 0; i < objectIndexArray.length; i++) {
            if (keyIndex == objectIndexArray[i]) {
                return true;
            }
        }
        return false;
    }

    // 获取key中#p0中的参数名称
    private static List<String> getKeyParsList(String key) {
        List<String> ListPar = new ArrayList<String>();
        if (key.indexOf("#") >= 0) {
            int plusIndex = key.substring(key.indexOf("#")).indexOf("+");
            int indexNext = 0;
            String parName = "";
            int indexPre = key.indexOf("#");
            if (plusIndex > 0) {
                indexNext = key.indexOf("#") + key.substring(key.indexOf("#")).indexOf("+");
                parName = key.substring(indexPre, indexNext);
            } else {
                parName = key.substring(indexPre);
            }
            ListPar.add(parName.trim());
            key = key.substring(indexNext + 1);
            if (key.indexOf("#") >= 0) {
                ListPar.addAll(getKeyParsList(key));
            }
        }
        return ListPar;
    }

}
