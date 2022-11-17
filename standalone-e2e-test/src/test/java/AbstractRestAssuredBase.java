import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AbstractRestAssuredBase {
    @BeforeAll
    public static void init() {
        String targetenv = System.getProperty("targetenv");
        System.out.println("environment is " + targetenv);
        RestAssured.baseURI = "http://localhost:8080/";
        if (targetenv != null) {
            switch (targetenv) {
                case "local":
                    RestAssured.baseURI = "http://localhost:8080/";
                    break;
                case "prod":
                    RestAssured.baseURI = "http://prod:8080/";
                    break;
                default:
                    System.out.println("Not supported targetenv, using default local!");
                    RestAssured.baseURI = "http://localhost:8080/";
                    break;
            }
        }
    }

    public static String getJsonStringFromFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);
        String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        return content;
    }
}

