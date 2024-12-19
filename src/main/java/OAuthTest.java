import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import pojo.Course;
import pojo.GetCourse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class OAuthTest {
    public static void main(String[] args){
        String[] courseTitle = {"Selenium Webdriver Java", "Cypress", "Protractor"};
        RestAssured.baseURI = "https://rahulshettyacademy.com/oauthapi/";
        String response = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().log().all()
                .post("oauth2/resourceOwner/token")
                .then().extract().response().asString();

        JsonPath js = new JsonPath(response);
        String token = js.getString("access_token");
        System.out.println(token);

        GetCourse gc = given().queryParam("access_token", token)
                .when().log().all()
                .get("getCourseDetails").as(GetCourse.class);

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());
        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
        List<Course> apiCourses = gc.getCourses().getApi();
        List<Course> webAutomationCourses = gc.getCourses().getWebAutomation();
        List<Course> mobileCourses = gc.getCourses().getMobile();

        for(int i=0; i<apiCourses.size(); i++){
            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
                System.out.println(apiCourses.get(i).getPrice());
            }
        }
        // all title from web automation courses (class assignment)
        ArrayList<String> a = new ArrayList<String>();
        for(int i=0; i<webAutomationCourses.size(); i++){
            //System.out.println(webAutomationCourses.get(i).getCourseTitle()); - My solution
            a.add(webAutomationCourses.get(i).getCourseTitle());
        }
        List<String> expectedList = Arrays.asList(courseTitle);
        Assert.assertTrue(a.equals(expectedList));

        //sum of all courses price (exploring possibilities)
        int total = 0;
        for(int i=0; i<apiCourses.size(); i++){
            total += Integer.parseInt(apiCourses.get(i).getPrice());
        }
        for(int i=0; i<webAutomationCourses.size(); i++){
            total += Integer.parseInt(webAutomationCourses.get(i).getPrice());
        }
        for(int i=0; i<mobileCourses.size(); i++){
            total += Integer.parseInt(mobileCourses.get(i).getPrice());
        }
        System.out.println(total);

        //System.out.println(response2);
    }
}
