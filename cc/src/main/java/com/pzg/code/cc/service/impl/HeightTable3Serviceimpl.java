package com.pzg.code.cc.service.impl;

import com.pzg.code.cc.entity.HeightTable3;
import com.pzg.code.cc.mapper3.HeightTableMapper3;
import com.pzg.code.cc.service.HeightTable3Service;
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
 * @ClassName :  HeightTable3Serviceimpl
 * @Author : PZG
 * @Date : 2019-05-07   11:04
 * @Description :
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.READ_UNCOMMITTED, timeout = 36000, rollbackFor = Exception.class)
@EnableTransactionManagement
public class HeightTable3Serviceimpl implements HeightTable3Service {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeightTable3Serviceimpl.class);
    @Autowired
    private HeightTableMapper3 heightTableMapper3;


    @Override
    public Integer insert3(HeightTable3 heightTable3) {
        heightTableMapper3.insert3(heightTable3);
        return heightTable3.getId();
    }

    @Override
    public List<HeightTable3> getAll3() {
        return heightTableMapper3.getAll();
    }
}
