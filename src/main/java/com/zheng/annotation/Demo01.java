package com.zheng.annotation;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
@Slf4j
public class Demo01 {
    public Map<Integer,User>  test01(Map<String,User> map, List<User> list) throws NoSuchMethodException {
            log.info(Demo01.class.getMethod("test01").toString());
            return null;
    }

    public static void main(String[] args) {
        try{
           Method method=Demo01.class.getDeclaredMethod("test01",Map.class,List.class);
           Type[] types=method.getGenericParameterTypes();
           for(Type type:types){
               log.info("#"+type);
               if(type instanceof ParameterizedType){
                   Type[] types1=((ParameterizedType) type).getActualTypeArguments();
                   for(Type type1:types1){
                       log.info("###"+type1);
                   }
               }
           }

           Type returnType=method.getGenericReturnType();
           if(returnType instanceof ParameterizedType){
               log.info("@"+returnType);
               Type[] rtypes=((ParameterizedType) returnType).getActualTypeArguments();
               for(Type type:rtypes){
                   log.info("@@@@"+type);
               }
           }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
