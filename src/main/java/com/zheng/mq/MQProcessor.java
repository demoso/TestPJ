package com.zheng.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MQProcessor {


    /**
     * 订单完成监听
     */
    String ORDER_INPUT = "orderInput";

    @Input(ORDER_INPUT)
    SubscribableChannel orderDoneInput();

    /**
     * 订单完成发送
     */
    String ORDER_OUTPUT = "orderOutput";

    @Output(ORDER_OUTPUT)
    MessageChannel orderDoneOutput();

    /**
     * 回执
     */
    String backOutPut = "backOutPut";
    @Output(backOutPut)
    MessageChannel backOutPut();

    /**
     * 回执
     */
    String backInPut = "backInPut";
    @Input(backInPut)
    SubscribableChannel backInPut();

}
