package com.zheng.annotation;

import com.alibaba.fastjson.JSON;
import com.sun.tools.classfile.Annotation;
import com.zheng.lock.anno.DistributedLock;
import com.zheng.web.HelloController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
@Slf4j
public class AnnotationProcessor {

    public static void init(Object object) {

        if (!(object instanceof User)) {
            throw new IllegalArgumentException("[" + object.getClass().getSimpleName() + "] isn't type of User");
        }

        Constructor[] constructors = object.getClass().getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.isAnnotationPresent(UserMeta.class)) {
                UserMeta userFill = (UserMeta) constructor.getAnnotation(UserMeta.class);
                int age = userFill.age();
                int id = userFill.id();
                String name = userFill.name();
                ((User) object).setAge(age);
                ((User) object).setId(id);
                ((User) object).setName(name);
            }
        }
    }

        public static void main(String[] args) throws ClassNotFoundException {
//            User user = new User();
            try {
                Class<HelloController> clazz = (Class<HelloController>) Class.forName("com.zheng.web.HelloController");
                Class clazz1 =  "com.zheng.web.HelloController".getClass();
                Class clazz3 =   com.zheng.web.HelloController.class;
                log.info("name:{},simplename:{}",clazz.getName(),clazz.getSimpleName());
                HelloController helloController=(HelloController) clazz.newInstance();
//                helloController.hello6(6);
                Field field2=clazz.getDeclaredField("sendMQ");
                log.info("field.name:"+field2.getName()+":"+field2.getType());
//                field2.set();
//                field2.get()

              //  Method[] methods =clazz.getDeclaredMethods();
                Method[] methods =clazz.getMethods();
                for(Method method: methods){
                    System.out.println(method.getName());
                    DistributedLock distributedLock=method.getAnnotation(DistributedLock.class);
                    System.out.println(method.getAnnotation(DistributedLock.class));
                    System.out.println("distributedLock:"+ JSON.toJSONString(distributedLock));
                }
                Method method2=clazz.getDeclaredMethod("hello6", int.class);
                method2.invoke(clazz.newInstance(),10);

                Field[] fields=clazz.getDeclaredFields();
                for(Field field:fields){
                    System.out.println(field.getName());
                    System.out.println(field.getAnnotation(Autowired.class));
                }
                Annotation annotation = (Annotation) clazz.getAnnotation(DistributedLock.class);
                System.out.println(annotation);

            }catch (ClassNotFoundException | NoSuchFieldException e){
                 e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
//            AnnotationProcessor.init(user);
//            System.out.println(user.toString());
        }



}


