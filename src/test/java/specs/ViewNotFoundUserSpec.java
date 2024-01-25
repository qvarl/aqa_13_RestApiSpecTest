package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class ViewNotFoundUserSpec {

    public static RequestSpecification viewNotFoundSingleUserRequestSpec = with()
            .log().uri()
            .log().headers()
            .log().body();

    public static ResponseSpecification viewNotFoundUserSingleResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(404)
            .expectBody(notNullValue())
            .build();
}