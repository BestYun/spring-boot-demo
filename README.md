# spring-boot-demo

## [spring-boot-web](https://github.com/BestYun/spring-boot-demo/tree/master/spring-boot-web)
创建控制器<br>
1)方式一
```
@Controller
@ResponseBody

public class Controller1 {
    @RequestMapping(value = "/route/v1",method = RequestMethod.GET)
    String get(){
        return "Controller ResponseBody RequestMapping /route/v1 get";
    }
}
```

2)方式二
```
@RestController
public class Controller2 {
    @GetMapping("/route/v2")
    String v2(){
        return "/route/v2";
    }
}
```

@RestController 是@Controller和@ResponseBody简写<br>
@GetMapping 是@RequestMapping(value = "/v1",method = RequestMethod.GET)简写<br>

3)方式三<br>
http://127.0.0.1:8888/route/v3<br>
http://127.0.0.1:8888/route/v4<br>
上面两个url公共路径route可以统一使用@RequestMapping("/route")简化<br>

```
@RestController
@RequestMapping("/route")
public class Controller3 {

    @GetMapping("/v3")
    String v3(){
        return "/route/v3";
    }

    @GetMapping("/v4")
    String index(){
        return "/route/v4";
    }

}
```


路由get post<br>
get请求@GetMapping("/route/get")<br>
post请求@PostMapping("route/post")<br>

获取request
```
@RestController
public class ControllerHeader {

    @Autowired
    HttpServletRequest httpServletRequest;

    @GetMapping("route/request")
    String getHeader(){
        return httpServletRequest.getHeader("Host");
    }
}
```

获取response
```
@RestController
public class ControllerResponse {
    @Autowired
    HttpServletResponse servletResponse;

    @GetMapping("/route/response")
    String response(){
        servletResponse.setStatus(403);
        return "response http code = 403";
    }
}
```

获取url中的参数@PathVariable<br>
http://127.0.0.1:8081/user/1
```
@RestController
@RequestMapping("/param")
public class ParamController1 {

    @GetMapping("/user/{userID}")
    Object getUser(@PathVariable("userID") int userID){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("errCode",0);
        result.put("msg","ok");
        result.put("data","user info... userID = "+userID);
        return result;
    }
}
```

获取请求参数<br>
http://127.0.0.1:8081/hello?name=yun 获取name的值
```
    @GetMapping("/hello")
    String sayHello(@PathParam("username") String username){
        return username + " hello";
    }

//    @RequestParam可以设置默认值
    @GetMapping("/hello2")
    String sayHelloDefault(@RequestParam(value = "username",required = false,defaultValue = "Li yun") String username){
        return username + " hello";
    }
```


@RequestBody接收Content-Type :application/json类型的数据

```
@PostMapping("/param/user/add")
ResultJson createUser(@RequestBody User user){

    return new ResultJson(0,"create ok",user);
}

```



配置文件属性
```
#端口
server.port = 8888
```



热加载<br>
在pom.xml添加以下依赖，然后在idea->preferences->Build,Excution,Deployment->Compiler->Build Project automatically 勾选
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

## 参数校验以及Json返回[spring-boot-validate](https://github.com/BestYun/spring-boot-demo/tree/master/spring-boot-validate)

参数验证
0)配置hibernate Validator为快速失败返回模式,只要有一个错误立即结束校验
```
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
//
@Configuration
public class ValidateConfig {
    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }
}
```




1）封装成类处理@Valid User user,BindingResult bindingResult
```
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class User {

    @NotBlank(message = "name不能为空")
    @Length(min = 6,message = "name长度不能小于6")
    private String name;

    @NotNull(message = "age不能为空")
    @Max(value = 200,message = "age不能大于200")
    @Min(value = 0,message = "age不能小于0")
    private Integer age;
    //get set ...
}

@PostMapping("/user2")
    JsonResult createUser2(
            @Valid User user,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){

            return JsonResult.fail(1,bindingResult.getFieldError().getDefaultMessage());
        }

        return JsonResult.success(0,user);
    }
```

