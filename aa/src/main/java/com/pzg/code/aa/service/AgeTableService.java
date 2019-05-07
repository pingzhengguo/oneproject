package com.pzg.code.aa.service;

import com.pzg.code.aa.entity.AgeTable;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName :  AgeTableService
 * @Author : PZG
 * @Date : 2019-05-07   13:55
 * @Description :
 */
public interface AgeTableService {

    List<AgeTable> selectByExample(String property, List list);

    List<AgeTable> selectAll();

    AgeTable selectByPrimaryKey(Object key);

    int insert(AgeTable entity);

    int deleteByExample(List<String> list, String property, Class<AgeTable> clazz);

}
