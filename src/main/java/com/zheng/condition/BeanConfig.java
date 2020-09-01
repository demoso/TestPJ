package com.zheng.condition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
//第一个条件类实现的方法返回true，第二个返回false，则结果false，不注入进容器。
//第一个条件类实现的方法返回true，第二个返回true，则结果true，注入进容器中。
@Conditional({WindowsCondition.class,ObstinateCondition.class})
public class BeanConfig {

    //只有一个类时，大括号可以省略
    //如果WindowsCondition的实现方法返回true，则注入这个bean
   // @Conditional({WindowsCondition.class})
    @Bean(name = "bill")
    public Person person1(){
        return new Person("Bill Gates",62);
    }

    //如果LinuxCondition的实现方法返回true，则注入这个bean
   // @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person person2(){
        return new Person("Linus",48);
    }
}
