package com.pzg.code.aa.controller;

import com.pzg.code.aa.entity.AgeTable;
import com.pzg.code.aa.service.AgeTableService;
import com.pzg.code.commons.conf.TestConfig;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName :  AgeTableController
 * @Author : PZG
 * @Date : 2019-05-07   14:18
 * @Description :
 */
@Api(tags = {"AgeTableController"})
@Controller
@RequestMapping("/ageTable")
public class AgeTableController {

    @Autowired
    private AgeTableService ageTableService;


    @ApiOperation(value = "insert", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Object insert(AgeTable ageTable) {
        return ageTableService.insert(ageTable);
    }

    @ApiOperation(value = "getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public Object getAll() {
        return ageTableService.selectAll();
    }


    @ApiOperation(value = "selectByExample", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/selectByExample", method = RequestMethod.GET)
    @ResponseBody
    public Object selectByExample() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        return ageTableService.selectByExample("id", list);
    }


    @ApiOperation(value = "getOne", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ResponseBody
    public Object getOne(Integer id) {
        return ageTableService.selectByPrimaryKey(id);
    }


    @ApiOperation(value = "getConfig", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getConfig", method = RequestMethod.GET)
    @ResponseBody
    public Object getConfig() {
        return ageTableService.getConfig();
    }


}
