package com.pzg.code.commons.fileUpload;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : PZG
 * @date : 2018-09-04   10:48
 * @description : 流程文件
 */
@Table(name = "process_fastdfs_file")
public class ProcessFastFile {
    /**
     * 逻辑ID，保证唯一
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 文件的初始名字
     */
    @Column(name = "file_original_name")
    private String fileOriginalName;

    /**
     * 文件在服务器的路径
     */
    @Column(name = "file_server_path")
    private String fileServerPath;

    /**
     * 原始文件大小（单位字节）
     */
    @Column(name = "file_original_size")
    private Double fileOriginalSize;

    /**
     * 文件上传时间
     */
    @Column(name = "file_upload_time")
    private Date fileUploadTime;

    /**
     * 获取逻辑ID，保证唯一
     *
     * @return id - 逻辑ID，保证唯一
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置逻辑ID，保证唯一
     *
     * @param id 逻辑ID，保证唯一
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * 获取文件的初始名字
     *
     * @return file_original_name - 文件的初始名字
     */
    public String getFileOriginalName() {
        return fileOriginalName;
    }

    /**
     * 设置文件的初始名字
     *
     * @param fileOriginalName 文件的初始名字
     */
    public void setFileOriginalName(String fileOriginalName) {
        this.fileOriginalName = fileOriginalName;
    }

    /**
     * 获取文件在服务器的路径
     *
     * @return file_server_path - 文件在服务器的路径
     */
    public String getFileServerPath() {
        return fileServerPath;
    }

    /**
     * 设置文件在服务器的路径
     *
     * @param fileServerPath 文件在服务器的路径
     */
    public void setFileServerPath(String fileServerPath) {
        this.fileServerPath = fileServerPath;
    }

    /**
     * 获取原始文件大小（单位字节）
     *
     * @return file_original_size - 原始文件大小（单位字节）
     */
    public Double getFileOriginalSize() {
        return fileOriginalSize;
    }

    /**
     * 设置原始文件大小（单位字节）
     *
     * @param fileOriginalSize 原始文件大小（单位字节）
     */
    public void setFileOriginalSize(Double fileOriginalSize) {
        this.fileOriginalSize = fileOriginalSize;
    }

    /**
     * 获取文件上传时间
     *
     * @return file_upload_time - 文件上传时间
     */
    public Date getFileUploadTime() {
        return fileUploadTime;
    }

    /**
     * 设置文件上传时间
     *
     * @param fileUploadTime 文件上传时间
     */
    public void setFileUploadTime(Date fileUploadTime) {
        this.fileUploadTime = fileUploadTime;
    }
}