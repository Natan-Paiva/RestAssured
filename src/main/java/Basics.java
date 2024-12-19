import files.ReusableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) throws IOException {
        //validate if Add Place API is working as expected
        //given - all input details
        //when - submit the API / resource, http method
        //then - validate the response
        //Add Place -> Update Place with New Address -> Get Place to validate new address
        //content of the file to String -> content of file can convert into Byte -> Byte date to String
        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                //.body(payload.AddPlace())
                .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Natan\\Documents\\Base QA\\addPlace.json"))))
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response); //for parsing json
        String placeId = js.getString("place_id");
        System.out.println(placeId);

        // Update Place
        String newAddress = "dr barcelos, canoas";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payload.UpdatePlace(placeId, newAddress))
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

        // Get Place
        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().statusCode(200).extract().response().asString();
        JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress, newAddress);
    }
}
