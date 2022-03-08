package helper;

import data.Booking;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiHelper {

    static String URL = "https://restful-booker.herokuapp.com" ;

    public static void setUpAPI(){
        RestAssured.baseURI = URL;
    }
    public static Response GetBookings(){
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
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/booking/" + id)
                .then()
                .extract().response();
        System.out.println(response.getBody().asString());
        return response;
    }

    public static Response createBooking(String data){
        Response response = given()
                .contentType(ContentType.JSON)
                .auth()
                .basic("admin", "password123")
                .body(data)
                .post("/booking")
                .then()
                .extract()
                .response();
        System.out.println(response.getBody().asString());
        return response;
    }

    public static Response deleteBooking(int id){
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token="+createToken())
                .when()
                .delete("/booking/" + id)
                .then()
                .extract()
                .response();
        System.out.println(response.getBody().asString());
        return response;
    }

    public static String createToken(){
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(Booking.authData)
                .post("/auth")
                .then()
                .extract()
                .response();
        return response.jsonPath().get("token");
    }
}


