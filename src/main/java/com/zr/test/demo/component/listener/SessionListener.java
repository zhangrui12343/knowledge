package com.zr.test.demo.component.listener;

import com.zr.test.demo.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
@Slf4j
public class SessionListener implements HttpSessionListener {

        public void sessionCreated(HttpSessionEvent httpSessionEvent) {
            //设置session持续时间，单位为秒
            /*1.setMaxInactiveInterval设置的是当前会话的失效时间，不是整个web的时间，单位为以秒计算。
             * 如果设置的值为零或负数，则表示会话将永远不会超时。常用于设置当前会话时间。
             * 2.session-timeout元素（WEB.XML文件中的元素）用来指定默认的会话超时时间间隔，
             * 以分钟为单位。该元素值必须为整数。如果session-timeout元素的值为零或负数，
             * 则表示会话将永远不会超时
             */
            httpSessionEvent.getSession().setMaxInactiveInterval(30*60);
            log.info("{}-----------session监听器中Session已创建------------------",TimeUtil.getTime());
        }

        public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
            log.info("{}-----------session监听器中Session已销毁------------------",TimeUtil.getTime());
        }

}
