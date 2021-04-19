package com.codenation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.title}")
    private String TITLE;

    @Value("${swagger.description}")
    private String DESCRIPTION;

    @Value("${swagger.version}")
    private String VERSION;

    @Value("${swagger.terms-of-service-url}")
    private String TERMS_OF_SERVICE_URL;

    @Value("${swagger.contact.name}")
    private String CONTACT_NAME;

    @Value("${swagger.contact.url}")
    private String CONTACT_URL;

    @Value("${swagger.contact.email}")
    private String CONTACT_EMAIL;

    @Value("${swagger.licence}")
    private String LICENCE;

    @Value("${swagger.licence-url}")
    private String LICENCE_URL;

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
                TITLE,
                DESCRIPTION,
                VERSION,
                TERMS_OF_SERVICE_URL,
                contact(),
                LICENCE,
                LICENCE_URL,
                new ArrayList<>());
    }

    private Contact contact() {
        return new Contact(
                CONTACT_NAME,
                CONTACT_URL,
                CONTACT_EMAIL);
    }
}