package cn.examination.config.configuration;

/**
 * @author hanxiangyu
 * @createTime 2021/6/22
 * @description
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 *  swagger-ui
 */
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger")
@EnableAutoConfiguration
@Data
public class SwaggerConfig {

//    @Value("swagger.basepackage")
    private String basepackage;
//    @Value("swagger.title")
    private String title;


    //在构建文档的时候 只显示添加了@Api注解的类
    @Bean //作为bean纳入spring容器
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basepackage))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
        // 需要添加权限认证
//                .securitySchemes()
//                .securityContexts();
    }
    private ApiInfo apiInfo(){
        return  new ApiInfoBuilder()
                .title(title)
                .description("API接口文档，及相关接口的说明")
                .version("1.0.0")
                .build();
    }
}
