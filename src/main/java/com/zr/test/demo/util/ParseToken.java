package com.zr.test.demo.util;

import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.pojo.AuthKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;

@Slf4j
public class ParseToken {
    public static AuthKey checkAuth(String key) {
        AuthKey rskey = new AuthKey();
        try {
            log.info("检查权限，解密前 key = {}", key);
            //key = DESUtils.decrypt(DESUtils.KEY_DEFALUT, URLDecoder.decode(key, "utf-8"));
            //判断是否需要decode
            if (key.contains("+") || key.contains("=") || key.contains("/") || key.contains(" ")
                    || key.contains("?") || key.contains("#") || key.contains("&")
            ) {
                key = DESUtils.decrypt(DESUtils.KEY_DEFALUT, key);
            } else {
                key = DESUtils.decrypt(DESUtils.KEY_DEFALUT, URLDecoder.decode(key, "utf-8"));
            }

        } catch (Exception e) {
            log.error("解密出错： " + e.getMessage(), e);
            throw new CustomException(ErrorCode.SYS_DECRIPT_ERR);
        }
        if (StringUtils.isEmpty(key)) {
            log.error("解密后密文为空！");
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        log.info("检查权限，解密后 key = {}", key);
        String[] keys = key.split("\\|");
        //检验key的规范性目前主要是校验userId 和 roleId
        try {
            rskey.setRoleId(Integer.valueOf(keys[0]));
        } catch (Exception e) {
            log.error("解密roleId出错：" + e.getMessage() + " roleId = " + keys[1]);
            throw new CustomException(ErrorCode.SYS_KEY_PARAM_ERR);
        }

        try {
            rskey.setUserId(Integer.valueOf(keys[1]));
        } catch (Exception e) {
            log.error("解密userId出错：" + e.getMessage() + " userId = " + keys[0]);
            throw new CustomException(ErrorCode.SYS_KEY_PARAM_ERR);
        }

        rskey.setTime(keys[2]);

        log.info("解密key后的信息 authKey = {}", rskey);

        return rskey;
    }
}
