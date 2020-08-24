package com.zheng.mq;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.*;
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@StreamListener
public @interface StreamListenerWithHeader {
    @AliasFor(annotation = StreamListener.class)
    String value() default "";
    @AliasFor(annotation = StreamListener.class)
    String target() default "";
    @AliasFor(annotation = StreamListener.class)
    String condition() default "";
    @AliasFor(annotation = StreamListener.class)
    String copyHeaders() default "true";
}