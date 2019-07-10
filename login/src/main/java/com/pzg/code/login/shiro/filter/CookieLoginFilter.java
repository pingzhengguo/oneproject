package com.pzg.code.login.shiro.filter;


import com.alibaba.fastjson.JSON;
import com.pzg.code.commons.conf.TestConfig;
import com.pzg.code.commons.utils.CustomException;
import com.pzg.code.login.http.HttpCoon;
import com.pzg.code.login.service.UserService;
import com.pzg.code.login.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 作者 z77z
 * @date 创建时间：2017年3月5日 下午1:16:38
 * 思路：
 * 1.读取当前登录用户名，获取在缓存中的sessionId队列
 * 2.判断队列的长度，大于最大登录限制的时候，按踢出规则
 * 将之前的sessionId中的session域中存入kickout：true，并更新队列缓存
 * 3.判断当前登录的session域中的kickout如果为true，
 * 想将其做退出登录处理，然后再重定向到踢出登录提示页面
 */
@Configuration
public class CookieLoginFilter extends OncePerRequestFilter {
    @Autowired
    private TestConfig testConfig;
    @Autowired
    private UserService userService;

    /**
     * 将中软云上的用户登录信息存储到本系统中
     *
     * @param userInfo
     * @param session
     */
    public void saveLoginInfo(String userInfo, HttpSession session) {
        //根据用户信息中的account获取自己系统中的用户信息
        Map mapUserInfo = JSON.parseObject(userInfo);
        String UserInfo = (String) mapUserInfo.get("account");
        UserVo loginUserVo = userService.getLoginUserVo(UserInfo);
        //用户信息存到session中
        //用户信息存到shiro中，跳转到shiro验证
        String userName = loginUserVo.getUser().getUserName();
        String password = loginUserVo.getUser().getPassword();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        subject.login(token);
    }

    @Override
    protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        if (!testConfig.getTestLogin().equals(2)) {
            //未开启单点登录功能
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        }
        //单点登录校验cookie
        HttpSession session = httpRequest.getSession();
        if (session.getAttribute("loginUser") != null) {
            //已经登录
            filterChain.doFilter(httpRequest, httpResponse);
            return;
        }
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies != null && cookies.length > 0) {
            //遍历所有Cookie
            for (Cookie cookie : cookies) {
                //找到access_token的Cookie
                if (("access_token").equals(cookie.getName())) {
                    // cookie存在
                    String access_token = URLDecoder.decode(cookie.getValue(), "utf-8");
                    //根据token获取用户信息
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("access_token", access_token);
                    try {
                        String thirdUrl = "http://59.215.191.17:8080/oauth2-server/v1/openapi/userInfo";
                        //自动登录
                        String userInfo = HttpCoon.httpURLConnectionGET(thirdUrl, map1);
                        saveLoginInfo(userInfo, session);
                        filterChain.doFilter(httpRequest, httpResponse);
                        return;
                    } catch (CustomException e) {
                        e.printStackTrace();
                        filterChain.doFilter(httpRequest, httpResponse);
                        //跳转到系统异常地址
                        // TODO: 2018/10/10
                    }
                }
            }
        }
        filterChain.doFilter(httpRequest, httpResponse);
        return;
        //重定向
        //WebUtils.issueRedirect(request, response, kickoutUrl);
    }
}
