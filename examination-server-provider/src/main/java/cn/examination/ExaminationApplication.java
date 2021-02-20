package cn.examination;

import cn.examination.config.configuration.AuthRsaKeyProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("cn.examination.config.mapper")
@EnableConfigurationProperties(AuthRsaKeyProperties.class)
public class ExaminationApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExaminationApplication.class,args);
    }
}
