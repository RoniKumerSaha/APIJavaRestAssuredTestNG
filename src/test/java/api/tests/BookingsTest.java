package api.tests;

import data.Booking;
import helper.ApiHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

public class BookingsTest {

    @BeforeEach
    @DisplayName("Setting up the API")
    public void setUP(){
        ApiHelper.setUpAPI();
    }


    @Test
    @DisplayName("Verify all booking are present or not")
    public void verifyAllBooking(){
        Response res = ApiHelper.GetBookings();
        Assertions.assertEquals(200, res.statusCode());
        Assertions.assertTrue(res.getBody().asString().contains("\"bookingid\":9"));
    }
    @Test
    @DisplayName("Verify a single booking")
    public void verifyBooking(){
        Response res = ApiHelper.GetBookingWithID(10);
        JsonPath data = res.jsonPath();
        Assertions.assertEquals(200, res.statusCode());
        Assertions.assertEquals("Mark", data.get("firstname"));
        Assertions.assertEquals("Brown", data.get("lastname"));
        Assertions.assertEquals(625, (int)data.get("totalprice"));
    }

    @Test
    @DisplayName("Verify creating a new booking")
    public void verifyCreateBooking(){
        Response response = ApiHelper.createBooking(Booking.bookingData);
        JsonPath resData = response.jsonPath();
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Jim", resData.getMap("booking").get("firstname"));
    }

    @Test
    @DisplayName("Verify deleting a booking")
    @Disabled
    public void verifyDeleteBooking(){
        Response response = ApiHelper.deleteBooking(5);
        Assertions.assertEquals(201, response.statusCode());
    }

}
