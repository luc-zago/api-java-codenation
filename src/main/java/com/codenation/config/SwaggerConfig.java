package com.codenation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket errorManagerApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.codenation"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
    }
    private ApiInfo metaInfo(){
        return new ApiInfo(
                "Error Manager",
                "Projeto Aceleração Condenation Conta Azul",
                "1.0",
                "Terms of service",
                "Grupo 03",
                "MIT Licence",
                "https://mit-license.org/");
    }
}