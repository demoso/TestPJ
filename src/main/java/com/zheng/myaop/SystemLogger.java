package com.zheng.myaop;

import java.lang.annotation.*;

/**
 * @Description Accesslog:
 * @Author LiHaitao
 * @Date 2018/12/21 15:37
 **/


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLogger {
    String description() default "";

}

