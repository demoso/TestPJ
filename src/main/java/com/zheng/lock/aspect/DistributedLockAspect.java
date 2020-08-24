package com.zheng.lock.aspect;


import com.alibaba.fastjson.JSON;
import com.zheng.cache.RedissonService;
import com.zheng.lock.anno.DistributedLock;
import com.zheng.lock.parser.ICacheResultParser;
import com.zheng.lock.parser.IKeyGenerator;
import com.zheng.lock.parser.impl.DefaultKeyGenerator;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存开启注解拦截
 * @Aspect:作用是把当前类标识为一个切面供容器读取
 *
 * @Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。方法签名必须是 public及void型。
 *           可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名的方式为 此表达式命名。
 *           因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
 * @Around：环绕增强，相当于MethodInterceptor
 * @AfterReturning：后置增强，相当于AfterReturningAdvice，方法正常退出时执行
 * @Before：标识一个前置增强方法，相当于BeforeAdvice的功能，相似功能的还有
 * @AfterThrowing：异常抛出增强，相当于ThrowsAdvice
 * @After: final增强，不管是抛出异常或者正常退出都会执行
 * @author wanghaobin
 * @description
 * @date 2017年5月18日
 * @since 1.7
 */
@Aspect
@Service
public class DistributedLockAspect {
    @Autowired
    private IKeyGenerator keyParser;
    @Autowired
    private RedissonService redissonService;
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    private ConcurrentHashMap<String, ICacheResultParser> parserMap = new ConcurrentHashMap<String, ICacheResultParser>();
    private ConcurrentHashMap<String, IKeyGenerator> generatorMap = new ConcurrentHashMap<String, IKeyGenerator>();

    @Pointcut("@annotation(com.zheng.lock.anno.DistributedLock)")
    public void aspect() {
    }

    @Around("aspect()&&@annotation(distributedLock)")
    public Object interceptor(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        Object result = "系统繁忙，请稍后再试!";
        RLock lock = null;
        String key = "";
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] arguments = joinPoint.getArgs();
            key = getKey(distributedLock, parameterTypes, arguments);
            if (StringUtils.isEmpty(key)){
                throw new RuntimeException("key不能为空");
            }
            lock=redissonService.getRLock(key);
            if (lock != null) {
                log.info("distributedLock:"+ JSON.toJSONString(distributedLock));
                final boolean status = lock.tryLock(distributedLock.waitTime(),distributedLock.expire(), distributedLock.timeUnit());
                if (status) {
                    result = joinPoint.proceed();
                }
            }else {
                log.info("未获取到锁：{}", key);
            }
        } catch (Exception e) {
            log.error("系统异常：" + key, e);
        }
        finally {
            log.info("finally");
            if (lock != null && lock.isHeldByCurrentThread()) {
                log.info("解锁unlock："+lock.isHeldByCurrentThread());
                lock.unlock();
            }
        }
        return result;
    }

    /**
     * 解析表达式
     *
     * @param anno
     * @param parameterTypes
     * @param arguments
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private String getKey(DistributedLock anno, Class<?>[] parameterTypes,
                          Object[] arguments) throws InstantiationException,
            IllegalAccessException {
        String key;
        String generatorClsName = anno.generator().getName();
        IKeyGenerator keyGenerator;
        if (anno.generator().equals(DefaultKeyGenerator.class)) {
            keyGenerator = keyParser;
        } else {
            if (generatorMap.contains(generatorClsName)) {
                keyGenerator = generatorMap.get(generatorClsName);
            } else {
                keyGenerator = anno.generator().newInstance();
                generatorMap.put(generatorClsName, keyGenerator);
            }
        }

        key = keyGenerator.getKey(anno.key(), anno.scope(), parameterTypes,
                arguments);
        return key;
    }

    /**
     * 解析结果
     *
     * @param anno
     * @param result
     * @param value
     * @param returnType
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private Object getResult(DistributedLock anno, Object result, String value,
                             Type returnType) throws InstantiationException,
            IllegalAccessException {
        String parserClsName = anno.parser().getName();
        ICacheResultParser parser = null;
        if (parserMap.containsKey(parserClsName)) {
            parser = parserMap.get(parserClsName);
        } else {
            parser = anno.parser().newInstance();
            parserMap.put(parserClsName, parser);
        }
        if (parser != null) {
            if (anno.result()[0].equals(Object.class)) {
                result = parser.parse(value, returnType,
                        null);
            } else {
                result = parser.parse(value, returnType,
                        anno.result());
            }
        }
        return result;
    }
    public DistributedLock getRlockInfo(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(DistributedLock.class);
    }

    /**
     * 获取redis lock key
     *
     * @param proceedingJoinPoint
     * @return
     */
    public String getLocalKey(ProceedingJoinPoint proceedingJoinPoint, DistributedLock rlockInfo) {
        StringBuilder localKey = new StringBuilder("Rlock");
        final Object[] args = proceedingJoinPoint.getArgs();
        String key = "";

        // 如果没有设置锁值
//        if (StringUtils.isNotEmpty(rlockInfo.key())) {
//            key = rlockInfo.key();
//        } else {
//            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
//            Class[] parameters = methodSignature.getParameterTypes();
//            String methodName = methodSignature.getMethod().getName();
//
//            if (parameters != null) {
//                for (int i = 0; i < parameters.length; i++) {
//                    Class parameter = parameters[i];
//                    if (parameter.getSimpleName().equals("NDevice")) {
//                        NDevice de = (NDevice) args[i];
//                        key = de.getUuid() + methodName;
//                    }
//                    if (parameter.getSimpleName().equals("FrameBean")) {
//                        FrameBean de = (FrameBean) args[i];
//                        key = de.getColumn1() + methodName;
//                    }
//                }
//                // 如果没有获取到业务编号，则使用方法签名
//                if (StringUtils.isEmpty(key)) {
//                    key = methodName;
//                }
//            }
//        }
        return key;
    }
}
