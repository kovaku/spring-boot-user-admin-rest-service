package com.github.kovaku.user;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetAllUserTest {
    @LocalServerPort
    int randomServerPort;
    URI uri;

    @Value("classpath:alldefaultusers.json")
    Resource alldefaultusersresource;

    @BeforeAll
    void init () throws URISyntaxException {
    uri = new URI("http://localhost:" + randomServerPort + "/api/v1/users");
    }

    @Test
    void testGetAllUsersDefault() throws IOException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(200, result.getStatusCodeValue());

        String usersExpected = new String(Files.readAllBytes(alldefaultusersresource.getFile().toPath()));
        usersExpected = usersExpected.replace("8080", String.valueOf(randomServerPort));  //because the response URL contains the port what is random
        JSONAssert.assertEquals(usersExpected,result.getBody(), JSONCompareMode.STRICT );
    }

}
