package com.zheng.condition;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class ConditionalTest {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);

    @Test
    public void test1(){
//        Map<String, Person> map = applicationContext.getBeansOfType(Person.class);
//        System.out.println(map);

        String[] beanDefinitionNames= applicationContext.getBeanDefinitionNames();
        for (String bean:beanDefinitionNames){
            System.out.println("--->"+bean);
        }
        String osName = applicationContext.getEnvironment().getProperty("os.name");
        System.out.println("当前系统为：" + osName);
        Map<String, Person> map = applicationContext.getBeansOfType(Person.class);
        System.out.println(map);
    }
}