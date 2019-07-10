package com.pzg.code.login.service;

import com.pzg.code.commons.utils.CustomException;
import com.pzg.code.login.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName :  UserService
 * @Author : PZG
 * @Date : 2019-07-10   12:07
 * @Description :
 */
public interface UserService {

    UserVo getLoginUserVo(String userName);

    void outLogin(HttpServletRequest request, HttpServletResponse response) throws CustomException;

}
