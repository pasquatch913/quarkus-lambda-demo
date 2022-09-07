package com.pasquatch;

import io.quarkus.amazon.lambda.http.model.AwsProxyRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class UserTest {
    @Test
    public void testJaxrs() {
        RestAssured.when().get("/hello").then()
                .contentType("text/plain")
                .body(equalTo("hello jaxrs"));
    }

    @Test
    public void testbody() {
        String encodedRequestBody = "ewoiZmlyc3ROYW1lIjogIkpvaG4iLAogImxhc3ROYW1lIjogIlNtaXRoIgogfQ==";
        String expectedBody = "{\"firstName\":\"John\",\"lastName\":\"Smith\"}";

        AwsProxyRequest request = new AwsProxyRequest();
        request.setBody(encodedRequestBody);
        RestAssured.given().body(encodedRequestBody)
                .contentType("application/json")
                .when().post("/hello/test")
                .then()
                .body(equalTo(expectedBody));

    }

}
