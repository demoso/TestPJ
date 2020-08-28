package com.zheng.aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy //启用AspectJ自动代理
@Configuration //定义配置类
@ComponentScan( basePackages={"com.zheng.aspect"}) //扫描的包路径
public class Config {
}
