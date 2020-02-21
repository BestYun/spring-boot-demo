package com.readchen.springbootfilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class SpringBootFilterApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFilterApplication.class, args);
	}



	@Autowired
	MyInterceptor myInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//	注册拦截器
		InterceptorRegistration registration = registry.addInterceptor(myInterceptor);
		//所有路径都被拦截
		registration.addPathPatterns("/**");
		//添加不拦截路径
		registration.excludePathPatterns("/","/login","/error","/static/**","/logout");

	}
}
