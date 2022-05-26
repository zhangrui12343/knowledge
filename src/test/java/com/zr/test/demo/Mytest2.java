package com.zr.test.demo;

import com.zr.test.demo.util.Md5Util;
import org.junit.Test;

public class Mytest2 {
    @Test
    public void test() throws Exception {
        System.out.println(Md5Util.getMD5("Aa111111"));
    }
    /**
     * 可逆的的加密解密方法；两次是解密，一次是加密
     * @param inStr
     * @return
     */
    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }


}
