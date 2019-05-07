package com.pzg.code.bb.service.impl;

import com.pzg.code.bb.entity.NameTable2;
import com.pzg.code.bb.mapper2.NameTableMapper2;
import com.pzg.code.bb.service.NameTable2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName :  NameTable2ServiceImpl
 * @Author : PZG
 * @Date : 2019-05-07   10:41
 * @Description :
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.READ_UNCOMMITTED, timeout = 36000, rollbackFor = Exception.class)
@EnableTransactionManagement
public class NameTable2ServiceImpl implements NameTable2Service {
    private static final Logger LOGGER = LoggerFactory.getLogger(NameTable2ServiceImpl.class);
    @Autowired
    private NameTableMapper2 nameTableMapper2;

    @Override
    public Integer save2(NameTable2 nameTable2) {
        nameTableMapper2.insert2(nameTable2);
        return nameTable2.getId();
    }

    @Override
    public List<NameTable2> getAll2() {
        return nameTableMapper2.getAll();
    }
}
