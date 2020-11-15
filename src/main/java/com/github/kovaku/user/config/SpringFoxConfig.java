package com.github.kovaku.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        final ApiInfo apiInfo = new ApiInfo("REST API", new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(CHANGELOG_FILENAME)))
            .lines()
            .collect(Collectors.joining(System.lineSeparator())),
            "1.0.0-RC1", "", new Contact("team", "", "bla@bla.com"), "", "", Collections.emptyList());

        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.github.kovaku.user"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo();
    }

    private ApiInfo apiInfo() {
        ApiIn
    }
}
