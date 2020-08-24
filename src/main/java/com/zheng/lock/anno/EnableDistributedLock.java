package com.zheng.lock.anno;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author wangdemo
 * @create 2017/11/17.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoConfiguration.class)
@Documented
@Inherited
public @interface EnableDistributedLock {
}
