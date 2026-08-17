package com.qiuzhitech.onlineshopping_06.controller;

import com.qiuzhitech.onlineshopping_06.service.JwtService;
import com.qiuzhitech.onlineshopping_06.user.UserTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserTestController {
    Map<Integer, UserTest> users = new HashMap<>();

    @Resource(name = "zhangsan")
    UserTest defaultUser;

    @Resource
    JwtService jwtService;

    @ResponseBody
    @PostMapping("/users")
    public String createUser(@RequestParam("id") int id,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email) {
        UserTest userTest = UserTest.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();

        users.put(id, userTest);
        return "success";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable("id") int id,
                          Map<String, Object> resultMap) {
        UserTest userTest = users.getOrDefault(id, defaultUser);
        resultMap.put("user", userTest);
        String jwt = jwtService.generateToken(userTest);
        resultMap.put("jwtToken", jwt);
        String jwtUserName = jwtService.extractUsername(jwt);
        resultMap.put("jwtUserName", jwtUserName);
        return "user_detail";
    }

}
