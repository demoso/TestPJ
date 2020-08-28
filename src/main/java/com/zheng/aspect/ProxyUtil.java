package com.zheng.aspect;

import lombok.AllArgsConstructor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;
@AllArgsConstructor
public class ProxyUtil {
    private MathImpl math;

    public Object getProxy(){
         ClassLoader classLoader=this.getClass().getClassLoader();
         Class[] interfaces=math.getClass().getInterfaces();
         //黎明类部类
         return Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
             @Override
             public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                 System.out.println("before");
              Object object= method.invoke(math,(Object) new int[]{1,2} );
                 System.out.println("after");
                 return object;
             }
         } );
    }
}
