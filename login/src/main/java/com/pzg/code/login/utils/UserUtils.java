package com.pzg.code.login.utils;


import com.pzg.code.login.vo.UserVo;

import javax.servlet.http.HttpSession;


public class UserUtils {

    public static UserVo getLoginUser(HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            return null;
        } else {
            return (UserVo) loginUser;
        }
    }


}
