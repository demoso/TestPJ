package com.zheng.aspect;

import com.zheng.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component //把当类交给容器管理
public class MathImpl implements IMath {
    @Override
    public int add(int a, int b) {
        return a+b;
    }

}
