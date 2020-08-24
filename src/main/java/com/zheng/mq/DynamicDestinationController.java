package com.zheng.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@EnableBinding
@Controller
public class DynamicDestinationController {

    @Autowired
    private BinderAwareChannelResolver resolver;

    /************************************方式一************************************/
    @RequestMapping(path = "/{dest}", method = RequestMethod.POST, consumes = "*/*")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleRequest(@PathVariable("dest") String dest,
                              @RequestBody String body,
                              @RequestHeader(HttpHeaders.CONTENT_TYPE) Object contentType) {
        sendMessage(body, dest, contentType);
    }

    private void sendMessage(String body, String dest, Object contentType) {
        resolver.resolveDestination(dest).send(MessageBuilder.createMessage(body,
                new MessageHeaders(Collections.singletonMap(MessageHeaders.CONTENT_TYPE, contentType))));
    }
}