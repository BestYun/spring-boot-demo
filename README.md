# spring-boot-demo

## spring-boot-web
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

## 参数校验以及Json返回

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
mysql驱动

jpa

mybatis

mybatis-plus

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

