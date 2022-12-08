import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SwaggerEndpointTest extends AbstractRestAssuredBase {
    @BeforeAll
    static void setup() {
        RestAssured.basePath="/api/swagger-ui/";
    }

    @Test
    void testSwaggerUIAvailable() {
        RestAssured.given(getRequestSpecification())
                .log().all()
                .get()
                .then()
                .statusCode(200);
    }
}
