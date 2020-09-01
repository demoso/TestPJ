package com.zheng.nimport;


import com.zheng.condition.BeanConfig;
import com.zheng.condition.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class NimportTest {
    //https://blog.csdn.net/xcy1193068639/article/details/81491071
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


    @Test
    public void test7() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ImportConfig.class);
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }
}