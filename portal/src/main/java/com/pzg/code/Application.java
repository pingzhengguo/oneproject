package com.pzg.code;


import com.github.tobato.fastdfs.FdfsClientConfig;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
@EnableCaching
@EnableAsync
@EnableScheduling
@MapperScan(basePackages = "com.pzg.code.**.mapper",sqlSessionFactoryRef = "firstSqlSessionFactory")
@org.mybatis.spring.annotation.MapperScans(value = {
        @org.mybatis.spring.annotation.MapperScan(basePackages = "com.pzg.code.**.mapper2",sqlSessionFactoryRef = "secondSqlSessionFactory"),
        @org.mybatis.spring.annotation.MapperScan(basePackages = "com.pzg.code.**.mapper3",sqlSessionFactoryRef = "thirdSqlSessionFactory"),
})
@Import(FdfsClientConfig.class)
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
