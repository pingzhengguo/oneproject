package com.pzg.code.bb.controller;

import com.pzg.code.bb.entity.NameTable2;
import com.pzg.code.bb.service.NameTable2Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName :  NameTableController
 * @Author : PZG
 * @Date : 2019-05-07   10:13
 * @Description :
 */
@Api(tags = {"NameTableController2"})
@Controller
@RequestMapping("/nameTable2")
public class NameTableController2 {

    @Autowired
    private NameTable2Service nameTable2Service;


    @ApiOperation(value = "insert2", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/insert2", method = RequestMethod.POST)
    @ResponseBody
    public Object insert2(NameTable2 nameTable2) {
        return nameTable2Service.save2(nameTable2);
    }


    @ApiOperation(value = "getAll2", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getAll2", method = RequestMethod.GET)
    @ResponseBody
    public Object getAll2() {
        return nameTable2Service.getAll2();
    }


}
