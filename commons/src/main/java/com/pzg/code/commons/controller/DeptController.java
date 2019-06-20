package com.pzg.code.commons.controller;

import com.pzg.code.commons.excel.Dept;
import com.pzg.code.commons.excel.FileUtil;
import com.pzg.code.commons.mapper.DeptMapper;
import com.pzg.code.commons.utils.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName :  DeptController
 * @Author : PZG
 * @Date : 2019-06-20   11:52
 * @Description :
 */
@Api(tags = {"DeptController"})
@Controller
@RequestMapping("/DeptController")
public class DeptController {

    @Autowired
    private DeptMapper deptMapper;

    @RequestMapping(value = "dept/excel", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo deptExcel() {
        try {
            List<Dept> list = deptMapper.getAll();
//            return FileUtil.createExcelByPOIKit("部门表", list, Dept.class);
            return FileUtil.createCsv("部门表", list, Dept.class);
        } catch (Exception e) {
            return ResultInfo.failure("导出Excel失败，请联系网站管理员！");
        }
    }
}
