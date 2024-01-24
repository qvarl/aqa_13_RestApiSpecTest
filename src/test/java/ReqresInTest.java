import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresInTest {

    String BASE_URL = "https://reqres.in";

    @Test
    void CreateUserTest() {

        String name = "morpheus";
        String job = "QA";
        String requestData = "{ \"name\": \"" + name + "\", \"job\": \"" + job + "\"}";

        given()
                .baseUri(BASE_URL)
                .log().uri()
                .contentType(JSON)
                .body(requestData)

                .when()
                .post("/api/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is(name), "job", is(job));
    }

    @Test
    void checkDeleteUserStatusTest() {

        given()
                .baseUri(BASE_URL)
                .log().uri()

                .when()
                .delete("/api/users/2")

                .then()
                .log().status()
                .statusCode(204);
    }

    @Test
    void checkViewUserIDTest() {

        Integer userID = 1;

        given()
                .baseUri(BASE_URL)
                .log().uri()

                .when()
                .get("/api/users/" + userID)

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(userID));
    }

    @Test
    void viewNotFoundUserTest() {

        Integer userID = 23;

        given()
                .baseUri(BASE_URL)
                .log().uri()

                .when()
                .get("/api/users/" + userID)

                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    void registredWithoutEmailTest() {

        String requestData = "{ \"password\": \"pistol\"}";

        given()
                .baseUri(BASE_URL)
                .log().uri()
                .contentType(JSON)
                .body(requestData)

                .when()
                .post("/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }
}
