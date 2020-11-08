package cn.ac.yhao.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @GetMapping("/index")
    @ApiOperation("index测试")
    public String index() {
        return "hello index";
    }
}
