package com.readchen.springbootHikariCP;

import com.readchen.springbootHikariCP.vo.JsonResult;
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
