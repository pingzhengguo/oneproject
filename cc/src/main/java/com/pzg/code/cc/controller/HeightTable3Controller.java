package com.pzg.code.cc.controller;

import com.pzg.code.cc.entity.HeightTable3;
import com.pzg.code.cc.mapper3.HeightTableMapper3;
import com.pzg.code.cc.service.HeightTable3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName :  HeightTable3Controller
 * @Author : PZG
 * @Date : 2019-05-07   11:08
 * @Description :
 */
@Api(tags = {"HeightTable3Controller"})
@Controller
@RequestMapping("/heightTable3")
public class HeightTable3Controller {

    @Autowired
    private HeightTable3Service heightTable3Service;


    @ApiOperation(value = "insert3", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/insert3", method = RequestMethod.POST)
    @ResponseBody
    public Object insert3(HeightTable3 heightTable3) {
        return heightTable3Service.insert3(heightTable3);
    }


    @ApiOperation(value = "getAll3", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/getAll3", method = RequestMethod.GET)
    @ResponseBody
    public Object getAll2() {
        return heightTable3Service.getAll3();
    }


}
