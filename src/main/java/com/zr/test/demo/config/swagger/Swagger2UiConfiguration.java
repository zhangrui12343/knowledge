package com.zr.test.demo.config.swagger;

import com.zr.test.demo.config.swagger.annotation.SwaggerApiGroup;
import com.zr.test.demo.util.ApplicationContextUtil;
import com.zr.test.demo.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * swagger2的配置
 * @author huang_kangjie
 * @date 2020/6/3 0003 19:29
 */
@Configuration
@EnableSwagger2
@Slf4j
public class Swagger2UiConfiguration extends WebMvcConfigurerAdapter {
    @Value("${swagger.show}")
    private boolean swaggerShow = false;

    /**
     * 提前为初始化Docket准备，因为Docket只有一个构造方法，而且构造方法的入参为DocumentationType
     * @return DocumentationType
     */
    @Bean
    public DocumentationType documentationType(){
        return DocumentationType.SWAGGER_2;
    }

    @Bean
    public Docket all() {
        //设置请求头
        List<Parameter> pars = this.getPars();
        if(!swaggerShow) {
            //返回一个不可扫描的包，便于快速启动
            return getDocket(pars, "test.controller");
        }
        //扫描自定义的分组注解
        this.scanPackage();
        return getDocket(pars, "com.zr.test.demo.controller");
    }

    private Docket getDocket(List<Parameter> pars, String packageName){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerShow)
                .groupName("ALL")
                .select()
                .apis(RequestHandlerSelectors.basePackage(packageName))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }



    /**
     * 自动的扫描api的分类注解，这样就不需要每次都来改当前配置类
     * 扫描所有的自定义分组注解
     *
     * 注入思想：
     *  1、首先往容器中注入Docket的bean
     *  2、在取出Bean，并设置bean
     */
    @SuppressWarnings("unchecked")
    public synchronized void scanPackage(){
        try {
            //加载com.streamax.base.config.swagger.annotation下的所有注解
            String packageName = "com.zr.test.demo.config.swagger.annotation";
            Set<Class<?>> classSet =  ClassUtil.getClassSet(packageName);
            log.debug("swagger loadclass size = {}", classSet.size());
            Iterator<Class<?>> it = classSet.iterator();
            //设置请求头
            List<Parameter> pars = this.getPars();
            List<Docket> list = new ArrayList<>();
            int i = 1;
            while (it.hasNext()) {
                Class<?> clazz = it.next();
                log.debug(" swagger loadclass clazz name = {}" , clazz.getName());
                if(!clazz.getName().startsWith(packageName)){
                    continue;
                }
                ApplicationContextUtil.registerBean("docket" + i, Docket.class.getName());
                Docket docket = ApplicationContextUtil.getBean("docket" + i, Docket.class);
                log.info(" swagger loadclass clazz = {}", clazz);
                SwaggerApiGroup swaggerApiGroup = clazz.getAnnotation(SwaggerApiGroup.class);
                if(swaggerApiGroup != null && docket != null) {
                    String groupName = swaggerApiGroup.value();
                    if(StringUtils.isEmpty(groupName)) {
                        groupName = clazz.getSimpleName();
                    }
                    docket.enable(swaggerShow)
                            .groupName(groupName)
                            .select()
                            .apis(RequestHandlerSelectors.withClassAnnotation((Class<? extends Annotation>) clazz))
                            .paths(PathSelectors.any())
                            .build()
                            .globalOperationParameters(pars)
                            .apiInfo(apiInfo());
                }
                i++;
            }
            log.info(" swagger api group sueccss!!!");
        } catch (Exception e) {
            log.error("swagger api group error, " + e.getMessage(), e);
        }
    }

    /**
     * 获取需要设置的参数和请求头
     * @return List<Parameter>
     */
    public List<Parameter> getPars(){
        //设置请求头
        List<Parameter> pars = new ArrayList<>();

        ParameterBuilder token = new ParameterBuilder();
        token.name("token").description("用户登陆得到的token，加密信息").modelRef(new ModelRef("string")).parameterType("header").required(false);
        pars.add(token.build());
        return pars;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("knowledge")
                .description("knowledge")
                .version("1.0.0")
                .build();
    }

}