2）直接在方法参数校验
```

import com.readchen.springbootvalidate.vo.JsonResult;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotBlank;


@RestController
//在请求方法中校验需要在类上加该注解
@Validated
public class UserController2 {


    @PostMapping("/user3")
//    只能通过全局异常捕获参数校验错误信息
    JsonResult createUser(
            @NotBlank(message = "用户名不能为空")
            @Length(min = 6,message = "用户名不能小于6")
                    String name){

        return JsonResult.success(0,name);
    }

}

```

3）统一处理错误异常
```
package com.readchen.springbootvalidate;

import com.readchen.springbootvalidate.vo.JsonResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 处理application/json这种请求头
     *
     * @PostMapping("/user4")
     *     JsonResult createUser(@RequestBody @Validated User user){
     *
     *         return JsonResult.success(0, user);
     *     }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        String msg = e.getBindingResult().getFieldError().getDefaultMessage();

        return JsonResult.fail(1, msg);
    }


    /**
     * 处理请求方法里面的参数校验异常
     *  @PostMapping("/user3")// 只能通过全局异常捕获参数校验错误信息
     *     JsonResult createUser(
     *             @NotBlank(message = "用户名不能为空")
     *             @Length(min = 6,message = "用户名不能小于6")
     *                     String name){
     *
     *         return JsonResult.success(0,name);
     *     }
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public JsonResult handleValidate(ConstraintViolationException e) {
        //获取异常中第一个错误信息
        String message = e.getConstraintViolations().iterator().next().getMessage();

        return JsonResult.fail(1000, message);
    }


    /**
     * 绑定类处理校验异常
     * 处理@Validated User user 参数校验抛出的异常
     *
     * @PostMapping("/user")//放在统一异常处理
     * JsonResult createUser(@Validated User user){
     * return JsonResult.success(0,user);
     * }
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public JsonResult bindExceptionHandler(BindException e) {
        //获取异常中随机一个异常信息
        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return JsonResult.fail(1, defaultMessage);
    }

    @ExceptionHandler(value = Exception.class)
//    其他异常
    public JsonResult handleException(Exception ex) {


        return JsonResult.fail(2, ex.toString());
    }


}

```
返回json格式,springboot默认使用jackjson
```
@RestController
public class ParamPostController {

    @PostMapping("param/user/{userID}")
    ResultJson getUser(@PathVariable("userID") int userID){

        return new ResultJson(0,"ok","post user info... userID = "+userID);
    }


}
```




## 数据库

1)mysql驱动,添加以下依赖
```
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

2)mysql配置
```

#mysql数据源url
spring.datasource.url=jdbc:mysql://localhost:3306/blog?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
#用户
spring.datasource.username=root
#密码
spring.datasource.password=root
#mysql驱动
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

```


Jpa<br>
添加依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

Jpa mysql 数据库配置
```

#mysql数据源url
spring.datasource.url=jdbc:mysql://localhost:3306/mp?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
#用户
spring.datasource.username=root
#密码
spring.datasource.password=root
#mysql驱动
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

mybatis

mybatis-plus
```
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.3.1</version>
</dependency>
```

mybatis-plus mysql 数据库配置
```
#mysql数据源url
spring.datasource.url=jdbc:mysql://localhost:3306/mp?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
#用户
spring.datasource.username=root
#密码
spring.datasource.password=root
#mysql驱动
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

1)创建mapper包
2)添加扫描@MapperScan("com.readchen.springbootmybatisplus.mapper")
```
@SpringBootApplication
@MapperScan("com.readchen.springbootmybatisplus.mapper")
public class SpringBootMybatisPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMybatisPlusApplication.class, args);
	}

}
```

3)创建User模型
```
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long managerId;
    private LocalDateTime createTime;
}
```

4)在mapper包创建User mapper
```
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.readchen.springbootmybatisplus.entity.User;

public interface UserMapper extends BaseMapper<User> {
}
```

mybatis-plus查询<br>
1.查询
```
@Autowired
private UserMapper userMapper;

List<User> users = userMapper.selectList(null);
User user = userMapper.selectById(userId);

