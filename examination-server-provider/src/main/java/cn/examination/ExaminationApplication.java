package cn.examination;

import cn.examination.config.configuration.AuthRsaKeyProperties;
import cn.examination.config.configuration.SwaggerConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("cn.examination.config.mapper")
@EnableConfigurationProperties({AuthRsaKeyProperties.class, SwaggerConfig.class})
public class ExaminationApplication{
//public class ExaminationApplication extends SpringBootServletInitializer {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(ExaminationApplication.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(ExaminationApplication.class,args);
    }
}
