package com.github.kovaku.user.config;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SpringFoxConfig {
    private static final String API_TITLE = "User Rest API sample serice";
    private static final String API_VERSION = "1.0.1";
    private static final String API_TERMS_OF_SERVICE = "1.0.1";
    private static final String API_LICENSE = "Apache 2.0";
    private static final String API_LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0";
    private static final Contact CONTACT = new Contact("Zsolt Kovacs", "https://github.com/kovaku", "kovaku@gmail.com");
    public static final Response NOT_FOUND = new ResponseBuilder().code("404").description(HttpStatus.NOT_FOUND.getReasonPhrase()).build();
    public static final List<Response> GLOBAL_RESPONSES = List.of(NOT_FOUND);

    private final Resource apiChangeLog;

    public SpringFoxConfig(@Value("classpath:API_CHANGE_LOG.info") Resource apiChangeLog) {
        this.apiChangeLog = apiChangeLog;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.github.kovaku.user"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(getApiInfo())
            .globalResponses(HttpMethod.GET, GLOBAL_RESPONSES)
            .globalResponses(HttpMethod.POST, GLOBAL_RESPONSES)
            .globalResponses(HttpMethod.PUT, GLOBAL_RESPONSES)
            .globalResponses(HttpMethod.DELETE, GLOBAL_RESPONSES);
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(API_TITLE, getApiChangeLogAsString(), API_VERSION, API_TERMS_OF_SERVICE, CONTACT,
            API_LICENSE, API_LICENSE_URL, Collections.emptyList());
    }

    public String getApiChangeLogAsString() {
        try (Reader reader = new InputStreamReader(apiChangeLog.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
