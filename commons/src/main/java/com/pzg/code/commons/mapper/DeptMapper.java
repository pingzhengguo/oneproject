package com.pzg.code.commons.mapper;

import com.pzg.code.commons.excel.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName :  DeptMapper
 * @Author : PZG
 * @Date : 2019-06-20   11:55
 * @Description :
 */
@Mapper
public interface DeptMapper {

    List<Dept> getAll();
}
