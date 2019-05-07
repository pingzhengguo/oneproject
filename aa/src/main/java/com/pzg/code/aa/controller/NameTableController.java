package com.pzg.code.aa.controller;

import com.pzg.code.aa.entity.NameTable;
import com.pzg.code.aa.service.NameTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@Api(tags = {"NameTableController"})
@Controller
@RequestMapping("/nameTable")
public class NameTableController {

    @Autowired
    private NameTableService nameTableService;


    @ApiOperation(value = "insert", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Object insert(NameTable nameTable) {
        return nameTableService.save(nameTable);
    }

    @ApiOperation(value = "getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public Object getAll() {
        return nameTableService.getAll();
    }


    @ApiOperation(value = "getOne", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ResponseBody
    public Object getOne(Integer id) {
        return nameTableService.getOne(id);
    }


}
