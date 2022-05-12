package com.zr.test.demo;
import com.zr.test.demo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 临时测试
 * @author zr
 * @date 2022/2/8 0031 17:11
 */
@Slf4j
public class MyTest extends DemoApplicationTests {
     @Autowired
     IUserService iUserService;
     @Test
     public void test() throws Exception {
          iUserService.insert();
     }

}

