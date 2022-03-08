package api.tests;

import data.Booking;
import helper.ApiHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

public class BookingsTest {
    int bookingID;

    @BeforeMethod
    public void setUP(){
        ApiHelper.setUpAPI();
    }


    @Test(priority = 1, description = "Checking all existing bookings", dependsOnMethods = {"verifyCreateBooking"})
    public void verifyAllBooking(){
        Response res = ApiHelper.GetBookings();
        Assert.assertEquals(200, res.statusCode());
        Assert.assertTrue(res.getBody().asString().contains("\"bookingid\":" + bookingID));
    }
    @Test(priority = 2, description = "Verify a booking using booking id", dependsOnMethods = {"verifyCreateBooking"})
    public void verifyBooking(){
        Response res = ApiHelper.GetBookingWithID(bookingID);
        JsonPath data = res.jsonPath();
        Assert.assertEquals(200, res.statusCode());
        Assert.assertEquals( data.get("firstname"), "Jim");
        Assert.assertEquals( data.get("lastname"), "Brown");
        Assert.assertEquals((int)data.get("totalprice"), 111);
    }

    @Test(priority = 3, description = "Creating a new booking")
    public void verifyCreateBooking(){
        Response response = ApiHelper.createBooking(Booking.bookingData);
        JsonPath resData = response.jsonPath();
        bookingID = resData.get("bookingid");
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("Jim", resData.getMap("booking").get("firstname"));
    }

    @Test(priority = 4, description = "Delete a booking", dependsOnMethods = {"verifyCreateBooking"})
    public void verifyDeleteBooking(){
        Response response = ApiHelper.deleteBooking(bookingID);
        Assert.assertEquals(201, response.statusCode());
    }

}
