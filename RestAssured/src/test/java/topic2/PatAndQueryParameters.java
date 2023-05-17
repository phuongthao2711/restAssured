package topic2;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PatAndQueryParameters {

    //https://reqres.in/api/users?page=2&id=5

    @Test
    public void testQueryAndPath() {
        given()
                .pathParam("mypath", "users")
                .queryParam("page", 2)
                .queryParam("id", 5)
                .when()
                .get("https://reqres.in/api/{mypath}")
                .then()
                .statusCode(200)
                .log().all();
    }

}