//WHERE name = ? AND age = ?
Map<String,Object> cloums = new HashMap<>();
cloums.put("name","yun");
cloums.put("age",18);
List<User> users = userMapper.selectByMap(cloums);



//条件构造器  where age > 10
QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
queryWrapper.gt("age","10");
List<User> users = userMapper.selectList(queryWrapper);



//条件构造器 WHERE (age > ? AND name LIKE ? AND email IS NOT NULL)
QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
queryWrapper.gt("age","10").like("name","y").isNotNull("email");
List<User> users = userMapper.selectList(queryWrapper);


//条件构造器 WHERE (name LIKE ? OR name LIKE ?) ORDER BY age ASC
QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
queryWrapper.like("name","y").or().like("name","王").orderByAsc("age");
List<User> users = userMapper.selectList(queryWrapper);



//子查询 WHERE (age > ? AND manager_id IN (select manager_id from user where name like '%王' )) 
QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
queryWrapper.apply("age > {0}",10).
    inSql("manager_id","select manager_id from user where name like '%王%' ");

List<User> users = userMapper.selectList(queryWrapper);


//WHERE (name LIKE ? AND (age > ? OR email IS NOT NULL))
QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
queryWrapper.like("name","%王%").
        and(qw->qw.gt("age",10).or().isNotNull("email"));

List<User> users = userMapper.selectList(queryWrapper);


//WHERE (name LIKE ? OR (age > ? AND email IS NOT NULL))
QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
queryWrapper.like("name","%王%").
    or(qw->qw.gt("age",40).isNotNull("email"));

List<User> users = userMapper.selectList(queryWrapper);



//WHERE (age > ? AND email IS NOT NULL) AND name LIKE ? 
//nested 不是以and或者or开头作为条件
QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
queryWrapper.nested(qw->qw.gt("age",20).isNotNull("email")).
    like("name","%王%");


// WHERE name=? AND age=?
// 实体作为条件参数
User user = new User();
user.setName("yun");
user.setAge(18);

QueryWrapper<User> queryWrapper = new QueryWrapper<User>(user);
List<User> users = userMapper.selectList(queryWrapper);



//WHERE (name = ?)
//lambda表达式查询
LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
queryWrapper.eq(User::getName,"yun");

List<User> users = userMapper.selectList(queryWrapper);


通过注解自定义sql

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user ${ew.customSqlSegment}")
    List<User> getUserAll(@Param(Constants.WRAPPER) Wrapper wrapper);

    @Select("select * from user ")
    List<User> getUserAll2();


    @Select("select * from user where name = '${name}' ")
    List<User> getUserByName(String name);

}


@Select("select * from user where name = '${name}' ")
List<User> getUserByName(String name);

LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getName,"yun").or().eq(User::getAge,18);
List<User> users = userMapper.getUserAll(queryWrapper);

List<User> users = userMapper.getUserAll2();

List<User> users = userMapper.getUserByName("yun");

```

分页
```
配置分页插件
@Configuration
public class MyBatisPlusConfig {

    @Bean
    PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}


LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
lambdaQueryWrapper.gt(User::getAge,10);

Page<User> page = new Page<User>(1,5);
IPage<User> iPage = userMapper.selectPage(page,lambdaQueryWrapper);
Map<String,Object> map = new HashMap<String,Object>();
//        当前页
map.put("index",iPage.getCurrent());
//        总页数
map.put("pageSize",iPage.getPages());
//        记录
map.put("users",iPage.getRecords());
//        总记录数
map.put("total",iPage.getTotal());


```

更新
```
User user = new User();
user.setId(1228548876865130498L);
user.setName("yun update");
//通过id
int rows = userMapper.updateById(user);


UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<User>();
userUpdateWrapper.eq("age",18);
User user = new User();
user.setName("yun update222");
user.setEmail("yunupdate@163.com");
int rows = userMapper.update(user,userUpdateWrapper);


```



连接池

配置多数据源



## 缓存
redis

## 日志

## 消息队列

## 拦截器、过滤器、定时器

## 统一异常处理

## 文档


## 部署

