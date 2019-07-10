package com.pzg.code.login.shiro.realm;

import com.pzg.code.commons.conf.TestConfig;
import com.pzg.code.login.entity.Authority;
import com.pzg.code.login.entity.Role;
import com.pzg.code.login.entity.User;
import com.pzg.code.login.mapper.AuthorityMapper;
import com.pzg.code.login.mapper.RoleMapper;
import com.pzg.code.login.mapper.UserMapper;
import com.pzg.code.login.service.UserService;
import com.pzg.code.login.utils.MD5SignUtil;
import com.pzg.code.login.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: 自定义 shiro 验证realm
 * @ModifiedBy:
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private TestConfig testConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthorityMapper authorityMapper;

    /**
     * @Description: 1、CAS单点登录认证 ,验证用户身份（默认是自动认证的 这里手动实现一下） 2、将用户的相关信息放置到session中,方便获取
     * @params:
     * @return:
     * @ModifiedBy:
     * @throws:
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        UserVo loginUserVo = userService.getLoginUserVo(usernamePasswordToken.getUsername());
        loginUserVo.setTooken(usernamePasswordToken.getHost());
        User user = loginUserVo.getUser();
        if (!testConfig.getTestLogin().equals("3")) {
            // 从数据库获取对应用户名密码的用户
            if (user == null) {
                throw new AccountException("您输入的用户不存在");
            } else if (!MD5SignUtil.md5(user.getPassword()).equals(new String(usernamePasswordToken.getPassword()))) {
                logger.info("用户：" + user.getUserName() + " 密码输入错误");
                throw new AccountException("密码有误");
            }
        }
        // 将用户信息存入session中,方便程序获取,此处可以将根据登录账号查询出的用户信息放到session中
        logger.info("用户：" + loginUserVo.getUser().getUserName() + " 登录系统");
        SecurityUtils.getSubject().getSession().setAttribute("loginUser", loginUserVo);
        if (usernamePasswordToken.getHost() == null) {
            SecurityUtils.getSubject().getSession().setTimeout(1800000L);
        } else if (usernamePasswordToken.getHost().equals("a")) {
            SecurityUtils.getSubject().getSession().setTimeout(-1000L);
        }
        return new SimpleAuthenticationInfo(loginUserVo, usernamePasswordToken.getPassword(), usernamePasswordToken.getUsername());
    }

    /**
     * @Description: 自定义 权限控制方法：主要作用为当前登录用户授予角色和权限 与实际业务挂钩
     * @see: 本例中该方法的调用时机为需授权的资源被访问时
     * @see: 并且每次访问需授权的资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * @see: 如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     * @params:
     * @return:
     * @ModifiedBy:
     * @throws:
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限授权##################");
        //获取当前登录输入的账号
        UserVo loginUserVo = (UserVo) super.getAvailablePrincipal(principalCollection);
        //根据账号，查询出用户的相关信息
        if (loginUserVo != null) {
            //权限信息对象info,用来存放当前的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //##################################方式一###########################################
            //给用户赋予角色（让shiro自己去验证 shiro配置中）（set集合会去重）
            Set<String> roleName = new HashSet<>();
            String s = loginUserVo.getRole().getRoleName();
            roleName.add(s);
            //给用户 赋予角色
            info.addRoles(roleName);
            //给用户赋予权限 （让shiro自己去验证 shiro配置中）（set集合会去重）暂时设定为授予功能权限
            Set<String> permission = new HashSet<>();
            List<Authority> sysAuthorities = loginUserVo.getAuthorityList();
            if (null != sysAuthorities && sysAuthorities.size() > 0) {
                for (Authority authority : sysAuthorities) {
                    //若果权限表达式不为空 添加此权限
                    if (!StringUtils.isBlank(authority.getPath())) {
                        permission.add(authority.getPath());
                    }
                }
            }
            //给用户赋予权限（让shiro去验证 shiro配置中/注解控制）
            info.addStringPermissions(permission);
            logger.info("已为用户赋予了角色和权限");
            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof UsernamePasswordToken;
    }

}
