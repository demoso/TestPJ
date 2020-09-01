package com.zheng.myaop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/7/26.
 */
@RestController
@RequestMapping("/student")
public class StudentController {



    @GetMapping(path = "/ok")
    @SystemLogger(description = "查询学生")    //注解方法处
    public String select() {
        return "ok";
    }
}
