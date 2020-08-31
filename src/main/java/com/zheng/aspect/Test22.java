package com.zheng.aspect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class Test22 {
    @Autowired
private KeyPrefix keyPrefix;

//    public static void main(String[] args) {
//        ApplicationContext ac= new AnnotationConfigApplicationContext(Config.class);
//        IMath iMath=ac.getBean("mathImpl",IMath.class);
//        int i=iMath.add(1,2);
//
////        Map<String,OutOfMemoryError> memoryErrorMap=new HashMap<>();
////        ProxyUtil proxyUtil= new ProxyUtil(new MathImpl());
////        MathImpl math= new MathImpl();
//
////        System.out.println(i);
////        ProxyUtil proxyUtil= new ProxyUtil(new MathImpl());
////        IMath iMath1=(IMath) proxyUtil.getProxy();
////        iMath1.add(23,43);
//
//        KeyPrefix  keyPrefix= ac.getBean("defaultKeyPrefix",KeyPrefix.class);
//        System.out.println(keyPrefix.getPrefix());
//    }


    @Test
    public void testPrintMessage() {
        System.out.println(keyPrefix.getPrefix());
    }
}
