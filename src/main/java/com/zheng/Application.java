package com.zheng;


import com.hq.cloud.jcache.anno.config.EnableCreateCacheAnnotation;
import com.hq.cloud.jcache.anno.config.EnableMethodCache;
import com.zheng.lock.anno.EnableDistributedLock;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by zhenglian on 2016/10/9.
 */
@SpringBootApplication
@EnableMethodCache(basePackages = "com.zheng")
@EnableCreateCacheAnnotation
@EnableBinding(Source.class)
@EnableDistributedLock
public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext=  SpringApplication.run(Application.class, args);
       System.out.println(applicationContext.getApplicationName());

    }

}
