package com.zr.test.demo.aspects;


import com.zr.test.demo.common.Constant;
import com.zr.test.demo.common.Request;
import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.component.log.LogPrint;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.pojo.AuthKey;
import com.zr.test.demo.util.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * 切面处理（用于日志记录，参数校验，多语言支持，时间统计）
 *
 * @author yangliangliang
 * @date 2018/11/1
 */
@Aspect
@Component
@Order(2)
public class BaseAspect {
    private static Logger logger = LoggerFactory.getLogger(BaseAspect.class);

    /**
     * 切点定义
     */
    @Pointcut("execution(* com.zr.test.demo.service.*.*.*(..))")
    public void service() {
    }

    /**
     * 切面逻辑定义
     *
     * @param point 切面参数
     * @return 返回结果
     * @throws Throwable 抛出异常
     */
    @Around("service()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Object object = point.getTarget();
        Method method = object.getClass().getDeclaredMethod(signature.getName(), signature.getMethod().getParameterTypes());
        String simpleName = object.getClass().getSimpleName();
        String methodName = method.getName();

        //判定是否打印参数
        LogPrint logPrint = method.getAnnotation(LogPrint.class);
        boolean print = logPrint == null || logPrint.print();
        Object[] params = point.getArgs();
        Object o = null;
        HttpServletRequest request= ApplicationContextUtil.getHttpServletRequest();
        if(!request.getRequestURI().equals("/user/login")&&!request.getRequestURI().equals("/user/register")){
            String token=request.getHeader(Constant.TOKEN);
            if(StringUtil.isEmpty(token)) {
                logger.error("请求头为空！ key = {}", token);
                throw new CustomException(ErrorCode.SYS_REQUEST_HEADER_ERR, "请求头 key = token 为空");
            }
            HttpSession session=request.getSession(false);
            if(session==null){
                throw new CustomException(ErrorCode.SYS_NO_AUTHORITY, "请登录！");
            }
            if(session.getAttribute(token)==null){
                logger.error("无效token！");
                throw new CustomException(ErrorCode.SYS_NO_AUTHORITY, "鉴权失败！");
            }
            //查询该用户是否存在，或者是否被禁用

            //延长sesion时间
            session.setMaxInactiveInterval(30*60);
        }
        try {
           o = request.getAttribute("_sid");
        } catch (Exception ignore){

        }
        String sid;
        if(o != null) {
            sid = o.toString();
        } else {
           sid = UUID.randomUUID().toString();
        }

        //校验参数的合法性
        if (params != null && params.length > 0 && params[0] != null) {
            //Object param = params[0];
            for(Object param : params) {
                if (param instanceof Request) {
                    ValidatorUtil.check(((Request) param).getData());
                    String requestSid = ((Request) param).getSid();
                    sid = StringUtils.isEmpty(requestSid) ?  sid : requestSid;
                }else {
                    ValidatorUtil.check(param);
                }
            }
        }

        Object response = point.proceed();
        Result result = (Result) response;
        //国际化语言处理
        String msg = result.getMessage();
        if (StringUtil.isEmpty(msg)) {
            msg = ErrorCode.getMsgByCode(result.getCode());
        }
        result.setMessage(msg);
        //打印结果,消耗时间，返回数据
        result.setSid(sid);
        long endTime = System.currentTimeMillis();
        result.setTime(endTime - startTime);
        if(print) {
            logger.info(simpleName + "." + methodName + "-" + "resultJson==>sid[" + sid + "];time[" + (endTime - startTime) + "] => 接口返回：{}", JsonUtils.toJson(result));
        }else {
            if(logger.isDebugEnabled()){
                logger.debug(simpleName + "." + methodName + "-" + "resultJson==>sid[" + sid + "];time[" + (endTime - startTime) + "] => 接口返回：{}", JsonUtils.toJson(result));
            }
        }

        return result;
    }
}


