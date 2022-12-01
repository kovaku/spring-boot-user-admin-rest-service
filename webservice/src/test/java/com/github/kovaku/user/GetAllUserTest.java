package com.github.kovaku.user;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.apache.commons.io.IOUtils.resourceToString;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetAllUserTest {
    @LocalServerPort
    private int randomServerPort;
    private URI uri;

    @BeforeAll
    void init() throws URISyntaxException {
        uri = new URI("http://localhost:" + randomServerPort + "/api/v1/users");
    }

    @Test
    void testGetAllUsersDefault() throws JSONException, IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(200, result.getStatusCodeValue());

        String usersExpected = resourceToString("/allDefaultUsers.json", StandardCharsets.UTF_8);
        usersExpected = usersExpected.replace("8080", String.valueOf(randomServerPort));  //because the response URL contains the port what is random
        JSONAssert.assertEquals(usersExpected, result.getBody(), JSONCompareMode.STRICT);
    }

}
