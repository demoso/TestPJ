package com.zheng.aspect;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class NewKeyPrefix implements KeyPrefix1 {
    private static String DEFAULT_PREFIX="555";
    @Override
    public String getPrefix(){
        return DEFAULT_PREFIX;
    }
}
