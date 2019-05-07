package com.pzg.code.cc.mapper3;

import com.pzg.code.cc.entity.HeightTable3;
import com.pzg.code.commons.utils.MyMapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface HeightTableMapper3 extends MyMapper<HeightTable3> {

    @Options(useGeneratedKeys=true, keyProperty="heightTable3.id",keyColumn="id"  )
    Integer insert3(HeightTable3 heightTable3);


    List<HeightTable3> getAll();
}