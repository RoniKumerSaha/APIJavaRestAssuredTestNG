package helper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiHelper {

    static String URL = "https://restful-booker.herokuapp.com" ;
    public static Response GetBookings(){
        RestAssured.baseURI = URL;
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/booking")
                .then()
                .extract().response();
        System.out.println(response.getBody().asString());
       return response;
    }

    public static Response GetBookingWithID(int id){
        RestAssured.baseURI = URL;
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/booking/" + id)
                .then()
                .extract().response();
        System.out.println(response.getBody().asString());
        return response;
    }

}


