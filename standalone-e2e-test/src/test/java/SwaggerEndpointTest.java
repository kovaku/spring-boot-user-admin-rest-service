import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SwaggerEndpointTest extends AbstractRestAssuredBase {
    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - executes once before all test methods in this class");
    }

    @Test
    void testSwaggerUIAvailable() {
        RestAssured.given()
                .log().all()
                .get()
                .then()
                .statusCode(200);
    }
}
