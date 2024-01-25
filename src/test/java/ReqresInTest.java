import models.*;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class ReqresInTest {

    String BASE_URL = "https://reqres.in";

    @Test
    void createUserTest() {

        CreateUserRequestModel requestData = new CreateUserRequestModel();
        requestData.setName("morpheus");
        requestData.setJob("AQA");

        CreateUserResponseModel response = given()
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
                .extract().as(CreateUserResponseModel.class);

        assertThat(response.getName()).isEqualTo(requestData.getName());
        assertThat(response.getJob()).isEqualTo(requestData.getJob());
    }

    @Test
    void deleteUserTest() {

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
    void viewSingleUserTest() {

        Integer userID = 1;

        ViewSingleUserResponseModel response =
                given()
                        .baseUri(BASE_URL)
                        .log().uri()

                        .when()
                        .get("/api/users/" + userID)

                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(ViewSingleUserResponseModel.class);

        assertThat(response.getData().getId()).isEqualTo(userID);

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

        RegistredRequestModel requestData = new RegistredRequestModel();
        requestData.setPassword("pistol");

        RegistredResponsetModel response = given()
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
                .extract().as(RegistredResponsetModel.class);

        assertThat(response.getError()).isEqualTo("Missing email or username");
    }
}
