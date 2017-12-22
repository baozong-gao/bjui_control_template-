package com.shenxin.core.control.execption;

import com.shenxin.core.control.sysexception.PersistentException;
import com.shenxin.core.control.sysexception.ValidatroException;
import com.shenxin.core.control.util.BasisException;
import com.shenxin.core.control.util.ReturnUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 总异常处理类 处理所有spring管理的运行时异常
 * @author Administrator
 */
@Slf4j
@Component
public class ExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        log.error("出错了", ex);
        String error = "";
        if (ex instanceof ArithmeticException) {
            request.setAttribute("exStackTrace", getExceptionStackTraceMessage(ex));
            return new ModelAndView("error");
        }else if(ex instanceof ValidatroException || ex instanceof PersistentException){
            error = ReturnUtil.error(ex.getMessage());
        } else if (ex instanceof UnauthenticatedException || ex instanceof UnauthorizedException) {
            return null;
        } else if (ex instanceof DuplicateKeyException) {
            error = ReturnUtil.error("数据已存在");
        } else if (ex instanceof BasisException) {
            error = ReturnUtil.error(ex.getMessage());
        } else {
            error = ReturnUtil.error("未知异常");
        }
        ReturnUtil.retJson(response, error);
        return null;
    }

    private String getExceptionStackTraceMessage(Exception ex) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        ex.printStackTrace(printWriter);
        return result.toString();
    }
}