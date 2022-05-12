package com.zr.test.demo.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.util.Base64;

/**
 * des的解密工具
 *
 * 该工具完全适配于动态的iv和key
 *
 * 该工具不处理任何异常，所有的异常都需要调用者自己去处理
 *
 * @author huang_kangjie
 * @date 2020/3/24 0019 20:40
 */
@Slf4j
public class DESUtils {

     /**
      * 密钥算法
      */
     private static final String ALGORITHM = "DES";
     /**
      * 加密/解密算法-工作模式-填充模式
      */
     private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

     //偏移变量，固定占8位字节
     private static final String IV_DEFAULT = "rogernet";

     //密钥
     public static final String KEY_DEFALUT = "rogernet";

     //加密key的最小长度
     public static final int KEY_LENGTH = 8;

     /**
      * 默认编码
      */
     private static final String CHARSET = "ascii";
     //private static final String CHARSET = "utf-8";

     /**
      * 生成key
      *
      * @param password
      * @return
      * @throws Exception
      */
     private static Key generateKey(String password) throws Exception {
          DESKeySpec dks = new DESKeySpec(password.getBytes(CHARSET));
          SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
          return keyFactory.generateSecret(dks);
     }


     /**
      * DES加密字符串
      *
      * @param password 加密密码，长度不能够小于8位
      * @param data 待加密字符串
      * @return 加密后内容
      */
     public static String encrypt(String password, String data) throws Exception {
          if (password== null || password.length() < KEY_LENGTH) {
               throw new RuntimeException("加密失败，key不能小于8位");
          }
          if (data == null){
               return null;
          }
          Key secretKey = generateKey(password);
          Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
          IvParameterSpec iv = new IvParameterSpec(IV_DEFAULT.getBytes(CHARSET));
          cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
          return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(CHARSET)));
     }

     /**
      * DES解密字符串
      *
      * @param password 解密密码，长度不能够小于8位
      * @param data 待解密字符串
      * @return 解密后内容
      */
     public static String decrypt(String password, String data) throws Exception {
          if (password== null || password.length() < KEY_LENGTH) {
               throw new RuntimeException("解密失败，key不能小于8位");
          }
          if (data == null){
               return null;
          }
          Key secretKey = generateKey(password);
          Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
          IvParameterSpec iv = new IvParameterSpec(IV_DEFAULT.getBytes(CHARSET));
          cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
          return new String(cipher.doFinal(Base64.getDecoder().decode(data)), CHARSET);
     }

}

