package com.registration.configuration;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger {

    @Value("${env}")
    private String env;

    @Value("${server.port:8082}")
    private int port;

    public String getHost() {
        switch (this.env)
        {
            default:
                return "localhost:"+this.port;
        }
    }

    @Bean
    public Docket api() throws IOException {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .host(this.getHost())
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }
    private ApiInfo apiInfo() throws IOException {
        String version = new BufferedReader(new InputStreamReader(this.getClass()
                .getResourceAsStream("/version.txt")))
                .readLine();
        if (version.trim().equals("dev-local")) {
            version = "local#" + new Date().getTime();
        }
        return new ApiInfoBuilder()
                .title("Registration Backend")
                .description("APIs para a aplicação Registration")
                .contact(new Contact("GM", "https://github.com/GustavoMeneses", "gmonteiromenezes@gmail.com"))
                .version(version)
                .build();
    }

    private List<? extends SecurityScheme> securitySchemes() {
        List<SecurityScheme> authorizationTypes = Arrays.asList(new ApiKey("token", "Authorization", "header"));
        return authorizationTypes;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts   = Arrays.asList(SecurityContext.builder()
                .forPaths(PathSelectors.any()).securityReferences(securityReferences()).build());
        return securityContexts;
    }

    private List<SecurityReference> securityReferences() {
        List<SecurityReference> securityReferences = Arrays
                .asList(SecurityReference.builder().reference("token").scopes(new AuthorizationScope[0]).build());
        return securityReferences;
    }
}
