package com.zr.test.demo.config;

import com.github.pagehelper.PageHelper;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * mysql配置
 * @author yangliangliang
 * @date 2018/11/3
 */
@Configuration
@ConfigurationProperties("pagehelper")
@Data
public class MysqlConfig {

    private String helperDialect;
    private String reasonable;
    private String supportMethodsArguments;
    private String params;


    /**
     * 分页插件设置
     * @return 分页插件对象
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        //添加配置，也可以指定文件路径
        Properties p = new Properties();
        //p.setProperty("offsetAsPageNum", "true");
        //p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("helperDialect", helperDialect);
        p.setProperty("reasonable", reasonable);
        p.setProperty("supportMethodsArguments", supportMethodsArguments);
        p.setProperty("params", params);
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
