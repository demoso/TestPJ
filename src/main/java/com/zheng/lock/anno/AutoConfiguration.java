package com.zheng.lock.anno;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author wangdemo
 * @create 2017/11/17.
 */
@ComponentScan({"com.zheng"})
@EnableAspectJAutoProxy
public class AutoConfiguration {

}
