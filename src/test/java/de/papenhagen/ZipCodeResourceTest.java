package de.papenhagen;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ZipCodeResourceTest {

    @Test
    public void testZipCodeEndpointWithHamburg() {
        given()
                .when().get("/zipcode/20095")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is("{\"lat\":53.5541579534295,\"lon\":10.0011036114924,\"state\":\"Hamburg\",\"zipCode\":20095}"));
    }

    @Test
    public void testZipCodeEndpointWithLowNumber() {
        given()
                .when().get("/zipcode/123")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void testZipCodeEndpointWithHighNumber() {
        given()
                .when().get("/zipcode/123123123123")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }
    @Test
    public void testZipCodeEndpointWithNonNumber() {
        //getting a bad request for invalid input
        given()
                .when().get("/zipcode/hund")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testZipCodeEndpointWithMixedNumber() {
        //getting a bad request for invalid input
        given()
                .when().get("/zipcode/123hund")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }


}