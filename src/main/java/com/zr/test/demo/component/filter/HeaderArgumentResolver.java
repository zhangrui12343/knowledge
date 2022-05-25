package com.zr.test.demo.component.filter;

import com.zr.test.demo.util.HeaderBuildUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 解决header的参数同一处理
 *
 * @author huang_kangjie
 * @date 2021/4/30 0030 11:34
 */
@Component
public class HeaderArgumentResolver implements HandlerMethodArgumentResolver {

     @Override
     public boolean supportsParameter(MethodParameter parameter) {
          return parameter.getParameter().getType().isAssignableFrom(HeaderBuildUtil.Header.class);
     }

     @Override
     public Object resolveArgument(MethodParameter parameter,
                                   ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest,
                                   WebDataBinderFactory binderFactory){
          return HeaderBuildUtil.builder(webRequest);
     }

}
