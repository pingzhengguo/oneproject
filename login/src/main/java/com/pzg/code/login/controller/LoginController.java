package com.pzg.code.login.controller;

import com.pzg.code.commons.conf.TestConfig;
import com.pzg.code.commons.utils.ResultInfo;
import com.pzg.code.login.service.UserService;
import com.pzg.code.login.utils.MD5SignUtil;
import com.pzg.code.login.utils.UserUtils;
import com.pzg.code.login.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;


/**
 * 用户登录
 */
@Api(tags = {"LoginController"})
@RestController
@RequestMapping("/home")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private TestConfig testConfig;

    /**
     * @Description: 登录入口
     * @params:
     * @throws:
     */
    @ApiOperation(value = "登录入口", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo login(String username, String password, HttpServletRequest request, HttpSession session) {
        if (username == null) {
            return ResultInfo.failure("用户名不能为空");
        }
        if (password == null) {
            return ResultInfo.failure("密码不能为空");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5SignUtil.md5(password), null);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResultInfo.failure(e.getMessage());
        }
        UserVo loginUser = UserUtils.getLoginUser(session);
        return ResultInfo.success().build(loginUser);
    }


    /**
     * @Description: 登出地址
     * @params:
     * @throws:
     */
    @ApiOperation(value = "登出入口", httpMethod = "GET"/*, produces = MediaType.APPLICATION_JSON_VALUE*/)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String loginOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        UserVo loginUser = UserUtils.getLoginUser(session);
        if (loginUser == null) {
            return "redirect:/home";
        }
        //登出时，清空session信息
        Enumeration<String> enumeration = session.getAttributeNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            session.removeAttribute(key);
        }
//        if (!testConfig.getTestLogin().equals(2)) {
//            return "redirect:/home";
//        }
        try {
            userService.outLogin(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //退出登录
//        return "redirect:http://59.215.191.17:8081/cas/logout?service=http://localhost:8080";
        return "退出成功！";
    }
}
