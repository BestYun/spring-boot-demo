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

mybatis-plugins

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

