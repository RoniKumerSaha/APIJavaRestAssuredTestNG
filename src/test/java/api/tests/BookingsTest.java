package api.tests;

import helper.ApiHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

public class BookingsTest {

    @Test
    public void verifyAllBooking(){
        Response res = ApiHelper.GetBookings();
        Assertions.assertEquals(200, res.statusCode());
        Assertions.assertTrue(res.getBody().asString().contains("\"bookingid\":9"));
    }
    @Test
    public void verifyBooking(){
        Response res = ApiHelper.GetBookingWithID(10);
        JsonPath data = res.jsonPath();
        Assertions.assertEquals(200, res.statusCode());
        Assertions.assertEquals("Susan", data.get("firstname"));
        Assertions.assertEquals("Ericsson", data.get("lastname"));
        Assertions.assertEquals(515, (int)data.get("totalprice"));
    }

}
