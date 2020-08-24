package com.zheng.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NewCacheable {

    String key() default "";

    int[] objectIndexArray();

    String[] objectFieldArray();

    int expireTime() default 1800;//30分钟

}