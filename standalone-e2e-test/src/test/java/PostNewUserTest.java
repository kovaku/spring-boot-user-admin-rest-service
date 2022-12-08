import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Named.named;

public class PostNewUserTest extends AbstractRestAssuredBase {
    static String NEW_USER_NAME = "new user";
    static String NEW_USER_EMAIL = "test@test.com";

    @BeforeAll
    public static void setup() {
        RestAssured.basePath = "api/v1/user";
    }

    @Test
    void testAddNewUserPositiveFlow() {
        int idOfNewUser = RestAssured.given(getRequestSpecification())
                .body(Map.of("name", NEW_USER_NAME, "email", NEW_USER_EMAIL))
                .contentType(ContentType.JSON)
                .log().all()
                .post()
                .prettyPeek()
                .then()
                .statusCode(201)
                .body("name", equalTo(NEW_USER_NAME))
                .body("email", equalTo(NEW_USER_EMAIL))
                .extract()
                .path("id");

        // cleanup newly created user
        log.info("id of the new record to be cleaned up: " + idOfNewUser);

        RestAssured.given()
                .basePath("api/v1/user/{userId}")
                .pathParam("userId", idOfNewUser)
                .log().all()
                .delete()
                .prettyPeek()
                .then()
                .statusCode(204);
    }

    @ParameterizedTest
    @MethodSource("badRequestBodies")
    void testAddNewUserBadRequest(String requestBody) {
        RestAssured.given(getRequestSpecification())
                .body(requestBody)
                .contentType(ContentType.JSON)
                .log().all()
                .post()
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    static Stream<Arguments> badRequestBodies() {
        return Stream.of(
                Arguments.of(named("Missing email field", "{\"email\": \"test@test.com\"}")),
                Arguments.of(named("Missing name field", "{\"name\": \"nameofuser hello\"}")),
                Arguments.of(named("NULL name field", "{\"name\": null ,\"email\": \"test@test.com\"}")),
                Arguments.of(named("NULL email field", "{\"name\": \"nameofuser hello\" ,\"email\": null}"))
                // Arguments.of(named("Invalid name", "{\"name\": 2 ,\"email\": \"test@test.com\"}"))
        );
    }
}
