package com.zheng.web;


import com.hq.cloud.jcache.anno.Cache;
import com.zheng.cache.RedissonService;
//import com.zheng.lock.anno.DistributedLock;
import com.zheng.lock.anno.ProxyAgent;
import com.zheng.mq.SendMQ;
import com.zheng.utils.LocaleUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhenglian on 2016/10/9.
 */
@Slf4j
@RestController
//@AllArgsConstructor
public class HelloController extends LocaleUtil {
//    public HelloController(final SendMQ sendMQ, final RedissonService redissonService) {
//        this.sendMQ = sendMQ;
//        this.redissonService = redissonService;
//    }
//    @Autowired
//    private  LocaleUtil localeUtil;
    @Autowired
    private SendMQ sendMQ;
    @Autowired
    private RedissonService redissonService;




    @RequestMapping("/hello")
    @ResponseBody
    //@DistributedLock(key = "yyy:{1}")
    @Cache(name="abcduserCache-", key="#id", expire = 10)
    public String hello( int id) {
        log.info("START:"+new Date());
        RLock lock = redissonService.getRLock("AccountLock:ACCOUNT_SETTLEMENT_LOCK"+id);

        log.info("locked:"+lock.isLocked()+":"+new Date());
        lock.lock(180, TimeUnit.SECONDS);
        log.info("END:"+new Date());
        try {
            sendMQ.Send();
            String welcome = getMsg("welcome");
            System.out.println(welcome);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放锁..." + Thread.currentThread().getId());
            lock.unlock();
        }
         return  "welcome";

    }

    @RequestMapping("/hello2")
    @ResponseBody
    @Cacheable(value = "andican111#222",key="#id")
    public String hello2(int id) {
        String welcome = getMsg("welcome");
        System.out.println(welcome);
        return  welcome;
    }
    @RequestMapping("/hello3")
    @ResponseBody
    @Cache
    public String hello3(int id) {
        String welcome = getMsg("welcome");
        System.out.println(welcome);
        return  welcome;
    }

    @RequestMapping("/hello4")
    @ResponseBody
    //@DistributedLock(key = "yyy")
    @Cache(name="userCache-", key="#id", expire = 10)
    public String hello4( int id) {
        log.info("START:"+new Date());
        RLock lock = redissonService.getRLock("AccountLock:ACCOUNT_SETTLEMENT_LOCK"+id);

        log.info("locked:"+lock.isLocked()+":"+new Date());
        lock.lock(180, TimeUnit.SECONDS);
        log.info("END:"+new Date());
        try {
            sendMQ.Send();
            String welcome = getMsg("welcome");
            System.out.println(welcome);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 当前线程获取到锁再释放锁
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return  "welcome";

    }



    @RequestMapping("/hello5")
    @ResponseBody
    //@Cache(key = "yyy")
    //@Cached(name="userCache-", key="#id", expire = 10)
    public String hello5( int id) {
        log.info("START:"+new Date());
        RLock lock = redissonService.getRLock("AccountLock:ACCOUNT_SETTLEMENT_LOCK"+id);

        log.info("locked:"+lock.isLocked()+":"+new Date());

        try {
            if (lock.tryLock(0, 180,TimeUnit.SECONDS)) {
                log.info("END:" + new Date());
                // sendMQ.Send();
                String welcome = getMsg("welcome");
                System.out.println(welcome);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 当前线程获取到锁再释放锁
            if (lock != null && lock.isHeldByCurrentThread()) {
                log.info("finally");
               // lock.unlock();
            }
        }
        return  "welcome";
    }


    @RequestMapping("/hello6")
    @ResponseBody

    //@Cached(name="userCache-", key="#id", expire = 10)
    public String hello6( int id) {
        String welcome=null;
        try {
           // sendMQ.Send();
             welcome = getMsg("welcome");
            System.out.println(welcome);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  welcome;

    }



    @GetMapping("/testone")
    public String test() {
        log.info("test");
        return "ok";
    }

    @GetMapping("testwo")
    public String home() {
        log.info("home");
        return "ok";
    }
}
