package com.pzg.code.aa.service.impl;

import com.pzg.code.aa.entity.AgeTable;
import com.pzg.code.aa.mapper.AgeTableMapper;
import com.pzg.code.aa.service.AgeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName :  AgeTableServiceimpl
 * @Author : PZG
 * @Date : 2019-05-07   13:55
 * @Description :
 */
@Service
@Transactional(propagation = Propagation.NESTED, isolation = Isolation.READ_UNCOMMITTED, timeout = 36000, rollbackFor = Exception.class)
@EnableTransactionManagement
public class AgeTableServiceimpl implements AgeTableService {
    @Autowired
    private AgeTableMapper ageTableMapper;

    @Override
    public List<AgeTable> selectByExample(String property, List list) {
        Example example = new Example(AgeTable.class);
//        example.createCriteria().andIn(property, list);
//        example.createCriteria().andBetween(property, 2, 3);
//        example.createCriteria().andIn(property, list);
//        example.createCriteria().andCondition("id >=", 2);
//        example.createCriteria().andCondition("id <>", 3);
//        example.createCriteria().andIsNotNull("id");
        example.createCriteria().andBetween(property, 1, 3).andCondition("id=", 2);
        return ageTableMapper.selectByExample(example);
    }

    @Override
    public List<AgeTable> selectAll() {
        return ageTableMapper.selectAll();
    }

    @Override
    public AgeTable selectByPrimaryKey(Object key) {
        return ageTableMapper.selectByPrimaryKey(key);
    }

    @Override
    public int insert(AgeTable entity) {
        return ageTableMapper.insert(entity);
    }

    @Override
    public int deleteByExample(List<String> list, String property, Class<AgeTable> clazz) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, list);
        return this.ageTableMapper.deleteByExample(example);
    }

}
