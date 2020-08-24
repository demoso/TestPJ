package com.zheng.lock.anno;

import com.zheng.utils.LocaleUtil;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;


@EnableAspectJAutoProxy(exposeProxy = true)
@Component
public class ProxyAgent extends LocaleUtil {
    private LocaleUtil getProxyObject() {
        return ((LocaleUtil)AopContext.currentProxy());
    }


    public  String getMsg(String code) {
        return getProxyObject().getMsg(code);
    }

    /**
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @return
     */
    public  String getMsg(String code, Object[] args){
        return getProxyObject().getMsg(code, args);
    }

    /**
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    public  String getMsg(String code,Object[] args,String defaultMessage){
       return getProxyObject().getMsg(code,args,defaultMessage);
    }

}

