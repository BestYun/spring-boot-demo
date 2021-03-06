package com.readchen.springbootHikariCP.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.readchen.springbootHikariCP.entity.User;
import com.readchen.springbootHikariCP.mapper.UserMapper;
import com.readchen.springbootHikariCP.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("users")
    JsonResult getUsers(){
        List<User> users = userMapper.selectList(null);
        return JsonResult.success(users);
    }

    @GetMapping("user/{userID}")
    JsonResult getUserByID(@PathVariable("userID") long userId){
        User user = userMapper.selectById(userId);
        return JsonResult.success(user);
    }

    @GetMapping("test1")
    JsonResult getUserByNameAndAge(){

        Map<String,Object> cloums = new HashMap<>();
        cloums.put("name","yun");
        cloums.put("age",18);
        List<User> users = userMapper.selectByMap(cloums);
        return JsonResult.success(users);
    }


    @GetMapping("test2")
    JsonResult getUserByQuery(){
//       条件构造器  where age > 10
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.gt("age","10");
        List<User> users = userMapper.selectList(queryWrapper);
        return JsonResult.success(users);
    }

    @GetMapping("test3")
    JsonResult getUserByQuery2(){
//       条件构造器 WHERE (age > ? AND name LIKE ? AND email IS NOT NULL)
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.gt("age","10").like("name","y").isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        return JsonResult.success(users);
    }

    @GetMapping("test4")
    JsonResult getUserByQuery4(){
//       条件构造器 WHERE (name LIKE ? OR name LIKE ?) ORDER BY age ASC
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.like("name","y").or().like("name","王").orderByAsc("age");
        List<User> users = userMapper.selectList(queryWrapper);
        return JsonResult.success(users);
    }


    @GetMapping("test5")
    JsonResult getUserByQuery5(){
//       子查询 WHERE (age > ? AND manager_id IN (select manager_id from user where name like '%王' ))
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.apply("age > {0}",10).
                inSql("manager_id","select manager_id from user where name like '%王%' ");

        List<User> users = userMapper.selectList(queryWrapper);
        return JsonResult.success(users);
    }

    @GetMapping("test6")
    JsonResult getUserByQuery6(){
//      WHERE (name LIKE ? OR (age > ? AND email IS NOT NULL))
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.like("name","%王%").
                or(qw->qw.gt("age",40).isNotNull("email"));

        List<User> users = userMapper.selectList(queryWrapper);
        return JsonResult.success(users);
    }

    @GetMapping("test7")
    JsonResult getUserByQuery7(){
//      WHERE (age > ? AND email IS NOT NULL) AND name LIKE ?
//        nested 不是以and或者or开头作为条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.nested(qw->qw.gt("age",20).isNotNull("email")).
                like("name","%王%");

        List<User> users = userMapper.selectList(queryWrapper);
        return JsonResult.success(users);
    }

    @GetMapping("test8")
    JsonResult getUserByQuery8(){
//      WHERE name=? AND age=?
//      实体作为条件参数

        User user = new User();
        user.setName("yun");
        user.setAge(18);

        QueryWrapper<User> queryWrapper = new QueryWrapper<User>(user);

        List<User> users = userMapper.selectList(queryWrapper);
        return JsonResult.success(users);
    }

    @GetMapping("test9")
    JsonResult getUserByQuery9(){
//WHERE (name = ?)
//        lambda表达式查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getName,"yun").or().eq(User::getAge,18);

        List<User> users = userMapper.selectList(queryWrapper);
        return JsonResult.success(users);
    }



    @GetMapping("test10")
    JsonResult getUserByQuery10(){
        //自定义sql
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getName,"yun").or().eq(User::getAge,18);

        List<User> users = userMapper.getUserAll(queryWrapper);
        return JsonResult.success(users);
    }


    @GetMapping("test11")
    JsonResult getUserByQuery11(){
        //自定义sql

        List<User> users = userMapper.getUserAll2();
        return JsonResult.success(users);
    }



    @GetMapping("test12")
    JsonResult getUserByQuery12(){
        //自定义sql

        List<User> users = userMapper.getUserByName("yun");
        return JsonResult.success(users);
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
