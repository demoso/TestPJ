package com.zheng.nimport;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 想要让一个普通类接受 Spring 容器管理，有以下方法
 *
 * 使用 @Bean 注解
 * 使用 @Controller @Service @Repository @Component 注解标注该类，然后再使用 @ComponentScan 扫描包
 * @Import 方法，即现在这种方式
 *
 * 作者：宿命99
 * 链接：https://www.jianshu.com/p/afd2c49394c2
 */
@Configuration
@Import({Student.class,MySelector.class,MyBeanDefinitionRegistrat.class})
public class ImportConfig {

}