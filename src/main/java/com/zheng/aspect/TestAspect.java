package com.zheng.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component //把当前类对象交给spring容器管理
@Aspect //标注当前类未切面
@Slf4j
public class TestAspect {
    @Before(value = "execution(public int com.zheng.aspect.MathImpl.add(int , int ))")
    public void before(){
        System.out.println("相加之前");
    }
}
