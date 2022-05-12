package com.zr.test.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Slf4j
public class RandomUtil {
    private static SecureRandom random;

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            log.error("SecureRandom getInstanceStrong has exception:{}", e.getMessage());
        }
    }

    public static int getRandomInt(int range) {
        return random.nextInt(range);
    }
}
