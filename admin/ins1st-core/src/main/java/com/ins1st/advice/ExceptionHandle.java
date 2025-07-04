package com.ins1st.advice;

import com.alibaba.fastjson.JSON;
import com.ins1st.contants.ExceptionEnum;
import com.ins1st.core.R;
import com.ins1st.exception.BizException;
import com.ins1st.exception.GenException;
import com.ins1st.exception.NoAuthException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一异常处理
 *
 * @author sun
 */
@ControllerAdvice
public class ExceptionHandle {

    /**
     * 处理异常拦截
     *
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Object handle4Exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        return handle(request, response, ExceptionEnum.SYSTEM_ERRO.getMessage());
    }


    /**
     * 权限注解异常
     *
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(value = NoAuthException.class)
    public Object handle4NoAuthException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        return handle(request, response, ExceptionEnum.NO_AUTH.getMessage());
    }

    /**
     * 业务异常
     *
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    public Object handle4BizException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        return handle(request, response, StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : ExceptionEnum.SYSTEM_ERRO.getMessage());
    }

    /**
     * 代码生成器异常
     *
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(value = GenException.class)
    public Object handle4GenException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        return handle(request, response, StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : ExceptionEnum.GEN_ERROR.getMessage());
    }

    /**
     * 处理返回值
     *
     * @param request
     * @param response
     * @param mesaage
     * @return
     */
    private Object handle(HttpServletRequest request, HttpServletResponse response, String mesaage) {
        if (isAjax(request)) {
            writeJson(response, R.error(mesaage));
            return null;
        } else {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/500.html");
            return mav;
        }
    }


    /**
     * 判断属于什么请求
     *
     * @param request
     * @return
     */
    private boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }

    /**
     * 向response写数据
     *
     * @param response
     * @param r
     */
    protected void writeJson(HttpServletResponse response, R r) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(r));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
