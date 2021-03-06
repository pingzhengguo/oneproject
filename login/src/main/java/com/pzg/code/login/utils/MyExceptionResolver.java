package com.pzg.code.login.utils;


import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 自定义异常解析
 * @version
 * @ModifiedBy:
 */
public class MyExceptionResolver implements HandlerExceptionResolver {

    /**
    *
    * @return
    * @throws
    */
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        // TODO Auto-generated method stub
        System.out.println("==============异常开始=============");
        //如果是shiro无权操作，因为shiro 在操作auno等一部分不进行转发至无权限url
        if (ex instanceof UnauthorizedException) {
            ModelAndView mv = new ModelAndView("redirect:/noPermissions");
            return mv;
        }
        ex.printStackTrace();
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
        return mv;
    }
}
