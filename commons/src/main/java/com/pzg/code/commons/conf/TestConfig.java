package com.pzg.code.commons.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:config/config_test.properties")
public class TestConfig {

    @Value("${test.address}")
    private String testAddress;

    public String getTestAddress() {
        return testAddress;
    }

    public void setTestAddress(String testAddress) {
        this.testAddress = testAddress;
    }

    @Value("${test.login}")
    private String testLogin;

    public String getTestLogin() {
        return testLogin;
    }

    public void setTestLogin(String testLogin) {
        this.testLogin = testLogin;
    }

    /**
     * fastDfs的下载地址头
     */
    @Value("${fastdfs.address}")
    private String fastdfsAddress;

    public String getFastdfsAddress() {
        return fastdfsAddress;
    }

    public void setFastdfsAddress(String fastdfsAddress) {
        this.fastdfsAddress = fastdfsAddress;
    }
}
