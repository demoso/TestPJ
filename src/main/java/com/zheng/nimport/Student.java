package com.zheng.nimport;


import com.zheng.condition.Person;
import org.springframework.context.annotation.Bean;

public class Student {
    @Bean(name = "billGate")
    public Person person1(){
        return new Person("Bill Gates",62);
    }

}
