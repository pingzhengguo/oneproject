package com.pzg.code.commons.fileUpload;

import com.pzg.code.commons.conf.TestConfig;
import com.pzg.code.commons.mapper.ProcessFastFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;

/**
 * @ClassName :  WorkFlowUpload
 * @Author : PZG
 * @Date : 2018-09-05   11:12
 * @Description :
 */
@Component
public class WorkFlowUpload {
    @Autowired
    private FastDFSClientWrapper fastDFSClientWrapper;
    @Resource
    private ProcessFastFileMapper processFastFileMapper;
    /**
     * 加载文件服务器的地址
     */
    @Resource
    private TestConfig fastDfsValueConfig;  //地址   http://10.0.91.222:8999/

    /**
     * 采用springmvc提供的文件上传
     */
    public Map<Integer, ProcessFastFile> workFlowUpload(HttpServletRequest request) throws Exception {

        ProcessFastFile processFastFile = null;
        Map<Integer, ProcessFastFile> processFastFileMap = new HashMap<>();

        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getSession().getServletContext());

        //检查form中是否有enctype="multipart/form-data"
        if (cmr.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest msr = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = msr.getFileNames();

            while (iter.hasNext()) {
                //一次遍历所有文件
                List<MultipartFile> files = msr.getFiles(iter.next().toString());
                if (files != null) {
                    try {
                        for (MultipartFile file : files) {
                            //得到文件初始的名字
                            String fileOriginalName = file.getOriginalFilename();
                            InputStream inputStream = file.getInputStream();
                            // 取文件格式后缀名
                            String extName = fileOriginalName.substring(fileOriginalName.indexOf(".") + 1);
                            //得到文件在服务器的路径
                            String path = fastDFSClientWrapper.uploadFile(file);
                            //上传
                            file.transferTo(new File(fileOriginalName));
                            Double size = Double.valueOf(file.getSize());
                            processFastFile = new ProcessFastFile();
                            Date timeStamp = Date.from(Instant.now());
                            //把值放到对象里
                            processFastFile.setFileOriginalName(fileOriginalName);
                            processFastFile.setFileOriginalSize(size);
                            processFastFile.setFileServerPath(path);
                            processFastFile.setFileUploadTime(timeStamp);
                            //添加文件信息进fastdfs_file表
                            processFastFileMapper.insert(processFastFile);
                            processFastFile.setFileServerPath(fastDfsValueConfig.getFastdfsAddress() + path);
                            processFastFileMap.put(processFastFile.getId(), processFastFile);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Exception("文件上传失败");
                    }
                } else {
                    throw new Exception("上传的文件为空");
                }
            }

        }
        return processFastFileMap;
    }

}
