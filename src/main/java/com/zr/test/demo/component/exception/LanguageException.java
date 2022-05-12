package com.zr.test.demo.component.exception;

import lombok.Data;

/**
 * 语言翻译异常
 * @author huang_kangjie
 * @date 2020/4/26 0026 11:28
 */
@Data
public class LanguageException extends Exception {

     private String message;

     public LanguageException(){
          super();
     }

     public LanguageException(String message){
          super(message);
          this.message = message;
     }

}

