package com.pzg.code.bb.service;

import com.pzg.code.bb.entity.NameTable2;

import java.util.List;

/**
 * @ClassName :  NameTable2Service
 * @Author : PZG
 * @Date : 2019-05-07   10:40
 * @Description :
 */
public interface NameTable2Service {
    Integer save2(NameTable2 nameTable2);


    List<NameTable2> getAll2();
}
