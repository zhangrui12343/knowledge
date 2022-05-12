package com.zr.test.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author llyang
 * @date 2019/12/10 00:28:56
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextUtil.class);
    private static ApplicationContext context;
    private static ConfigurableApplicationContext configurableContext = null;
    private static BeanDefinitionRegistry beanDefinitionRegistry = null;

    /**
     * 得到Http请求
     * @return 返回HTTP请求对象
     */
    public static HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    /**
     * 得到Http响应
     * @return 返回HTTP响应对象
     */
    public static HttpServletResponse getHttpServletResponse() {
        return (HttpServletResponse) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
    /**
     * 注册bean
     * @param beanId 所注册bean的id
     * @param className bean的className，
     *                     三种获取方式：1、直接书写，如：com.mvc.entity.User
     *                                   2、User.class.getName
     *                                   3.user.getClass().getName()
     */
    public static void registerBean(String beanId,String className) {
        // get the BeanDefinitionBuilder
        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(className);
        // get the BeanDefinition
        BeanDefinition beanDefinition=beanDefinitionBuilder.getBeanDefinition();
        // register the bean
        beanDefinitionRegistry.registerBeanDefinition(beanId,beanDefinition);
    }

    /**
     * 重载方法
     * @param applicationContext 应用环境上下班
     * @throws BeansException 抛出异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.context = applicationContext;
        configurableContext = (ConfigurableApplicationContext) context;
        beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();
    }

    /**
     * 获得已经初始化注入的Bean
     * @param clazz 类类型
     * @param <T> 泛型
     * @return 返回spring boot 初始化后的Bean
     */
    public static <T> T  getBean(Class<T> clazz) {
        return context != null ? context.getBean(clazz) : null;
    }

    /**
     * 获得已经初始化注入的Bean
     * @param beanName bean的名称
     * @param <T> 泛型
     * @return 返回spring boot 初始化后的Bean
     */
    public static <T> T  getBean(String beanName, Class<T> clazz) {
        return context != null ? context.getBean(beanName, clazz) : null;
    }

    /**
     * 获得已经初始化注入的Bean
     * @param beanName bean的名称
     * @return 返回spring boot 初始化后的Bean
     */
    public Object getBean(String beanName) {
        return context != null ? context.getBean(beanName) : null;
    }


}
