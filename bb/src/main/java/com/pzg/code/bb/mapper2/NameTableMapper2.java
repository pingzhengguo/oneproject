package com.pzg.code.bb.mapper2;

import com.pzg.code.bb.entity.NameTable2;
import com.pzg.code.commons.utils.MyMapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

public interface NameTableMapper2 extends MyMapper<NameTable2> {
    @Options(useGeneratedKeys = true, keyProperty = "nameTable2.id", keyColumn = "id")
    Integer insert2(NameTable2 nameTable2);


    List<NameTable2> getAll();


}