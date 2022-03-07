package api.tests;

import data.Booking;
import helper.ApiHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

public class BookingsTest {

    @BeforeMethod
    public void setUP(){
        ApiHelper.setUpAPI();
    }


    @Test(priority = 2, description = "Checking all existing bookings")
    public void verifyAllBooking(){
        Response res = ApiHelper.GetBookings();
        Assert.assertEquals(200, res.statusCode());
        Assert.assertTrue(res.getBody().asString().contains("\"bookingid\":9"));
    }
    @Test(priority = 1, description = "Verify a booking using booking id")
    public void verifyBooking(){
        Response res = ApiHelper.GetBookingWithID(10);
        JsonPath data = res.jsonPath();
        Assert.assertEquals(200, res.statusCode());
        Assert.assertEquals("Mark", data.get("firstname"));
        Assert.assertEquals("Brown", data.get("lastname"));
        Assert.assertEquals(625, (int)data.get("totalprice"));
    }

    @Test(priority = 3, description = "Creating a new booking")
    public void verifyCreateBooking(){
        Response response = ApiHelper.createBooking(Booking.bookingData);
        JsonPath resData = response.jsonPath();
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("Jim", resData.getMap("booking").get("firstname"));
    }

    @Test(priority = 4, description = "Delete a booking")
    public void verifyDeleteBooking(){
        Response response = ApiHelper.deleteBooking(5);
        Assert.assertEquals(201, response.statusCode());
    }

}
