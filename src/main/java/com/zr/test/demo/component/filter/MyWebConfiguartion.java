package com.zr.test.demo.component.filter;

import com.zr.test.demo.config.AuthConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器注册
 *
 * @author huang_kangjie
 * @date 2020/6/19 0019 13:39
 */
@Configuration
@Slf4j
public class MyWebConfiguartion implements WebMvcConfigurer {

     private final AuthConfig authConfig;
     private final HeaderArgumentResolver headerArgumentResolver;

     @Autowired
     public MyWebConfiguartion(AuthConfig authConfig, HeaderArgumentResolver headerArgumentResolver) {
          this.authConfig = authConfig;
          this.headerArgumentResolver = headerArgumentResolver;
     }

     /**
      * 处理请求头的拦截修改
      * @param argumentResolvers 参数处理器
      */
     @Override
     public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
          argumentResolvers.add(headerArgumentResolver);
     }

     @Override
     public void addInterceptors(InterceptorRegistry registry) {
          // 多个拦截器组成一个拦截器链
          // addPathPatterns 用于添加拦截规则
          // excludePathPatterns 用户排除拦截
          //添加权限验证拦截器

          log.info("authConfig = {}", authConfig);

       //   registry.addInterceptor(new AuthInterceptor());
          log.debug("======== Interceptor Registry Complete ! ========");
     }

     /**
      * 处理权限的url
      * @return List<String>
      */
     private List<String> authList(){
          List<String> rsList = new ArrayList<>();
          this.authConfig.getIncludes().forEach( url -> rsList.add(url.getUrl()));
          return rsList;
     }
}

