import io.restassured.RestAssured;
import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.CreateUserSpec.createUserRequestSpec;
import static specs.CreateUserSpec.createUserResponseSpec;
import static specs.DeleteUserSpec.deleteUserRequestSpec;
import static specs.DeleteUserSpec.deleteUserResponseSpec;
import static specs.RegisterWithoutEmail.registerWithoutEmailRequestSpec;
import static specs.RegisterWithoutEmail.registerWithoutEmailResponseSpec;
import static specs.ViewNotFoundUserSpec.viewNotFoundSingleUserRequestSpec;
import static specs.ViewNotFoundUserSpec.viewNotFoundUserSingleResponseSpec;
import static specs.ViewSingleUserSpec.viewSingleUserRequestSpec;
import static specs.ViewSingleUserSpec.viewSingleUserResponseSpec;

public class ReqresInTest {

    @BeforeAll
    static void setUp() {

        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured.filters(withCustomTemplates());
    }

    @Test
    void createUserTest() {

        CreateUserRequestModel requestData = new CreateUserRequestModel();
        requestData.setName("morpheus");
        requestData.setJob("AQA");

        CreateUserResponseModel response = given(createUserRequestSpec)
                .body(requestData)
                .when()
                .post("/users")
                .then()
                .spec(createUserResponseSpec)
                .extract().as(CreateUserResponseModel.class);

        assertThat(response.getName()).isEqualTo(requestData.getName());
        assertThat(response.getJob()).isEqualTo(requestData.getJob());
    }

    @Test
    void deleteUserTest() {

        given(deleteUserRequestSpec)
                .when()
                .delete("/users/2")
                .then()
                .spec(deleteUserResponseSpec);
    }

    @Test
    void viewSingleUserTest() {

        int userID = 1;

        ViewSingleUserResponseModel response = given(viewSingleUserRequestSpec)
                .when()
                .get("/users/" + userID)
                .then()
                .spec(viewSingleUserResponseSpec)
                .extract().as(ViewSingleUserResponseModel.class);

        assertThat(response.getData().getId()).isEqualTo(userID);
    }

    @Test
    void viewNotFoundSingleUserTest() {

        int userID = 23;

        given(viewNotFoundSingleUserRequestSpec)
                .when()
                .get("/users/" + userID)
                .then()
                .spec(viewNotFoundUserSingleResponseSpec);
    }

    @Test
    void registerWithoutEmailTest() {

        RegistredRequestModel requestData = new RegistredRequestModel();
        requestData.setPassword("pistol");

        RegistredResponsetModel response = given(registerWithoutEmailRequestSpec)
                .body(requestData)
                .when()
                .post("/register")
                .then()
                .spec(registerWithoutEmailResponseSpec)
                .extract().as(RegistredResponsetModel.class);

        assertThat(response.getError()).isEqualTo("Missing email or username");
    }
}
