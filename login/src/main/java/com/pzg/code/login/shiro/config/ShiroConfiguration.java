package com.pzg.code.login.shiro.config;

import com.pzg.code.login.shiro.constant.ShiroConstant;
import com.pzg.code.login.shiro.filter.CookieLoginFilter;
import com.pzg.code.login.shiro.realm.MyShiroRealm;
import com.pzg.code.login.utils.MyExceptionResolver;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Description: shiro 主配置相关 (暂时关闭--若开启 则必须登录成功 才可以进行接口调试，不然都会被拦截到登录页)
 * @ModifiedBy:
 */
@Configuration
public class ShiroConfiguration {
    @Autowired
    private MyShiroRealm myShiroRealm;

    @Autowired
    private CookieLoginFilter cookieLoginFilter;

    @Bean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions)
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public HandlerExceptionResolver solver() {
        HandlerExceptionResolver handlerExceptionResolver = new MyExceptionResolver();
        return handlerExceptionResolver;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl(ShiroConstant.REDIRECT_LOGIN_HTML);
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl(ShiroConstant.SUCCESS_URL);
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl(ShiroConstant.UNAUTHORIZED_URL);

        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        //Cookie自动登录
        filtersMap.put("cookieLogin", cookieLoginFilter);
        shiroFilterFactoryBean.setFilters(filtersMap);
        // 过滤器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被过滤的链接 顺序判断
        // 过虑器链定义，从上向下顺序执行，一般将/**放在最下边
        // 对静态资源设置匿名访问
        // anon:所有url都都可以匿名访问
        //静态资源方向
        filterChainDefinitionMap.put(ShiroConstant.STATIC_URL, "anon");
        filterChainDefinitionMap.put(ShiroConstant.SUCCESS_URL, "anon");
        filterChainDefinitionMap.put(ShiroConstant.HOME_URL, "anon");
        filterChainDefinitionMap.put(ShiroConstant.LOGIN_HTML, "anon");
        filterChainDefinitionMap.put(ShiroConstant.VIEW_URL, "anon");
        filterChainDefinitionMap.put(ShiroConstant.IMAGES_URL, "anon");
        filterChainDefinitionMap.put(ShiroConstant.JS_URL, "anon");
        filterChainDefinitionMap.put(ShiroConstant.PLUGIN_URL, "anon");
        filterChainDefinitionMap.put(ShiroConstant.STYLE_URL, "anon");
        //登录页面、提交接口放行
        filterChainDefinitionMap.put(ShiroConstant.LOGIN_URL, "anon");
        filterChainDefinitionMap.put(ShiroConstant.REDIRECT_LOGIN_HTML, "anon");
        //首页放行
        filterChainDefinitionMap.put(ShiroConstant.INDEX_URL, "anon");
        //swagger放行
        filterChainDefinitionMap.put(ShiroConstant.SWAGGER_URL, "anon");
        filterChainDefinitionMap.put(ShiroConstant.SWAGGER_JARS, "anon");
        filterChainDefinitionMap.put(ShiroConstant.SWAGGER_V2, "anon");
        filterChainDefinitionMap.put(ShiroConstant.SWAGGER_RESOURCE, "anon");

        //游客访问路由
        //游客访问接口
        filterChainDefinitionMap.put(ShiroConstant.SYS_MANGER_ANON, "anon");
        filterChainDefinitionMap.put(ShiroConstant.THIRD_ANON, "anon");
        filterChainDefinitionMap.put(ShiroConstant.HOME_ANON, "anon");
        filterChainDefinitionMap.put(ShiroConstant.FILE_ANON, "anon");
        filterChainDefinitionMap.put(ShiroConstant.DIRECTORY_ANON, "anon");
        filterChainDefinitionMap.put(ShiroConstant.INTERACTION_ANON, "anon");
        filterChainDefinitionMap.put(ShiroConstant.INTERACTION_PROBLEM_ANON, "anon");
        filterChainDefinitionMap.put(ShiroConstant.RESOURCE_ANON, "anon");
        filterChainDefinitionMap.put(ShiroConstant.MENU_ANON, "anon");
        filterChainDefinitionMap.put(ShiroConstant.PROCESS_USER_APPLY, "anon");
        filterChainDefinitionMap.put(ShiroConstant.OG_SEARCH, "anon");
        filterChainDefinitionMap.put(ShiroConstant.WORK_FLOW_FILE_UPLOAD, "anon");
        filterChainDefinitionMap.put(ShiroConstant.FILE_UPLOAD, "anon");
        filterChainDefinitionMap.put(ShiroConstant.FILE_INFO, "anon");
        filterChainDefinitionMap.put(ShiroConstant.USER_REGISTRATION, "anon");

        filterChainDefinitionMap.put(ShiroConstant.GUIDE_FOR_USE, "anon");
        filterChainDefinitionMap.put(ShiroConstant.ANON, "anon");

        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put(ShiroConstant.LOGOUT_URL, "logout");
        // authc:所有url都必须认证通过才可以访问
        filterChainDefinitionMap.put("/**", "authc");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/noPermissions");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    public MyShiroRealm myShiroRealm() {
        return myShiroRealm;
    }

    /**
     * cookie对象;
     *
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能
     *
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

}