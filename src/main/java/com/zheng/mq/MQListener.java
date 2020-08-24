package com.zheng.mq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.Map;

/**
 *账户监听
 * @Author: xjf
 *@Date: 18-12-24 下午2:56
 *@Description:
 */
@Slf4j
@EnableBinding(MQProcessor.class)
public class MQListener {


    /**
     * 增加积分StreamListenerWithHeader
     * :@Payload注解内容为消息体，@Headers注解获取所有的Header头信息，@Header注解获取指定name的头信息
     * :@StreamListener(value = MySink.USER_CHANNEL, condition = "headers['flag']=='aa'")
     * 高可用的多实例服务务必添加spring.cloud.stream.bindings.{channel-name}.group配置，避免消息被重复消费；
     * @param payload
     */
    @StreamListenerWithHeader(MQProcessor.ORDER_INPUT)
    @SendTo(value = {MQProcessor.backOutPut})
    public int userReceive(@Payload String payload, @Headers Map headers, @Header(name = "tenantId") Object name) {
        log.info("start------------->");
        log.info(headers.get("contentType").toString());
        headers.forEach((key, value)->{
            log.info("key = " + key);
            log.info("value = " + value);
        });
//        StreamListenerWithHeader ann = AnnotationUtils.findAnnotation(getClass(),StreamListenerWithHeader.class);
//        System.out.println(ann.value());
//        System.out.println(ann.target());

        log.info("Header--name : {}", name.toString());
        log.info("Received from {} channel username: {}", MQProcessor.ORDER_INPUT, payload);
        return 9999;
    }


    @StreamListenerWithHeader(MQProcessor.backInPut)
    public void backReceive(@Payload String payload, @Headers Map headers, @Header(name = "tenantId") Object name,@Header(name = "sourceData") Object value) {
        log.info("start backInPut------------->");
//        log.info(headers.get("contentType").toString());
//        headers.forEach((key, value)->{
//            log.info("key = " + key);
//            log.info("value = " + value);
//        });
        log.info("Header--body : {}", value.toString());
        log.info("Header--name : {}", name.toString());
//        log.info("Received from {} channel username: {}", MQProcessor.ORDER_INPUT, payload);

    }

    public void pointAdd(int payload) {
        log.info("收到增加积分的消息: " + payload);
    }

}