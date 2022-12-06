import configuration.RestAssuredTestConfiguration;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestAssuredTestConfiguration.class)
public class AbstractRestAssuredBase {
    static final Logger log = LoggerFactory.getLogger(AbstractRestAssuredBase.class);

    @Value("${service.baseURI}")
    private String baseUri;

    public RequestSpecification getRequestSpecification(){
        log.info("Url to be used: " + baseUri);
        return RestAssured.given().baseUri(baseUri);
    }

    public static String getJsonStringFromFile(String filename) throws IOException {
        return IOUtils.resourceToString("/"+filename, StandardCharsets.UTF_8);
    }
}

