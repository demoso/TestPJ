package com.zheng.lock.anno;


import com.zheng.lock.constants.CacheScope;
import com.zheng.lock.parser.ICacheResultParser;
import com.zheng.lock.parser.IKeyGenerator;
import com.zheng.lock.parser.impl.DefaultKeyGenerator;
import com.zheng.lock.parser.impl.DefaultResultParser;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 * @author yuy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited
@Documented
public @interface DistributedLock {
    /**
     * 缓存key menu_{0.id}{1}_type
     *
     */
     String key() default "";

    /**
     * 作用域
     */
     CacheScope scope() default CacheScope.application;

    /**
     * 过期时间
     */
     int expire() default 5;

    /**
     * 等待时间
     */
     int waitTime() default 0;

    /**
     * 时间单位
     */
     TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 描述
     */
    public String desc() default "";

    /**
     * 返回类型
     */
    public Class[] result() default Object.class;

    /**
     * 返回结果解析类
     */
    public Class<? extends ICacheResultParser> parser() default DefaultResultParser.class;

    /**
     * 键值解析类
     */
    public Class<? extends IKeyGenerator> generator() default DefaultKeyGenerator.class;
}
