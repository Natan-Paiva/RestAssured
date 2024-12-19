import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class serializeTest {
    public static void main(String[] args){
        AddPlace ap = new AddPlace();
        ap.setAccuracy(50);
        ap.setName("Test");
        ap.setPhone_number("55555555");
        ap.setAddress("canoas-rs");
        ap.setLanguage("Portuguese");
        ap.setWebsite("https://testing.com");
        List<String> myList = new ArrayList<String>();
        myList.add("Testing");
        myList.add("API");
        myList.add("Serialization");
        ap.setTypes(myList);
        Location l = new Location();
        l.setLat(33.123123);
        l.setLng(-44.456456);
        ap.setLocation(l);

        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(ap)
                .when().log().all().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .extract().response().asString();
    }
}
