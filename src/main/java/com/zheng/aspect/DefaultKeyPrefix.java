package com.zheng.aspect;

import org.springframework.stereotype.Component;

@Component
public class DefaultKeyPrefix implements KeyPrefix {
    private static String DEFAULT_PREFIX="4333";
    @Override
    public String getPrefix(){
        return DEFAULT_PREFIX;
    }
}
