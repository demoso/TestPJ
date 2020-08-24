package com.zheng.mq;


import com.zheng.annotation.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @auther: Along
 * @description: 类说明
 * @Date: created in 2019/1/10 11:30
 */
@Component
@Slf4j
public class SendMQ {

    @Autowired
    private MQProcessor mqProcessor;

    /**
     * APP 站内消息
     */
    public void Send(){
        try{
            for(int i=0;i<1;i++) {
                boolean send = mqProcessor.orderDoneOutput().send(MsgBuilder.withPayloadWithDefaultHeader(i).build());
            }
        }catch(Exception e){
            log.info("APP站内消息发送异常...");
            e.printStackTrace();
        }
    }



//
//        @Bean
//        @InboundChannelAdapter(value =MQProcessor.ORDER_OUTPUT, poller = @Poller(fixedRate = "30000", maxMessagesPerPoll = "1"))
//        public MessageSource timerMessageSource() {
//            return () -> MsgBuilder.withPayloadWithDefaultHeader("send:"+Math.random()).build();
//        }


    }
