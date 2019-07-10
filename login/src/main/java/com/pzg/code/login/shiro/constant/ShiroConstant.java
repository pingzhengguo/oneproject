package com.pzg.code.login.shiro.constant;

import com.pzg.code.commons.controller.DeptController;

/**
 * @Description: shiro相关常量配置
 * @ModifiedBy:
 */
public class ShiroConstant {


    private ShiroConstant() {
        throw new IllegalStateException("ShiroCasConstant class");
    }

    /**
     * 登录页面地址
     */
    public static String SWAGGER_URL = "/swagger-ui.html";
    public static String SWAGGER_JARS = "/webjars/**";
    public static String SWAGGER_RESOURCE = "/swagger-resources/**";
    public static String SWAGGER_V2 = "/v2/**";
    public static String UNAUTHORIZED_URL = "/403";
    public static String STATIC_URL = "/static/**";
    public static String IMAGES_URL = "/images/**";
    public static String JS_URL = "/js/**";
    public static String PLUGIN_URL = "/plugin/**";
    public static String STYLE_URL = "/style/**";
    public static String LOGIN_URL = "/systemManage/login/**";

    /**
     * 路由放行
     */
    public static String SUCCESS_URL = "/index";
    public static String HOME_URL = "/home";
    public static String LOGIN_HTML = "/login";
    public static String VIEW_URL = "/view/**";
    public static String INDEX_URL = "/";
    public static String LOGOUT_URL = "/logout";
    public static String REDIRECT_LOGIN_HTML = "/noAuthc";
    /**
     * 游客放行地址
     */
    //系统信息查询
    public static String SYS_MANGER_ANON = "/systemManage/anon/**";
    public static String THIRD_ANON = "/thirdParty/**";
    //门户首页信息
    public static String HOME_ANON = "/home/**";
    //新闻公告信息
    public static String FILE_ANON = "/fileInfo/anon/**";
    //资源目录的目录信息
    public static String DIRECTORY_ANON = "/serviceDirectory/anon/**";
    //资源目录的资源信息
    public static String RESOURCE_ANON = "/resourceDirectory/resource/anon/**";
    public static String MENU_ANON = "/resourceDirectory/directory/anon/**";
    public static String INTERACTION_ANON = "/interactProblem/interaction/**";
    public static String INTERACTION_PROBLEM_ANON = "/interactProblemType/interaction/**";
    public static String PROCESS_USER_APPLY = "/process/userApply/anon/**";
    public static String OG_SEARCH = "/sysuser/**";
    //文件操作
    public static String WORK_FLOW_FILE_UPLOAD = "/workFlow/fileUpload/**";
    public static String FILE_UPLOAD = "/fileUpload/**";
    public static String FILE_INFO = "/components/anon/**";
    //用户注册是否开放
    public static String USER_REGISTRATION = "/systemManage/userManager/auth/**";

    public static String GUIDE_FOR_USE = "/systemManage/anon/selectUseGuideFiles1";

    public static String ANON = "/**/anon/**";

}
