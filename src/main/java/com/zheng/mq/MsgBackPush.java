package com.zheng.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MsgBackPush {

    @Output("back-push")
    MessageChannel backPush();
}
