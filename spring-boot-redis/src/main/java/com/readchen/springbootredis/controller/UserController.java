package com.readchen.springbootredis.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;

import com.readchen.springbootredis.entity.User;
import com.readchen.springbootredis.mapper.UserMapper;
import com.readchen.springbootredis.service.RedisService;
import com.readchen.springbootredis.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("users")
    JsonResult getUsers(){
        List<User> users = userMapper.selectList(null);
        return JsonResult.success(users);
    }

    @GetMapping("user/{userID}")
    JsonResult getUserByID(@PathVariable("userID") long userId){
        if(userId<1000000){
            userId = 1228548876865130498l;
        }


        User user = (User) redisService.get(""+userId);
        if (user==null){
            user = userMapper.selectById(userId);
            redisService.set(""+userId,user);

        }

        return JsonResult.success(user);
    }

    @GetMapping("redis")
    JsonResult redis1(){
//        stringRedisTemplate.opsForValue().set("users","name:yun");

        redisService.set("user","{'name':'yun'}");

        return JsonResult.success(redisService.get("user"));
    }



    @GetMapping("/update1")
    JsonResult updateUser(){
        User user = new User();
        user.setId(1228548876865130498L);
        user.setName("yun update");
        int rows = userMapper.updateById(user);
        if(rows>0){
            User user1 = userMapper.selectById(1228548876865130498L);
            return JsonResult.success(user1);
        }
        return JsonResult.fail(1,"更新失败");
    }





    @GetMapping("/update4")
    JsonResult updateUser4(){
//Lambda 链式更新
        boolean update = new LambdaUpdateChainWrapper<User>(userMapper).eq(User::getAge,18).
                set(User::getAge,30).set(User::getName,"yun").update();

        if(update){
            User user1 = userMapper.selectById(1228548876865130498L);
            return JsonResult.success(user1);
        }
        return JsonResult.fail(1,"更新失败");
    }

    @PostMapping("user")
    JsonResult addUser(){
        User user = new User();
        user.setAge(18);
        user.setName("yun");
        user.setEmail("yun@163.com");
        user.setManagerId(1088248166370832385L);

        int rows = userMapper.insert(user);
        if(rows>0){
            return JsonResult.success("添加成功");
        }else{
            return JsonResult.fail(2,"添加失败");
        }

    }

}
