package com.pzg.code.login.mapper;

import com.pzg.code.commons.utils.MyMapper;
import com.pzg.code.login.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MyMapper<User> {
}