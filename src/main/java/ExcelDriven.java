import files.ReusableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ExcelDriven {
    @Test
    public void addBook() throws IOException {
        DataDriven dd = new DataDriven();
        ArrayList<String> data = dd.getData("RestAddBook");

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", data.get(1));
        map.put("isbn", data.get(2));
        map.put("aisle", data.get(3));
        map.put("author", data.get(4));

        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(map)
                .when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = ReusableMethods.rawToJson(response);
        String id = js.get("ID");
        System.out.println(id);
    }
}
