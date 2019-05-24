package com.pzg.code.aa.service;

import com.pzg.code.aa.entity.NameTable;

import java.util.List;

/**
 * @ClassName :  NameTableService
 * @Author : PZG
 * @Date : 2019-05-07   10:10
 * @Description :
 */
public interface NameTableService {

    Integer save(NameTable nameTable);

    NameTable getOne(Integer id);


    List<NameTable> getAll();


}
