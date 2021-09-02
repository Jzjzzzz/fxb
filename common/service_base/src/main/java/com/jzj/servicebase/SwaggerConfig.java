package com.jzj.servicebase;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Jzj
 * @version 1.0
 * @date 2021/7/24 21:00
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket adminApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(adminApiInfo())
                .select()
                // .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo adminApiInfo(){
        return new ApiInfoBuilder()
                .title("复习宝后台管理系统API文档")
                .description("本文档描述了复习宝后台管理系统的各个模块的接口的调用方式")
                .version("1.0")
                .contact(new Contact("漫漫长路", "http://jzjzzzz.icu", "946232976@qq.com"))
                .build();
    }


}
