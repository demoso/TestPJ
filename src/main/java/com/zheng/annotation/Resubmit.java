package com.zheng.annotation;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.Callable;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Resubmit {

    @AliasFor("cacheNames")
    String[] value() default {};


    @AliasFor("value")
    String[] cacheNames() default {};


    String key() default "";

    /**
     * 过期时间
     *
     * @return
     * @author Ace
     * @date 2017年5月3日
     */
    public int expire() default 720;

}
