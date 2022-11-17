import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class AbstractRestAssuredBase {
    @BeforeAll
    public static void init() {
        RestAssured.baseURI="http://localhost:8080/";
        RestAssured.basePath="api/v1/users";
    }
}
