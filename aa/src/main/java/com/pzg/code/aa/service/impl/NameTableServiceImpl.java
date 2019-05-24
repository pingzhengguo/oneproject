package com.pzg.code.aa.service.impl;

import com.pzg.code.aa.entity.NameTable;
import com.pzg.code.aa.mapper.NameTableMapper;
import com.pzg.code.aa.service.NameTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName :  NameTableServiceImpl
 * @Author : PZG
 * @Date : 2019-05-07   10:11
 * @Description :
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.READ_UNCOMMITTED, timeout = 36000, rollbackFor = Exception.class)
@EnableTransactionManagement
public class NameTableServiceImpl implements NameTableService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NameTableServiceImpl.class);

    @Autowired
    private NameTableMapper nameTableMapper;

    @Override
    public Integer save(NameTable nameTable) {
        nameTableMapper.insert(nameTable);
        return nameTable.getId();
    }

    @Override
    public NameTable getOne(Integer id) {
        return nameTableMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<NameTable> getAll() {
        return nameTableMapper.selectAll();
    }


}
