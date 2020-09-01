package com.zheng.aspect;

import org.springframework.stereotype.Component;

@Component
public class DefaultKeyPrefix1 implements KeyPrefix1 {
    private static String DEFAULT_PREFIX="4333";
    @Override
    public String getPrefix(){
        return DEFAULT_PREFIX;
    }
}
