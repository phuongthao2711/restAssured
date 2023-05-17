package topic1;

import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

    /**
        given()
            content type, set cookies, add auth, add param, set headers info, ...
        when()
            GET, POST, PUT, DELETE
        then()
            validate status code, extract response, extract headers cookies & response body ...
     */

public class HTTPRequest {

    int id;

    @Test
    public void getUser() {
        given()
        .when()
                .get("https://reqres.in/api/users?page=2")
        .then()
                .statusCode(200)
                .log().all()
                .body("page", equalTo(2));
    }

    @Test
    public void createUser() throws FileNotFoundException {
        // 4 ways to create request body

        //W1: Hashmap > .body(data)
        HashMap data = new HashMap();
        data.put("name", "thao");
        data.put("job", "tester");
        String levelArr[] = {"A", "B"};
        data.put("level", levelArr);

        //W2: org.json
        //in given() .body(data.toString())
//        JSONObject data = new JSONObject();
//        data.put("name", "thao");
//        data.put("job", "tester");
//        String levelArr[] = {"A", "B"};
//        data.put("level", levelArr);

        //W3: Plain Old Java Object (POJO class) > body(data)
//        Pojo data = new Pojo();
//        data.setName("thao");
//        data.setJob("tester");
//        String levelArr[] = {"A", "B"};
//        data.setLevel(levelArr);

        //W4: from Json file > .body(data.toString())
//        File f = new File(".\\body.json");
//        FileReader fr = new FileReader(f);
//        JSONTokener jt = new JSONTokener(fr);
//        JSONObject data = new JSONObject(jt);


        id = given()
                .contentType("application/json")
                .body(data)
        .when()
                .post("https://reqres.in/api/users")
                .jsonPath().getInt("id");

//      .then()
//                .statusCode(201)
//                .body("name", equalTo("thao"))
//                .body("level[0]", equalTo("A"))
//                .header("Content-Type","application/json; charset=utf-8")
//                .log().all();

    }

    @Test(dependsOnMethods = {"createUser"})
    public void updateUser() {
        HashMap data = new HashMap();
        data.put("name", "thao");
        data.put("job", "astronaut");

        given()
                .contentType("application/json")
                .body(data)
        .when()
                .put("https://reqres.in/api/users/" + id)
        .then()
                .statusCode(200)
                .log().all();
    }

    @Test(dependsOnMethods = {"updateUser"})
    public void deleteUser() {
        when()
                .delete("https://reqres.in/api/users/" + id)
        .then()
                .statusCode(204)
                .log().all();
    }
}
