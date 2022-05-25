package com.zr.test.demo.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 鉴权的配置文件
 *
 * @author huang_kangjie
 * @date 2020/6/19 0019 14:12
 */

@Component
@ConfigurationProperties(prefix = AuthConfig.PREFIX)
@Data
public class AuthConfig {

     public static final String PREFIX = "auth";

     private List<Url> includes;

     @Data
     public static class Url {

          @JsonProperty(value = "url")
          private String url;

          @JsonProperty(value = "code")
          private Integer code;
     }

}

