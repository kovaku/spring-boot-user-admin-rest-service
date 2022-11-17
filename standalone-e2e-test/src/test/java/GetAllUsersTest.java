import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class GetAllUsersTest extends AbstractRestAssuredBase {

    @Test
    void testDefaultGetAllUsers() {
        RestAssured.given()
                .log().all()
                .get()
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("size()", equalTo(2)) //array size in the root -> number of user records
                .body("[0].id", equalTo(1))  // spotcheck first ID
                .body("[0].name", equalTo("John Doe"))  // spotcheck first name
                .body("[0].links.size()", equalTo(1))  // check number of links returned
                .body("[1].id", equalTo(2));  // spotcheck second ID
    }

    @Test
    void testDefaultGetAllUsersViaExtract() {
        String responseString = RestAssured.given()
                .log().all()
                .get()
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .asString();

        JsonPath js = new JsonPath(responseString);
        Assertions.assertEquals(2, js.getInt("size()"), "Number of users returned is incorrect!" );
        Assertions.assertEquals("John Doe", js.getString("[0].name"), "First user name is incorrect!" );

        // https://www.javadoc.io/doc/io.rest-assured/json-path/latest/io/restassured/path/json/JsonPath.html
        //additional code snippets
        List<Map> allNames = js.get("name");   //get all names from the response
        System.out.println("Name of the users in the response: " + allNames);
        List<Map> oneUser = js.get("findAll { x -> x.name == 'John Doe'}");  //return the userwith name 'John Doe'
        System.out.println("John Doe's record: " + oneUser);
    }
}
