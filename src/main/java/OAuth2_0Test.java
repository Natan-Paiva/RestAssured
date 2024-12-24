import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import pojo.Course;
import pojo.GetCourse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuth2_0Test {
    public static void main(String[] args) throws InterruptedException {
        //to test this file, you must access the link below, login with your credent
        // ials, then copy the resulting url and paste it on currentUrl variable

        String currentUrl = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AanRRruyeuoClIzQ-l2MB-BET8-wWgAQDWWeCulnxWW2r_qCDU6gqUDy3oB-Xe8T5Z8wmQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=consent";

        String partielCode = currentUrl.split("code=")[1];
        String code = partielCode.split("&scope")[0];

        String accessTokenRes = given().urlEncodingEnabled(false)
                .queryParam("code", code)
                .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("grant_type", "authorization_code")
                .queryParams("state", "verifyfjdss")
                .queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token")
                .then().extract().response().asString();

        JsonPath js = new JsonPath(accessTokenRes);
        String accessToken = js.getString("access_token");

        String response = given().queryParam("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/getCourse.php").asString();
        System.out.println(response);
    }
}