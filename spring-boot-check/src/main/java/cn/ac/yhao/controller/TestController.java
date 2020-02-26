package cn.ac.yhao.controller;

import cn.ac.yhao.check.Check;
import cn.ac.yhao.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {


    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello!";
    }

    @RequestMapping("/")
    public ModelAndView toTest() {
        return new ModelAndView("test");
    }

    @Check({"name", "age>18:年龄需要满18周岁"})
    @RequestMapping("/testCheck")
    @ResponseBody
    public String test(User user) {
        return "user: [name:"+user.getName()+"age:" +user.getAge()+"]";
    }
}
