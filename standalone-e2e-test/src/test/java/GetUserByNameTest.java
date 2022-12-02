import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;

public class GetUserByNameTest extends AbstractRestAssuredBase {
    @BeforeAll
    public static void setup() {
        RestAssured.basePath = "api/v1/user";
    }

    @ParameterizedTest
    @MethodSource("defaultUserNames")
    void testDefaultUserNames(String userName, int expectedUserId) {
        RestAssured.given(getRequestSpecification())
                .queryParam("name", userName)
                .log().all()
                .get()
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("id", equalTo(expectedUserId));
    }

    static Stream<Arguments> defaultUserNames() {
        return Stream.of(
                Arguments.of("John Doe", 1),
                Arguments.of("Jane Doe", 2)
        );
    }

    @Test
    void testNonExistingUser(){
        RestAssured.given(getRequestSpecification())
                .queryParam("name", "NotExistingName")
                .log().all()
                .get()
                .prettyPeek()
                .then()
                .statusCode(404);
    }
}
