import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class GetUserByIdTest extends AbstractRestAssuredBase {
    @BeforeAll
    public static void setup() {
        RestAssured.basePath = "api/v1/user/{userId}";
    }

    @ParameterizedTest
    @MethodSource("defaultUserNames")
    void testDefaultUserNames(int userId, String expectedName) {
        String responseString = RestAssured.given(getRequestSpecification())
                .pathParam("userId", userId)
                .log().all()
                .get()
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .asString();

        JsonPath js = new JsonPath(responseString);
        Assertions.assertEquals(expectedName, js.getString("name"), "User name is incorrect!");
    }

    static Stream<Arguments> defaultUserNames() {
        return Stream.of(
                Arguments.of(1, "John Doe"),
                Arguments.of(2, "Jane Doe")
        );
    }

    @Test
    void testNonExistingUser() {
        RestAssured.given(getRequestSpecification())
                .pathParam("userId", 999)
                .log().all()
                .get()
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void testBadRequest() {
        RestAssured.given(getRequestSpecification())
                .pathParam("userId", "abc")
                .log().all()
                .get()
                .prettyPeek()
                .then()
                .statusCode(400);
    }
}
