package com.pzg.code.commons.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName :  BaseService
 * @Author : PZG
 * @Date : 2019-03-13   13:49
 * @Description :
 */
@Service
public interface BaseService<T> {

    List<T> selectAll();

    T selectOne(T entity);

    T selectByPrimaryKey(Object key);

    int insert(T entity);

    int deleteByPrimaryKey(Object key);

    int deleteByExample(List<String> list, String property, Class<T> clazz);

    int updateByPrimaryKey(T entity);

    int updateByPrimaryKeySelective(T entity);

    List<T> selectByExample(Object example);
}