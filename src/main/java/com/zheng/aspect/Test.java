package com.zheng.aspect;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext ac= new AnnotationConfigApplicationContext(Config.class);
        IMath iMath=ac.getBean("mathImpl",IMath.class);
        int i=iMath.add(1,2);
        System.out.println(i);
    }
}
