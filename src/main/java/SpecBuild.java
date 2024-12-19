import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuild {
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

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();
        ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();

        RequestSpecification res = given().spec(req)
                .body(ap);

        Response response = res.when().post("maps/api/place/add/json")
                .then().spec(resspec)
                .extract().response();

        String responseStr = response.asString();
        System.out.println(responseStr);
    }
}
