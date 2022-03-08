package api.tests;

import data.Booking;
import helper.ApiHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

public class BookingsTest2 {
    int bookingID;

    @Test
    public void setUP(){
        ApiHelper.setUpAPI();
    }


    @Test(groups = "smoke", dependsOnGroups = "ini")
    public void verifyAllBooking(){
        Response res = ApiHelper.GetBookings();
        Assert.assertEquals(200, res.statusCode());
        Assert.assertTrue(res.getBody().asString().contains("\"bookingid\":" + bookingID));
    }
    @Test(groups = {"smoke", "wip"}, dependsOnGroups = "ini")
    public void verifyBooking(){
        Response res = ApiHelper.GetBookingWithID(bookingID);
        JsonPath data = res.jsonPath();
        Assert.assertEquals(200, res.statusCode());
        Assert.assertEquals( data.get("firstname"), "Jim");
        Assert.assertEquals( data.get("lastname"), "Brown");
        Assert.assertEquals((int)data.get("totalprice"), 111);
    }

    @Test(groups = "ini", dependsOnMethods = "setUP")
    public void verifyCreateBooking(){
        Response response = ApiHelper.createBooking(Booking.bookingData);
        JsonPath resData = response.jsonPath();
        bookingID = resData.get("bookingid");
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("Jim", resData.getMap("booking").get("firstname"));
    }

    @Test(groups = "smoke", dependsOnGroups = "ini")
    public void verifyDeleteBooking(){
        Response response = ApiHelper.deleteBooking(bookingID);
        Assert.assertEquals(201, response.statusCode());
    }

}

