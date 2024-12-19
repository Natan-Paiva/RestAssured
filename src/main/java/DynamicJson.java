import files.ReusableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class DynamicJson {
    @Test(dataProvider = "BooksData")
    public void addBook(String aisle, String isbn){
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(payload.AddBook(aisle, isbn))
                .when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = ReusableMethods.rawToJson(response);
        String id = js.get("ID");
        System.out.println(id);

        //delete boobk
        given().header("Content-Type", "application/json")
                .body("{\n" +
                        "\"ID\":\""+id+"\"\n" +
                        "}\n")
                .when().post("Library/DeleteBook.php")
                .then().assertThat().log().all().statusCode(200).
                body("msg", equalTo("book is successfully deleted"));
    }
    @DataProvider(name="BooksData")
    public Object[][] getData(){
        return new Object[][] {{"12312", "dafasdf"}, {"34232", "asdfioasd"}, {"00980", "coiuxvo"}};
    }
}